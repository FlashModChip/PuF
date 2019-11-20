package components;

/**
 * static component
 * if added, entity will be excluded from updates during game-loop
 * entity will be stored in chunks to reduce object-count on collision detection
 */
public class StaticComponent extends Component<Boolean> {

    public StaticComponent() {

    }

    @Override
    public Boolean getValue() {
        return enabled;
    }

    @Override
    public void setValue(Boolean value) {
        this.enabled = value;
    }
}
