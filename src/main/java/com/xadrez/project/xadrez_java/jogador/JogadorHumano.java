package com.xadrez.project.xadrez_java.jogador;

import com.xadrez.project.xadrez_java.acoes.Jogada;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;

public class JogadorHumano extends Jogador {
	public JogadorHumano(int jogador) {
		super(jogador);
	}
	
	public Jogador copiar() {
		return new JogadorHumano(this.jogador);
	}
	
	@Override
	public Jogada realizarJogada(Tabuleiro tabuleiro) {
		return null;
	};
}
