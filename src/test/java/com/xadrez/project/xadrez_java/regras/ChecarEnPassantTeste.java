package com.xadrez.project.xadrez_java.regras;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.xadrez.project.xadrez_java.acoes.Jogada;
import com.xadrez.project.xadrez_java.historico.Historico;
import com.xadrez.project.xadrez_java.jogador.Jogador;
import com.xadrez.project.xadrez_java.jogador.JogadorIA;
import com.xadrez.project.xadrez_java.peca.CorPeca;
import com.xadrez.project.xadrez_java.peca.Peao;
import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.peca.Torre;
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
	
	@Test
	public void checarEnPassantValido() {
		Peca peaoP = new Peao(CorPeca.PRETA, new Posicao(Coluna.D, Linha.L5), this.jogador2);
		
		this.tabuleiro.inserirPeca(peaoP);
		this.jogador2.setPeca(peaoP);
		
		Jogada ultimaJogada = new Jogada("D7", "D5");
		
		historico.salvarTurno(1, this.tabuleiro, this.jogador2, peaoP, ultimaJogada);
		
		//Checar validade do En Passant, neste caso sendo verdadeiro pois peão preto fez
		//passo duplo perto do peão branco
		assertTrue(this.validador.checarEnPassant(this.peaoB,this.tabuleiro,this.historico), "Deverá retornar verdadeiro para en passant");
	}
	
	@Test
	public void checarEnPassantInvalidoDePeao() {
		Peca peaoP = new Peao(CorPeca.PRETA, new Posicao(Coluna.D, Linha.L5), this.jogador2);
		
		this.tabuleiro.inserirPeca(peaoP);
		this.jogador2.setPeca(peaoP);
		
		Jogada ultimaJogada = new Jogada("D6", "D5");
		
		historico.salvarTurno(1, this.tabuleiro, this.jogador2, peaoP, ultimaJogada);
		
		//Checar validade do En Passant, que será falso pois apesar do peão se mover para
		//o lado do outro, ele não fez um passo duplo
		assertFalse(this.validador.checarEnPassant(this.peaoB, this.tabuleiro, this.historico));
	}
	
	@Test
	public void checarEnPassantInvalidoPorHistorico() {
		Peca peaoP = new Peao(CorPeca.PRETA, new Posicao(Coluna.D, Linha.L5), this.jogador2);
		Peca torreP = new Torre(CorPeca.PRETA, new Posicao(Coluna.F, Linha.L5), this.jogador2);
		
		this.tabuleiro.inserirPeca(peaoP);
		this.tabuleiro.inserirPeca(torreP);
		this.jogador2.setPeca(peaoP);
		this.jogador2.setPeca(torreP);
		
		Jogada penultimaJogada = new Jogada("D7", "D5");
		Jogada ultimaJogada = new Jogada("F8", "F5");
		
		historico.salvarTurno(1, this.tabuleiro, this.jogador2, peaoP, penultimaJogada);
		historico.salvarTurno(2, this.tabuleiro, this.jogador2, torreP, ultimaJogada);
		
		//Checar validade do en passant, que será falso pois o jogador perdeu a janela de 
		//oportunidade
		assertFalse(this.validador.checarEnPassant(this.peaoB, this.tabuleiro, this.historico), "Retorna falso pois jogador perdeu janela de en passant");
	}
}
