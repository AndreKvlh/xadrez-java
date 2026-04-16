package com.xadrez.project.xadrez_java;

import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;
import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.jogador.Jogador;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

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
    		Tabuleiro tabuleiro = new Tabuleiro();
    		Jogador jogador1 = new Jogador(0);
    		Jogador jogador2 = new Jogador(1);
    		jogador1.posInicialPecas(tabuleiro);
    		jogador2.posInicialPecas(tabuleiro);
    		tabuleiro.gerarTabuleiro();
    		jogador1.getPecasAtuais().get(14).calcularPossibilidades(tabuleiro);
        launch();
    }
}
