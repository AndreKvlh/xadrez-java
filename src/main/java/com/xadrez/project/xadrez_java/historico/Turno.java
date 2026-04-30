package com.xadrez.project.xadrez_java.historico;

import com.xadrez.project.xadrez_java.acoes.Jogada;
import com.xadrez.project.xadrez_java.jogador.Jogador;
import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;

public record Turno(
		Integer turno,
		Tabuleiro tabuleiro,
		Jogador jogador,
		Peca peca,
		Jogada jogada
) {}
