package com.xadrez.project.xadrez_java.peca;

import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;
import com.xadrez.project.xadrez_java.jogador.Jogador;

import java.util.ArrayList;

public abstract class Peca {
	//Atributo que representa a peça no tabuleiro
	protected char representacao;
	
	//Atributo que armazena a string da posição da peça no tabuleiro atualmente
	protected String posicao;
	
	//Atributo que armazena quem é o jogador responsável pela peça;
	protected Jogador jogadorResp;
	
	//Atributo que informa se essa peça saiu da posição inicial
	protected boolean posInicial;
	
	//ArrayList que armazena todas as possibilidades de movimento
	//da peça
	protected ArrayList<String> posDeMovimento = new ArrayList<>();
	
	//Variável que coordena para quantas direções a peça pode andar
	protected int[][] direcoes;
	
	//Variável que delimita o número de casas que a peça pode andar. Zero representa não
	//ter limite
	private int limMovimento;
	
	//Construtor
	public Peca(char representacao, String posicao, Jogador jogadorResp) {
		super();
		this.representacao = representacao;
		this.posicao = posicao;
		this.posInicial = true;
		this.jogadorResp = jogadorResp;
	}
	
	public abstract Peca copiar();
	
	//Método que calcula o movimento das peças e adiciona ao ArrayList
	public void calcularPossibilidades(Tabuleiro tabuleiro) {
		int[] coord = tabuleiro.posicaoEmCoord(this.posicao);
		posDeMovimento.add(tabuleiro.coordEmPosicao(coord[0], coord[0] + 1));
	}

	public String getPosicao() {
		return posicao;
	}

	public void setPosicao(String posicao) {
		this.posicao = posicao;
	}

	public boolean isPosInicial() {
		return posInicial;
	}

	public void setPosInicial(boolean posInicial) {
		this.posInicial = posInicial;
	}

	public int getLimMovimento() {
		return limMovimento;
	}

	public void setLimMovimento(int limMovimento) {
		this.limMovimento = limMovimento;
	}

	public char getRepresentacao() {
		return representacao;
	}

	public void setRepresentacao(char representacao) {
		this.representacao = representacao;
	}
	
	public Jogador getJogadorResp() {
		return jogadorResp;
	}
	
	public void setJogadorResp(Jogador jogadorResp) {
		this.jogadorResp = jogadorResp;
	}
	
	public ArrayList<String> getPosDeMovimento() {
		return posDeMovimento;
	}
}
