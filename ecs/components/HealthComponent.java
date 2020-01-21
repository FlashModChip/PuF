package components;

/**
 * health component
 */
public class HealthComponent extends Component<Double> {
    
	private Double health;
    
    public HealthComponent(Double val) {
        this.health = val;
    }

    public Double getValue() {
        return health;
    }

    public void setValue(Double value) {
        this.health = value;
    }
}
