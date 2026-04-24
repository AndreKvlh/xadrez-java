package com.xadrez.project.xadrez_java.peca.peao;

import com.xadrez.project.xadrez_java.jogador.Jogador;
import com.xadrez.project.xadrez_java.peca.CorPeca;
import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.peca.TipoPeca;
import com.xadrez.project.xadrez_java.tabuleiro.Coluna;
import com.xadrez.project.xadrez_java.tabuleiro.Linha;
import com.xadrez.project.xadrez_java.tabuleiro.Posicao;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;

public class Peao extends Peca{
	private final int[][] direcoesAtaque;
	
	public Peao(CorPeca cor, Posicao posicaoAtual, Jogador jogadorResp) {
		super(cor, posicaoAtual, jogadorResp);
		this.setTipo(TipoPeca.PEAO);
		this.direcoes = jogadorResp.getJogador() == 0 ? new int[][]{{0,-1}} : new int[][]{{0,1}};
		this.direcoesAtaque = jogadorResp.getJogador() == 0 ? new int[][]{{1,-1},{-1,-1}} : new int[][]{{1,1},{-1,1}};
		this.limMovimento = true;
	}

	@Override
	public void calcularPossibilidades(Tabuleiro tabuleiro) {
		if(!getPosDeMovimento().isEmpty()) getPosDeMovimento().clear();
		int[] coord = this.posicaoAtual.getCoord();
		int distancia = !this.posInicial ? 2 : 3;
		for(int[] direcoes : this.direcoes) {
			for (int i = 1; i < distancia; i++) {
				int colAtual = coord[0] + (i * direcoes[0]);
				int linAtual = coord[1] + (i * direcoes[1]);
				if (colAtual > 7 || colAtual < 0 || linAtual > 7 || linAtual < 0) break;
				Posicao posAtual = new Posicao(Coluna.deIndice(colAtual), Linha.deIndice(linAtual));
				Peca conteudoPeca = tabuleiro.getPeca(posAtual);
				if (conteudoPeca != null) break;
				getPosDeMovimento().add(posAtual);
			}
		}
		for(int[] direcoes : this.direcoesAtaque) {
			int colAtual = coord[0] + direcoes[0];
			int linAtual = coord[1] + direcoes[1];
			if (colAtual > 7 || colAtual < 0 || linAtual > 7 || linAtual < 0) break;
			Posicao posAtual = new Posicao(Coluna.deIndice(colAtual), Linha.deIndice(linAtual));
			Peca conteudoPeca = tabuleiro.getPeca(posAtual);
			if (conteudoPeca == null) continue;
			if (this.getCor().equals(conteudoPeca.getCor())) continue;
			getPosDeMovimento().add(posAtual);
		}
	}
	
	@Override
	public Peca copiar() {
		return new Peao(this.cor, this.posicaoAtual, this.jogadorResp);
	}
}	
