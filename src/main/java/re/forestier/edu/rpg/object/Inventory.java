package re.forestier.edu.rpg.object;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Inventory {

    private Map<String, Object> inventory;

    public Inventory() {
        this.inventory = new HashMap<>();
    }

    public Inventory(ArrayList<String> names, int maxWeight) {
        this.inventory = new HashMap<>();
        addObjects(names, maxWeight);
    }

  
    public void addObject(String name, int freeWeight) {
        if (ObjectList.contains(name)) {
            Object object = ObjectList.getObject(name);
            if (object.getWeight()<freeWeight)
                inventory.put(name, object);
        } else {
            System.out.println("Unkown Object");
        }
    }

    public void addObjects(ArrayList<String> names, int maxWeight) {
        for (String name : names) {
            if (ObjectList.contains(name)) {
                inventory.put(name, ObjectList.getObject(name));
            } else {
                System.out.println("Unkown Object");
            }
        }

        if(getTotalWeight()>maxWeight)
            System.out.println("must not exceed max weight");
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

    public boolean isEmpty(){
        return inventory.isEmpty();
    }

    public Collection<Object> getObjects(){
        return inventory.values();
    }

    public void clear() {
        inventory.clear();
    } 
}
