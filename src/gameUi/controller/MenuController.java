// Song: November
// license Music: https://www.bensound.com

package gameUi.controller;

import application.Main;
import gameUi.Start;
import gameUi.task.MoveBackgroundThread;
import gameUi.task.PlayMusicThread;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

import static java.lang.Thread.currentThread;


public class MenuController {

    private MoveBackgroundThread mBgT;
    private PlayMusicThread pMT;

    private File soundOnImageSrc;
    private File soundOffImageSrc;
    private Image soundImage;

    private String musicSrc = "../../resources/music/bensound-november.mp3";


    @FXML
    public ImageView image;

    @FXML
    public Label titleLabel;

    @FXML
    public Label optionsButton;

    @FXML
    public Label exitButton;

    @FXML
    public ImageView soundButton;

    @FXML
    protected void initialize() {

        mBgT = new MoveBackgroundThread(image);
        pMT = new PlayMusicThread(musicSrc);

        mBgT.start();
        pMT.start();

        System.out.println("sound Thread isAlive: " + pMT.isAlive());
        pMT.startSound();
        mBgT.moveBG();

        System.out.println(pMT + " " + mBgT);
    }

    public void stopStartSoundButton() {

        if (pMT.getMediaPlayerStatus() != true) {
            soundOnImageSrc = new File("src/resources/images/soundButtonOn.png");
            soundImage = new Image(soundOnImageSrc.toURI().toString());
            pMT.start();
            pMT.startSound();

            System.out.println("sound Thread isAlive: " + pMT.isAlive());
        } else {
            soundOffImageSrc = new File("src/resources/images/soundButtonOff.png");
            soundImage = new Image(soundOffImageSrc.toURI().toString());
            pMT.stopSound();

            System.out.println("sound Thread isAlive: " + pMT.isAlive());
        }

        soundButton.setImage(soundImage);
        System.out.println("sound on" + pMT.getMediaPlayerStatus());
    }

    public void changeToGameScene () throws Exception {
        pMT.stopSound();
        changeScene("game");

//        System.out.println("sound Thread isAlive: " + pMT.isAlive());
//        System.out.println("movingBackground Thread isAlive: " + mBgT.isAlive());
    }

    public void changeScene(String fxmlFilename) throws Exception{
        Parent scene = FXMLLoader.load(getClass().getResource("../../resources/view/" + fxmlFilename +".fxml"));
        Start.getGuiStage().setScene(new Scene(scene, 900, 600));
        Start.getGuiStage().centerOnScreen();
    }
}
