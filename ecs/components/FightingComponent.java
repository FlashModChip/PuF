package components;

/**
 *
 * Shows entity is in Fighting Mode and fight
 * @author franzi
 */

public class FightingComponent extends Component<Integer> {

    private int fightValue = 0;

    public FightingComponent() {
        this.enabled = false;
    }

    @Override
    public Integer getValue() {
        return this.fightValue;
    }

    @Override
    public void setValue(Integer value) {
        this.fightValue = value;
    }

}
