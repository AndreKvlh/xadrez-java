package com.xadrez.project.xadrez_java.peca.cavalo;

import com.xadrez.project.xadrez_java.peca.CorPeca;
import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.peca.TipoPeca;
import com.xadrez.project.xadrez_java.tabuleiro.Posicao;
import com.xadrez.project.xadrez_java.jogador.Jogador;

public class Cavalo extends Peca {
	public Cavalo(CorPeca cor, Posicao posicaoAtual, Jogador jogadorResp) {
		super(cor, posicaoAtual, jogadorResp);
		this.setTipo(TipoPeca.CAVALO);
		this.direcoes = new int[][]{{-1,-2},{1,-2},{2,-1},{2,1},{1,2},{-1,2},{-2,1},{-2,-1}};
		this.limMovimento = true;
	}

	@Override
	public Peca copiar() {
		return new Cavalo(this.cor, this.posicaoAtual, this.jogadorResp);
	}
}
