package com.xadrez.project.xadrez_java.jogador;

import java.util.Scanner;

import com.xadrez.project.xadrez_java.acoes.Jogada;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;

public class JogadorHumano extends Jogador {
	private final Scanner leitor;
	
	public JogadorHumano(int jogador, Scanner leitor) {
		super(jogador);
		this.leitor = leitor;
	}
	
	public Jogador copiar() {
		return new JogadorHumano(this.jogador, this.leitor);
	}
	
	@Override
	public Jogada realizarJogada(Tabuleiro tabuleiro) {
		//Atributos para obtenção das informações da jogada
		String inicio, destino;
		
		System.out.print("Digite a posição referente a peça que você quer selecionar: ");
		inicio = this.leitor.next();
		System.out.println();
		System.out.print("Digite a posição na qual você quer mover a peça: ");
		destino = this.leitor.next();
		
		return new Jogada(inicio, destino);
	}
}
