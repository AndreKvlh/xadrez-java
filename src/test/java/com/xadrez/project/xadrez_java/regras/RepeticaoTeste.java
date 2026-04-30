package com.xadrez.project.xadrez_java.regras;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.xadrez.project.xadrez_java.acoes.Jogada;
import com.xadrez.project.xadrez_java.historico.Historico;
import com.xadrez.project.xadrez_java.jogador.Jogador;
import com.xadrez.project.xadrez_java.jogador.JogadorIA;
import com.xadrez.project.xadrez_java.peca.CorPeca;
import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.peca.rainha.Rainha;
import com.xadrez.project.xadrez_java.peca.rei.Rei;
import com.xadrez.project.xadrez_java.tabuleiro.Posicao;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;

public class RepeticaoTeste {
	private Jogador jogador1;
	private Jogador jogador2;
	private Tabuleiro tabuleiro;
	private Historico historico;
	private Validador validador;
	
	@BeforeEach
	void setup() {		
		this.jogador1 = new JogadorIA(0);
		this.jogador2 = new JogadorIA(1);
		this.tabuleiro = new Tabuleiro(jogador1, jogador2);
		this.historico = new Historico();
		this.validador = new Validador();
	}
	
	@Test
	public void verificarRepeticao() {
		String[][] jogadasJ1 = new String[][] {{"A1","A2"},{"A2","A1"},{"A1","A2"}};
		String[][] jogadasJ2 = new String[][] {{"C2","C1"},{"C1","C2"},{"C2","C1"}};
		
		Peca reiB = new Rei(CorPeca.BRANCA, Posicao.converterStringEmPos(jogadasJ1[0][0]), jogador1);
		Peca rainhaP = new Rainha(CorPeca.BRANCA, Posicao.converterStringEmPos(jogadasJ2[0][0]), jogador1);
		
		this.tabuleiro.inserirPeca(reiB);
		this.tabuleiro.inserirPeca(rainhaP);
		
		this.jogador1.setPeca(reiB);
		this.jogador2.setPeca(rainhaP);
		
		int turno = 1;
		
		//Loop para preencher as jogadas no histórico
		for(int i = 0; i < 6; i++) {
			Peca pecaJogada;
			Jogador jogadorResp;
			String[][] jogadasJogador;
			int indiceJogada = 0;
			if (i % 2 == 0) {
				pecaJogada = reiB;
				jogadorResp = this.jogador1;
				jogadasJogador = jogadasJ1;
				
			} else {
				pecaJogada = rainhaP;
				jogadorResp = this.jogador2;
				jogadasJogador = jogadasJ2;
			}
			Jogada jogadaAtual = new Jogada(jogadasJogador[indiceJogada][0],jogadasJogador[indiceJogada][1]);
			
			this.historico.salvarTurno(turno, this.tabuleiro, jogadorResp, pecaJogada, jogadaAtual);
			if(i % 2 == 1) indiceJogada++;
			turno++;
		}
		
		assertTrue(this.validador.checarRepeticao(this.jogador1, this.tabuleiro, this.historico), "Deverá retornar verdadeiro se houver repetição nas jogadas");
	}
}
