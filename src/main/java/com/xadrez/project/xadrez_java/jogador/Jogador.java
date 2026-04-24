package com.xadrez.project.xadrez_java.jogador;

import java.util.ArrayList;

import com.xadrez.project.xadrez_java.peca.Peca;

public class Jogador {
	//Variável que define qual jogador é
	private int jogador;
	
	//Variável condicional que diz se o jogador está em xeque
	private boolean xeque = false;
	
	//ArrayList que compila todas as peças que o jogador possui
	private ArrayList<Peca> pecasAtuais = new ArrayList<>();
	
	//ArrayList que compila todas as peças capturadas do adversário;
	private ArrayList<Peca> pecasCapturadas = new ArrayList<>();
	
	public Jogador(int jogador) {
		this.jogador = jogador;
	}
	
	//Construtor de cópia de objeto
	public Jogador(Jogador outro) {
		this.jogador = outro.jogador;
		this.pecasAtuais = new ArrayList<>();
		this.pecasCapturadas = new ArrayList<>();
		this.xeque = outro.xeque;
	}

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
