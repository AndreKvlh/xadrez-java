package com.xadrez.project.xadrez_java.historico;

import java.util.ArrayList;
import java.util.List;

import com.xadrez.project.xadrez_java.acoes.Jogada;
import com.xadrez.project.xadrez_java.jogador.Jogador;
import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;

public class Historico {
	private List<Turno> historico;
	
	public Historico() {
		this.historico = new ArrayList<>();
	}
	
	public void salvarTurno(
			Integer turno, 
			Tabuleiro tabuleiro, 
			Jogador jogador, 
			Peca peca, 
			Jogada jogada) {
		//Copiar tabuleiro, jogador, peça e jogada
		Tabuleiro cpTabuleiro = new Tabuleiro(tabuleiro);
		Jogador cpJogador = jogador.copiar();
		Peca cpPeca = peca.copiar();
		Jogada cpJogada = jogada.copiar();
		
		//Cria uma instância do record de turno
		Turno cpTurno = new Turno(turno, cpTabuleiro, cpJogador, cpPeca, cpJogada);
		
		//Adiciona ao array de histórico o turno copiado
		historico.add(cpTurno);
	}
	
	public Turno getTurno(int turnoProcurado) {
		for(Turno turno : this.historico) {
			if(turno.turno() == turnoProcurado) return turno;
		}
		return null;
	}
	
	public Turno getUltimoTurno() {
		return this.historico.getLast();
	}
	
	//TO DO: Trocar array por ArrayList
	public Turno[] getUltimosSeisTurnos() {
		Turno[] ultimosTurnos = new Turno[6];
		for(int i = 0; i <= 5; i++) {
			if(this.historico.size() - i < 0) break;
			int ultimoTurno = this.historico.size() - i; 
			ultimosTurnos[i] = this.getTurno(ultimoTurno);
		}
		return ultimosTurnos;
	}
}
