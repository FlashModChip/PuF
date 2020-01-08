package components;

import javafx.geometry.Point2D;

/**
 * Component Tracking "AI" target, radius and aggro
 */
public class AIComponent extends Component<Boolean> {
    
    public Point2D target;
    public float detectionRadius;
    public boolean isActive;

    //public Component<Point2D> positionComponent;

    public AIComponent() {
        target = new Point2D(0,0);
        detectionRadius = 100;//??
        isActive = false;
    }
    
    public AIComponent(Point2D trgt,float detectionradius, boolean activated) {
        target = trgt;
        detectionRadius = detectionradius;
        isActive = activated;
    }

    public void changeRadius(float detectionradius) {
        detectionRadius = detectionradius;
    }

    public void changeTarget(Point2D trgt) {
        target = trgt;
    }

    @Override
    public Boolean getValue() {
        return isActive;
    }

    @Override
    public void setValue(Boolean value) {
        this.isActive = value;
    }
}
