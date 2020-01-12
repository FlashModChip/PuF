package gameUi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Start extends Application {

    private static Stage guiStage;

    public static Stage getGuiStage() {
        return guiStage;
    }


    //zum testen ohne Intro
    @Override
    public void start(Stage primaryStage) throws Exception{
        guiStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../resources/view/game.fxml"));
        guiStage.setTitle("DarkestCrawlerDebug");
        guiStage.setScene(new Scene(root, 900, 600));
        guiStage.show();
    }

    //mit Intro
//    @Override
//    public void start(Stage primaryStage) throws Exception{
//        guiStage = primaryStage;
//        Parent root = FXMLLoader.load(getClass().getResource("../resources/view/main.fxml"));
//        guiStage.setTitle("Darkest Crawler");
//        guiStage.setScene(new Scene(root, 600, 400));
//        guiStage.show();
//    }

    public static Node loadFXML(String fxmlFilename) {
        try {
            return FXMLLoader.load(Start.class.getClassLoader().getResource(fxmlFilename));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
