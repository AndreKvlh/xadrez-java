package com.xadrez.project.xadrez_java.acoes;

import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.tabuleiro.Posicao;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;

public record Jogada(String inicio, String destino) {
	public Peca pecaSelecionada(Tabuleiro tabuleiro) {
		return tabuleiro.getPeca(this.posInicio());
	}
	
	public Posicao posInicio() {
		return Posicao.converterStringEmPos(inicio);
	}
	
	public Posicao posDestino() {
		return Posicao.converterStringEmPos(destino);
	}
}
