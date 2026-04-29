package com.xadrez.project.xadrez_java.tabuleiro;

import java.util.ArrayList;

import com.xadrez.project.xadrez_java.jogador.Jogador;
import com.xadrez.project.xadrez_java.peca.Peca;

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
	
	//Variáveis que armazenam as informações dos jogadores
	private Jogador[] jogadores;
	
	//Arrays que compilam as colunas e linhas de forma 
	//igual a de um tabuleiro, começando a contar a partir
	//do canto inferior esquerdo
	private char[] colunas = {'A','B','C','D','E','F','G','H'};
	private char[] linhas = {'8','7','6','5','4','3','2','1'};
	
	public Tabuleiro(Jogador j1, Jogador j2) {
		this.jogadores = new Jogador[] {j1,j2};
	};
	
	//Construtor que copia o tabuleiro a fim de testes virtuais
	public Tabuleiro(Tabuleiro tabuleiro) {
		//Copia os jogadores no estado atual
		this.jogadores = new Jogador[] {tabuleiro.getJogadores()[0].copiar(), tabuleiro.getJogadores()[1].copiar()};

		for (Jogador jogador : tabuleiro.getJogadores()) {
			for (Peca pecaOriginal : jogador.getPecasAtuais()) {
				Peca pecaCopia = pecaOriginal.copiar();
				
				Jogador donoNovo = (pecaOriginal.getJogadorResp() == tabuleiro.getJogadores()[0]) ? this.jogadores[0] : this.jogadores[1];
				pecaCopia.setJogadorResp(donoNovo);
				
				donoNovo.getPecasAtuais().add(pecaCopia);
				this.inserirPeca(pecaCopia);
			}
		}
		
	}
	
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
	
	//Alteração do método acima
	public void inserirPeca(Peca peca) {
		int[] coord = peca.getPosicaoAtual().getCoord();
		pecasNoTabuleiro[coord[1]][coord[0]] = peca;
	}
	
	//Método para limpar a posição que contém uma peça
	public void removerPeca(Posicao pos) {
		if (this.getPeca(pos) == null) return;
		int[] coord = pos.getCoord();
		pecasNoTabuleiro[coord[1]][coord[0]] = null;
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
				Peca peca = pecasNoTabuleiro[linha][coluna];
				char tipo = peca.getTipo().getSimbolo(peca.getCor().getCorPeca());
				System.out.print(tipo);
			}
			System.out.println("|");
		}
		System.out.println("-----------------");
	}
	
	//Obter informação da peça que está em uma posição específica
	public Peca getPecaNoTabuleiro (int linha, int coluna) {
		return this.pecasNoTabuleiro[coluna][linha];
	}
	
	//Método que substituirá o acima
	public Peca getPeca (Posicao pos) {
		int[] coord = pos.getCoord();
		return this.pecasNoTabuleiro[coord[1]][coord[0]];
	}
	
	//Obter a informação dos jogadores atuais
	public Jogador[] getJogadores() {
		return this.jogadores;
	}
	
	public ArrayList<Peca> getPecasNoTabuleiro() {
		ArrayList<Peca> pecas = new ArrayList<>();
		for (int linha = 0; linha < 8; linha++) {
			for (int coluna = 0; coluna < 8; coluna++) {
				if (this.getPecaNoTabuleiro(linha, coluna) == null) continue;
				Peca peca = this.getPecaNoTabuleiro(linha, coluna);
				pecas.add(peca);
			}
		}
		return pecas;
	}
}
