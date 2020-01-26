package components;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.File;

/**
 * useful!
 */
public class Sprite extends Component<Rectangle> {

    private Image sprite;
    private Point2D position;
    private String path;
    private Rectangle recimg;
    private double imageXPos;
    private int stepCounter;


    public Sprite(String path, Point2D pos, boolean animated) {
        File file = new File(path+".png");
        this.sprite = new Image(file.toURI().toString());

        this.recimg = new Rectangle(pos.getX(), pos.getY());

        if(animated) {
            this.recimg.setFill(new ImagePattern(sprite, -2.3, -1.0, 23, 9, true));
        } else {
            this.recimg.setFill(new ImagePattern(sprite));
        }
        this.recimg.setHeight(35);
        this.recimg.setWidth(35);
        this.position = pos;
        this.imageXPos = -2.30;
        this.stepCounter = 0;
    }

    public void translate(Point2D point){
        this.position.add(point);
    }

    public void translate(double x, double y){
        this.recimg.setTranslateX(x);
        this.recimg.setTranslateY(y);
    }

    public void animate(double xCordOnPic, double yCordOnPic) {
        this.recimg.setFill(new ImagePattern(sprite,xCordOnPic,yCordOnPic,23,9,true));
    }

    public void translateX(double x){
        this.recimg.setTranslateX(x);
    }

    public void translateY(double y){
        this.recimg.setTranslateY(y);
    }

    public Point2D getPosition() {
        return position;
    }

    public double getImageXPos() {
        return imageXPos;
    }

    public void setImageXPos(double imageXPos) {
        this.imageXPos = imageXPos;
    }

    public int getStepCounter() {
        return stepCounter;
    }

    public void setStepCounter(int stepCounter) {
        this.stepCounter = stepCounter;
    }

    @Override
    public void setValue(Rectangle value) {
        this.recimg =  value;
    }

    @Override
    public Rectangle getValue() {
        return this.recimg;
    }

    public Image getSprite() {
        return sprite;
    }

}
