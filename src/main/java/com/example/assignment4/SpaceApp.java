package com.example.assignment4;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SpaceApp extends Application
{
    public void start(Stage stage)
    {
        MainUI root = new MainUI();
        Scene scene = new Scene(root);
        stage.setTitle("Space App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }

}
