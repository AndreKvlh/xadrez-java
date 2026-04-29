package com.xadrez.project.xadrez_java.computador;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

import com.xadrez.project.xadrez_java.jogador.Jogador;
import com.xadrez.project.xadrez_java.jogador.JogadorHumano;
import com.xadrez.project.xadrez_java.jogador.JogadorIA;
import com.xadrez.project.xadrez_java.peca.CorPeca;
import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.peca.bispo.Bispo;
import com.xadrez.project.xadrez_java.peca.peao.Peao;
import com.xadrez.project.xadrez_java.peca.rei.Rei;
import com.xadrez.project.xadrez_java.peca.torre.Torre;
import com.xadrez.project.xadrez_java.tabuleiro.Coluna;
import com.xadrez.project.xadrez_java.tabuleiro.Linha;
import com.xadrez.project.xadrez_java.tabuleiro.Posicao;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;

public class ComputadorXequeMateTeste {
	@Test
	public void testarDeteccaoXequeMateIA() {
		//Criação da base para o teste: jogadores e tabuleiro
		Scanner leitor = new Scanner(System.in);
		Jogador jogador1 = new JogadorHumano(0, leitor);
		Jogador jogador2 = new JogadorIA(1);
		Tabuleiro tabuleiro = new Tabuleiro(jogador1, jogador2);

		//Criação das peças participantes da cor preta
		Peca reiP = new Rei(CorPeca.PRETA, new Posicao(Coluna.E,Linha.L8),jogador2);
		Peca bispoP = new Bispo(CorPeca.PRETA, new Posicao(Coluna.F,Linha.L8),jogador2);
		Peca peaoPe7 = new Peao(CorPeca.PRETA, new Posicao(Coluna.E,Linha.L7),jogador2);
		Peca peaoPf7 = new Peao(CorPeca.PRETA, new Posicao(Coluna.F,Linha.L7),jogador2);
		
		//Criação das peças participantes da cor branca
		Peca bispoB = new Bispo(CorPeca.BRANCA, new Posicao(Coluna.B,Linha.L5), jogador1);
		Peca torreB = new Torre(CorPeca.BRANCA, new Posicao(Coluna.D,Linha.L5), jogador1);
		
		//Adição das peças para cada jogador:
		jogador1.getPecasAtuais().add(bispoB);
		jogador1.getPecasAtuais().add(torreB);
		
		jogador2.getPecasAtuais().add(reiP);
		jogador2.getPecasAtuais().add(bispoP);
		jogador2.getPecasAtuais().add(peaoPe7);
		jogador2.getPecasAtuais().add(peaoPf7);
		
		//Inserir as peças dentro do tabuleiro
		for(Peca peca : jogador1.getPecasAtuais()) {
			tabuleiro.inserirPeca(peca);
		}
		for(Peca peca : jogador2.getPecasAtuais()) {
			tabuleiro.inserirPeca(peca);
		}

		tabuleiro.gerarTabuleiro();
		
		//Teste para ver se o método realizarJogada da IA retorna nulo quando ele não tem
		//uma jogada disponível
		assertNull(jogador2.realizarJogada(tabuleiro), "Deverá retornar nulo devido a não possuir movimentos possíveis");
	}
}
