package systems;

import entities.*;
import gameUi.Main;
import components.*;
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

    // Through Door Warkaound
    private boolean isThroughDoor = false;


    // debug

    private boolean debugDummy = Settings.getDebug("MovementSystem@dummy");


    private boolean debugBuffer = false;


    // hashmap colliders
    private HashMap<Entity, PositionComponent > colliders = new HashMap<>();



    /**
     * constructor
     */
    public MovementSystem() {

        if (debugDummy) {

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
            System.out.println("Kommme ich hier rein" + isThroughDoor);




            if (entity.hasComponent(PositionComponent.class)) {

                //gets Items Heahlth and Weapons
                if((entity.hasComponent(HealthComponent.class)&&!(entity.hasComponent(FightingComponent.class)))
                        || entity.hasComponent(WeaponComponent.class)&&!(entity.hasComponent(FightingComponent.class))){

                    colliders.put(entity, (PositionComponent) entity.getComponent(PositionComponent.class));
                }


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

            }
        }
        HashMap<UUID, Entity> entitiesCollider = EntityManager.entities;
        for (Map.Entry<UUID, Entity> entryCollider : entitiesCollider.entrySet()) {
//            UUID uuid = entry.getKey();
            Entity entity = entryCollider.getValue();
            State entityState = entity.getState();


            if (entity.hasComponent(PositionComponent.class)) {

                //gets Items Heahlth and Weapons
                if ((entity.hasComponent(HealthComponent.class) && !(entity.hasComponent(FightingComponent.class)))
                        || entity.hasComponent(WeaponComponent.class) && !(entity.hasComponent(FightingComponent.class))) {

                    colliders.put(entity, (PositionComponent) entity.getComponent(PositionComponent.class));
                }


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


                        // === PART 3 - COLLISION DETECTION ===


                        // update position (add vector to current position)
                        position = new Point2D(position.getX() + velocity.getX(), position.getY() + velocity.getY());

                        // check if we have left the map and in case throw an event
                        for(int i = 0; i< Main.colliderWallMap.size(); i++){
//                            System.out.println(componentSprite.getValue().getWidth() +"  "+ componentSprite.getValue().getHeight());
                            if(Main.colliderWallMap.get(i).intersects(position.getX(), position.getY(), componentSprite.getValue().getWidth(), componentSprite.getValue().getHeight())){

                                noCollision = false;
                            }
                        }
                        for(Map.Entry<Entity, PositionComponent> colliderEntry: colliders.entrySet()){
                            Point2D positionItem = colliderEntry.getValue().getValue();
                            if (positionItem.getY()<=position.getY() && (positionItem.getY()+40)>=position.getY()
                                    && positionItem.getX()<=position.getX() && (positionItem.getX() + 40)>=position.getX()){
                               // Sprite Temp = (Sprite) colliderEntry.getKey().getComponent(Sprite.class);
                                if(colliderEntry.getKey().hasComponent(HealthComponent.class)) {
                                    Double tempHealth =  (Double) colliderEntry.getKey().getComponent(HealthComponent.class).getValue();
                                    Double tempHealthPlayerEnemy = (Double) (entity.getComponent(HealthComponent.class).getValue());
                                    System.out.println("gesundheit davor" + tempHealthPlayerEnemy);
                                    entity.getComponent(HealthComponent.class).setValue(tempHealth+tempHealthPlayerEnemy);
                                    colliderEntry.getKey().delete();
                                    System.out.println("gesundheit danach" +  entity.getComponent(HealthComponent.class).getValue());

                                } else if(colliderEntry.getKey().hasComponent(WeaponComponent.class) ){
                                    Double tempWeapon =  (Double) colliderEntry.getKey().getComponent(WeaponComponent.class).getValue();
                                    Double tempWeaponPlayerEnemy = (Double) (entity.getComponent(WeaponComponent.class).getValue());
                                    System.out.println("Waffen davor" + tempWeaponPlayerEnemy);
                                    entity.getComponent(HealthComponent.class).setValue(tempWeapon+tempWeaponPlayerEnemy);
                                    colliderEntry.getKey().delete();
                                    System.out.println("Waffen danach" +  entity.getComponent(HealthComponent.class).getValue());
                                }

                                noCollision = false;
                                //TODO Angriff oder aufsammeln etc.
                            }
                        }
                        for(int i = 0; i< Main.colliderDoorMap.size(); i++){

                            if(Main.colliderDoorMap.get(i).intersects(position.getX(), position.getY(), componentSprite.getValue().getWidth(), componentSprite.getValue().getHeight())) {
                                
                            	System.err.println("nächstes Level");
                                System.err.println((position.getX() +"||"+ position.getY()));
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
                                    Item item1 = new Item(100,250);
                                    Enemy enemy = new Enemy(100,200);


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
                    }
                }
            }


        if (debug) {
            System.out.println("moved components: " + count);
            System.out.println("MovementSystem <end>");
        }
    }

}

