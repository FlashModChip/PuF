package components;

import java.util.HashMap;

/**
 * inv component
 */
public class InventoryComponent extends Component<HashMap<String, HashMap<String, Integer>>> {
    
	//Name of item -> Stat modified, value modified
	private HashMap<String, HashMap<String, Integer>> items;
    
    public InventoryComponent() {
    	items = new HashMap<String, HashMap<String, Integer>>();
    }

    public void addItem (String name, HashMap<String, Integer> modStats) {
        items.put(name, modStats);
    }

    public void setValue(HashMap<String, HashMap<String, Integer>> itms) {
        this.items = itms;
    }

	@Override
	public HashMap<String, HashMap<String, Integer>> getValue() {
	
		return items;
	}

	
}
