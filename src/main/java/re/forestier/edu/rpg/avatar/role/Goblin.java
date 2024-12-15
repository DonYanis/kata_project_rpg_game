package re.forestier.edu.rpg.avatar.role;

import java.util.Map;

import re.forestier.edu.rpg.avatar.AvatarClass;

public class Goblin implements AvatarClass{

    @Override
    public String getName() {
        return "GOBLIN";
    }

    @Override
    public Map<Integer, Map<String, Integer>> getAbilitiesPerLevel() {
        return Map.of(
            1, Map.of("ALC", 1, "INT", 2, "ATK", 2),
            2, Map.of("ATK", 3, "ALC", 4),
            3, Map.of("VIS", 1),
            4, Map.of("DEF", 1),
            5, Map.of("DEF", 2, "ATK", 4)
        );
    }
}
