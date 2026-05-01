package com.xadrez.project.xadrez_java.peca;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.xadrez.project.xadrez_java.jogador.Jogador;
import com.xadrez.project.xadrez_java.jogador.JogadorIA;
import com.xadrez.project.xadrez_java.peca.cavalo.Cavalo;
import com.xadrez.project.xadrez_java.peca.rei.Rei;
import com.xadrez.project.xadrez_java.peca.torre.Torre;
import com.xadrez.project.xadrez_java.tabuleiro.Coluna;
import com.xadrez.project.xadrez_java.tabuleiro.Linha;
import com.xadrez.project.xadrez_java.tabuleiro.Posicao;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;

public class CalcularRoqueTeste {
	private Jogador jogador;
	private Tabuleiro tabuleiro;
	private Peca rei;
	private Peca torreD;
	private Peca torreE;
	
	@BeforeEach
	void start() {
		this.jogador = new JogadorIA(0);
		this.tabuleiro = new Tabuleiro(this.jogador, null);
		this.rei = new Rei(CorPeca.BRANCA, new Posicao(Coluna.E, Linha.L1), this.jogador);
		this.torreD = new Torre(CorPeca.BRANCA, new Posicao(Coluna.H, Linha.L1), this.jogador);
		this.torreE = new Torre(CorPeca.BRANCA, new Posicao(Coluna.A, Linha.L1), this.jogador);
		this.tabuleiro.inserirPeca(rei);
		this.tabuleiro.inserirPeca(torreD);
		this.tabuleiro.inserirPeca(torreE);
		
		this.jogador.setPeca(rei);
		this.jogador.setPeca(torreD);
		this.jogador.setPeca(torreE);
	}
	
	@Test
	public void checarRoquePossivel() {
		Posicao roqueD = Posicao.converterStringEmPos("G1");
		
		//Verificar se é possível roque a direita, no qual é verdadeiro
		assertTrue(this.rei.validarMovimento(roqueD, this.tabuleiro), "Deverá retornar verdadeiro pois quadrados estão livres para fazer o roque");
	}
	
	@Test
	public void checarRoqueForaDaPosInicial() {
		this.torreD.setPosInicial(false);
		Posicao roqueD = Posicao.converterStringEmPos("G1");
		
		//Verificar se é possível roque a direita, no qual é falso devido a não estar mais na posição inicial
		assertFalse(this.rei.validarMovimento(roqueD, this.tabuleiro), "Deverá retornar falso pois torre já se moveu");
	}
	
	@Test
	public void checarRoqueImpossivel() {
		Peca cavaloE = new Cavalo(CorPeca.BRANCA, new Posicao(Coluna.B, Linha.L1), this.jogador);
		this.tabuleiro.inserirPeca(cavaloE);
		this.jogador.setPeca(cavaloE);
		
		Posicao roqueE = Posicao.converterStringEmPos("C1");
		
		//Verificar se é possível um roque a esquerda, no qual é falso
		assertFalse(this.rei.validarMovimento(roqueE, this.tabuleiro), "Deverá retornar falso pois há cavalo impedindo o roque");
	}
}
