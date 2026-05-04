package com.xadrez.project.xadrez_java.peca.rei;

import java.util.ArrayList;

import com.xadrez.project.xadrez_java.jogador.Jogador;
import com.xadrez.project.xadrez_java.peca.CorPeca;
import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.peca.TipoPeca;
import com.xadrez.project.xadrez_java.peca.torre.Torre;
import com.xadrez.project.xadrez_java.tabuleiro.Coluna;
import com.xadrez.project.xadrez_java.tabuleiro.Posicao;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;

public class Rei extends Peca {
	public Rei(CorPeca cor, Posicao posicaoAtual, Jogador jogadorResp) {
		super(cor, posicaoAtual, jogadorResp);
		this.setTipo(TipoPeca.REI);
		this.direcoes = new int[][] {{0,-1},{1,-1},{1,0},{1,1},{0,1},{-1,1},{-1,0},{-1,-1}};
		this.limMovimento = true;
	}

	@Override
	public boolean validarMovimento(Posicao posNova, Tabuleiro tabuleiro) {
		System.out.println(this.posInicial);
		this.calcularPossibilidades(tabuleiro);
		if(this.isPosInicial()) this.calcularRoque(tabuleiro);
		if (!this.getPosDeMovimento().contains(posNova)) {
			System.out.println("Movimento inválido! Tente novamente");
			return false;
		}
		return true;
	}
	
	@Override
	public Peca copiar() {
		return new Rei(this.cor, this.posicaoAtual, this.jogadorResp);
	}
	
	public void calcularRoque(Tabuleiro tabuleiro) {
		boolean roqueDireito = false, roqueEsquerdo = false;
		
		ArrayList<Peca> torres = new ArrayList<>();
		for (Peca peca : tabuleiro.getPecasNoTabuleiro()) {
			if(peca.getCor().equals(this.cor) && peca instanceof Torre) torres.add(peca);
		}
		
		//Retornar se o array for vazio
		if (torres.size() == 0) return;
		
		for (Peca torre : torres) {
			if (!torre.isPosInicial()) continue;
			int xRei = this.posicaoAtual.x();
			int xTorre = torre.getPosicaoAtual().x();
			int direcao = (xRei - xTorre) * -1;
			//xRei < xTorre = Torre direita (direção positiva)
			//xRei > xTorre = Torre esquerda (direção negativa)
			
			for (int i = 1; i < Math.abs(direcao); i++) {
				System.out.println(direcao);
				int dx;
				if(direcao > 0) dx = 1;
				else dx = -1;
				
				//Verificar se há uma peça no quadrado verificado
				Peca peca = tabuleiro.getPecaNoTabuleiro(xRei + (dx * i), this.getPosicaoAtual().y());
				if (peca != null) break;

				//Verificar se ele estiver no último quadrado antes da torre para então
				//validar o roque
				if (i == Math.abs(direcao) - 1) {
					if(dx > 0) roqueDireito = true;
					else roqueEsquerdo = true;
				}
			}
		}
		//Retornar caso não haja roque para ambos os lados
		if(!roqueDireito && !roqueEsquerdo) return;
		
		//Verificar qual roque é válido, lembrando que pode haver nos dois o
		//movimento válido
		if (roqueDireito) {
			Coluna novaColuna = Coluna.deIndice(this.posicaoAtual.x() + 2);
			this.posDeMovimento.add(new Posicao(novaColuna, this.posicaoAtual.l()));
		}
		if (roqueEsquerdo) {
			Coluna novaColuna = Coluna.deIndice(this.posicaoAtual.x() - 2);
			this.posDeMovimento.add(new Posicao(novaColuna, this.posicaoAtual.l()));
		}
	}	
}
