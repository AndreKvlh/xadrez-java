package com.xadrez.project.xadrez_java.peca.rei;

import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;
import com.xadrez.project.xadrez_java.jogador.Jogador;

public class Rei extends Peca {
	public Rei(char representacao, String posicao, Jogador jogadorResp) {
		super(representacao, posicao, jogadorResp);
		this.direcoes = new int[][] {{0,-1},{1,-1},{1,0},{1,1},{0,1},{-1,1},{-1,0},{-1,-1}};
	}
	
	//Sobreposição do método que calcula as possibilidades de movimento
	@Override
	public void calcularPossibilidades(Tabuleiro tabuleiro) {
		if(!getPosDeMovimento().isEmpty()) getPosDeMovimento().clear();
		int[] coord = tabuleiro.posicaoEmCoord(getPosicao());
		for(int[] direcoes : this.direcoes) {
			int[] novaPos = {coord[0] + direcoes[0], coord[1] + direcoes[1]};
			if (novaPos[0] > 7 || novaPos[0] < 0 || novaPos[1] > 7 || novaPos[1] < 0) continue;
			if (tabuleiro.getPecaNoTabuleiro(novaPos[0], novaPos[1]) != null) {
				if (this.getJogadorResp() == tabuleiro.getPecaNoTabuleiro(novaPos[0], novaPos[1]).getJogadorResp()) continue;
				getPosDeMovimento().add(tabuleiro.coordEmPosicao(novaPos[0], novaPos[1]));
				continue;
			}
			getPosDeMovimento().add(tabuleiro.coordEmPosicao(novaPos[0], novaPos[1]));
		}
	}

	@Override
	public Peca copiar() {
		return new Rei(this.representacao, this.posicao, this.jogadorResp);
	}
}
