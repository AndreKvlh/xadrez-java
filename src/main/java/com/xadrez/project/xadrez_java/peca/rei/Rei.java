package com.xadrez.project.xadrez_java.peca.rei;

import com.xadrez.project.xadrez_java.peca.CorPeca;
import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.peca.TipoPeca;
import com.xadrez.project.xadrez_java.tabuleiro.Posicao;
import com.xadrez.project.xadrez_java.jogador.Jogador;

public class Rei extends Peca {
	public Rei(CorPeca cor, Posicao posicaoAtual, Jogador jogadorResp) {
		super(cor, posicaoAtual, jogadorResp);
		this.setTipo(TipoPeca.REI);
		this.direcoes = new int[][] {{0,-1},{1,-1},{1,0},{1,1},{0,1},{-1,1},{-1,0},{-1,-1}};
		this.limMovimento = true;
	}

	@Override
	public Peca copiar() {
		return new Rei(this.cor, this.posicaoAtual, this.jogadorResp);
	}
}
