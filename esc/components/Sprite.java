package components;

import java.io.File;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

/**
 * useful?
 */
public class Sprite extends Component<Image> {
    private Image sprite;    
    private Point2D position;
    private String path;

    public Sprite(String path,Point2D pos) {    	
    	File file = new File(path+".png");
    	sprite = new Image(file.toURI().toString());   
    	 position = pos;
    }
    
    public void translate(Point2D point){
        this.position.add(point);        
    }

    @Override
    public void setValue(Image value) {
    	this.sprite =  value;
    }

    @Override
    public Image getValue() {
        return this.sprite;
    }
}
