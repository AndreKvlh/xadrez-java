package com.xadrez.project.xadrez_java;

import com.xadrez.project.xadrez_java.gui.VisaoTabuleiro;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application
{	
	@Override
    public void start(Stage stage) {
		VisaoTabuleiro visaoTabuleiro = new VisaoTabuleiro();
        Scene scene = new Scene(visaoTabuleiro.getGrid(), 600, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
