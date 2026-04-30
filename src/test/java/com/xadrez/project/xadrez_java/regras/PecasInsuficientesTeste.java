package com.xadrez.project.xadrez_java.regras;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.xadrez.project.xadrez_java.acoes.Movimento;
import com.xadrez.project.xadrez_java.jogador.Jogador;
import com.xadrez.project.xadrez_java.jogador.JogadorIA;
import com.xadrez.project.xadrez_java.peca.CorPeca;
import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.peca.cavalo.Cavalo;
import com.xadrez.project.xadrez_java.peca.rei.Rei;
import com.xadrez.project.xadrez_java.peca.torre.Torre;
import com.xadrez.project.xadrez_java.tabuleiro.Coluna;
import com.xadrez.project.xadrez_java.tabuleiro.Linha;
import com.xadrez.project.xadrez_java.tabuleiro.Posicao;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;

public class PecasInsuficientesTeste {
	private Tabuleiro tabuleiro;
	private Validador validador;
	private Movimento movimento;
	private Jogador jogador1;
	private Jogador jogador2;
	private Peca reiB;
	private Peca reiP;
	
	@BeforeEach
	void setup() {
		this.jogador1 = new JogadorIA(0);
		this.jogador2 = new JogadorIA(1);
		this.tabuleiro = new Tabuleiro(jogador1, jogador2);
		this.validador = new Validador();
		this.movimento = new Movimento(this.tabuleiro);
		this.reiB = new Rei(CorPeca.BRANCA, new Posicao(Coluna.E,Linha.L1), jogador1);
		this.reiP = new Rei(CorPeca.PRETA, new Posicao(Coluna.E,Linha.L8), jogador2);
		this.tabuleiro.inserirPeca(reiB);
		this.tabuleiro.inserirPeca(reiP);
		this.jogador1.setPeca(reiB);
		this.jogador2.setPeca(reiP);
	}
	
	@Test
	public void verificarApenasReisEmCampo() {
		assertTrue(this.validador.checarPecasInsuficientes(jogador1, tabuleiro, movimento), "Retorna verdadeiro se houver apenas os reis de cada lado em campo e mais nenhuma peça");
	}
	
	@Test
	public void verificarPecaCavaloOuBispo() {
		Peca cavaloB = new Cavalo(CorPeca.BRANCA, new Posicao(Coluna.B, Linha.L1), jogador1);
		this.tabuleiro.inserirPeca(cavaloB);
		this.jogador1.setPeca(cavaloB);
		
		assertTrue(this.validador.checarPecasInsuficientes(jogador1, tabuleiro, movimento), "Retorna verdadeiro se a peça remanescente fora o rei é um bispo ou um cavalo");
	}
	
	@Test
	public void verificarPecaRestante() {
		Peca torreB = new Torre(CorPeca.BRANCA, new Posicao(Coluna.A, Linha.L1), jogador1);
		this.tabuleiro.inserirPeca(torreB);
		this.jogador1.setPeca(torreB);
		
		assertFalse(this.validador.checarPecasInsuficientes(jogador1, tabuleiro, movimento), "Retorna falso pois mesmo ficando uma peça somente não é um bispo ou um cavalo");
	}
}
