package com.xadrez.project.xadrez_java.peca.peao;

import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;

public class Peao extends Peca{
	public Peao(char representacao, String posicao, int jogadorResp) {
		super(representacao, posicao, jogadorResp);
		this.direcoes = new int[][]{{0,-1}};
	}
	
	@Override
	public void calcularPossibilidades(Tabuleiro tabuleiro) {
		int[] coord = tabuleiro.posicaoEmCoord(getPosicao());
		int[] novaPos = {coord[0] + this.direcoes[0][0], coord[1] + this.direcoes[0][1]};
		if (novaPos[0] > 7 || novaPos[1] > 7) return;
		if (tabuleiro.getPecaNoTabuleiro(novaPos[0], novaPos[1]) != null) {
			if (this.getJogadorResp() == tabuleiro.getPecaNoTabuleiro(novaPos[0], novaPos[1]).getJogadorResp()) return;
			getPosDeMovimento().add(tabuleiro.coordEmPosicao(novaPos[0], novaPos[1]));
			return;
		}
		getPosDeMovimento().add(tabuleiro.coordEmPosicao(novaPos[0], novaPos[1]));
	}
}	
