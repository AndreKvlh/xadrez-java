package com.xadrez.project.xadrez_java.peca;

import com.xadrez.project.xadrez_java.tabuleiro.Posicao;
import com.xadrez.project.xadrez_java.jogador.Jogador;

public class Torre extends Peca {
	public Torre(CorPeca cor, Posicao posicaoAtual, Jogador jogadorResp) {
		super(cor, posicaoAtual, jogadorResp);
		this.direcoes = new int[][] {{0,-1},{1,0},{0,1},{-1,0}};
		this.limMovimento = false;
		this.unicode = this.getCor() == CorPeca.BRANCA ? "\u2656" : "\u265C";
	}

	@Override
	public Peca copiar() {
		return new Torre(this.cor, this.posicaoAtual, this.jogadorResp);
	}
}
