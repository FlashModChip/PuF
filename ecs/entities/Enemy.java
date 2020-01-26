package entities;

import java.util.UUID;

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
        init(0,0, null);
    }


    public Enemy(double x, double y) {
        super();
        init(x, y, null);
    }
    
    public Enemy(double x, double y, UUID room) {
        super();
        init(x, y,room);
    }

    /**
     * construction
     * @param x
     * @param y
     */
    private void init(double x, double y, UUID room) {

        PositionComponent tmp = new PositionComponent(x, y);
        addComponent(tmp);
        addComponent(new VelocityComponent());
        addComponent(new Sprite("pic/Enemy", new Point2D(175, 125),true));
        addComponent(new HealthComponent(5000.0));
        addComponent(new WeaponComponent(0.0));
        addComponent(new RenderComponent());
        addComponent(new AIComponent(tmp,150, true));
        addComponent(new FightingComponent());
        
        if(room != null) 
        {
        	addComponent(new RoomComponent(room));
        }
        
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
