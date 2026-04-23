package com.xadrez.project.xadrez_java.peca;

import java.util.ArrayList;

import com.xadrez.project.xadrez_java.jogador.Jogador;
import com.xadrez.project.xadrez_java.tabuleiro.Coluna;
import com.xadrez.project.xadrez_java.tabuleiro.Linha;
import com.xadrez.project.xadrez_java.tabuleiro.Posicao;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;

public abstract class Peca {
	//Atributo que representa a peça no tabuleiro
	protected char representacao;
	
	//Atributos que substitui o acima
	protected TipoPeca tipo;
	protected CorPeca cor;
	
	//Atributo que armazena a string da posição da peça no tabuleiro atualmente
	protected String posicao;
	
	//Atributo que substitui o acima
	protected Posicao posicaoAtual;
	
	//Atributo que armazena quem é o jogador responsável pela peça;
	protected Jogador jogadorResp;
	
	//Atributo que informa se essa peça saiu da posição inicial
	protected boolean posInicial;
	
	//ArrayList que armazena todas as possibilidades de movimento
	//da peça
	protected ArrayList<Posicao> posDeMovimento = new ArrayList<>();
	
	//Variável que coordena para quantas direções a peça pode andar
	protected int[][] direcoes;
	
	//Variável que delimita o número de casas que a peça pode andar. Zero representa não
	//ter limite
	protected boolean limMovimento;
	
	//Construtor
	public Peca(CorPeca cor, Posicao posicaoAtual, Jogador jogadorResp) {
		super();
		this.cor = cor;
		this.posicaoAtual = posicaoAtual;
		this.posInicial = true;
		this.jogadorResp = jogadorResp;
	}
	
	public abstract Peca copiar();
	
	public boolean validarMovimento(Posicao posNova, Tabuleiro tabuleiro) {
		this.calcularPossibilidades(tabuleiro);
		if (!this.getPosDeMovimento().contains(posNova)) {
			System.out.println("Movimento inválido! Tente novamente");
			return false;
		}
		return true;
	}
	
	public void calcularPossibilidades(Tabuleiro tabuleiro) {
		if(!getPosDeMovimento().isEmpty()) getPosDeMovimento().clear();
		int[] coord = this.posicaoAtual.getCoord();
		int distancia = !this.limMovimento ? 8 : 2;
		for(int[] direcoes : this.direcoes) {
			for (int i = 1; i < distancia; i++) {
				int colAtual = coord[0] + (i * direcoes[0]);
				int linAtual = coord[1] + (i * direcoes[1]);
				if (colAtual > 7 || colAtual < 0 || linAtual > 7 || linAtual < 0) break;
				Posicao posAtual = new Posicao(Coluna.deIndice(colAtual), Linha.deIndice(linAtual));
				Peca conteudoPeca = tabuleiro.getPeca(posAtual);
				if (conteudoPeca != null) {
					if (this.getCor().equals(conteudoPeca.getCor())) break;
					getPosDeMovimento().add(posAtual);
					break;
				}
				getPosDeMovimento().add(posAtual);
			}
		}
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
	
	public ArrayList<Posicao> getPosDeMovimento() {
		return posDeMovimento;
	}

	public Posicao getPosicaoAtual() {
		return posicaoAtual;
	}

	public void setPosicaoAtual(Posicao posicaoAtual) {
		this.posicaoAtual = posicaoAtual;
	}

	public TipoPeca getTipo() {
		return tipo;
	}

	public void setTipo(TipoPeca tipo) {
		this.tipo = tipo;
	}

	public CorPeca getCor() {
		return cor;
	}

	public void setCor(CorPeca cor) {
		this.cor = cor;
	}
}
