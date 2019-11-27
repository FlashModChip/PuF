package entities;

import java.util.HashMap;

import components.KeyInputComponent;
import components.PositionComponent;
import components.ShapeComponent;
import components.VelocityComponent;

/**
 * item entity
 */
public class Item extends Entity {

	String name;
	//Stats modified
	HashMap<String, Integer> modStats;
	
    public Item() {
        super();
        init(0,0);
    }

   
    public Item(double x, double y, String name, HashMap<String, Integer> modsts) {
        super();
        init(x, y);
        modStats = modsts;
    }

    /**
     * construction
     * @param x
     * @param y
     */
    private void init(double x, double y) {        
        addComponent(new PositionComponent(x, y));          
        addComponent(new ShapeComponent(40, 40));        
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
