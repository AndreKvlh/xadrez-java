package com.xadrez.project.xadrez_java.acoes;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.xadrez.project.xadrez_java.historico.Historico;
import com.xadrez.project.xadrez_java.jogador.Jogador;
import com.xadrez.project.xadrez_java.jogador.JogadorIA;
import com.xadrez.project.xadrez_java.peca.CorPeca;
import com.xadrez.project.xadrez_java.peca.Peao;
import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.regras.Validador;
import com.xadrez.project.xadrez_java.tabuleiro.Coluna;
import com.xadrez.project.xadrez_java.tabuleiro.Linha;
import com.xadrez.project.xadrez_java.tabuleiro.Posicao;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;

public class LiberarEnPassantTeste {
	private Jogador jogador1;
	private Jogador jogador2;
	private Validador validador;
	private Historico historico;
	private Tabuleiro tabuleiro;
	private Movimento movimento;
	
	private Peca peaoB;
	
	@BeforeEach
	void start() {
		this.jogador1 = new JogadorIA(0);
		this.jogador2 = new JogadorIA(1);
		this.validador = new Validador();
		this.historico = new Historico();
		this.tabuleiro = new Tabuleiro(jogador1, jogador2);
		this.movimento = new Movimento(tabuleiro, historico);
		
		this.peaoB = new Peao(CorPeca.BRANCA, new Posicao(Coluna.E,Linha.L5), this.jogador1);
		
		this.tabuleiro.inserirPeca(peaoB);
		this.jogador1.setPeca(peaoB);
	}
	
	@Test
	public void checarSePermiteJogadaEnPassant() {
		Peca peaoP = new Peao(CorPeca.PRETA, new Posicao(Coluna.D, Linha.L5), this.jogador2);
		
		this.tabuleiro.inserirPeca(peaoP);
		this.jogador2.setPeca(peaoP);
		
		Jogada ultimaJogada = new Jogada("D7", "D5");
		
		historico.salvarTurno(1, this.tabuleiro, this.jogador2, peaoP, ultimaJogada);
		
		Jogada enPassant = new Jogada("E5", "D6");
		
		//Checar se jogada em questão é possível. Movimento tem que retornar verdadeiro
		//por ser um movimento de en passant
		assertTrue(this.movimento.validarJogada(this.jogador1, enPassant),"Retorna verdadeiro confirmando en passant");
	}
	
	@Test
	public void checarCapturaEnPassant() {
		Peca peaoP = new Peao(CorPeca.PRETA, new Posicao(Coluna.D, Linha.L5), this.jogador2);
		
		this.tabuleiro.inserirPeca(peaoP);
		this.jogador2.setPeca(peaoP);
		
		Jogada ultimaJogada = new Jogada("D7", "D5");
		
		historico.salvarTurno(1, this.tabuleiro, this.jogador2, peaoP, ultimaJogada);
		
		Jogada enPassant = new Jogada("E5", "D6");
		//Checar se jogada em questão é possível. Movimento tem que retornar verdadeiro
		//por ser um movimento de en passant
		assertNotNull(this.movimento.executarMovimento(this.peaoB, enPassant.posInicio(), enPassant.posDestino(), this.tabuleiro));
	}
}
