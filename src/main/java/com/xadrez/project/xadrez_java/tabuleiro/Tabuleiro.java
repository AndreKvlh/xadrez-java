package com.xadrez.project.xadrez_java.tabuleiro;

import com.xadrez.project.xadrez_java.peca.Peca;

import java.util.Arrays;
import java.util.Collections;

public class Tabuleiro {
	//Matriz de controle das peças e movimentos;
	private Peca[][] pecasNoTabuleiro = new Peca[8][8];
	
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
		String posicao = String.format("%c%c",this.colunas[c],this.linhas[l]);
		return posicao;
	}
	
	//Função para converter uma posição em coordenada;
	public int[] posicaoEmCoord(String pos) {
		int coluna = this.acharCoord(this.colunas, pos.charAt(0));
		int linha = this.acharCoord(this.linhas, pos.charAt(1));
		int[] coord = {coluna, linha};
		return coord;
	}
	
	//Função feita para fazer uma binary search mesmo com
	//o array em ordem decrescente
	private int acharCoord(char[] seq ,char c) {
		for (int i = 0; i < seq.length; i++) {
			if (seq[i] == c) return i;
		}
		return -1;
	}
	
	//Coloca a peça na posição designada
	public void colocarPeca(Peca p, String pos) {
		int[] coord = this.posicaoEmCoord(pos);
		pecasNoTabuleiro[coord[1]][coord[0]] = p;
	}
	
	//Gerar o tabuleiro no console
	public void gerarTabuleiro() {
		for (int linha = 0; linha < 8; linha++) {
			System.out.println("-----------------");
			for (int coluna = 0; coluna < 8; coluna++) {
				System.out.print("|");
				if (this.pecasNoTabuleiro[linha][coluna] == null) {
					System.out.print(" ");
					continue;
				}
				System.out.print(pecasNoTabuleiro[linha][coluna].getRepresentacao());
			}
			System.out.println("|");
		}
		System.out.println("-----------------");
	}
	
	//Obter informação da peça que está em uma posição específica
	public Peca getPecaNoTabuleiro (int linha, int coluna) {
		return this.pecasNoTabuleiro[coluna][linha];
	}
}
