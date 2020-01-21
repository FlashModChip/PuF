package entities;

import java.util.HashMap;

import components.KeyInputComponent;
import components.PositionComponent;
import components.ShapeComponent;
import components.VelocityComponent;

/**
 * player stats
 */
public class Stats extends Entity {
	
	//Name (Movement speed, shot delay, dmg) & Value
	HashMap<String, Integer> stats;
	
    public Stats() {
        super();     
    }

   
    public Stats(double x, double y, String name, HashMap<String, Integer> sts) {
        super();     
        stats = sts;
    }  
}
