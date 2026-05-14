package com.xadrez.project.xadrez_java.peca;

import com.xadrez.project.xadrez_java.tabuleiro.Posicao;
import com.xadrez.project.xadrez_java.jogador.Jogador;

public class Cavalo extends Peca {
	public Cavalo(CorPeca cor, Posicao posicaoAtual, Jogador jogadorResp) {
		super(cor, posicaoAtual, jogadorResp);
		this.direcoes = new int[][]{{-1,-2},{1,-2},{2,-1},{2,1},{1,2},{-1,2},{-2,1},{-2,-1}};
		this.limMovimento = true;
		this.unicode = this.getCor() == CorPeca.BRANCA ? "\u2658" : "\u265E";
	}

	@Override
	public Peca copiar() {
		return new Cavalo(this.cor, this.posicaoAtual, this.jogadorResp);
	}
}
