package components;

import entities.State;

public abstract class Component<T> {
	
  
    protected boolean enabled = true;

    // set default state
    protected State state = State.UPDATE;

    /**
     * constructor
     */
    public Component() {

    }

    /**
     * get the state of the component
     *
     * @return
     *      state
     */
    public State getState() {
        return state;
    }

    /**
     * set the state of the component
     *
     * @param state
     *      state
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * generic getter
     *
     * @return
     *      value (type not predefined)
     */
    public abstract T getValue();

    /**
     * generic setter
     *
     * @param value
     *      value (type not predefined)
     */
    public abstract void setValue(T value);

    /**
     * get enabled state
     *
     * @return
     *      state
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * set enabled
     *
     * @param enabled
     *      state
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
