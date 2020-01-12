package gameUi.controller;

import gameUi.Start;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class StartController {

    // singleton
    private static StartController instance;

    // singleton access
    public static StartController getInstance() {
        return instance;
    }

    @FXML
    VBox viewHolder;

    @FXML
    Label textLabel;

    public void initialize() throws Exception {
        instance = this;
        textLabel.setText("PuF Projekt 19/20");
        System.out.println(textLabel);
        FadeTransition a = playFade();
        a.play();

        a.setOnFinished (event -> {
            System.out.println(textLabel.getText());
            textLabel.setText("F. Lehman, R. Börnert, R. Bauer");

            FadeTransition b = null;
            try {
                b = playFade();
                b.play();
            } catch (Exception e) {
                e.printStackTrace();
            }

            b.setOnFinished(event1 -> {
                try {
                    changeScene("menu",600,400);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
    }

    public void changeView(String fxmlFilename) {
        Node view = Start.loadFXML("../resources/view/" + fxmlFilename + ".fxml");
        viewHolder.getChildren().setAll(view); // clears the list of child elements and adds the view as a new child element
    }

    public void changeScene(String fxmlFilename, int width, int height) throws Exception{
        Parent scene = FXMLLoader.load(getClass().getResource("../../resources/view/" + fxmlFilename +".fxml"));
        Start.getGuiStage().setScene(new Scene(scene, width, height));
    }

    public FadeTransition playFade() throws Exception {
        System.out.println("playFade Transition");
        FadeTransition ft = new FadeTransition(Duration.millis(1000), viewHolder);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setCycleCount(2);
        ft.setAutoReverse(true);
//        ft.play();
        return ft;
    }
}
