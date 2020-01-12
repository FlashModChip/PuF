package gameUi.task;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.Random;


public class MoveBackgroundThread extends Thread {

    private ImageView image;

    public MoveBackgroundThread(ImageView image) {
        this.image = image;
    }

    public PathTransition moveBG () {
        double fromX = image.getLayoutBounds().getWidth() / 2;
        double fromY = image.getLayoutBounds().getHeight() / 2;

        Path path = new Path();
        path.getElements().add(new MoveTo(fromX, fromY));
        path.getElements().add(new CubicCurveTo(new Random().nextInt(300), new Random().nextInt(300), new Random().nextInt(300), 90, new Random().nextInt(300), 80));
        path.getElements().add(new CubicCurveTo(new Random().nextInt(300), new Random().nextInt(300), new Random().nextInt(300), new Random().nextInt(300), 20, 10));

        PathTransition pt = new PathTransition();
        pt.setDuration(Duration.seconds(100));
        pt.setPath(path);
        pt.setNode(image);
        pt.setCycleCount(Timeline.INDEFINITE);
        pt.setAutoReverse(true);
        pt.play();

        return pt;
    }

}
