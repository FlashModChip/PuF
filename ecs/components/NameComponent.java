package components;

/**
 * name component
 */
public class NameComponent extends Component<String> {
    private String name;

    public NameComponent() {
        this.name = "";
    }

    public NameComponent(String name) {
        this.name = name;
    }

    @Override
    public String getValue() {
        return name;
    }

    @Override
    public void setValue(String value) {
        this.name = value;
    }
}
