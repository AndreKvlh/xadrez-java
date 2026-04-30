package com.xadrez.project.xadrez_java.historico;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.xadrez.project.xadrez_java.acoes.Jogada;
import com.xadrez.project.xadrez_java.jogador.Jogador;
import com.xadrez.project.xadrez_java.jogador.JogadorIA;
import com.xadrez.project.xadrez_java.peca.CorPeca;
import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.peca.peao.Peao;
import com.xadrez.project.xadrez_java.tabuleiro.Coluna;
import com.xadrez.project.xadrez_java.tabuleiro.Linha;
import com.xadrez.project.xadrez_java.tabuleiro.Posicao;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;

public class HistoricoTest {
	@Test
	public void testarVerificarHistorico() {
		//Inicia variáveis simulando um turno
		int turno = 1;
		Jogador jogador1 = new JogadorIA(0);
		Jogador jogador2 = new JogadorIA(1);
		Tabuleiro tabuleiro = new Tabuleiro(jogador1, jogador2);
		Peca peca = new Peao(CorPeca.BRANCA, new Posicao(Coluna.A,Linha.L2), jogador1);
		Jogada jogada = new Jogada("A2","A4");
		
		//Iremos criar o histórico e chamar o método para salvar o turno
		Historico historico = new Historico();
		historico.salvarTurno(turno, tabuleiro, jogador1, peca, jogada);
		
		//Teste para ver se ele salvou de fato o turno colocado
		assertNotNull(historico.getTurno(1), "Deverá confirmar que o turno foi salvo retornando um valor que não é nulo.");
	}
}
