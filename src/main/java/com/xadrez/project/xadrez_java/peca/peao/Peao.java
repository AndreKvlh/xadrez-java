package com.xadrez.project.xadrez_java.peca.peao;

import java.util.Scanner;

import com.xadrez.project.xadrez_java.jogador.Jogador;
import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.peca.bispo.Bispo;
import com.xadrez.project.xadrez_java.peca.cavalo.Cavalo;
import com.xadrez.project.xadrez_java.peca.rainha.Rainha;
import com.xadrez.project.xadrez_java.peca.torre.Torre;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;

public class Peao extends Peca{
	public Peao(char representacao, String posicao, Jogador jogadorResp) {
		super(representacao, posicao, jogadorResp);
		this.direcoes = jogadorResp.getJogador() == 0 ? new int[][]{{0,-1}} : new int[][]{{0,1}};
	}
	
	
	//Sobreposição do método que calcula as possibilidades de movimento
	@Override
	public void calcularPossibilidades(Tabuleiro tabuleiro) {
		//Limpa o ArrayList a fim de evitar que ele fique lotado com diversas posições e
		//possa criar um movimento errôneo para a peça
		if(!this.getPosDeMovimento().isEmpty()) this.getPosDeMovimento().clear();
		
		//Arrays que irão agrupar a coordenada atual e a nova da peça
		int[] coord = tabuleiro.posicaoEmCoord(getPosicao());
		int[] novaPos = {coord[0] + this.direcoes[0][0], coord[1] + this.direcoes[0][1]};
		
		//Vetores que indicarão aonde que o peão poderá atacar
		int[][] dirAmeaca = getJogadorResp().getJogador() == 0 ? new int[][]{{-1,-1},{1,-1}} : new int[][]{{1,1},{-1,1}};
		
		//Loop que verifica primeiro as casas de ameaça a fim de identificar alguma 
		//possibilidade de movimento e adicionar no ArrayList responsável
		for(int[] direcoes : dirAmeaca) {
			int[] posCaptura = {coord[0] + direcoes[0], coord[1] + direcoes[1]};
			if (posCaptura[0] > 7 || posCaptura[0] < 0 || posCaptura[1] > 7 || posCaptura[1] < 0) continue;
			if (tabuleiro.getPecaNoTabuleiro(posCaptura[0], posCaptura[1]) == null) continue;
			if (this.getJogadorResp() == tabuleiro.getPecaNoTabuleiro(posCaptura[0], posCaptura[1]).getJogadorResp()) continue;
			this.getPosDeMovimento().add(tabuleiro.coordEmPosicao(posCaptura[0], posCaptura[1]));
		}
		
		//Adiciona o próximo passo caso seja válido para movimento
		if (novaPos[0] > 7 || novaPos[0] < 0 || novaPos[1] > 7 || novaPos[1] < 0) return;
		if (tabuleiro.getPecaNoTabuleiro(novaPos[0], novaPos[1]) != null) return;
		this.getPosDeMovimento().add(tabuleiro.coordEmPosicao(novaPos[0], novaPos[1]));
		
		//Adiciona o passo duplo caso esteja na posição inicial
		if(this.isPosInicial()) {
			int[] passoAdicional = {coord[0] + (this.direcoes[0][0] * 2), coord[1] + (this.direcoes[0][1] * 2)};
			if (tabuleiro.getPecaNoTabuleiro(passoAdicional[0], passoAdicional[1]) != null) return;
			this.getPosDeMovimento().add(tabuleiro.coordEmPosicao(passoAdicional[0], passoAdicional[1]));
		}
	}


	@Override
	public Peca copiar() {
		return new Peao(this.representacao, this.posicao, this.jogadorResp);
	}
}	
