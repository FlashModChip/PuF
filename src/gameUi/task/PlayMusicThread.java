package gameUi.task;

import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

public class PlayMusicThread extends Thread {

    private URL resource;
    private Media media;
    private Boolean mediaPlayerStatus = true;
    private MediaPlayer mediaPlayer;

    public PlayMusicThread(String mediaUrl) {
        this.resource = getClass().getResource(mediaUrl);
        this.media = new Media(resource.toString());
        this.mediaPlayer = new MediaPlayer(this.media);
        this.mediaPlayer.setCycleCount(Timeline.INDEFINITE);
//        this.mediaPlayer.setOnRepeat(true);
    }

    public void startSound() {
        mediaPlayer.play();
        mediaPlayerStatus = true;
    }

    public void stopSound(){
        mediaPlayer.stop();
        mediaPlayerStatus = false;
    }
//    public void stopStartSound() {
//        if (mediaPlayerStatus == true) {
//
//        } else {
//            mediaPlayer.play();
//            mediaPlayerStatus = true;
//        }
//    }

    public Boolean getMediaPlayerStatus() {
        return mediaPlayerStatus;
    }

}
