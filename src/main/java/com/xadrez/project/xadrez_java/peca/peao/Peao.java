package com.xadrez.project.xadrez_java.peca.peao;

import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;
import com.xadrez.project.xadrez_java.jogador.Jogador;

public class Peao extends Peca{
	public Peao(char representacao, String posicao, Jogador jogadorResp) {
		super(representacao, posicao, jogadorResp);
		this.direcoes = jogadorResp.getJogador() == 0 ? new int[][]{{0,-1}} : new int[][]{{0,1}};
	}
	
	
	//Sobreposição do método que calcula as possibilidades de movimento
	@Override
	public void calcularPossibilidades(Tabuleiro tabuleiro) {
		if(!getPosDeMovimento().isEmpty()) getPosDeMovimento().clear();
		int[] coord = tabuleiro.posicaoEmCoord(getPosicao());
		System.out.printf("%d,%d",coord[0],coord[1]);
		int[] novaPos = {coord[0] + this.direcoes[0][0], coord[1] + this.direcoes[0][1]};
		System.out.printf("%d,%d",novaPos[0],novaPos[1]);
		if (novaPos[0] > 7 || novaPos[1] > 7) return;
		if (tabuleiro.getPecaNoTabuleiro(novaPos[0], novaPos[1]) != null) {
			if (this.getJogadorResp() == tabuleiro.getPecaNoTabuleiro(novaPos[0], novaPos[1]).getJogadorResp()) return;
			getPosDeMovimento().add(tabuleiro.coordEmPosicao(novaPos[0], novaPos[1]));
			return;
		}
		getPosDeMovimento().add(tabuleiro.coordEmPosicao(novaPos[0], novaPos[1]));
	}
}	
