package com.xadrez.project.xadrez_java;

import com.xadrez.project.xadrez_java.jogo.Jogo;

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
    		Jogo jogo = new Jogo();
    		jogo.rodarJogo();
        launch();
    }
}
