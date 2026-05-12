package com.xadrez.project.xadrez_java.gui;

import java.util.ArrayList;
import java.util.Optional;

import com.xadrez.project.xadrez_java.acoes.Jogada;
import com.xadrez.project.xadrez_java.acoes.Movimento;
import com.xadrez.project.xadrez_java.alertas.EmXequeException;
import com.xadrez.project.xadrez_java.jogador.JogadorIA;
import com.xadrez.project.xadrez_java.jogo.Jogo;
import com.xadrez.project.xadrez_java.jogo.Status;
import com.xadrez.project.xadrez_java.peca.CorPeca;
import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.tabuleiro.Posicao;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class VisaoTabuleiro {
	private GridPane gridPane;
	private Jogo jogo;
	private Tabuleiro tabuleiro;
	private Movimento movimento;
	
	public VisaoTabuleiro() {
		this.gridPane = new GridPane();
		this.jogo = new Jogo();
		this.tabuleiro = this.jogo.getTabuleiro();
		this.movimento = this.jogo.getMovimento();
		this.gridPane.setAlignment(Pos.CENTER);
		this.jogo.iniciarJogo();
		this.gerarTabuleiro();
		
	}
	
	public void gerarTabuleiro() {
		String corBranca = "#e6cd95";
		String corPreta = "#453118";
		String chars = "ABCDEFGH";
		String nums = "87654321";
		
		for (int l = 0; l < 10; l++) {
			for (int c = 0; c < 10; c++) {
				if ((l == 0 || l == 9) && (c == 0 || c == 9)) {
					System.out.println(" ");
					continue;
				}
				if (l == 0 || l == 9 || c == 0 || c == 9) {
					if(l == 0 || l == 9) {
						this.gerarTexto(chars.charAt(c - 1), c, l);
						continue;
					}
					this.gerarTexto(nums.charAt(l - 1), c, l);
					continue;
				}
				Peca peca = tabuleiro.getPecaNoTabuleiro(l - 1, c - 1);
				if ((l + c) % 2 == 0) this.gerarQuadrado(corBranca, l, c);
				else this.gerarQuadrado(corPreta, l, c);
				if (peca != null) this.gerarPeca(peca, l, c);
			}
		}
	}
	
	public void gerarQuadrado(String cor, int c, int l) {
		Rectangle quadrado = new Rectangle();
		quadrado.setWidth(70);
		quadrado.setHeight(70);
		quadrado.setFill(Paint.valueOf(cor));
		
		gridPane.add(quadrado, c, l);
	}
	
	public void gerarTexto(char ch, int c, int l) {
		Text texto = new Text(Character.toString(ch));
		texto.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		StackPane pane = new StackPane(texto);
		StackPane.setAlignment(texto, Pos.CENTER);
		pane.setMinWidth(20);
		pane.setMinHeight(20);
		gridPane.add(pane, c, l);
	}
	
	public void gerarPeca(Peca p, int c, int l) {
		Label peca = new Label(p.getUnicode());
		peca.setAlignment(Pos.CENTER);
		peca.setMaxWidth(70);
		peca.setMaxHeight(70);
		peca.setFont(Font.font("Segoe UI Symbol", FontWeight.BOLD, 45));
		
		DropShadow contorno = new DropShadow();
		contorno.setRadius(0.5);
		contorno.setOffsetX(0);
		contorno.setOffsetY(0);
		contorno.setColor(Color.WHITE);
		
		peca.setEffect(contorno);
		
		peca.setOnMouseClicked(event -> verificarPossibilidades(l, c));
		
		gridPane.add(peca, c, l);
	}
	
	public void verificarPossibilidades(int l, int c) {
		Peca peca = this.tabuleiro.getPecaNoTabuleiro(c - 1, l - 1);
		if(peca.getCor() == CorPeca.PRETA) return;
		peca.calcularPossibilidades(this.tabuleiro);
		this.realcarEscolhas(peca.getPosDeMovimento(), peca);
	};
	
	public void realcarEscolhas(ArrayList<Posicao> posMov, Peca peca) {
		gridPane.getChildren().clear();
		this.gerarTabuleiro();
		for(Posicao pos : posMov) {
			Peca pecaAdv = this.tabuleiro.getPeca(pos);
			Rectangle quadrado = new Rectangle();
			quadrado.setWidth(70);
			quadrado.setHeight(70);
			boolean captura = pecaAdv != null;
			String cor = captura ? "#BA0000" : "#E3C100";
			quadrado.setFill(Paint.valueOf(cor));
			quadrado.setOnMouseClicked(event -> realizarJogada(peca, pos));
			gridPane.add(quadrado, pos.x() + 1, pos.y() + 1);
		}
	};
	
	public void realizarJogada(Peca peca, Posicao pos) {
		/*Peca p = this.tabuleiro.getPeca(pos);
		if (captura) this.jogo.executarCaptura(peca, p);
		this.movimento.executarMovimento(peca, peca.getPosicaoAtual(), pos, this.tabuleiro);*/
		try {
			Jogada jogada = new Jogada(peca.getPosicaoAtual(), pos);
			this.jogo.executarJogada(jogada);
			this.checarEstadoJogo();
		} catch (EmXequeException e) {
			this.exibirAlerta(e.getMessage());
		}
	};
	
	public void jogadaIA() {
		gridPane.setDisable(true);
		
		PauseTransition pausa = new PauseTransition(Duration.seconds(1.5));
		
		pausa.setOnFinished(event -> {
			Jogada jogada = this.jogo.JogadaDaIA();
			this.jogo.executarJogada(jogada);
			this.checarEstadoJogo();
			gridPane.setDisable(false);
		});
		
		pausa.play();
	}
	
	private void exibirAlerta(String msg) {
		Alert alerta = new Alert(Alert.AlertType.WARNING);
		alerta.setTitle("Aviso");
		alerta.setHeaderText(null);
		alerta.setContentText(msg);
		alerta.showAndWait();
	}
	
	private void exibirMensagemFimDeJogo(String msg) {
		Alert alerta = new Alert(Alert.AlertType.INFORMATION);
		alerta.setTitle("Fim de Jogo");
		alerta.setHeaderText(null);
		alerta.setContentText(msg);
		
		ButtonType reiniciar = new ButtonType("Reiniciar");
		ButtonType sair = new ButtonType("Sair", ButtonBar.ButtonData.CANCEL_CLOSE);
		
		alerta.getButtonTypes().setAll(reiniciar,sair);
		
		Optional<ButtonType> resultado = alerta.showAndWait();
		
		if(resultado.isPresent()) {
			if(resultado.get() == reiniciar) {
				this.reiniciarTabuleiro();
			}
			else if (resultado.get() == sair) System.exit(0);
		}
	}
	
	public void reiniciarTabuleiro() {
		gridPane.setDisable(false);
		this.jogo.reiniciarJogo();
		gridPane.getChildren().clear();
		this.gerarTabuleiro();
	}
	
	public void checarEstadoJogo() {
		gridPane.getChildren().clear();
		this.gerarTabuleiro();
		
		int jogadorAtual = this.jogo.getJogadorAtual() == 0 ? 1 : 0;
		this.jogo.setJogadorAtual(jogadorAtual);
		Status statusAtual = this.jogo.getStatus();
		
		if(statusAtual != Status.EM_ANDAMENTO) {
			String mensagem;
			if(statusAtual == Status.XEQUE_MATE) mensagem = String.format("Jogador %d sofreu xeque-mate!", this.jogo.getJogadorAtual() + 1);
			else if(statusAtual == Status.AFOGAMENTO) mensagem = "Jogo encerrado por afogamento (stalemate)!";
			else mensagem = "Jogo encerrado por empate!";
			this.exibirMensagemFimDeJogo(mensagem);
			gridPane.setDisable(true);
		} else if (this.jogo.getJogador() instanceof JogadorIA) {
			this.jogadaIA();
		}
	}
	
	public GridPane getGrid() {
		return gridPane;
	}
}
