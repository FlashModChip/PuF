package components;

import javafx.geometry.Point2D;


/**
 * Component tracking AI target pos (reference to position component)
 * @author Roman
 *
 */
public class AITargetComponent extends Component<Boolean> {

    public PositionComponent position;
    public Boolean isActive;    

    public AITargetComponent() {
        position = null;
        isActive = false;
    }

    public AITargetComponent(PositionComponent pos, boolean activated) {
        position = pos;
        isActive = activated;
    }
    
    /**
     * Position of referenced Position component
     * @return
     */
    public Point2D getPosition() {
        return position.getValue();
    }    
    
    @Override
    public Boolean getValue() {

        if(position == null)
        {
          isActive = false;
        }

        return isActive;
    }
   
    @Override
    public void setValue(Boolean value) {
        this.isActive = value;
    }
}