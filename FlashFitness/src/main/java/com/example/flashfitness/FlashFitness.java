package com.example.flashfitness;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FlashFitness extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FlashFitness.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 874, 564);

        stage.setTitle("Menu");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}