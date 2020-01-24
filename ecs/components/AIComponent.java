package components;

import javafx.geometry.Point2D;

/**
 * Component Tracking "AI" target, radius and aggro (reference to position
 * component)
 */
/**
 * Component for Tracking "AI" using radius and a reference to position component
 * @author Roman
 *
 */
public class AIComponent extends Component<Boolean> {

    public PositionComponent position;
    public float detectionRadius;
    public Boolean isActive;

    public AIComponent() {
        detectionRadius = 100;
        position = null;
        isActive = false;
    }

    public AIComponent(PositionComponent pos, float detectionradius, boolean activated) {
        position = pos;
        detectionRadius = detectionradius;
        isActive = activated;
    }

    public void changeRadius(float detectionradius) {
        detectionRadius = detectionradius;
    }


    /**
     * Position of referenced Position component
     * @return
     */
    public Point2D getPosition() {
        return position.getValue();
    }

    /**
     * Detection radius
     * @return
     */
    public float getRadius() {
        return detectionRadius;
    }

    @Override
    public Boolean getValue() {

        if (position == null) {
            isActive = false;
        }

        return isActive;
    }

    @Override
    public void setValue(Boolean value) {
        this.isActive = value;
    }
}
