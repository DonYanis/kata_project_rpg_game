package re.forestier.edu.rpg.avatar.role;

import java.util.Map;

import re.forestier.edu.rpg.Player;
import re.forestier.edu.rpg.avatar.AvatarClass;

public class Adventurer implements AvatarClass {
    @Override
    public String getName() {
        return "ADVENTURER";
    }

    @Override
    public Map<Integer, Map<String, Integer>> getAbilitiesPerLevel() {
        return Map.of(
            1, Map.of("INT", 1, "DEF", 1, "ATK", 3, "CHA", 2),
            2, Map.of("INT", 2, "CHA", 3),
            3, Map.of("ATK", 5, "ALC", 1),
            4, Map.of("DEF", 3),
            5, Map.of("VIS", 1, "DEF", 4)
        );
    }

    @Override
    public void applyHealthBonus(Player player) {
        player.currentHealthPoints += 2;
        if (player.retrieveLevel() < 3) {
            player.currentHealthPoints -= 1;
        }
    }
}
