package entities;

import components.*;
import javafx.geometry.Point2D;

/**
 * enemy entity
 *
 * @author franzi
 */
public class Enemy extends Entity {


    public Enemy() {
        super();
        init(0,0);
    }


    public Enemy(double x, double y) {
        super();
        init(x, y);
    }

    /**
     * construction
     * @param x
     * @param y
     */
    private void init(double x, double y) {

        PositionComponent tmp = new PositionComponent(x, y);
        addComponent(tmp);
        addComponent(new VelocityComponent());
        addComponent(new Sprite("pic/Enemy", new Point2D(175, 125)));
        addComponent(new HealthComponent(5000.0));
        addComponent(new RenderComponent());
        addComponent(new AIComponent(tmp,100, true));
        addComponent(new FightingComponent());
    }

    /**
     * add material
     * @param material
     */
    /*
     * @Override public void setMaterial(PhongMaterial material) { ((ShapeComponent)
     * this.getComponent(ShapeComponent.class)).setMaterial(material); }
     */
}
