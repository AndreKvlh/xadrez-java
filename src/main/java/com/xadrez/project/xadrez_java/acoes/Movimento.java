package com.xadrez.project.xadrez_java.acoes;

import java.util.Scanner;

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
	public void executarMovimento(Peca peca, Posicao posAntiga, Posicao posNova, Tabuleiro tabuleiro) {
		Peca pecaInimiga = this.tabuleiro.getPeca(posNova);
		if(pecaInimiga != null) {
			//this.executarCaptura(peca, pecaInimiga);
		}
		peca.setPosicaoAtual(posNova);
		tabuleiro.inserirPeca(peca);
		tabuleiro.removerPeca(posAntiga);
		if(peca.isPosInicial()) peca.setPosInicial(false);
	}
	
	//Validar o movimento criando um tabuleiro virtual que imita o
	//estado atual do tabuleiro
	public boolean validarJogada(Jogador jogador, Posicao posAntiga, Posicao posNova) {
		Tabuleiro tabVirtual = new Tabuleiro(this.tabuleiro);
		Jogador jgVirtual = jogador.getJogador() == 0 ? tabVirtual.getJogadores()[0] : tabVirtual.getJogadores()[1];
		Peca pecaJogada = tabVirtual.getPeca(posAntiga);
		this.executarMovimento(pecaJogada, posAntiga, posNova, tabVirtual);
		
		if (this.validador.checarXeque(jgVirtual, tabVirtual)) {
			System.out.println("Posição te deixará/manterá em xeque, selecione outra");
			return false;
		}
		return true;
	}
	
	//Método que irá realizar a jogada, fazendo todas as verificações necessárias
	public void realizarJogada(Jogador jogador, Tabuleiro tabuleiro, Scanner leitor) {
		//Atributos para obtenção das informações da jogada
		String op;
		Posicao posAntiga, posNova;
		Peca pecaSelecionada;
		
		do {
			System.out.print("Digite a posição referente a peça que você quer selecionar: ");
			op = leitor.next();
			if (!this.validador.checarOutOfBounds(op)) continue;
			posAntiga = Posicao.converterStringEmPos(op);
			pecaSelecionada = tabuleiro.getPeca(posAntiga);
			
			if(!this.validador.checarPecaValida(pecaSelecionada, jogador)) continue;
			
			System.out.println();
			System.out.print("Digite a posição na qual você quer mover a peça: ");

			posNova = Posicao.converterStringEmPos(leitor.next());
			
			if(!pecaSelecionada.validarMovimento(posNova, tabuleiro)) continue;
			if(!this.validarJogada(jogador, posAntiga, posNova)) continue;
			break;
		} while (true);
		this.executarMovimento(pecaSelecionada, posAntiga, posNova, tabuleiro);
		if(this.validador.checarPromocao(pecaSelecionada)) /*this.promoverPeao(pecaSelecionada, tabuleiro)*/;
	}
}
