package re.forestier.edu.rpg.avatar;

import java.util.Map;

import re.forestier.edu.rpg.avatar.role.Adventurer;
import re.forestier.edu.rpg.avatar.role.Archer;
import re.forestier.edu.rpg.avatar.role.Dwarf;
import re.forestier.edu.rpg.avatar.role.Goblin;
import re.forestier.edu.rpg.exception.UnknownAvatarClassException;

public class AvatarClassFactory {
    
    private static final Map<String, AvatarClass> classMap = Map.of(
        "ADVENTURER", new Adventurer(),
        "ARCHER", new Archer(),
        "DWARF", new Dwarf(),
        "GOBLIN", new Goblin()
    );

    public static AvatarClass getAvatarClass(String className) {
        if (!classMap.containsKey(className))
            throw new UnknownAvatarClassException("Avatar class '" + className +"' is unknown");
        return classMap.get(className);
    }
}
