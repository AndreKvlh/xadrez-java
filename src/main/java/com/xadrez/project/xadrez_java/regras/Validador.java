package com.xadrez.project.xadrez_java.regras;

import com.xadrez.project.xadrez_java.jogador.Jogador;
import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.peca.TipoPeca;
import com.xadrez.project.xadrez_java.peca.peao.Peao;
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
		Jogador jogadorAdv = jogador.getJogador() == 0 ? tabuleiro.getJogadores()[1] : tabuleiro.getJogadores()[1];
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
}
