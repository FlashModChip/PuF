package components;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

/**
 * collider component (2D-Shape only)
 */
public class ColliderComponent extends Component<Rectangle> 
{
    private Rectangle shape;
    
    //private Color color = Color.TRANSPARENT;
    //Maybe PNG-Tile-Property(Path?)

    public ColliderComponent(double width, double height) 
    {
    	Rectangle shape = new Rectangle(width, height);
    	
        //PhongMaterial material = new PhongMaterial();
        //material.setDiffuseColor(color);
        //shape.setMaterial(material);
    	
        this.shape = shape;
    }

   
    public void translate(double x, double y){
        this.shape.setTranslateX(x);
        this.shape.setTranslateY(y);      
    }
    
    public void translate(Point2D point){
        this.shape.setTranslateX(point.getX());
        this.shape.setTranslateY(point.getY());      
    }

    public void translateX(double x){
        this.shape.setTranslateX(x);
    }

    public void translateY(double y){
        this.shape.setTranslateY(y);
    }    

	/* Unnecessary (probably :-))
	 * public void rotate(Point3D rotation){ Rotate xRotate = new
	 * Rotate(rotation.getX(), Rotate.X_AXIS); Rotate yRotate = new
	 * Rotate(rotation.getY(), Rotate.Y_AXIS); Rotate zRotate = new
	 * Rotate(rotation.getZ(), Rotate.Z_AXIS); shape.getTransforms().clear();
	 * shape.getTransforms().addAll(xRotate,yRotate,zRotate); }
	 */


    @Override
    public Rectangle getValue() {
        return this.shape;
    }

	@Override
	public void setValue(Rectangle value) {		
		this.shape = value;
		
	}
}
