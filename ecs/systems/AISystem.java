package systems;



import entities.Entity;
import entities.EntityManager;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import level.RoomData;
import settings.Settings;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import components.AIComponent;
import components.AITargetComponent;
import components.Component;
import components.KeyInputComponent;
import components.VelocityComponent;


/**
 * System controlling Enemy-Movement ("AI" *wink *wink*)
 * @author Roman
 *
 */
public class AISystem implements ECSystem {

    private EntityManager entityManager = EntityManager.getInstance();

    private EventCommandSystem eventCommandSystem = EventCommandSystem.getInstance();

    private int movement = Settings.getSpeed();

    /**
     * Gets two lists, one containing all AIComponents and one with all
     * AITargetComponents -> Checking for each of them if a AITarget is in the
     * radius of an AIComponent, if yes set velocity, if no stop movement
     */
    @Override
    public void run(boolean debug) {

        HashMap<UUID, Component> AIcomponents = EntityManager.components.get(AIComponent.class);
        HashMap<UUID, Component> AItargetcomponents = EntityManager.components.get(AITargetComponent.class);
        

        for (Entry<UUID, Component> entry : AIcomponents.entrySet()) 
        {
            for (Entry<UUID, Component> entry_target : AItargetcomponents.entrySet())
             {
            	Entity entity = EntityManager.getEntity(entry.getKey());
             
                if (IsInRadius((AIComponent)entry,(AITargetComponent)entry_target)) 
                {        
                  if(entity.hasComponent(VelocityComponent.class))
                   {                       
                      entity.getComponent(VelocityComponent.class).setValue(getVelocity((AIComponent)entry, (AITargetComponent)entry_target));
                   }
                }
                else
                {
                    entity.getComponent(VelocityComponent.class).setValue(new Point2D(0,0));
                }

            }
        }
    }
   
    /**
     * Checks, if an AITarget is within range of an AI-radius (if both are active)
     * @param comp
     * @param comp_trgt
     * @return
     */
    private boolean IsInRadius(AIComponent comp, AITargetComponent comp_trgt) {

        if (comp.getValue() && comp_trgt.getValue()) {
            return ((comp_trgt.getPosition().getX() - comp.getPosition().getX())
                    * (comp_trgt.getPosition().getX() - comp.getPosition().getX())
                    + (comp_trgt.getPosition().getX()- comp.getPosition().getY())
                            * (comp_trgt.getPosition().getX() - comp.getPosition().getY()) <= comp.getRadius()
                                    * comp.getRadius());
        } else {
            return false;
        }
        
    }
   
    /**
     * Calculates new velocity to target using position values
     * @param entry
     * @param entry_target
     * @return
     */
    private Point2D getVelocity(AIComponent entry, AITargetComponent entry_target) {
       
        float xVel, yVel;
        
        xVel = yVel = 0;

        if(entry.getPosition().getX() > entry_target.getPosition().getX()){
            xVel = movement;
        }
        else if(entry.getPosition().getX() < entry_target.getPosition().getX())
        {
            xVel = -movement;
        }

        if(entry.getPosition().getY()> entry_target.getPosition().getY())
        {
            yVel = movement;
        }
        else if(entry.getPosition().getY() < entry_target.getPosition().getY())
        {
            yVel = -movement;
        }

    return new Point2D(xVel,yVel);
    
    }
}