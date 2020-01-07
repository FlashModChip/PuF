package components;

import java.io.File;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * useful?
 */
public class Sprite extends Component<Rectangle> {
    private Image sprite;    
    private Point2D position;
    private String path;
    private Rectangle recimg;

    public Sprite(String path,Point2D pos) {    	
    	File file = new File(path+".png");
    	this.sprite = new Image(file.toURI().toString());

    	this.recimg = new Rectangle(pos.getX(), pos.getY());
    	this.recimg.setFill(new ImagePattern(sprite));
    	this.recimg.setHeight(40.0);
    	this.recimg.setWidth(40.0);
    	 this.position = pos;
    }
    
    public void translate(Point2D point){
        this.position.add(point);        
    }

    public void translate(double x, double y){
        this.recimg.setTranslateX(x);
        this.recimg.setTranslateY(y);
    }

    public void translateX(double x){
        this.recimg.setTranslateX(x);
    }

    public void translateY(double y){
        this.recimg.setTranslateY(y);
    }

    @Override
    public void setValue(Rectangle value) {
    	this.recimg =  value;
    }

    @Override
    public Rectangle getValue() {
        return this.recimg;
    }


}
