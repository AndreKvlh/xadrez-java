package com.xadrez.project.xadrez_java.jogador;

import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.peca.peao.Peao;
import com.xadrez.project.xadrez_java.peca.bispo.Bispo;
import com.xadrez.project.xadrez_java.peca.cavalo.Cavalo;
import com.xadrez.project.xadrez_java.peca.torre.Torre;
import com.xadrez.project.xadrez_java.peca.rainha.Rainha;
import com.xadrez.project.xadrez_java.peca.rei.Rei;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;

import java.util.ArrayList;

public class Jogador {
	//Define as peças de cada cor, diferenciadas pelo fato de serem
	//Maíusculas ou não
	private char[] pecasBrancas = {'P','T','C','B','K','Q'};
	private char[] pecasPretas = {'p','t','c','b','k','q'};
	
	//Variável que define qual jogador é
	private int jogador;
	
	//ArrayList que compila todas as peças que o jogador possui
	private ArrayList<Peca> pecasAtuais = new ArrayList<>();
	
	//ArrayList que compila todas as peças capturadas do adversário;
	private ArrayList<Peca> pecasCapturadas = new ArrayList<>();
	
	public Jogador(int jogador) {
		this.jogador = jogador;
	}
	
	//Método que define quais são as peças e suas posições
	public void definirPecas(Tabuleiro tabuleiro) {
		if (jogador == 0) {
			for (int linha = 6; linha < 8; linha++) {
				for (int coluna = 0; coluna < 8; coluna++) {
					if (linha == 6) {
						Peao peao = new Peao(pecasBrancas[0], tabuleiro.coordEmPosicao(coluna, linha));
						pecasAtuais.add(peao);
						tabuleiro.colocarPeca(peao, peao.getPosicao());
						continue;
					}
					switch (coluna) {
						case 0, 7:
							Torre torre = new Torre(pecasBrancas[1], tabuleiro.coordEmPosicao(coluna, linha));
							pecasAtuais.add(torre);
							tabuleiro.colocarPeca(torre, torre.getPosicao());
							break;
						case 1, 6:
							Cavalo cavalo = new Cavalo(pecasBrancas[2], tabuleiro.coordEmPosicao(coluna, linha));
							pecasAtuais.add(cavalo);
							tabuleiro.colocarPeca(cavalo, cavalo.getPosicao());
							break;
						case 2, 5:
							Bispo bispo = new Bispo(pecasBrancas[3], tabuleiro.coordEmPosicao(coluna, linha));
							pecasAtuais.add(bispo);
							tabuleiro.colocarPeca(bispo, bispo.getPosicao());
							break;
						case 3:
							Rei rei = new Rei(pecasBrancas[4], tabuleiro.coordEmPosicao(coluna, linha));
							pecasAtuais.add(rei);
							tabuleiro.colocarPeca(rei, rei.getPosicao());
							break;
						default:
							Rainha rainha = new Rainha(pecasBrancas[5], tabuleiro.coordEmPosicao(coluna, linha));
							pecasAtuais.add(rainha);
							tabuleiro.colocarPeca(rainha, rainha.getPosicao());
					}
				}
			}
			return;
		};
		for (int linha = 1; linha >= 0; linha--) {
			for (int coluna = 0; coluna < 8; coluna++) {
				if (linha == 1) {
					Peao peao = new Peao(pecasPretas[0], tabuleiro.coordEmPosicao(coluna, linha));
					pecasAtuais.add(peao);
					tabuleiro.colocarPeca(peao, peao.getPosicao());
					continue;
				}
				switch (coluna) {
					case 0, 7:
						Torre torre = new Torre(pecasPretas[1], tabuleiro.coordEmPosicao(coluna, linha));
						pecasAtuais.add(torre);
						tabuleiro.colocarPeca(torre, torre.getPosicao());
						break;
					case 1, 6:
						Cavalo cavalo = new Cavalo(pecasPretas[2], tabuleiro.coordEmPosicao(coluna, linha));
						pecasAtuais.add(cavalo);
						tabuleiro.colocarPeca(cavalo, cavalo.getPosicao());
						break;
					case 2, 5:
						Bispo bispo = new Bispo(pecasPretas[3], tabuleiro.coordEmPosicao(coluna, linha));
						pecasAtuais.add(bispo);
						tabuleiro.colocarPeca(bispo, bispo.getPosicao());
						break;
					case 3:
						Rei rei = new Rei(pecasPretas[4], tabuleiro.coordEmPosicao(coluna, linha));
						pecasAtuais.add(rei);
						tabuleiro.colocarPeca(rei, rei.getPosicao());
						break;
					default:
						Rainha rainha = new Rainha(pecasPretas[5], tabuleiro.coordEmPosicao(coluna, linha));
						pecasAtuais.add(rainha);
						tabuleiro.colocarPeca(rainha, rainha.getPosicao());
				}
			}
		}
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
}
