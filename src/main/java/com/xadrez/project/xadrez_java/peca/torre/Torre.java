package com.xadrez.project.xadrez_java.peca.torre;

import com.xadrez.project.xadrez_java.peca.CorPeca;
import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.peca.TipoPeca;
import com.xadrez.project.xadrez_java.tabuleiro.Posicao;
import com.xadrez.project.xadrez_java.jogador.Jogador;

public class Torre extends Peca {
	public Torre(CorPeca cor, Posicao posicaoAtual, Jogador jogadorResp) {
		super(cor, posicaoAtual, jogadorResp);
		this.setTipo(TipoPeca.TORRE);
		this.direcoes = new int[][] {{0,-1},{1,0},{0,1},{-1,0}};
		this.limMovimento = false;
	}

	@Override
	public Peca copiar() {
		return new Torre(this.cor, this.posicaoAtual, this.jogadorResp);
	}
}
