package com.xadrez.project.xadrez_java.acoes;

import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.tabuleiro.Posicao;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;

public record Jogada(Posicao inicio, Posicao destino) {
	public Peca pecaSelecionada(Tabuleiro tabuleiro) {
		return tabuleiro.getPeca(this.inicio());
	}
	
	public Posicao inicio() {
		return inicio;
	}
	
	public Posicao destino() {
		return destino;
	}
	
	public Jogada copiar() {
		return new Jogada(this.inicio,this.destino);
	}
}
