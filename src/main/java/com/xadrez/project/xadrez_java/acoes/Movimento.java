package com.xadrez.project.xadrez_java.acoes;

import com.xadrez.project.xadrez_java.historico.Historico;
import com.xadrez.project.xadrez_java.jogador.Jogador;
import com.xadrez.project.xadrez_java.peca.*;
import com.xadrez.project.xadrez_java.regras.Validador;
import com.xadrez.project.xadrez_java.tabuleiro.*;

public class Movimento {
	private Validador validador;
	private Tabuleiro tabuleiro;
	private Historico historico;
	
	public Movimento(Tabuleiro tabuleiro, Historico historico) {
		this.validador = new Validador();
		this.tabuleiro = tabuleiro;
		this.historico = historico;
	}
	
	//Executa o movimento de roque
	public void executarRoque(Peca peca, Posicao posAntiga, Tabuleiro tabuleiro) {
		int dx = peca.getPosicaoAtual().x() - posAntiga.x();
		Linha linhaRei = peca.getPosicaoAtual().l();
		
		Peca torre = null;
		Posicao novaPosTorre;
		if (dx > 0) {
			torre = tabuleiro.getPeca(new Posicao(Coluna.H,linhaRei));
			novaPosTorre = new Posicao(Coluna.F,linhaRei);
		}
		else {
			torre = tabuleiro.getPeca(new Posicao(Coluna.A,linhaRei));
			novaPosTorre = new Posicao(Coluna.D,linhaRei);
		}
		
		if (!(torre instanceof Torre)) return;
		if(torre.isPosInicial()) torre.setPosInicial(false);
		this.executarMovimento(torre, torre.getPosicaoAtual(), novaPosTorre, tabuleiro);
	}
	
	//Método para liberar a possibilidade de fazer o en passant
	public void liberarEnPassant(Peca peca, Posicao posicao) {
		int[][] direcoes = peca.getCor() == CorPeca.BRANCA ? new int[][]{{1,-1},{-1,-1}} : new int[][]{{1,1},{-1,1}};
		
		int dx = peca.getPosicaoAtual().x() - posicao.x();
		int dy = peca.getPosicaoAtual().y() - posicao.y();
		
		for(int[] direcao : direcoes) {
			if (dx == direcao[0] && dy == direcao[1]) {
				peca.getPosDeMovimento().add(posicao);
				break;
			}
		}
	}
	
	//Executar o movimento da peça
	public Peca executarMovimento(Peca peca, Posicao posAntiga, Posicao posNova, Tabuleiro tabuleiro) {
		Peca pecaInimiga = null;
		if(this.validador.checarEnPassant(peca, this.tabuleiro, this.historico)) {
			Posicao posCaptura = new Posicao(posNova.c(), posAntiga.l());
			pecaInimiga = this.tabuleiro.getPeca(posCaptura);
		} else {
			pecaInimiga = this.tabuleiro.getPeca(posNova);
		}
		peca.setPosicaoAtual(posNova);
		tabuleiro.inserirPeca(peca);
		tabuleiro.removerPeca(posAntiga);
		if(this.validador.checarRoque(peca, posAntiga, posNova)) this.executarRoque(peca, posAntiga, tabuleiro);
		return pecaInimiga;
	}
	
	//Validar o movimento criando um tabuleiro virtual que imita o
	//estado atual do tabuleiro
	public boolean validarJogada(Jogador jogador, Jogada jogada) {
		//if(!this.validador.checarOutOfBounds(jogada.inicio())) return false;
		Posicao posAntiga = jogada.inicio();
		Posicao posNova = jogada.destino();
		
		Tabuleiro tabVirtual = new Tabuleiro(this.tabuleiro);
		Jogador jgVirtual = jogador.getJogador() == 0 ? tabVirtual.getJogadores()[0] : tabVirtual.getJogadores()[1];
		Peca pecaJogada = tabVirtual.getPeca(posAntiga);
		
		//if(!this.validador.checarPecaValida(pecaJogada, jgVirtual)) return false;
		if(!pecaJogada.validarMovimento(posNova, tabVirtual, this.validador.checarEnPassant(pecaJogada, tabVirtual, this.historico))) return false;
		this.executarMovimento(pecaJogada, posAntiga, posNova, tabVirtual);
		if(this.validador.checarXeque(jgVirtual, tabVirtual)) return false;
		
		return true;
	}
}
