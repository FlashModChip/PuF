package systems;

import entities.EntityManager;
import settings.Settings;

/**
 * stores and runs all systems
 */
public class SystemManager {
    // entity-manager
    EntityManager entityManager = EntityManager.getInstance();

    // store all systems here
    RenderSystem render = new RenderSystem();
    KeyInputSystem keyInput = new KeyInputSystem();
    //RotationSystem rotation = new RotationSystem();
    GravitySystem gravity = new GravitySystem();
    MovementSystem movement = new MovementSystem();
    EventCommandSystem eventCommandSystem = EventCommandSystem.getInstance();
    GarbageCollectorSystem garbage = new GarbageCollectorSystem();
    AISystem ai = new AISystem();
    FightingSystem fightingSystem = new FightingSystem();

    // settings
    Settings settings = Settings.getInstance();

    // debug
    //private boolean debug_init = settings.getDebug("SystemManager@init");
    private boolean debug_update = false;
    private boolean debug_init = false;
    /**
     * constructor
     */
    public SystemManager() {

    }

    /**
     * runs once on init
     */
    public void init() {
        if(debug_init) {
            System.err.println("SystemManager@init <start>");
            System.out.println("entities: " + entityManager.entities.size());
            System.out.println("buffered: " + entityManager.entitiesUpdateBuffer.size());
        }

        movement.run(debug_init);
        //rotation.run(debug_init);
        render.run(debug_init);
        garbage.run(debug_init);
        System.out.println("run run");

        if(debug_init) System.out.println("SystemManager@init <end>");
    }

    /**
     * runs the systems on every tick
     */
    public void update() {
        if(debug_update) {
            System.err.println("SystemManager@update <start>");
            System.out.println("entities: " + entityManager.entities.size());
        }

        if (debug_update && entityManager.entitiesUpdateBuffer.size() > 0) {
            System.out.println("SystemManager: entities to update: " + entityManager.entitiesUpdateBuffer.size());
        }

        ai.run(debug_update);
        fightingSystem.run(debug_update);
        keyInput.run(debug_update);
        movement.run(debug_update);
        render.run(debug_update);
        garbage.run(debug_update);
        eventCommandSystem.run(debug_update);

        if(debug_update) System.out.println("SystemManager@update <end>");
    }
}
