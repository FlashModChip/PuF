package entities;

import com.iando.client.ecs.components.*;

import components.KeyInputComponent;
import components.NameComponent;
import components.PositionComponent;
import components.ShapeComponent;
import components.VelocityComponent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

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
        addComponent(new PositionComponent(x, y));
        addComponent(new VelocityComponent());
        addComponent(new KeyInputComponent());       
        addComponent(new ShapeComponent(40, 40));
        //addComponent(new RenderComponent());
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
