package com.xadrez.project.xadrez_java.regras;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.xadrez.project.xadrez_java.peca.CorPeca;
import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.peca.rei.Rei;
import com.xadrez.project.xadrez_java.peca.torre.Torre;
import com.xadrez.project.xadrez_java.tabuleiro.Coluna;
import com.xadrez.project.xadrez_java.tabuleiro.Linha;
import com.xadrez.project.xadrez_java.tabuleiro.Posicao;

public class checarRoqueTeste {
	public Peca rei;
	public Peca torre;
	public Validador validador;
	
	@BeforeEach
	void start() {
		this.rei = new Rei(CorPeca.BRANCA, new Posicao(Coluna.E, Linha.L1), null);
		this.torre = new Torre(CorPeca.BRANCA, new Posicao(Coluna.H, Linha.L1), null);
		this.validador = new Validador();
	}
	
	@Test
	public void checarRoqueValido() {
		Posicao posNova = Posicao.converterStringEmPos("G1");
		
		//Teste para verificar se o verificador checa o movimento de roque
		assertTrue(this.validador.checarRoque(this.rei, this.rei.getPosicaoAtual(), posNova), "Deverá retornar verdadeiro pois é um rei e está se movendo duas casas");
	}
	
	@Test
	public void checarRoqueInvalido1() {
		Posicao posNova = Posicao.converterStringEmPos("F1");
		
		//Teste para verificar se o verificador checa o movimento de roque
		assertFalse(this.validador.checarRoque(this.rei, this.rei.getPosicaoAtual(), posNova), "Deverá retornar falso pois mesmo sendo o rei ele está andando apenas uma casa");
	}
	
	@Test
	public void checarRoqueInvalido2() {
		Posicao posNova = Posicao.converterStringEmPos("F1");
		
		//Teste para verificar se o verificador checa o movimento de roque
		assertFalse(this.validador.checarRoque(this.torre, this.torre.getPosicaoAtual(), posNova), "Deverá retornar falso pois não é o rei fazendo o movimento");
	}
}
