package components;

import javafx.geometry.Point2D;

/**
 * velocity component
 */
public class VelocityComponent extends Component<Point2D> {
    
	private Point2D velocity;

    public VelocityComponent() {
        velocity = new Point2D(0,0);
    }

    public VelocityComponent(Point2D velocity) {
        this.velocity = velocity;
    }

    public VelocityComponent(double xVector, double yVector, double zVector) {
        velocity = new Point2D(xVector, yVector);
    }

    public void addValue(Point2D addVelocity) {
        this.velocity = new Point2D(velocity.getX() + addVelocity.getX(), velocity.getY() + addVelocity.getY());
    }

    @Override
    public Point2D getValue() {
        return velocity;
    }

    @Override
    public void setValue(Point2D value) {
        this.velocity = value;
    }
}
