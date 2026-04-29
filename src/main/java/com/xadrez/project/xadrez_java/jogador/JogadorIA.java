package com.xadrez.project.xadrez_java.jogador;

import com.xadrez.project.xadrez_java.acoes.Jogada;
import com.xadrez.project.xadrez_java.acoes.Movimento;
import com.xadrez.project.xadrez_java.computador.Computador;
import com.xadrez.project.xadrez_java.regras.Validador;
import com.xadrez.project.xadrez_java.tabuleiro.Posicao;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;

public class JogadorIA extends Jogador{
	private final Computador computador;
	private final Validador validador;
	private Movimento movimento;
	
	public JogadorIA(int jogador) {
		super(jogador);
		this.computador = new Computador();
		this.validador = new Validador();
		this.movimento = null;
	}

	@Override
	public Jogador copiar() {
		return new JogadorIA(this.jogador);
	}
	
	public Jogada realizarJogada(Tabuleiro tabuleiro) {
		//Atributos para obtenção das informações da jogada
		String inicio, destino;
		this.movimento = new Movimento(tabuleiro);
		inicio = this.computador.selecionarPeca(getPecasAtuais(), tabuleiro, this.validador, this.movimento);
		if (inicio.isEmpty()) return null;
		
		destino = this.computador.moverPeca(tabuleiro.getPeca(Posicao.converterStringEmPos(inicio)), tabuleiro);
		
		return new Jogada(inicio, destino);
	}


}
