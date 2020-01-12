package systems;

import gameUi.Start;
import entities.Entity;
import entities.EntityManager;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import settings.Settings;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import components.Component;
import components.KeyInputComponent;
import components.VelocityComponent;

/**
 * handles key input
 *
 * affected components: keyInput, jump, velocity
 */
public class KeyInputSystem implements ECSystem {
    // entity-manager
    private EntityManager entityManager = EntityManager.getInstance();

    // event-system
    private EventCommandSystem eventCommandSystem = EventCommandSystem.getInstance();

    // speed
    private int movement = Settings.getSpeed();

   // private int jump = Settings.getJump(); Brauchen wir nicht, springt nicht ??

    private double xVel, yVel;

    // key hashmap
    private HashMap<KeyCode,Boolean> keyInput = Start.keyInput;

    @Override
    public void run(boolean debug) {
        if(debug) System.err.println("KeyInputSystem <start>");
        int count = 0;
        HashMap<UUID, Component> components = EntityManager.components.get(KeyInputComponent.class);

        // check if we there are any keyInputComponents
        if (components == null) {
            if(debug) System.out.println("entities with key input: 0");
        } else {
            if(debug) System.out.println("entities with key input: " + components.size());

            // default velocity
            xVel = 0;
            yVel = 0;
            System.err.println(keyInput );

            // W-key has special functionality ??????
            //boolean buttonW = false;

            // react on buffered user input
            // move player
            if(isPressed(KeyCode.DOWN)){
                yVel = movement;
               // System.out.println("klappt dies hier???");

               // buttonW = true; ????
            }
            if (isPressed(KeyCode.UP)){
                // do nothing
                yVel = -movement;
            }
            if(isPressed(KeyCode.RIGHT)){
                xVel = movement;
            }
            if(isPressed(KeyCode.LEFT)){
                xVel = -movement;
            }
            if(isPressed(KeyCode.A)){
                //TODO Angriff gegen Feind
            }
            if(isPressed(KeyCode.D)){
                //TODO Item einsammeln
            }
            if(isPressed(KeyCode.W)){
                // TODO Item einlösen ??
            }

            // traverse all keyInputComponents
            for(Map.Entry<UUID, ? extends Component> entry : components.entrySet()) {
                UUID uuid = entry.getKey();
                Component component = entry.getValue();

                System.err.println(entry.getValue());



                // check if component is enabled
                if (component.isEnabled()) {

                    Entity entity = EntityManager.getEntity(uuid);

                    // update jump
					/*
					 * if (buttonW && entity.hasComponent(JumpComponent.class)) { Component
					 * jumpComponent = entity.getComponent(JumpComponent.class); if ((boolean)
					 * jumpComponent.getValue()) { yVel = jump; jumpComponent.setValue(false);
					 * eventCommandSystem.addEvent(new GameEvent(GameEvent.ENTITY_JUMP)); }
					 * 
					 * count++; }
					 */

                    // update velocity
                    if (entity.hasComponent(VelocityComponent.class)) {
                        Component velocityComponent = entity.getComponent(VelocityComponent.class);
                        // restore current y-velocity
                        Point2D velocity = (Point2D) velocityComponent.getValue();
                        if (yVel == 0) {
                           // yVel = velocity.getY();
                        }
                        // set velocity
                        velocityComponent.setValue(new Point2D(xVel, yVel));

                        count++;
                    }
                }
            }
        }

        if(debug)
        {
            System.out.println("key input components: " + count);
            System.out.println("KeyInputSystem <end>");
        }
    }

    /**
     * helper function to parse key events
     *
     * @param key
     *      keycode
     * @return
     *      boolean: is key pressed
     */
    private boolean isPressed(KeyCode key){
      //  System.out.println(key + "____"+keyInput);
        return keyInput.getOrDefault(key,false);
    }
}