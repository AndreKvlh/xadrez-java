package com.xadrez.project.xadrez_java.gui;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class VisaoTabuleiro {
	private GridPane gridPane;
	
	public VisaoTabuleiro() {
		this.gridPane = new GridPane();
		this.gridPane.setAlignment(Pos.CENTER);
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
				if ((l + c) % 2 == 0) this.gerarQuadrado(corBranca, c, l);
				else this.gerarQuadrado(corPreta, c, l);
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
	
	public GridPane getGrid() {
		return gridPane;
	}
}
