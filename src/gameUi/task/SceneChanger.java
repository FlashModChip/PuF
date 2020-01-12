package gameUi.task;

import gameUi.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class SceneChanger {


    public void changeScene(String fxmlFilename, int width, int height) throws Exception{
        Parent scene = FXMLLoader.load(getClass().getResource("../../resources/view/" + fxmlFilename +".fxml"));
        Main.getGuiStage().setScene(new Scene(scene, width, height));
    }

}
