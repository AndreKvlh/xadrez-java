package com.xadrez.project.xadrez_java.peca.peao;

import com.xadrez.project.xadrez_java.jogador.Jogador;
import com.xadrez.project.xadrez_java.peca.CorPeca;
import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.peca.TipoPeca;
import com.xadrez.project.xadrez_java.tabuleiro.Posicao;

public class Peao extends Peca{
	public Peao(CorPeca cor, Posicao posicaoAtual, Jogador jogadorResp) {
		super(cor, posicaoAtual, jogadorResp);
		this.setTipo(TipoPeca.PEAO);
		this.direcoes = jogadorResp.getJogador() == 0 ? new int[][]{{0,-1}} : new int[][]{{0,1}};
		this.limMovimento = true;
	}

	@Override
	public Peca copiar() {
		return new Peao(this.cor, this.posicaoAtual, this.jogadorResp);
	}
}	
