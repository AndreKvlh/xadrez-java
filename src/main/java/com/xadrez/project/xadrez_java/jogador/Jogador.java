package com.xadrez.project.xadrez_java.jogador;

import java.util.ArrayList;

import com.xadrez.project.xadrez_java.acoes.Jogada;
import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;

public abstract class Jogador {
	//Variável que define qual jogador é
	protected int jogador;
	
	//Variável condicional que diz se o jogador está em xeque
	protected boolean xeque = false;
	
	//ArrayList que compila todas as peças que o jogador possui
	protected ArrayList<Peca> pecasAtuais = new ArrayList<>();
	
	//ArrayList que compila todas as peças capturadas do adversário;
	protected ArrayList<Peca> pecasCapturadas = new ArrayList<>();
	
	public Jogador(int jogador) {
		this.jogador = jogador;
		this.pecasAtuais = new ArrayList<>();
		this.pecasCapturadas = new ArrayList<>();
		this.xeque = false;
	}
	
	//Construtor de cópia de objeto
	/*public Jogador(Jogador outro) {
		this.jogador = outro.jogador;
		this.pecasAtuais = new ArrayList<>();
		this.pecasCapturadas = new ArrayList<>();
		this.xeque = outro.xeque;
	}*/
	
	public abstract Jogador copiar();
	
	//Método que irá realizar a jogada, fazendo todas as verificações necessárias
	public abstract Jogada realizarJogada(Tabuleiro tabuleiro); 

	public int getJogador() {
		return jogador;
	}

	public void setJogador(int jogador) {
		this.jogador = jogador;
	}

	public ArrayList<Peca> getPecasAtuais() {
		return pecasAtuais;
	}

	public void setPecasAtuais(ArrayList<Peca> pecasAtuais) {
		this.pecasAtuais = pecasAtuais;
	}
	
	public void setPeca(Peca peca) {
		this.pecasAtuais.add(peca);
	}
	
	public ArrayList<Peca> getPecasCapturadas() {
		return pecasCapturadas;
	}
	
	public boolean isXeque() {
		return xeque;
	}
	
	public void setXeque(boolean xeque) {
		this.xeque = xeque;
	}
}
