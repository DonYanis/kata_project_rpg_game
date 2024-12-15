package re.forestier.edu.rpg.object;

import java.util.HashMap;
import java.util.Map;

public class Inventory {

    private Map<String, Object> inventory;

    public Inventory() {
        this.inventory = new HashMap<>();
    }

  
    public void addObject(String name) {
        if (ObjectList.contains(name)) {
            inventory.put(name, ObjectList.getObject(name));
        } else {
            System.out.println("Unkown Object");
        }
    }

    public boolean contains(String name) {
        return inventory.containsKey(name);
    }

    public void remove(String name) {
        if (inventory.containsKey(name)) {
            inventory.remove(name);
        }
    }

    public Map<String, Object> getInventory() {
        return new HashMap<>(inventory); 
    }

     public int getTotalWeight() {
        int totalWeight = 0;
        for (Object object : inventory.values()) {
            totalWeight += object.getWeight(); 
        }
        return totalWeight;
    }

    public int getTotalValue() {
        int totalValue = 0;
        for (Object object : inventory.values()) {
            totalValue += object.getValue(); 
        }
        return totalValue;
    }
}
