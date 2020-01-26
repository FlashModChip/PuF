package systems;



import components.*;
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


/**
 * System controlling Enemy-Movement ("AI" *wink *wink*)
 * @author Roman
 *
 */
public class AISystem implements ECSystem {

    private EntityManager entityManager = EntityManager.getInstance();

    private EventCommandSystem eventCommandSystem = EventCommandSystem.getInstance();

    private int movement = Settings.getSpeed()/2;

    private AIComponent componentAI;
    private AITargetComponent componentAITarget;
    private VelocityComponent componentAIVelocity;

    /**
     * Gets AiTarget and Ai Componente and Velocity of Ai
     * Checks if is in radius --> and set Velocity for Ai
     */
    @Override
    public void run(boolean debug) {

        HashMap<UUID, Component> components = entityManager.components.get(VelocityComponent.class);
        
        // traverse all velocityComponents
        for (Map.Entry<UUID, ? extends Component> entry : components.entrySet()) {
            UUID uuid = entry.getKey();
            Entity entity = entityManager.getEntity(uuid);

            if(entity.hasComponent(AIComponent.class)){
                componentAI = (AIComponent) entity.getComponent(AIComponent.class);
                componentAIVelocity= (VelocityComponent) entity.getComponent(VelocityComponent.class);
            }
            
            if (entity.hasComponent(AITargetComponent.class)) {
                componentAITarget = (AITargetComponent) entity.getComponent(AITargetComponent.class);
            }
            
            if(componentAI != null && componentAITarget != null ) {
            if (IsInRadius(componentAI, componentAITarget)) {
                componentAIVelocity.setValue(getVelocity(componentAI, componentAITarget));
            }    }        
            
        }

     
        
        //System.out.println(entry.getKey())
        
    }



    /**
     * Checks, if an AITarget is within range of an AI-radius (if both are active)
     * @param comp
     * @param comp_trgt
     * @return
     */
    private boolean IsInRadius(AIComponent comp, AITargetComponent comp_trgt) {

        if (comp.getValue() && comp_trgt.getValue()) {
            return ((comp_trgt.getPosition().getX() - comp.getPosition().getX())<=comp.getRadius()
                    && (comp_trgt.getPosition().getX() - comp.getPosition().getX())>=(comp.getRadius()*-1)
                    && (comp_trgt.getPosition().getY() - comp.getPosition().getY())<=comp.getRadius()
                    && (comp_trgt.getPosition().getY() - comp.getPosition().getY())>=(comp.getRadius()*-1));
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
            xVel = -movement;
        }
        else if(entry.getPosition().getX() < entry_target.getPosition().getX())
        {
            xVel = movement;
        }

        if(entry.getPosition().getY()> entry_target.getPosition().getY())
        {
            yVel = -movement;
        }
        else if(entry.getPosition().getY() < entry_target.getPosition().getY())
        {
            yVel = movement;
        }

        return new Point2D(xVel,yVel);

    }
}