package com.xadrez.project.xadrez_java.computador;

import java.util.ArrayList;

import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;

public class Computador {
	//Método para selecionar uma peça que pode se mover
	public String selecionarPeca(ArrayList<Peca> pecas, Tabuleiro tabuleiro) {
		String posicaoPeca = "";
		for(Peca peca : pecas) {
			peca.calcularPossibilidades(tabuleiro);
			if(peca.getPosDeMovimento().size() == 0) continue;
			posicaoPeca = peca.getPosicao();
			System.out.println("Cheguei no fim do loop");
			break;
		}
		return posicaoPeca;
	}
	
	//Método para selecionar a jogada com a peça escolhida
	public String moverPeca(Peca peca, Tabuleiro tabuleiro) {
		String novaPosicao = "";
		for(String pos : peca.getPosDeMovimento()) {
			int[] coord = tabuleiro.posicaoEmCoord(pos);
			if(tabuleiro.getPecaNoTabuleiro(coord[0],coord[1]) != null) {
				novaPosicao = pos;
				break;
			}
		}
		int aleatorio = (int) (Math.random() * peca.getPosDeMovimento().size());
		novaPosicao = peca.getPosDeMovimento().get(aleatorio);
		return novaPosicao;
	}
}
