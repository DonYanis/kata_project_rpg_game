package re.forestier.edu.rpg.avatar;

import java.util.Map;

public class AvatarClassFactory {
    
    private static final Map<String, AvatarClass> classMap = Map.of(
        "ADVENTURER", new Adventurer(),
        "ARCHER", new Archer(),
        "DWARF", new Dwarf()
    );

    public static AvatarClass getAvatarClass(String className) {
        return classMap.get(className);
    }
}
