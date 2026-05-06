package com.xadrez.project.xadrez_java;

import com.xadrez.project.xadrez_java.gui.VisaoTabuleiro;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application
{	
	@Override
    public void start(Stage stage) {
        //Label label = new Label("Tabuleiro de Xadrez v0.1");
		VisaoTabuleiro visaoTabuleiro = new VisaoTabuleiro();
        Scene scene = new Scene(visaoTabuleiro.getGrid(), 600, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
    		//Jogo jogo = new Jogo();
    		//jogo.rodarJogo();
        launch();
    }
}
