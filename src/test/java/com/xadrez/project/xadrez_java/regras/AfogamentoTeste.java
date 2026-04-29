package com.xadrez.project.xadrez_java.regras;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

import com.xadrez.project.xadrez_java.acoes.Movimento;
import com.xadrez.project.xadrez_java.jogador.Jogador;
import com.xadrez.project.xadrez_java.jogador.JogadorHumano;
import com.xadrez.project.xadrez_java.peca.CorPeca;
import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.peca.cavalo.Cavalo;
import com.xadrez.project.xadrez_java.peca.rainha.Rainha;
import com.xadrez.project.xadrez_java.peca.rei.Rei;
import com.xadrez.project.xadrez_java.tabuleiro.Coluna;
import com.xadrez.project.xadrez_java.tabuleiro.Linha;
import com.xadrez.project.xadrez_java.tabuleiro.Posicao;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;

public class AfogamentoTeste {

	@Test
	public void testarSePartidaAfogou() {
		//Criação da base para o teste: jogadores e tabuleiro
		Scanner leitor = new Scanner(System.in);
		Jogador jogador1 = new JogadorHumano(0, leitor);
		Jogador jogador2 = new JogadorHumano(1, leitor);
		Tabuleiro tabuleiro = new Tabuleiro(jogador1, jogador2);
		
		//Criação das regras para validação e movimento
		Validador validador = new Validador();
		Movimento movimento = new Movimento(tabuleiro);
		
		//Peças brancas
		Peca reiB = new Rei(CorPeca.BRANCA, new Posicao(Coluna.A,Linha.L8), jogador1);
		Peca rainhaB = new Rainha(CorPeca.BRANCA, new Posicao(Coluna.B,Linha.L6), jogador1);
		Peca cavaloB = new Cavalo(CorPeca.BRANCA, new Posicao(Coluna.F,Linha.L6), jogador1);
		
		jogador1.getPecasAtuais().add(reiB);
		jogador1.getPecasAtuais().add(rainhaB);
		jogador1.getPecasAtuais().add(cavaloB);
		
		//Peças pretas
		Peca reiP = new Rei(CorPeca.PRETA, new Posicao(Coluna.C, Linha.L8), jogador2);
		
		jogador2.getPecasAtuais().add(reiP);
		
		//Inserir as peças dentro do tabuleiro
		for(Peca peca : jogador1.getPecasAtuais()) {
			tabuleiro.inserirPeca(peca);
		}
		for(Peca peca : jogador2.getPecasAtuais()) {
			tabuleiro.inserirPeca(peca);
		}

		tabuleiro.gerarTabuleiro();
				
		//Teste para ver se a posição em questão é uma posição de afogamento (stalemate)
		//Nenhuma peça consegue atacar a outra, resultando num empate
		assertTrue(validador.checarAfogamento(jogador2, tabuleiro, movimento), "Deverá retornar verdadeiro já que reis não podem se atacar e muito menos rainha consegue capturar");
	}
	
}
