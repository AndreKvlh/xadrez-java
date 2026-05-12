package com.xadrez.project.xadrez_java.peca.bispo;

import com.xadrez.project.xadrez_java.peca.CorPeca;
import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.peca.TipoPeca;
import com.xadrez.project.xadrez_java.tabuleiro.Posicao;
import com.xadrez.project.xadrez_java.jogador.Jogador;

public class Bispo extends Peca {
	public Bispo(CorPeca cor, Posicao posicaoAtual, Jogador jogadorResp) {
		super(cor, posicaoAtual, jogadorResp);
		this.setTipo(TipoPeca.BISPO);
		this.direcoes = new int[][] {{-1,-1},{-1,1},{1,1},{1,-1}};
		this.limMovimento = false;
		this.unicode = this.getCor() == CorPeca.BRANCA ? "\u2657" : "\u265D";
	}
	
	@Override
	public Peca copiar() {
		return new Bispo(this.cor, this.posicaoAtual, this.jogadorResp);
	}
}
