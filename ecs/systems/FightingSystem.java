package systems;

import components.*;
import entities.Entity;
import entities.EntityManager;
import gameUi.Main;
import javafx.geometry.Point2D;
import level.MapNavigator;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * System for Fights
 * @author franzi
 */
public class FightingSystem implements ECSystem {

    private EntityManager entityManager = EntityManager.getInstance();

    private EventCommandSystem eventCommandSystem = EventCommandSystem.getInstance();
    private int fightRadius = 30;

    private AIComponent componentAI;
    private AITargetComponent componentAITarget;
    private FightingComponent componenteAIFight;
    private FightingComponent componentTargetFight;

    private Entity entityPlayer;
    private Entity entityEnemy;
    
    private MapNavigator navi =  MapNavigator.getInstance();



    @Override
    public void run(boolean debug){
        HashMap<UUID, Component> components = entityManager.components.get(VelocityComponent.class);
        // traverse all velocityComponents
        for (Map.Entry<UUID, ? extends Component> entry : components.entrySet()) {
            UUID uuid = entry.getKey();
            Entity entity = entityManager.getEntity(uuid);

            if(entity.hasComponent(AIComponent.class)){
                entityEnemy = entity;
                componentAI = (AIComponent) entity.getComponent(AIComponent.class);
                componenteAIFight= (FightingComponent) entity.getComponent(FightingComponent.class);
            }
            if (entity.hasComponent(AITargetComponent.class)) {
                entityPlayer = entity;
                componentAITarget = (AITargetComponent) entity.getComponent(AITargetComponent.class);
                componentTargetFight = (FightingComponent) entity.getComponent(FightingComponent.class);
            }
            
            
            if(entity.hasComponent(RoomComponent.class) && (entity.getComponent(RoomComponent.class).getValue() != navi.getCurrentRoom().getRoomID()))
			{
				System.out.println("DEATH" + entity.getComponent(HealthComponent.class).getValue());
				Sprite enemySprite = (Sprite) entity.getComponent(Sprite.class);
				enemySprite.translate(1600, 500);
				entity.getComponent(PositionComponent.class).setValue(new Point2D(1600, 500));
				entity.getComponent(AIComponent.class).setValue(false);				
				entity.delete();
			}
            
        }

        if(componentAI != null && componentAITarget != null) 
        {
        if (IsInFightRadius(componentAI, componentAITarget)&& !isInDoor(entityPlayer)) {
            componenteAIFight.setEnabled(true);
            componentTargetFight.setEnabled(true);
            fight(entityEnemy, entityPlayer);
        } else{
            componenteAIFight.setEnabled(false);
            componentTargetFight.setEnabled(false);
        }
        }
    }

    /**
     * Checks if enemy und player near to fight
     * @param comp
     * @param comp_trgt
     * @return
     */
    private boolean IsInFightRadius(AIComponent comp, AITargetComponent comp_trgt) {

        if (comp.getValue() && comp_trgt.getValue()) {
            return ((comp_trgt.getPosition().getX() - comp.getPosition().getX())<=fightRadius
                    && (comp_trgt.getPosition().getX() - comp.getPosition().getX())>=fightRadius*-1)
                    && ((comp_trgt.getPosition().getY() - comp.getPosition().getY())<=fightRadius
                    && (comp_trgt.getPosition().getY() - comp.getPosition().getY())>=fightRadius*-1);
        } else {
            return false;
        }

    }

    /**
     * Checks if player is in Door --> than Fight Modi false
     *@param player
     *@return
     */

    private boolean isInDoor(Entity player){
       Sprite playerSprite= (Sprite) player.getComponent(Sprite.class);
        for(int i = 0; i<Main.colliderWallMap.size(); i++){
            if(Main.colliderWallMap.get(i).intersects(playerSprite.getValue().getX(), playerSprite.getValue().getY(),playerSprite.getValue().getWidth(), playerSprite.getValue().getHeight())){
                return false;
            }
        }

        return true;
    }



    /**
     * fighting
     * @param enemy
     * @param player
     */
    private void fight(Entity enemy, Entity player){

        if((double)enemy.getComponent(HealthComponent.class).getValue() < 0){
            System.out.println("DEATH" + enemy.getComponent(HealthComponent.class).getValue());
            Sprite enemySprite = (Sprite) enemy.getComponent(Sprite.class);
            enemySprite.translate(1600, 500);
            enemy.getComponent(PositionComponent.class).setValue(new Point2D(1600,500));
            enemy.getComponent(AIComponent.class).setValue(false);
            player.getComponent(FightingComponent.class).setEnabled(false);
            enemy.delete();
            player.getComponent(FightingComponent.class).setValue(0);
        }
        enemy.getComponent(HealthComponent.class).setValue(
               (double) enemy.getComponent(HealthComponent.class).getValue()- (int)player.getComponent(FightingComponent.class).getValue()) ;

    }


}


