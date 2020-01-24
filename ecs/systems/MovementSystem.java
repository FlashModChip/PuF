package systems;

import gameUi.Main;
import components.*;
import entities.Entity;
import entities.EntityManager;
import entities.State;
import game.Game;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import level.Direction;
import level.LevelData;
import level.LevelGenerator;
import level.LevelToUi;
import level.MapNavigator;
import settings.Settings;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * performs all movements on entities
 * including collision detection
 *
 * affected components: movement, velocity, collider, shape, light
 */
public class MovementSystem implements ECSystem {
    // entity-manager
    private EntityManager entityManager = EntityManager.getInstance();

    // event-system
    private EventCommandSystem eventCommandSystem = EventCommandSystem.getInstance();

    // game root
    private Pane root = Main.root;   

    // level size
    private int levelHeight = Game.levelHeight;
    private int levelWidth = Game.levelWidth;

    // debug

    private boolean debugDummy = Settings.getDebug("MovementSystem@dummy");
    private boolean debugStepSize = Settings.getDebug("MovementSystem@stepsize");


    private boolean debugBuffer = false;



    // dummy box for collision detection
    private Box dummyBox = new Box(0, 0, 0);

    // null vector for comparision
    private Point3D nullVector = new Point3D(0, 0, 0);



    // safe spot
    // we beam the collider to this place during collision detection to prevent self-collision
    // axis origin is where map creation start, it should be empty
    // if the entity is to big consider using negative values or in a 2D game offsetting on z-axis
    //  private Point3D safeSpot = Settings.getSafeSpot();

    // fallback dummy size
    // is used when entity has no shape and no collider
    private Box fallBackSize = Settings.getFallBackSize();

    // stepsize defines the max vector length of velocity
    // it should not be greater than the smallest entity on the map
    // otherwise collision detection could fail and the moving entity would magically jumping over it
    // decreasing this value will decrease performance as well
    // because we have to traverse all entities more often during collision detection
    private int stepsize = 12;

    // hashmap colliders
    private HashMap<Entity, PositionComponent > colliders = new HashMap<>();



    /**
     * constructor
     */
    public MovementSystem() {

        if (debugDummy) {
            PhongMaterial material = new PhongMaterial();
            material.setDiffuseColor(Color.RED);
            dummyBox.setMaterial(material);
            root.getChildren().addAll(dummyBox);
        }       
      
    }

    @Override
    public void run(boolean debug) {
        boolean noCollision = true;


        if (debug) System.err.println("MovementSystem <start>");
        int count = 0;


        // === PART 1 - POSITION ENTITIES FROM UPDATE-BUFFER ===
        // all entities in buffer will be placed in the world
        // translate: shapes, colliders & lights

        // get buffer
        HashMap<UUID, Entity> entities = EntityManager.entitiesUpdateBuffer;

        for (Map.Entry<UUID, Entity> entry : entities.entrySet()) {
//            UUID uuid = entry.getKey();
            Entity entity = entry.getValue();
            State entityState = entity.getState();
            if (debugBuffer) System.out.println("entity state: " + entityState);

            if (entity.hasComponent(PositionComponent.class)) {

                //gets Items Heahlth and Weapons
                if((entity.hasComponent(HealthComponent.class)&&!(entity.hasComponent(FightingComponent.class)))
                        || entity.hasComponent(WeaponComponent.class)&&!(entity.hasComponent(FightingComponent.class))){

                    colliders.put(entity, (PositionComponent) entity.getComponent(PositionComponent.class));
                }

                System.out.println(colliders);


                // get position component & data
                PositionComponent positionComponent = (PositionComponent) entity.getComponent(PositionComponent.class);
                Point2D position = positionComponent.getValue();
                State positionComponentState = positionComponent.getState();
                if (debugBuffer) System.out.println("position state: " + positionComponentState);

                // update shape
                if (entity.hasComponent(Sprite.class)) {
                    count++;
                    Sprite component = (Sprite) entity.getComponent(Sprite.class);
                    State componentState = component.getState();
                    if (debugBuffer) System.out.println("component state: " + componentState);
                    if (positionComponentState == State.UPDATE || component.getState() == State.UPDATE) {
                        component.translateX(position.getX());
                        component.translateY(position.getY());
                    }
                }

                // update collider
                if (entity.hasComponent(ColliderComponent.class)) {
                    count++;
                    ColliderComponent component = (ColliderComponent) entity.getComponent(ColliderComponent.class);
                    State componentState = component.getState();
                    if (debugBuffer) System.out.println("component state: " + componentState);
                    if (positionComponentState == State.UPDATE || component.getState() == State.UPDATE) {
                        component.translateX(position.getX());
                        component.translateY(position.getY());
                    }
                }

                /*
                 * // update light if (entity.hasComponent(LightComponent.class)) { count++;
                 * LightComponent component = (LightComponent)
                 * entity.getComponent(LightComponent.class); State componentState =
                 * component.getState(); if (debugBuffer)
                 * System.out.println("component state: "+componentState); if
                 * (positionComponentState == State.UPDATE || component.getState() ==
                 * State.UPDATE) { component.translate(position); }
                 * component.setState(State.STABLE); }
                 */
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


                //is in Fight Modi
                if(entity.hasComponent(FightingComponent.class)){
                    noCollision = !(entity.getComponent(FightingComponent.class).isEnabled());
                }


                // check if component is enabled and has a position
                // assume it has a velocity, otherwise it would not be in this list
                if (component.isEnabled() && entity.hasComponent(PositionComponent.class)) {

                    // get velocity & position
                    Point2D velocity = (Point2D) component.getValue();
                    PositionComponent positionComponent = (PositionComponent) entity.getComponent(PositionComponent.class);
                    Point2D position = positionComponent.getValue();



                    // check if entity needs an update (velocity is != 0)
                    if (!velocity.equals(nullVector)) {
                        count++;


                        // === PART 3 - COLLISION DETECTION ===

                        // before running collision detection, we move the collider to a safe spot to prevent self-collision
                        // the position will be reset automatically when applying the new position
                        if (entity.hasComponent(ColliderComponent.class)) {
                            count++;
                            // ((ColliderComponent) entity.getComponent(ColliderComponent.class)).translateX(safeSpot.getX());
                            // ((ColliderComponent) entity.getComponent(ColliderComponent.class)).translateX(safeSpot.getY());
                        }

                        // run collision detection
                        // velocity = collisionDetection(entity, position, velocity);

                        // update position (add vector to current position)
                        position = new Point2D(position.getX() + velocity.getX(), position.getY() + velocity.getY());

                        // check if we have left the map and in case throw an event
                        for(int i = 0; i< Main.colliderWallMap.size(); i++){
//                            System.out.println(componentSprite.getValue().getWidth() +"  "+ componentSprite.getValue().getHeight());
                            if(Main.colliderWallMap.get(i).intersects(position.getX(), position.getY(), componentSprite.getValue().getWidth(), componentSprite.getValue().getHeight())){

                                noCollision = false;
                            }
                        }
                        //   for(int i = 0; i< colliders.size(); i++){
                        for(Map.Entry<Entity, PositionComponent> colliderEntry: colliders.entrySet()){
                            Point2D positionItem = colliderEntry.getValue().getValue();
                            if (positionItem.getY()<=position.getY() && (positionItem.getY()+40)>=position.getY()
                                    && positionItem.getX()<=position.getX() && (positionItem.getX() + 40)>=position.getX()){
                                Sprite Temp = (Sprite) colliderEntry.getKey().getComponent(Sprite.class);
                                if(colliderEntry.getKey().hasComponent(HealthComponent.class)) {
                                    HealthComponent tempHealth = (HealthComponent) colliderEntry.getKey().getComponent(HealthComponent.class);
                                    Temp.translate(400, 500);
                                   PositionComponent TempPos = new PositionComponent(400,500);
                                    colliderEntry.setValue(TempPos);
                                    entity.addComponent(tempHealth);
                                    //colliderEntry.setValue(PositionComponent)
                                } else if(colliderEntry.getKey().hasComponent(WeaponComponent.class) ){
                                    Temp.translate(500,500);
                                    PositionComponent TempPos = new PositionComponent(500,500);
                                    colliderEntry.setValue(TempPos);
                                }
                                //tempStepItemsX+=100;

                                // if(positionItem.intersects(position.getX(), position.getY(), componentSprite.getValue().getWidth(), componentSprite.getValue().getHeight())) {
                                System.err.println("ITEM");
                                //  positionComponent.setValue(position);
                                noCollision = false;
                                //TODO Angriff oder aufsammeln etc.
                            }
                        }
                        for(int i = 0; i< Main.colliderDoorMap.size(); i++){

                            if(Main.colliderDoorMap.get(i).intersects(position.getX(), position.getY(), componentSprite.getValue().getWidth(), componentSprite.getValue().getHeight())) {
                                
                            	System.err.println("nächstes Level");
                                //  positionComponent.setValue(position);
                                System.err.println((position.getX() +"||"+ position.getY()));
                                //  noCollision = false;
                                Direction dir = null;
                                Point2D tmp = null;
                                
                                if(!Main.getMap().getMapNavigator().isLocked())
                                {
									dir = Main.getMap().getMapNavigator()
											.changecCurrentRoomByDirection(positionComponent.getValue());
									
									tmp = Main.getMap().getMapNavigator().getEntryCoords(dir);

									System.out.println(dir);
									System.out.println(tmp);									

									Main.getPlayer().getComponent(PositionComponent.class).setValue(Main.getMap().getMapNavigator().getEntryCoords(dir));
									
									Main.updateMap();
                                }                   

                              
                            }
                        }

                        // store velocity and position in it's components
                        if(noCollision) {
                            component.setValue(velocity);
                            positionComponent.setValue(position);

                            // update shape position
                            if (entity.hasComponent(Sprite.class)) {
                                count++;
                                ((Sprite) entity.getComponent(Sprite.class)).translateX(position.getX());
                                ((Sprite) entity.getComponent(Sprite.class)).translateY(position.getY());
                            }
                        }

                        // update collider position
                        // if (entity.hasComponent(ColliderComponent.class)) {
                        //     count++;
                        //     ((ColliderComponent) entity.getComponent(ColliderComponent.class)).translate(position);
                        // }

                        /*
                         * // update light position if (entity.hasComponent(LightComponent.class)) {
                         * count++; ((LightComponent)
                         * entity.getComponent(LightComponent.class)).translate(position); }
                         */
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


//TODO Collision siehe Philipps version