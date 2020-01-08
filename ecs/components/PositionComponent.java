package components;

import javafx.geometry.Point2D;

import java.util.List;



/**
 * position component
 */
public class PositionComponent extends Component<Point2D> {
    
	private Point2D position;

    public PositionComponent() {
        this.position = new Point2D(0, 0);
    }

    public PositionComponent(double x, double y) {
        this.position = new Point2D(x, y);
    }

    @Override
    public Point2D getValue() {
        return position;
    }

    @Override
    public void setValue(Point2D value) {
        this.position = value;
    }

	
}
