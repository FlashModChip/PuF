package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import components.Component;
import javafx.scene.paint.PhongMaterial;

/**
 * entity class
 */
public abstract class Entity {
    
    protected UUID uuid;

    // set a state, to control rendering and garbage collection
    protected State state;
  
    // this is only necessary on deconstruction
    List<Component> components = new ArrayList<>();

    public Entity() {         
        uuid = UUID.randomUUID();   
        state = State.UPDATE;
        // store entity
        EntityManager.addEntity(uuid, this);
        EntityManager.updateEntity(uuid, this);
    }
    
    public void delete() {
        state = State.DELETE;
        EntityManager.updateEntity(uuid, this);
    }
    
    public State getState() {
        return state;
    }
    
    public void setState(State state) {
        this.state = state;
    }

   
    public void addComponent(Component component) {

        // add component to local list
        components.add(component);

        // add component to manager
        EntityManager.addComponent(uuid, component);

        // force update entity
        flagToUpdate();
    }
   
    public Component getComponent(Class<? extends Component> component) {
        return EntityManager.getComponent(uuid, component);
    }

 
    public List<Component> getAllComponents() {
        return components;
    }

    
    public void removeComponent(Component component) {

        // remove component local list
        components.remove(component.getClass());

        // remove component from manager
        // DEPRECATED, will be done by garbageCollectorSystem
        // because e.g. removing a renderComponent does not unrender the entity
        // EntityManager.removeComponent(uuid, component);

        // instead flag component to delete
        component.setState(State.DELETE);

        // force update entity
        flagToUpdate();
    }

    /**
     * helper function to flag entity for update
     */
    private void flagToUpdate() {
        if (state != State.UPDATE) {
            state = State.UPDATE;
            EntityManager.updateEntity(uuid, this);
        }
    }

   
    public boolean hasComponent(Class<? extends Component> component) {
        return EntityManager.hasComponent(uuid, component);
    }

  
    //public abstract void setMaterial(PhongMaterial material);

}
