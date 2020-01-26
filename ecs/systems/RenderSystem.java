package systems;

import components.*;
import gameUi.Main;
import entities.Entity;
import entities.EntityManager;
import entities.State;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import settings.Settings;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * renders all entities
 * or removes them if necessary
 * operate on EntityManager.buffer
 *
 * affected components: render, shape, light
 */
public class RenderSystem implements ECSystem {
    // root
    private Pane root = Main.root;

    @Override
    public void run(boolean debug) {
        if(debug) System.err.println("RenderSystem <start>");
        int count = 0;

        // this new approach is using a separate buffer to indicate changes rather than static states
        // changes are indicated by flag and stored in the buffer
        HashMap<UUID, Entity> entities = EntityManager.entities;


        // new approach
        if (entities == null) {
            if(debug) System.out.println("entities to render: 0");
        } else {

            // traverse all renderComponents
//            for(Map.Entry<UUID, ? extends Component> entry : components.entrySet()) {
            for(Map.Entry<UUID, Entity> entry : entities.entrySet()) {
                UUID uuid = entry.getKey();
              //Component component = entry.getValue();
                Entity entity = entry.getValue();
                State entityState = entity.getState();
//                System.out.println("entity state: "+entityState);

                // check if component is enabled
//                if (entity.isEnabled()) {
                if (entity.hasComponent(RenderComponent.class)) {

//
                    // get render component flag
                    State renderComponentState = entity.getComponent(RenderComponent.class).getState();
//                    System.out.println("render state: "+renderComponentState);

                    // update shape
                    if (entity.hasComponent(Sprite.class)) {
                        count++;
                        Component component = entity.getComponent(Sprite.class);
                        State componentState = component.getState();
                     //  System.out.println("component state: "+componentState);
                        Rectangle shape = (Rectangle) component.getValue();
                        // remove shape from scene, when either the renderComponent or the shapeComponent is flagged to delete
                        if (entityState == State.DELETE || renderComponentState == State.DELETE || component.getState() == State.DELETE) {
                            root.getChildren().remove(shape);
                        }
                        // add shape to scene, when either the renderComponent or the shapeComponent is flagged to update
                       else if (renderComponentState == State.UPDATE || component.getState() == State.UPDATE ) {
                            //root.getChildren().add(shape);

                            if ((entity.hasComponent(HealthComponent.class) || (entity.hasComponent(WeaponComponent.class)) || (entity.hasComponent(AIComponent.class))
                                    && (entity.hasComponent(AITargetComponent.class)))) {
                                boolean isNotContains = false;
                                int randomYint ;
                                int randomXint ;
                                do {
                                    isNotContains = false;
                                    Random randomX = new Random();
                                    randomXint = randomX.nextInt(Settings.getWindowWidth()-50);
                                    Random randomY = new Random();
                                    randomYint = randomY.nextInt(Settings.getWindowHeight() / 2);
                                    Point2D TempPointA = new Point2D(randomXint, randomYint);
                                    Point2D TempPointB = new Point2D(randomXint + 60, randomYint);
                                    Point2D TempPointC = new Point2D(randomXint + 60, randomYint + 60);
                                    Point2D TempPointD = new Point2D(randomXint, randomYint + 60);
                                    Rectangle TempRectangle = new Rectangle(randomXint,randomYint, 60,60 );

                                    for (int i = 0; i < Main.colliderWallMap.size(); i++) {

                                        if (Main.colliderWallMap.get(i).intersects(TempRectangle.getX(), TempRectangle.getY(),TempRectangle.getWidth(), TempRectangle.getHeight())) {
                                            isNotContains = true;
                                        }

                                    }

                                } while (isNotContains);


                                entity.getComponent(PositionComponent.class).setValue(new Point2D(randomXint, randomYint));
                                Sprite item = (Sprite) entity.getComponent(Sprite.class);
                                item.translate(randomXint, randomYint);


                            }

                            root.getChildren().add(shape);
                            System.out.println(root);
                        }
                    }


                }
            }
        }
        if(debug) {
            System.out.println("rendered components: " + count);
            System.out.println("RenderSystem <end>");
        }
    }
}
