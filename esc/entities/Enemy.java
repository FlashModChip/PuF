package entities;

import components.KeyInputComponent;
import components.PositionComponent;
import components.ShapeComponent;
import components.VelocityComponent;

/**
 * player entity
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
        //addComponent(new NameComponent(name));
        addComponent(new PositionComponent(x, y));
        addComponent(new VelocityComponent());               
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
