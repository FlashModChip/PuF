package components;

/**
 * key input component
 */
public class KeyInputComponent extends Component<Boolean> {

    public KeyInputComponent() {

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
