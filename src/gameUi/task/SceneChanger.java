package gameUi.task;

import gameUi.Start;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class SceneChanger {


    public void changeScene(String fxmlFilename, int width, int height) throws Exception{
        Parent scene = FXMLLoader.load(getClass().getResource("../../resources/view/" + fxmlFilename +".fxml"));
        Start.getGuiStage().setScene(new Scene(scene, width, height));
    }

}
