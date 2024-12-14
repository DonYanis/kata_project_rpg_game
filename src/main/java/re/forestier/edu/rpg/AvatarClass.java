package re.forestier.edu.rpg;

import java.util.Map;

public interface AvatarClass {
    String getName();
    Map <Integer, Map<String, Integer>> getAbilitiesPerLevel();
    void applyHealthBonus(Player player);
    
    default void updateAbilities(Player player, int level) {
        Map<String, Integer> newAbilities = getAbilitiesPerLevel().get(level);
        newAbilities.forEach(player.abilities::put);
    }
}
