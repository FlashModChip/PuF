package components;

/**
 * weapon component
 * @author franzi
 */
public class WeaponComponent extends Component<Double> {

    private Double strength;

    public WeaponComponent(Double val) {
        this.strength = val;
    }

    public Double getValue() {
        return strength;
    }

    public void setValue(Double value) {
        this.strength = value;
    }
}
