package com.xadrez.project.xadrez_java.jogo;

import com.xadrez.project.xadrez_java.acoes.*;
import com.xadrez.project.xadrez_java.alertas.EmXequeException;
import com.xadrez.project.xadrez_java.historico.Historico;
import com.xadrez.project.xadrez_java.jogador.*;
import com.xadrez.project.xadrez_java.peca.*;
import com.xadrez.project.xadrez_java.regras.Validador;
import com.xadrez.project.xadrez_java.tabuleiro.*;

public class Jogo {
	private final Jogador[] jogadores;
	private final Tabuleiro tabuleiro;
	private final Movimento movimento;
	private final Validador validador;
	private final Historico historico;
	
	private Status statusJogo;
	
	private int jogadorAtual;
	
	//Atributo que controla o turno atual do jogo
	private int turno = 1;
	
	public Jogo(Jogador j1, Jogador j2) {
		this.validador = new Validador();
		this.jogadores = new Jogador[] {j1, j2};
		this.tabuleiro = new Tabuleiro(this.jogadores[0],this.jogadores[1]);
		this.historico = new Historico();
		this.movimento = new Movimento(this.tabuleiro, this.historico);
	}
	
	//Método que irá inicializar o tabuleiro de cada jogador, atribuindo 
	//para cada um respectivamente as suas peças
	public void posInicialPecas(Jogador jogador, Tabuleiro tabuleiro) {
		//Variáveis que irão ser alteradas conforme o jogador que as chama
		CorPeca corPecasJogador = jogador.getJogador() == 0 ? CorPeca.BRANCA : CorPeca.PRETA;
		int linhaPeoes = jogador.equals(this.jogadores[0]) ? 6 : 1;
		int linhaPecas = jogador.equals(this.jogadores[0]) ? 7 : 0;
		
		//Variável que abriga os objetos criados das peças
		Peca peca = null;
		
		//Inserindo peões nas suas respectivas linhas
		for (int col = 0; col < 8; col++) {
			Posicao pos = new Posicao(Coluna.deIndice(col), Linha.deIndice(linhaPeoes));
			peca = new Peao(corPecasJogador, pos, jogador);
			jogador.getPecasAtuais().add(peca);
			tabuleiro.inserirPeca(peca);
		}
		
		//Inserindo as demais peças
		for (int col = 0; col < 8; col++) {
			Posicao pos = new Posicao(Coluna.deIndice(col), Linha.deIndice(linhaPecas));
			peca = switch(col) {
				case 0, 7 -> new Torre(corPecasJogador, pos, jogador);
				case 1, 6 -> new Cavalo(corPecasJogador, pos, jogador);
				case 2, 5 -> new Bispo(corPecasJogador, pos, jogador);
				case 4 -> new Rei(corPecasJogador, pos, jogador);
				default -> new Rainha(corPecasJogador, pos, jogador);
			};
			jogador.getPecasAtuais().add(peca);
			tabuleiro.inserirPeca(peca);
		}
	}
	
	public void iniciarJogo() {
		for (Jogador jogador : this.jogadores) {
			this.posInicialPecas(jogador, this.tabuleiro);
		}
		this.jogadorAtual = this.jogadores[0].getJogador() == 0 ? 0 : 1;
		this.statusJogo = Status.EM_ANDAMENTO;
	}
	
	public void reiniciarJogo() {
		for (int linha = 0; linha < 8; linha++) {
			for (int coluna = 0; coluna < 8; coluna++) {
				Posicao pos = new Posicao(Coluna.deIndice(coluna), Linha.deIndice(linha));
				tabuleiro.removerPeca(pos);
			}
		}
		for (Jogador jogador : this.jogadores) {
			jogador.getPecasAtuais().clear();
			jogador.getPecasCapturadas().clear();
		}
		this.iniciarJogo();
	}
	
	public Jogada JogadaDaIA(Jogador jogador) {
		do {
			Jogada jogada = jogador.realizarJogada(tabuleiro);
			if(jogada == null) {
				this.darXequeMate(jogador);
				break;
			}
			if(!this.movimento.validarJogada(jogador, jogada)) continue;
			return jogada;
		} while (true);
		return null;
	}
	
	public void executarJogada(Jogada jogada) {
		Jogador jogador = jogada.pecaSelecionada(tabuleiro).getJogadorResp();
		if (!this.movimento.validarJogada(jogador, jogada) && jogador instanceof JogadorHumano) {
			throw new EmXequeException("Você está em xeque, tente novamente.");
		}
		Peca pecaSelecionada = jogada.pecaSelecionada(this.tabuleiro);
		Peca pecaCapturada = null;
		if(this.validador.checarEnPassant(pecaSelecionada, this.tabuleiro, this.historico)) {
			int dx = jogada.destino().x();
			int dy = jogada.destino().y() - jogada.inicio().y() > 0 ? jogada.destino().y() -1 : jogada.destino().y() + 1;
			Posicao pos = new Posicao(Coluna.deIndice(dx), Linha.deIndice(dy));
			pecaCapturada = this.tabuleiro.getPeca(pos);
		} else pecaCapturada = this.tabuleiro.getPeca(jogada.destino());
		if(pecaCapturada != null) this.executarCaptura(pecaSelecionada, pecaCapturada);
		this.movimento.executarMovimento(pecaSelecionada, jogada.inicio(), jogada.destino(), this.tabuleiro);
		
		//Salvar turno no histórico
		this.historico.salvarTurno(this.turno, this.tabuleiro, pecaSelecionada.getJogadorResp(), pecaSelecionada, jogada);
		if(pecaSelecionada.isPosInicial()) pecaSelecionada.setPosInicial(false);
	}
	
	//Executa a captura da peça, fazendo com que ela seja eliminada do 
	//array de pecasAtuais do jogador e colocado no array de pecasCapturadas
	//do outro
	public void executarCaptura (Peca pecaJogador, Peca pecaAdv) {
		Jogador jogadorAtual = pecaJogador.getJogadorResp();
		Jogador jogadorAdv = pecaAdv.getJogadorResp();
		
		jogadorAdv.getPecasAtuais().remove(pecaAdv);
		jogadorAtual.getPecasCapturadas().add(pecaAdv);
		
		this.tabuleiro.removerPeca(pecaAdv.getPosicaoAtual());
	}
	
	//Método responsável por promover o peão quando este chegar ao fim do tabuleiro
	public void promoverPeao(Peca peao, Tabuleiro tabuleiro, String peca) {
		//Pesquisa responsável para verificar e achar a peça dentre as peças que o jogador
		//possui
		int indice = peao.getJogadorResp().getPecasAtuais().indexOf(peao);
		if (indice != -1) {
			Peca novaPeca = switch (peca) {
				case "Torre" -> new Torre(peao.getCor(), peao.getPosicaoAtual(), peao.getJogadorResp());
				case "Cavalo" -> new Cavalo(peao.getCor(), peao.getPosicaoAtual(), peao.getJogadorResp());
				case "Bispo" -> new Bispo(peao.getCor(), peao.getPosicaoAtual(), peao.getJogadorResp());
				default -> new Rainha(peao.getCor(), peao.getPosicaoAtual(), peao.getJogadorResp());
			};
			
			//Alterar a peça e remover o peão
			peao.getJogadorResp().getPecasAtuais().set(indice, novaPeca);
			tabuleiro.removerPeca(peao.getPosicaoAtual());
			tabuleiro.inserirPeca(novaPeca);
		}
	}
	
	//TODO: Verificar a necessidade deste reforço para o xeque-mate para a IA
	public void darXequeMate(Jogador jogador) {
		this.statusJogo = Status.XEQUE_MATE;
	}
	
	//Método responsável por controlar o gameloop de cada um dos jogadores
	public void encerrarJogo(Jogador jogador) {
		if(this.validador.checarXequeMate(jogador, this.tabuleiro, this.movimento)) {
			this.darXequeMate(jogador);
		}
		if(this.validador.checarAfogamento(jogador, this.tabuleiro, this.movimento)) {
			this.statusJogo = Status.AFOGAMENTO;
		}
		if(this.validador.checarPecasInsuficientes(jogador, this.tabuleiro, this.movimento)
		|| this.validador.checarRepeticao(jogador, this.tabuleiro, this.historico)) {
			this.statusJogo = Status.EMPATE;
		}
	}
	
	public Tabuleiro getTabuleiro() {
		return tabuleiro;
	}
	
	public Movimento getMovimento() {
		return movimento;
	}
	
	public Validador getValidador() {
		return validador;
	}
	
	public Historico getHistorico() {
		return historico;
	}
	
	public Status getStatus() {
		return statusJogo;
	}
	
	public void setJogadorAtual(int jogador) {
		this.jogadorAtual = jogador;
	}
	
	public int getJogadorAtual() {
		return jogadorAtual;
	}
	
	public Jogador getJogador() {
		return jogadores[jogadorAtual];
	}
	
	public int getTurno() {
		return turno;
	}
	
	public void setTurno() {
		this.turno++;
	}
}
