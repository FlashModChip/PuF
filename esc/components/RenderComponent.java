package components;

/**
 * render component
 */
public class RenderComponent extends Component<Boolean> {

    public RenderComponent() {

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
