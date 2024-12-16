package re.forestier.edu.rpg.object;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ObjectList {

    private static final Map<String, Object> objectMap = new HashMap<>();

    static {
        objectMap.put("Lookout Ring", new Object("Lookout Ring", "Prevents surprise attacks", 1, 50));
        objectMap.put("Scroll of Stupidity", new Object("Scroll of Stupidity", "INT-2 when applied to an enemy", 0, 10));
        objectMap.put("Draupnir", new Object("Draupnir", "Increases XP gained by 100%", 2, 100));
        objectMap.put("Magic Charm", new Object("Magic Charm", "Magic +10 for 5 rounds", 3, 150));
        objectMap.put("Rune Staff of Curse", new Object("Rune Staff of Curse", "May burn your enemies... Or yourself. Who knows?", 3, 200));
        objectMap.put("Combat Edge", new Object("Combat Edge", "Well, that's an edge", 1, 30));
        objectMap.put("Holy Elixir", new Object("Holy Elixir", "Recover your HP", 2, 75));
        objectMap.put("Magic Bow", new Object("Magic Bow", "Attack very far enemies", 2, 75));

    }

    public static Object getObject(String name) {
        return objectMap.get(name);
    }

    public static Map<String, Object> getObjectList() {
        return objectMap;
    }

    public static boolean contains(String name) {
        return objectMap.containsKey(name);
    }

    public static Object getRandomObject(int maxWeight) {
        List<Object> availableObjects = new ArrayList<>();

        for (Object obj : objectMap.values()) {
            if (obj.getWeight() < maxWeight) {
                availableObjects.add(obj);
            }
        }
        if (availableObjects.isEmpty()) {
            return null;
        }

        Random random = new Random();
        return availableObjects.get(random.nextInt(availableObjects.size()));
    }
}
