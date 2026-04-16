package com.xadrez.project.xadrez_java.peca.rei;

import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;

public class Rei extends Peca {
	public Rei(char representacao, String posicao, int jogadorResp) {
		super(representacao, posicao, jogadorResp);
		this.direcoes = new int[][] {{0,-1},{1,-1},{1,0},{1,1},{0,1},{-1,1},{-1,0},{-1,-1}};
	}
	
	@Override
	public void calcularPossibilidades(Tabuleiro tabuleiro) {
		int[] coord = tabuleiro.posicaoEmCoord(getPosicao());
		for(int[] direcoes : this.direcoes) {
			int[] novaPos = {coord[0] + direcoes[0], coord[1] + direcoes[1]};
			if (novaPos[0] > 7 || novaPos[0] < 0 || novaPos[1] > 7 || novaPos[1] < 0) break;
			if (tabuleiro.getPecaNoTabuleiro(novaPos[0], novaPos[1]) != null) {
				if (this.getJogadorResp() == tabuleiro.getPecaNoTabuleiro(novaPos[0], novaPos[1]).getJogadorResp()) break;
				getPosDeMovimento().add(tabuleiro.coordEmPosicao(novaPos[0], novaPos[1]));
				break;
			}
			getPosDeMovimento().add(tabuleiro.coordEmPosicao(novaPos[0], novaPos[1]));
		}
		if (getPosDeMovimento().isEmpty()) System.out.printf("Sem possibilidades de movimento para %c", getRepresentacao());
		getPosDeMovimento().forEach(System.out::println);
	}
}
