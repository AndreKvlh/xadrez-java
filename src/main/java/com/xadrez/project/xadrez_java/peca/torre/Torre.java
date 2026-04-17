package com.xadrez.project.xadrez_java.peca.torre;

import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;
import com.xadrez.project.xadrez_java.jogador.Jogador;

public class Torre extends Peca {
	public Torre(char representacao, String posicao, Jogador jogadorResp) {
		super(representacao, posicao, jogadorResp);
		this.direcoes = new int[][] {{0,-1},{1,0},{0,1},{-1,0}};
	}
	
	//Sobreposição do método que calcula as possibilidades de movimento
	@Override
	public void calcularPossibilidades(Tabuleiro tabuleiro) {
		if(!getPosDeMovimento().isEmpty()) getPosDeMovimento().clear();
		int[] coord = tabuleiro.posicaoEmCoord(getPosicao());
		for(int[] direcoes : this.direcoes) {
			for (int i = 1; i < 8; i++) {
				int[] novaPos = {coord[0] + (direcoes[0] * i), coord[1] + (direcoes[1] * i)};
				if (novaPos[0] > 7 || novaPos[0] < 0 || novaPos[1] > 7 || novaPos[1] < 0) break;
				if (tabuleiro.getPecaNoTabuleiro(novaPos[0], novaPos[1]) != null) {
					if (this.getJogadorResp() == tabuleiro.getPecaNoTabuleiro(novaPos[0], novaPos[1]).getJogadorResp()) break;
					getPosDeMovimento().add(tabuleiro.coordEmPosicao(novaPos[0], novaPos[1]));
					break;
				}
				getPosDeMovimento().add(tabuleiro.coordEmPosicao(novaPos[0], novaPos[1]));
			}
		}
		if (getPosDeMovimento().isEmpty()) System.out.println("Sem possibilidades de movimento");
		getPosDeMovimento().forEach(System.out::println);
	}
}
