package re.forestier.edu.rpg.avatar;

import java.util.Map;

import re.forestier.edu.rpg.Player;

public interface AvatarClass {
    String getName();
    Map <Integer, Map<String, Integer>> getAbilitiesPerLevel();
    void applyHealthBonus(Player player);
}
