package com.xadrez.project.xadrez_java.acoes;

import com.xadrez.project.xadrez_java.jogador.Jogador;
import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.regras.Validador;
import com.xadrez.project.xadrez_java.tabuleiro.Posicao;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;

public class Movimento {
	private Validador validador;
	private Tabuleiro tabuleiro;
	
	public Movimento(Tabuleiro tabuleiro) {
		this.validador = new Validador();
		this.tabuleiro = tabuleiro;
	}
	
	//Executar o movimento da peça
	public Peca executarMovimento(Peca peca, Posicao posAntiga, Posicao posNova, Tabuleiro tabuleiro) {
		Peca pecaInimiga = this.tabuleiro.getPeca(posNova);
		peca.setPosicaoAtual(posNova);
		tabuleiro.inserirPeca(peca);
		tabuleiro.removerPeca(posAntiga);
		if(peca.isPosInicial()) peca.setPosInicial(false);
		if(pecaInimiga != null) {
			return pecaInimiga;
		}
		return null;
	}
	
	//Validar o movimento criando um tabuleiro virtual que imita o
	//estado atual do tabuleiro
	public boolean validarJogada(Jogador jogador, Jogada jogada) {
		if(!this.validador.checarOutOfBounds(jogada.inicio())) return false;
		Posicao posAntiga = jogada.posInicio();
		Posicao posNova = jogada.posDestino();
		
		Tabuleiro tabVirtual = new Tabuleiro(this.tabuleiro);
		Jogador jgVirtual = jogador.getJogador() == 0 ? tabVirtual.getJogadores()[0] : tabVirtual.getJogadores()[1];
		Peca pecaJogada = tabVirtual.getPeca(posAntiga);
		
		if(!this.validador.checarPecaValida(pecaJogada, jgVirtual)) return false;
		if(!pecaJogada.validarMovimento(posNova, tabVirtual)) return false;
		this.executarMovimento(pecaJogada, posAntiga, posNova, tabVirtual);
		
		if (this.validador.checarXeque(jgVirtual, tabVirtual)) {
			System.out.println("Posição te deixará/manterá em xeque, selecione outra");
			return false;
		}
		return true;
	}
}
