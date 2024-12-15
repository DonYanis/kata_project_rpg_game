package re.forestier.edu.rpg.avatar;

import java.util.Map;

import re.forestier.edu.rpg.avatar.role.Adventurer;
import re.forestier.edu.rpg.avatar.role.Archer;
import re.forestier.edu.rpg.avatar.role.Dwarf;
import re.forestier.edu.rpg.avatar.role.Goblin;

public class AvatarClassFactory {
    
    private static final Map<String, AvatarClass> classMap = Map.of(
        "ADVENTURER", new Adventurer(),
        "ARCHER", new Archer(),
        "DWARF", new Dwarf(),
        "GOBLIN", new Goblin()
    );

    public static AvatarClass getAvatarClass(String className) {
        return classMap.get(className);
    }
}
