package entities;

import components.*;
import javafx.geometry.Point2D;

/**
 * player entity
 */
public class Player extends Entity {


    public Player() {
        super();
        init(0,0);
    }


    public Player(double x, double y) {
        super();
        init(x, y);
    }

    /**
     * construction
     * @param x
     * @param y
     */
    private void init(double x, double y) {
        //addComponent(new NameComponent(name));

        //Stored, that the reference can be also given to AI component
        PositionComponent tmp = new PositionComponent(x, y);

        addComponent(tmp);
        addComponent(new VelocityComponent());
        addComponent(new KeyInputComponent());
        addComponent(new HealthComponent(5000.0));
        addComponent(new WeaponComponent(0.0));
        addComponent(new Sprite("pic/1Stehen", new Point2D(x,y)));
        addComponent(new RenderComponent());
        addComponent(new AITargetComponent(tmp, true));
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
