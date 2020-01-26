package systems;

import components.Component;
import components.PositionComponent;
import components.Sprite;
import components.VelocityComponent;
import entities.Entity;
import entities.EntityManager;
import entities.State;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * animate a walking sprite
 *
 * @author The other Roman
 */
public class AnimationSystem implements ECSystem {

    //animation velocity
    int STEP_VELOCITY = 4;

    private EntityManager entityManager = EntityManager.getInstance();

    private boolean debugBuffer = false;

    // null vector for comparision
    private Point3D nullVector = new Point3D(0, 0, 0);


    /**
     * mostly like MovementSystem but instead of translate the sprite
     * its animate it on changing the position
     */
    @Override
    public void run(boolean debug) {
        boolean noCollision = true;

        int count = 0;

        HashMap<UUID, Entity> entities = EntityManager.entitiesUpdateBuffer;

        for (Map.Entry<UUID, Entity> entry : entities.entrySet()) {
            Entity entity = entry.getValue();
            State entityState = entity.getState();
            if (debugBuffer) System.out.println("entity state: " + entityState);

            if (entity.hasComponent(PositionComponent.class)) {

                // get position component & data
                PositionComponent positionComponent = (PositionComponent) entity.getComponent(PositionComponent.class);
                Point2D position = positionComponent.getValue();
                State positionComponentState = positionComponent.getState();
                if (debugBuffer) System.out.println("position state: " + positionComponentState);
            }
        }

        // === PART 2 - RE-POSITIONING ENTITIES ON CHANGE ===
        // all entities with velocity need to be updated on every tick
        // requirements: position, velocity
        // translate: shapes, colliders & lights

        HashMap<UUID, Component> components = entityManager.components.get(VelocityComponent.class);

        // check if we there are any velocityComponents
        if (components == null) {
            if (debug) System.out.println("entities to move: 0");
        } else {
            if (debug) System.out.println("entities to move: " + components.size());

            // traverse all velocityComponents
            for (Map.Entry<UUID, ? extends Component> entry : components.entrySet()) {
                UUID uuid = entry.getKey();
                Component component = entry.getValue();
                Entity entity = entityManager.getEntity(uuid);
                Sprite componentSprite = (Sprite) entity.getComponent(Sprite.class);
                double currentTileRow = componentSprite.getImageXPos();
                int stepCounter = componentSprite.getStepCounter();

                // check if component is enabled and has a position
                // assume it has a velocity, otherwise it would not be in this list
                if (component.isEnabled() && entity.hasComponent(PositionComponent.class)) {

                    // get velocity & position
                    Point2D velocity = (Point2D) component.getValue();
                    PositionComponent positionComponent = (PositionComponent) entity.getComponent(PositionComponent.class);
                    Point2D position = positionComponent.getValue();

                    Point2D newPosition = positionComponent.getValue();
                    boolean isMoving = positionComponent.isMoving();
                    boolean movingLeft = positionComponent.isMovingLeft();


//                    System.out.println("get velo & pos " + position + componentSprite);



                    // check if entity needs an update (velocity is != 0)
                    if (!velocity.equals(nullVector)) {
                        count++;

                        // update position (add vector to current position)
                        newPosition = new Point2D(position.getX() + velocity.getX(), position.getY() + velocity.getY());

                        if (noCollision) {

                            // here starts the animation
                            if (entity.hasComponent(Sprite.class)) {
                                count++;

                                //if sprite is moving then set on move
                                //stepcounter for slowing down the animation
                                if (newPosition.getX() > position.getX()) {
                                    ((Sprite) entity.getComponent(Sprite.class)).setStepCounter(stepCounter += 1);
                                    isMoving = true;
                                    movingLeft = false;
                                } else if (newPosition.getX() < position.getX()) {
                                    ((Sprite) entity.getComponent(Sprite.class)).setStepCounter(stepCounter += 1);
                                    isMoving = true;
                                    movingLeft = true;
                                } else if (newPosition.getY() > position.getY()) {
                                    ((Sprite) entity.getComponent(Sprite.class)).setStepCounter(stepCounter += 1);
                                    isMoving = true;
                                } else if (newPosition.getY() < position.getY()) {
                                    ((Sprite) entity.getComponent(Sprite.class)).setStepCounter(stepCounter += 1);

                                    isMoving = true;
                                } else {
                                    isMoving = false;
                                }

                                //animation
                                //set the Image in viewFrame for each
                                if (stepCounter >= STEP_VELOCITY) {
                                    if (isMoving) {
                                        if (currentTileRow < -8) {
                                            ((Sprite) entity.getComponent(Sprite.class)).setImageXPos(-2.30);
                                        } else if (movingLeft) {
                                            ((Sprite) entity.getComponent(Sprite.class)).animate(currentTileRow, -1.0);
//                                            System.out.println("now update pic with current tile" + currentTileRow);
                                            ((Sprite) entity.getComponent(Sprite.class)).setImageXPos(currentTileRow -= 1.06);
                                        } else if (!movingLeft) {
                                            ((Sprite) entity.getComponent(Sprite.class)).animate(currentTileRow, -2.1);
                                            ((Sprite) entity.getComponent(Sprite.class)).setImageXPos(currentTileRow -= 1.06);
                                        }
                                    }
                                    //reset animationCounter to zero
                                    ((Sprite) entity.getComponent(Sprite.class)).setStepCounter(0);
                                }
                            }
                        }
                    }
                }
            }
        }

        if (debug) {
            System.out.println("moved components: " + count);
            System.out.println("MovementSystem <end>");
        }
    }

}
