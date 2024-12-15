package re.forestier.edu.rpg.avatar.role;

import java.util.Map;

import re.forestier.edu.rpg.Player;
import re.forestier.edu.rpg.avatar.AvatarClass;

public class Archer implements AvatarClass  {

    @Override
    public String getName() {
        return "ARCHER";
    }

    @Override
    public Map<Integer, Map<String, Integer>> getAbilitiesPerLevel() {
        return Map.of(
            1, Map.of("INT", 1, "ATK", 3, "CHA", 1, "VIS", 3),
            2, Map.of("DEF", 1, "CHA", 2),
            3, Map.of("ATK", 3),
            4, Map.of("DEF", 2),
            5, Map.of("ATK", 4)
        );
    }

    @Override
    public  void applyHealthBonus(Player player) {
        player.currentHealthPoints += 1;
        if(player.inventory.contains("Magic Bow")) {
            player.currentHealthPoints += (player.currentHealthPoints / 8) - 1;
        }
    }
    
}
