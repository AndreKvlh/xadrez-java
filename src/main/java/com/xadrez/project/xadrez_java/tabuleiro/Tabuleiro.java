package com.xadrez.project.xadrez_java.tabuleiro;

import java.util.Arrays;
import java.util.Collections;

public class Tabuleiro {
	//Matriz de controle das peças e movimentos;
	private char[][] pecasNoTabuleiro = new char[8][8];
	
	//Matriz de controle da cor das casas;
	private int[][] coresDasCasas = {
			{1,0,1,0,1,0,1,0},	
			{0,1,0,1,0,1,0,1},
			{1,0,1,0,1,0,1,0},	
			{0,1,0,1,0,1,0,1},
			{1,0,1,0,1,0,1,0},	
			{0,1,0,1,0,1,0,1},
			{1,0,1,0,1,0,1,0},	
			{0,1,0,1,0,1,0,1}
	};
	
	//Arrays que compilam as colunas e linhas de forma 
	//igual a de um tabuleiro, começando a contar a partir
	//do canto inferior esquerdo
	private char[] colunas = {'A','B','C','D','E','F','G','H'};
	private char[] linhas = {'8','7','6','5','4','3','2','1'};
	
	//Função para converter uma coordenada em posição;
	public String coordEmPosicao(int c, int l) {
		String posicao = String.format("%c%c",colunas[c],linhas[l]);
		return posicao;
	}
	
	//Função para converter uma posição em coordenada;
	public String posicaoEmCoord(String pos) {
		int coluna = this.acharPos(colunas, pos.charAt(0));
		int linha = this.acharPos(linhas, pos.charAt(1));
		return String.format("[%d,%d]",coluna,linha);
	}
	
	//Função feita para fazer uma binary search mesmo com
	//o array em ordem decrescente
	private int acharPos(char[] seq ,char c) {
		int cont = 0;
		for (int i = seq.length - 1; i >= 0; i--) {
			if (seq[i] == c) return cont;
			cont++;
		}
		return -1;
	}
}
