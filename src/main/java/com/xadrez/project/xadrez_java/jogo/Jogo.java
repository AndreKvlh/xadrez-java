package com.xadrez.project.xadrez_java.jogo;

import java.util.Scanner;

import com.xadrez.project.xadrez_java.acoes.Jogada;
import com.xadrez.project.xadrez_java.acoes.Movimento;
import com.xadrez.project.xadrez_java.alertas.EmXequeException;
import com.xadrez.project.xadrez_java.alertas.FimDeJogoException;
import com.xadrez.project.xadrez_java.historico.Historico;
import com.xadrez.project.xadrez_java.jogador.Jogador;
import com.xadrez.project.xadrez_java.jogador.JogadorHumano;
import com.xadrez.project.xadrez_java.jogador.JogadorIA;
import com.xadrez.project.xadrez_java.peca.CorPeca;
import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.peca.bispo.Bispo;
import com.xadrez.project.xadrez_java.peca.cavalo.Cavalo;
import com.xadrez.project.xadrez_java.peca.peao.Peao;
import com.xadrez.project.xadrez_java.peca.rainha.Rainha;
import com.xadrez.project.xadrez_java.peca.rei.Rei;
import com.xadrez.project.xadrez_java.peca.torre.Torre;
import com.xadrez.project.xadrez_java.regras.Validador;
import com.xadrez.project.xadrez_java.tabuleiro.Coluna;
import com.xadrez.project.xadrez_java.tabuleiro.Linha;
import com.xadrez.project.xadrez_java.tabuleiro.Posicao;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;

public class Jogo {
	private final Jogador[] jogadores;
	private final Tabuleiro tabuleiro;
	private final Movimento movimento;
	private final Validador validador;
	private final Scanner leitor;
	private final Historico historico;
	
	//Atributo que controla o estado do jogo
	private boolean jogoAtivo;
	
	private Status statusJogo;
	
	private int jogadorAtual;
	
	//Atributo que controla o turno atual do jogo
	private int turno = 1;
	
	public Jogo() {
		this.validador = new Validador();
		this.leitor = new Scanner(System.in);
		this.jogadores = new Jogador[] {new JogadorHumano(0, this.leitor), new JogadorIA(1)};
		this.tabuleiro = new Tabuleiro(this.jogadores[0],this.jogadores[1]);
		this.jogoAtivo = false;
		this.historico = new Historico();
		this.movimento = new Movimento(this.tabuleiro, this.historico);
	}
	
	//Método que irá inicializar o tabuleiro de cada jogador, atribuindo 
	//para cada um respectivamente as suas peças
	public void posInicialPecas(Jogador jogador, Tabuleiro tabuleiro) {
		//Variáveis que irão ser alteradas conforme o jogador que as chama
		int linhaPeoes = jogador.getJogador() == 0 ? 6 : 1;
		int linhaPecas = jogador.getJogador() == 0 ? 7 : 0;
		CorPeca corPecasJogador = jogador.getJogador() == 0 ? CorPeca.BRANCA : CorPeca.PRETA;
		
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
	public void promoverPeao(Peca peao, Tabuleiro tabuleiro) {
		//String agrupando todas as possibilidades de opções
		String opcoesPromo = "TtCcBbQq";
		
		//Variável que irá guardar a opção escolhida
		char op;
		System.out.print("Escolha uma das peças para promover o peão: T,C,B,Q ");
		
		//Loop que irá averiguar que se o jogador colocou a opção correta
		do {
			op = leitor.next().charAt(0);
			if(opcoesPromo.indexOf(op) != -1) {
				break;
			}
			System.out.println("Escolha inválida, tente novamente!");
		} while (true);
		
		//Pesquisa responsável para verificar e achar a peça dentre as peças que o jogador
		//possui
		int indice = peao.getJogadorResp().getPecasAtuais().indexOf(peao);
		if (indice != -1) {
			Peca novaPeca = switch (op) {
				case 'T','t' -> new Torre(peao.getCor(), peao.getPosicaoAtual(), peao.getJogadorResp());
				case 'C','c' -> new Cavalo(peao.getCor(), peao.getPosicaoAtual(), peao.getJogadorResp());
				case 'B','b' -> new Bispo(peao.getCor(), peao.getPosicaoAtual(), peao.getJogadorResp());
				default -> new Rainha(peao.getCor(), peao.getPosicaoAtual(), peao.getJogadorResp());
			};
			
			//Alterar a peça e remover o peão
			peao.getJogadorResp().getPecasAtuais().set(indice, novaPeca);
			tabuleiro.inserirPeca(novaPeca);
		}
	}
	
	public void iniciarJogo() {
		for (Jogador jogador : this.jogadores) {
			this.posInicialPecas(jogador, this.tabuleiro);
		}
		this.setJogoAtivo(true);
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
		this.jogadorAtual = 0;
		this.iniciarJogo();
	}
	
	public void darXequeMate(Jogador jogador) {
		Jogador jogadorVenc = jogador.getJogador() == 0 ? this.jogadores[1] : this.jogadores[0];
		System.out.printf("XEQUE-MATE, vitória do jogador %d", jogadorVenc.getJogador() + 1);
		this.jogoAtivo = false;
	}
	
	//Método responsável por controlar o gameloop de cada um dos jogadores
	public void encerrarJogo(Jogador jogador) {
		Jogador jogadorAdv = jogador.getJogador() == 0 ? this.jogadores[1] : this.jogadores[0];
		if(this.validador.checarXequeMate(jogadorAdv, this.tabuleiro, this.movimento)) {
			this.statusJogo = Status.XEQUE_MATE;
		}
		if(this.validador.checarAfogamento(jogadorAdv, this.tabuleiro, this.movimento)) {
			this.statusJogo = Status.AFOGAMENTO;
		}
		if(this.validador.checarPecasInsuficientes(jogadorAdv, this.tabuleiro, this.movimento)
		|| this.validador.checarRepeticao(jogadorAdv, this.tabuleiro, this.historico)) {
			this.statusJogo = Status.EMPATE;
		}
	}
	
	public Jogada JogadaDaIA() {
		Jogador jogador = this.jogadores[1];
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
		Peca pecaCapturada = this.tabuleiro.getPeca(jogada.destino());
		if(pecaCapturada != null) this.executarCaptura(pecaSelecionada, pecaCapturada);
		this.movimento.executarMovimento(pecaSelecionada, jogada.inicio(), jogada.destino(), this.tabuleiro);
		if(this.validador.checarPromocao(pecaSelecionada)) this.promoverPeao(pecaSelecionada, this.tabuleiro);
		
		//Salvar turno no histórico
		this.historico.salvarTurno(this.turno, this.tabuleiro, pecaSelecionada.getJogadorResp(), pecaSelecionada, jogada);
		
		if(pecaSelecionada.isPosInicial()) pecaSelecionada.setPosInicial(false);
			
		this.encerrarJogo(jogador);
		
		this.turno++;
	}
	
	public boolean isJogoAtivo() {
		return jogoAtivo;
	}
	
	public void setJogoAtivo(boolean jogoAtivo) {
		this.jogoAtivo = jogoAtivo;
	}

	public Tabuleiro getTabuleiro() {
		return tabuleiro;
	}
	
	public Movimento getMovimento() {
		return movimento;
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
}
