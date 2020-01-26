package components;

import javafx.geometry.Point2D;

/**
 * position component
 */
public class PositionComponent extends Component<Point2D> {

    private Point2D position;

    // animation
    private boolean isMoving = false;

    // right if false (direction)
    private boolean movingLeft = false;


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

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

}
