package com.xadrez.project.xadrez_java.regras;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.xadrez.project.xadrez_java.acoes.*;
import com.xadrez.project.xadrez_java.historico.*;
import com.xadrez.project.xadrez_java.jogador.Jogador;
import com.xadrez.project.xadrez_java.peca.*;
import com.xadrez.project.xadrez_java.tabuleiro.*;

public class Validador {
	//Checar se o peão está do outro lado do tabuleiro para ser promovido
	public boolean checarPromocao (Peca peca) {
		if (!(peca instanceof Peao)) return false;
		int dy = peca.getPosicaoAtual().getCoord()[1];
		if (dy != 7 && dy != 0) return false; 
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
	
	//Checar se o rei está em posição de xeque
	public boolean checarXeque (Jogador jogador, Tabuleiro tabuleiro) {
		Peca reiJogador = null;
		Jogador jogadorAdv = jogador.getJogador() == tabuleiro.getJogadores()[0].getJogador() ? tabuleiro.getJogadores()[1] : tabuleiro.getJogadores()[0];
		for(Peca peca : jogador.getPecasAtuais()) {
			if (peca instanceof Rei) {
				reiJogador = peca;
				break;
			}
		}
		for(Peca peca : jogadorAdv.getPecasAtuais()) {
			if (peca.getPosDeMovimento().size() == 0) peca.calcularPossibilidades(tabuleiro);
			if (peca.getPosDeMovimento().contains(reiJogador.getPosicaoAtual())) {
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
		
		Set<Peca> pecasJ1 = new HashSet<>();
		Set<Peca> pecasJ2 = new HashSet<>();
		
		Set<Posicao> jogadasJ1 = new HashSet<>();
		Set<Posicao> jogadasJ2 = new HashSet<>();
		
		for(int i = 0; i < ultimosTurnos.length; i++) {
			Jogada jogada = ultimosTurnos[i].jogada();
			Peca peca = jogada.pecaSelecionada(tabuleiro);
			if(i % 2 == 0) {
				pecasJ1.add(peca);
				jogadasJ1.add(jogada.inicio());
				jogadasJ1.add(jogada.destino());
			} else {
				pecasJ2.add(peca);
				jogadasJ2.add(jogada.inicio());
				jogadasJ2.add(jogada.destino());
			}
		}
		
		if(pecasJ1.size() > 1 || pecasJ2.size() > 1) return false;
		
		if(jogadasJ1.size() > 2 || jogadasJ2.size() > 2) return false;
		
		return true;
	}
 }
