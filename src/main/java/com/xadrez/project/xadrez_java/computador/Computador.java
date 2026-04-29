package com.xadrez.project.xadrez_java.computador;

import java.util.ArrayList;

import com.xadrez.project.xadrez_java.acoes.Jogada;
import com.xadrez.project.xadrez_java.acoes.Movimento;
import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.regras.Validador;
import com.xadrez.project.xadrez_java.tabuleiro.Posicao;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;

public class Computador {
	//Método para selecionar uma peça que pode se mover
	public String selecionarPeca(ArrayList<Peca> pecas, Tabuleiro tabuleiro, Validador validador, Movimento movimento) {
		ArrayList<Peca> pecasPrioritarias = new ArrayList<>();
		ArrayList<Peca> pecasQueTemMovimento = new ArrayList<>();
		ArrayList<Peca> movimentoEmXeque = new ArrayList<>();
		
		boolean emXeque = false;
		Peca pecaSelecionada = null;
		
		for(Peca peca : pecas) {
			peca.calcularPossibilidades(tabuleiro);
			if(peca.getPosDeMovimento().size() <= 0) continue;
			for(Posicao pos : peca.getPosDeMovimento()) {
				if(validador.checarXeque(peca.getJogadorResp(), tabuleiro)) {
					emXeque = true;
					Jogada jogada = new Jogada(peca.getPosicaoAtual().posicao(), pos.posicao());
					if(movimento.validarJogada(peca.getJogadorResp(), jogada)) {
						movimentoEmXeque.add(peca);
						continue;
					}
				}
				if(tabuleiro.getPeca(pos) != null) {
					pecasPrioritarias.add(peca);
					continue;
				}
			}
			pecasQueTemMovimento.add(peca);
		}
		int aleatorio;
		if(emXeque && movimentoEmXeque.size() != 0) {
			aleatorio = (int) (Math.random() * movimentoEmXeque.size());
			pecaSelecionada = movimentoEmXeque.get(aleatorio);
		}
		else if (!emXeque) {
			if(pecasPrioritarias.size() != 0) {	
				aleatorio = (int) (Math.random() * pecasPrioritarias.size());
				pecaSelecionada = pecasPrioritarias.get(aleatorio);
			}
			else {
				aleatorio = (int) (Math.random() * pecasQueTemMovimento.size());
				pecaSelecionada = pecasQueTemMovimento.get(aleatorio);
			}
		}
		if (pecaSelecionada != null) return pecaSelecionada.getPosicaoAtual().posicao();
		return "";
	}
	
	//Método para selecionar a jogada com a peça escolhida
	public String moverPeca(Peca peca, Tabuleiro tabuleiro) {
		for(Posicao pos : peca.getPosDeMovimento()) {
			if(tabuleiro.getPeca(pos) != null) {
				return pos.posicao();
			}
		}
		int aleatorio = (int) (Math.random() * peca.getPosDeMovimento().size());
		Posicao novaPosicao = peca.getPosDeMovimento().get(aleatorio);
		return novaPosicao.posicao();
	}
}
