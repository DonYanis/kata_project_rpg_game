package re.forestier.edu.rpg;

import java.util.Map;

public interface AvatarClass {
    String getName();
    Map <Integer, Map<String, Integer>> getAbilitiesPerLevel();
    void applyHealthBonus(player player);
}
