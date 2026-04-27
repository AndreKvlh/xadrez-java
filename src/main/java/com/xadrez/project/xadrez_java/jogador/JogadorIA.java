package com.xadrez.project.xadrez_java.jogador;

import com.xadrez.project.xadrez_java.acoes.Jogada;
import com.xadrez.project.xadrez_java.computador.Computador;
import com.xadrez.project.xadrez_java.tabuleiro.Posicao;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;

public class JogadorIA extends Jogador{
	private final Computador computador;
	
	public JogadorIA(int jogador) {
		super(jogador);
		this.computador = new Computador();
	}

	@Override
	public Jogador copiar() {
		return new JogadorIA(this.jogador);
	}
	
	public Jogada realizarJogada(Jogador jogador, Tabuleiro tabuleiro) {
		//Atributos para obtenção das informações da jogada
		String inicio, destino;
		inicio = this.computador.selecionarPeca(getPecasAtuais(), tabuleiro);
		destino = this.computador.moverPeca(tabuleiro.getPeca(Posicao.converterStringEmPos(inicio)), tabuleiro);
		
		return new Jogada(inicio, destino);
	}


}
