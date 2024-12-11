package re.forestier.edu.rpg;

import java.util.Map;

public class AvatarClassFactory {
    
    private static final Map<String, AvatarClass> classMap = Map.of(
        "ADVENTURER", new Adventurer(),
        "ARCHER", new Archer(),
        "DWARF", new Dwarf()
    );

    public static AvatarClass getAvatarClass(String className) {
        if (!classMap.containsKey(className)) {
            throw new IllegalArgumentException("Unknown class: " + className);
        }
        return classMap.get(className);
    }
}
