package systems;



import entities.Entity;
import entities.EntityManager;
import javafx.geometry.Point3D;
import level.RoomData;
import settings.Settings;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import components.AIComponent;
import components.AITargetComponent;
import components.Component;
import components.KeyInputComponent;

/**
 *System controlling Enemy-Movement ("AI" *wink *wink*)
 */
public class AISystem implements ECSystem {

	private EntityManager entityManager = EntityManager.getInstance();
	
	private EventCommandSystem eventCommandSystem = EventCommandSystem.getInstance();
	
	private int movement = Settings.getSpeed();
	
	
	@Override
	public void run(boolean debug) 
	{		
		HashMap<UUID, Component> AIcomponents = EntityManager.components.get(AIComponent.class);
		
		HashMap<UUID, Component> AItargetcomponents = EntityManager.components.get(AITargetComponent.class);
		
		for(Map.Entry<UUID, Component> entry : AIcomponents.entrySet())
		{
			
		}
		
		
	}
	
}