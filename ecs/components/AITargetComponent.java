package components;

import javafx.geometry.Point2D;

/**
 * Component tracking AI target pos (reference to position component)
 */
public class AITargetComponent extends Component<Point2D> {
    
    public PositionComponent position;    
    public boolean isActive;

    //public Component<Point2D> positionComponent;

    public AITargetComponent() {
    	position = new PositionComponent();        
        isActive = true;
    }
    
    public AITargetComponent(PositionComponent pos, boolean activated) {
    	position = pos;        
        isActive = activated;
    }   


    @Override
    public Point2D getValue() {
        return position.getValue();
    }

    @Override
    public void setValue(Point2D value) {
        this.position.setValue(value); 
    }
}
