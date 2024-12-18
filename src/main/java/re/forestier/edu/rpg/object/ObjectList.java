package re.forestier.edu.rpg.object;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ObjectList {

    private static final Map<String, RpgObject> objectMap = new HashMap<>();

    static {
        objectMap.put("Lookout Ring", new RpgObject("Lookout Ring", "Prevents surprise attacks", 1, 50));
        objectMap.put("Scroll of Stupidity", new RpgObject("Scroll of Stupidity", "INT-2 when applied to an enemy", 0, 10));
        objectMap.put("Draupnir", new RpgObject("Draupnir", "Increases XP gained by 100%", 2, 100));
        objectMap.put("Magic Charm", new RpgObject("Magic Charm", "Magic +10 for 5 rounds", 3, 150));
        objectMap.put("Rune Staff of Curse", new RpgObject("Rune Staff of Curse", "May burn your enemies... Or yourself. Who knows?", 3, 200));
        objectMap.put("Combat Edge", new RpgObject("Combat Edge", "Well, that's an edge", 1, 30));
        objectMap.put("Holy Elixir", new RpgObject("Holy Elixir", "Recover your HP", 2, 75));
        objectMap.put("Magic Bow", new RpgObject("Magic Bow", "Attack very far enemies", 2, 75));

    }

    public static RpgObject getObject(String name) {
        return objectMap.get(name);
    }

    public static Map<String, RpgObject> getObjectList() {
        return objectMap;
    }

    public static boolean contains(String name) {
        return objectMap.containsKey(name);
    }

    private static final Random RANDOM = new Random();
    public static RpgObject getRandomObject(int maxWeight) {
        List<RpgObject> availableObjects = new ArrayList<>();

        for (RpgObject obj : objectMap.values()) {
            if (obj.getWeight() < maxWeight) {
                availableObjects.add(obj);
            }
        }
        if (availableObjects.isEmpty()) {
            return null;
        }

        return availableObjects.get(RANDOM.nextInt(availableObjects.size()));
    }
}
