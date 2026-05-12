package com.xadrez.project.xadrez_java.regras;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.xadrez.project.xadrez_java.acoes.Jogada;
import com.xadrez.project.xadrez_java.acoes.Movimento;
import com.xadrez.project.xadrez_java.historico.Historico;
import com.xadrez.project.xadrez_java.historico.Turno;
import com.xadrez.project.xadrez_java.jogador.Jogador;
import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.peca.TipoPeca;
import com.xadrez.project.xadrez_java.peca.bispo.Bispo;
import com.xadrez.project.xadrez_java.peca.cavalo.Cavalo;
import com.xadrez.project.xadrez_java.peca.peao.Peao;
import com.xadrez.project.xadrez_java.peca.rei.Rei;
import com.xadrez.project.xadrez_java.tabuleiro.Coluna;
import com.xadrez.project.xadrez_java.tabuleiro.Linha;
import com.xadrez.project.xadrez_java.tabuleiro.Posicao;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;

public class Validador {
	//Checar se a opção está fora dos limites (OutOfBounds)
	public boolean checarOutOfBounds (String s) {
		try {
			Posicao.converterStringEmPos(s);
		} catch (IllegalArgumentException e) {
			System.out.println("Posição inválida! Tente novamente");
			return false;
		}
		return true;
	}
	
	//Checar se a peça selecionada é válida (não pode ser um espaço vazio
	//e nem uma peça do oponente)
	public boolean checarPecaValida (Peca peca, Jogador jogador) {
		if(peca == null) {
			System.out.println("Casa vazia! Tente novamente");
			return false;
		}
		Jogador jogadorResp = peca.getJogadorResp();
		if(!jogadorResp.equals(jogador)) {
			System.out.println("Essa não é a sua peça! Tente novamente");
			return false;
		}
		return true;
	}
	
	//Checar se o peão está do outro lado do tabuleiro para ser promovido
	public boolean checarPromocao (Peca peca) {
		if (!(peca instanceof Peao)) return false;
		int dy = peca.getPosicaoAtual().getCoord()[1];
		if (dy != 7 && dy != 0) return false; 
		return true;
	}
	
	//Checar se o rei está em posição de xeque
	public boolean checarXeque (Jogador jogador, Tabuleiro tabuleiro) {
		Peca reiJogador = null;
		Jogador jogadorAdv = jogador.getJogador() == 0 ? tabuleiro.getJogadores()[1] : tabuleiro.getJogadores()[0];
		for(Peca peca : jogador.getPecasAtuais()) {
			if (peca.getTipo().equals(TipoPeca.REI)) {
				reiJogador = peca;
				break;
			}
		}
		for(Peca peca : jogadorAdv.getPecasAtuais()) {
			peca.calcularPossibilidades(tabuleiro);
			if (peca.getPosDeMovimento().contains(reiJogador.getPosicaoAtual())) {
				System.out.println("Rei está em xeque!");
				return true;
			}
		}
		return false;
	}
	
	//Checar se há então xeque-mate
	public boolean checarXequeMate(Jogador jogador, Tabuleiro tabuleiro, Movimento movimento) {
		if (!this.checarXeque(jogador, tabuleiro)) return false;
		
		for(Peca peca : jogador.getPecasAtuais()) {
			peca.calcularPossibilidades(tabuleiro);
			for(Posicao posicao : peca.getPosDeMovimento()) {
				Jogada jogada = new Jogada(peca.getPosicaoAtual(), posicao);
				if(movimento.validarJogada(jogador, jogada)) return false;
			}
		}
		return true;
	}
	
	//Checar se jogo entrou em um stalemate
	public boolean checarAfogamento(Jogador jogador, Tabuleiro tabuleiro, Movimento movimento) {
		if (this.checarXeque(jogador, tabuleiro)) return false;
		for(Peca peca : jogador.getPecasAtuais()) {
			peca.calcularPossibilidades(tabuleiro);
			for(Posicao posicao : peca.getPosDeMovimento()) {
				Jogada jogada = new Jogada(peca.getPosicaoAtual(), posicao);
				if(movimento.validarJogada(jogador, jogada)) return false;
			}
		}
		return true;
	}
	
	//Checar se há peças suficientes para continuar o jogo
	//TO DO: possivelmente isso gere um bug caso o jogador tenha só o rei mas o outro ainda
	//tenha peças suficientes. Vou pensar nisso a posteriori
	public boolean checarPecasInsuficientes(Jogador jogador, Tabuleiro tabuleiro, Movimento movimento) {
		if (this.checarXeque(jogador, tabuleiro)) return false;
		ArrayList<Peca> pecasRestantes = new ArrayList<>();
		
		for(Peca peca : jogador.getPecasAtuais()) {
			if(!(peca instanceof Rei)) pecasRestantes.add(peca);	
		}
		
		if(pecasRestantes.size() == 0) return true;
		
		if(pecasRestantes.size() == 1) {
			Peca peca = pecasRestantes.get(0);
			return (peca instanceof Bispo || peca instanceof Cavalo);
		}
		return false;
	}
	
	//Checar se os últimos movimentos foram repetidos. É necessário que haja repetição das
	//três últimas jogadas a fim de atestar empate
	public boolean checarRepeticao(Jogador jogador, Tabuleiro tabuleiro, Historico historico) {
		Turno[] ultimosTurnos = historico.getUltimosSeisTurnos();
	
		if(ultimosTurnos[5] == null) return false;
		
		Set<TipoPeca> pecasJ1 = new HashSet<>();
		Set<TipoPeca> pecasJ2 = new HashSet<>();
		
		Set<Posicao> jogadasJ1 = new HashSet<>();
		Set<Posicao> jogadasJ2 = new HashSet<>();
		//TO DO: ajustar verificação conforme posição e não conforme string
		for(int i = 0; i < ultimosTurnos.length; i++) {
			if(i % 2 == 0) {
				pecasJ1.add(ultimosTurnos[i].peca().getTipo());
				jogadasJ1.add(ultimosTurnos[i].jogada().inicio());
				jogadasJ1.add(ultimosTurnos[i].jogada().destino());
			} else {
				pecasJ2.add(ultimosTurnos[i].peca().getTipo());
				jogadasJ2.add(ultimosTurnos[i].jogada().inicio());
				jogadasJ2.add(ultimosTurnos[i].jogada().destino());
			}
		}
		
		if(pecasJ1.size() > 1 || pecasJ2.size() > 1) return false;
		
		if(jogadasJ1.size() > 2 || jogadasJ2.size() > 2) return false;
		
		return true;
	}
	
	//Checar se o movimento em questão é de roque
	public boolean checarRoque(Peca peca, Posicao posAntiga, Posicao posNova) {
		if(!(peca instanceof Rei)) return false;
		if(!peca.isPosInicial()) return false;
		if(Math.abs(posAntiga.x() - posNova.x()) != 2) return false;
		return true;
	}
	
	//Checar se é possível realizar o En Passant
	public boolean checarEnPassant(Peca peca, Tabuleiro tabuleiro, Historico historico) {
		if (!(peca instanceof Peao)) return false;
		if (historico == null || historico.getUltimoTurno() == null) return false;
		
		
		int x = peca.getPosicaoAtual().x();
		int y = peca.getPosicaoAtual().y();
		
		List<Peca> pecasLaterais = new ArrayList<>();
		for (int i = -1; i < 2; i++) {
			if (i == 0) continue;
			try {
				Peca pecaAtual = tabuleiro.getPeca(new Posicao(Coluna.deIndice(x + i), Linha.deIndice(y)));
				if(pecaAtual != null) pecasLaterais.add(pecaAtual);
			} catch (IllegalArgumentException e){
				continue;
			}
		}
		
		//Verificação das laterais do peão
		if (pecasLaterais.stream().allMatch(p -> p == null)) return false;
		
		if (pecasLaterais.stream().allMatch(p -> !(p instanceof Peao))) return false;
		
		//Coleta do último turno
		Turno ultimoTurno = historico.getUltimoTurno();
		
		if(!(ultimoTurno.peca() instanceof Peao)) return false;
		if(ultimoTurno.peca().getCor().equals(peca.getCor())) return false;
		
		Posicao posInicioTurno = ultimoTurno.jogada().inicio();
		Posicao posDestinoTurno = ultimoTurno.jogada().destino();
		
		if(pecasLaterais.stream().allMatch(p -> !posDestinoTurno.posicao().equals(p.getPosicaoAtual().posicao()))) return false;
		
		//Caso seja identificado, iremos ver se houve passo duplo
		if(Math.abs(posInicioTurno.y() - posDestinoTurno.y()) != 2) return false;
		
		return true;
	}
 }
