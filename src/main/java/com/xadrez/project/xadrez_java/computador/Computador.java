package com.xadrez.project.xadrez_java.computador;

import java.util.ArrayList;

import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;

public class Computador {
	//Método para selecionar uma peça que pode se mover
	public String selecionarPeca(ArrayList<Peca> pecas, Tabuleiro tabuleiro) {
		ArrayList<Peca> pecasPrioritarias = new ArrayList<>();
		ArrayList<Peca> pecasQueTemMovimento = new ArrayList<>();
		String posicaoPeca = "";
		for(Peca peca : pecas) {
			peca.calcularPossibilidades(tabuleiro);
			if(peca.getPosDeMovimento().size() <= 0) continue;
			for(String pos : peca.getPosDeMovimento()) {
				int[] coord = tabuleiro.posicaoEmCoord(pos);
				if(tabuleiro.getPecaNoTabuleiro(coord[0], coord[1]) != null) {
					pecasPrioritarias.add(peca);
					continue;
				}
			}
			pecasQueTemMovimento.add(peca);
		}
		int aleatorio;
		Peca pecaAleatoria = null;
		if(pecasPrioritarias.size() != 0) {	
			aleatorio = (int) (Math.random() * pecasPrioritarias.size());
			pecaAleatoria = pecasPrioritarias.get(aleatorio);
			return pecaAleatoria.getPosicao();
		}
		aleatorio = (int) (Math.random() * pecasQueTemMovimento.size());
		pecaAleatoria = pecasQueTemMovimento.get(aleatorio);
		return pecaAleatoria.getPosicao();
	}
	
	//Método para selecionar a jogada com a peça escolhida
	public String moverPeca(Peca peca, Tabuleiro tabuleiro) {
		String novaPosicao = "";
		for(String pos : peca.getPosDeMovimento()) {
			int[] coord = tabuleiro.posicaoEmCoord(pos);
			if(tabuleiro.getPecaNoTabuleiro(coord[0],coord[1]) != null) {
				novaPosicao = pos;
				return novaPosicao;
			}
		}
		int aleatorio = (int) (Math.random() * peca.getPosDeMovimento().size());
		novaPosicao = peca.getPosDeMovimento().get(aleatorio);
		return novaPosicao;
	}
}
