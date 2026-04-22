package com.xadrez.project.xadrez_java;

import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;
import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.jogador.Jogador;
import com.xadrez.project.xadrez_java.computador.Computador;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Scanner;

public class App extends Application
{	
	@Override
    public void start(Stage stage) {
        Label label = new Label("Tabuleiro de Xadrez v0.1");
        Scene scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
    		Scanner leitor = new Scanner(System.in);
    		Jogador jogador1 = new Jogador(0);
    		Jogador jogador2 = new Jogador(1);
    		Tabuleiro tabuleiro = new Tabuleiro(jogador1, jogador2);
    		Computador computador = new Computador();
    		jogador1.posInicialPecas(tabuleiro);
    		jogador2.posInicialPecas(tabuleiro);
    		tabuleiro.gerarTabuleiro();
    		char opcao;
    		do {
    			jogador1.realizarJogada(tabuleiro, leitor);
    			jogador2.realizarJogada(tabuleiro, computador);
    			tabuleiro.gerarTabuleiro();
    			System.out.println("Você deseja continuar? (s/n)");
    			opcao = leitor.next().charAt(0);
    		} while (opcao != 'n');
        launch();
    }
}
