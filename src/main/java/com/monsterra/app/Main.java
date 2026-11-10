package com.monsterra.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label welcomeLabel = new Label("Welcome to MONSTERRA!");

        StackPane root = new StackPane(welcomeLabel);
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("MONSTERRA - Monster Battle RPG");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
