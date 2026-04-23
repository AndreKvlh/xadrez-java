package com.xadrez.project.xadrez_java.computador;

import java.util.ArrayList;

import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.tabuleiro.Posicao;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;

public class Computador {
	//Método para selecionar uma peça que pode se mover
	public Peca selecionarPeca(ArrayList<Peca> pecas, Tabuleiro tabuleiro) {
		ArrayList<Peca> pecasPrioritarias = new ArrayList<>();
		ArrayList<Peca> pecasQueTemMovimento = new ArrayList<>();
		Peca pecaSelecionada;
		for(Peca peca : pecas) {
			peca.calcularPossibilidades(tabuleiro);
			if(peca.getPosDeMovimento().size() <= 0) continue;
			for(Posicao pos : peca.getPosDeMovimento()) {
				if(tabuleiro.getPeca(pos) != null) {
					pecasPrioritarias.add(peca);
					continue;
				}
			}
			pecasQueTemMovimento.add(peca);
		}
		int aleatorio;
		if(pecasPrioritarias.size() != 0) {	
			aleatorio = (int) (Math.random() * pecasPrioritarias.size());
			pecaSelecionada = pecasPrioritarias.get(aleatorio);
			return pecaSelecionada;
		}
		aleatorio = (int) (Math.random() * pecasQueTemMovimento.size());
		pecaSelecionada = pecasQueTemMovimento.get(aleatorio);
		return pecaSelecionada;
	}
	
	//Método para selecionar a jogada com a peça escolhida
	public Posicao moverPeca(Peca peca, Tabuleiro tabuleiro) {
		for(Posicao pos : peca.getPosDeMovimento()) {
			if(tabuleiro.getPeca(pos) != null) {
				return pos;
			}
		}
		int aleatorio = (int) (Math.random() * peca.getPosDeMovimento().size());
		Posicao novaPosicao = peca.getPosDeMovimento().get(aleatorio);
		return novaPosicao;
	}
}
