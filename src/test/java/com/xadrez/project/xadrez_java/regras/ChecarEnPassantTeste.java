package com.xadrez.project.xadrez_java.regras;

import org.junit.jupiter.api.BeforeEach;

import com.xadrez.project.xadrez_java.acoes.Jogada;
import com.xadrez.project.xadrez_java.historico.Historico;
import com.xadrez.project.xadrez_java.jogador.Jogador;
import com.xadrez.project.xadrez_java.jogador.JogadorIA;
import com.xadrez.project.xadrez_java.peca.CorPeca;
import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.peca.peao.Peao;
import com.xadrez.project.xadrez_java.tabuleiro.Coluna;
import com.xadrez.project.xadrez_java.tabuleiro.Linha;
import com.xadrez.project.xadrez_java.tabuleiro.Posicao;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;

public class ChecarEnPassantTeste {
	private Jogador jogador1;
	private Jogador jogador2;
	private Validador validador;
	private Historico historico;
	private Tabuleiro tabuleiro;
	
	private Peca peaoB;
	
	@BeforeEach
	void start() {
		this.jogador1 = new JogadorIA(0);
		this.jogador2 = new JogadorIA(1);
		this.validador = new Validador();
		this.historico = new Historico();
		this.tabuleiro = new Tabuleiro(jogador1, jogador2);
		
		this.peaoB = new Peao(CorPeca.BRANCA, new Posicao(Coluna.E,Linha.L5), this.jogador1);
		
		this.tabuleiro.inserirPeca(peaoB);
		this.jogador1.setPeca(peaoB);
	}
	
	public void checarEnPassantValido() {
		Peca peaoP = new Peao(CorPeca.PRETA, new Posicao(Coluna.D, Linha.L5), this.jogador2);
		
		this.tabuleiro.inserirPeca(peaoP);
		this.jogador2.setPeca(peaoP);
		
		Jogada ultimaJogada = new Jogada("D7", "D5");
		
		historico.salvarTurno(1, this.tabuleiro, this.jogador2, peaoP, ultimaJogada);
		
		//Checar validade do En Passant, neste caso
	}
}
