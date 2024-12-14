package re.forestier.edu.rpg.avatar;

import java.util.HashMap;
import java.util.Map;

import re.forestier.edu.rpg.Player;

public class Archer implements AvatarClass  {

    @Override
    public String getName() {
        return "ARCHER";
    }

    @Override
    public Map<Integer, Map<String, Integer>> getAbilitiesPerLevel() {
        Map<Integer, Map<String, Integer>> abilities = new HashMap<>();
        abilities.put(1, Map.of("INT", 1, "ATK", 3, "CHA", 1, "VIS", 3));
        abilities.put(2, Map.of("DEF", 1, "CHA", 2));
        abilities.put(3, Map.of("ATK", 3));
        abilities.put(4, Map.of("DEF", 2));
        abilities.put(5, Map.of("ATK", 4));
        return abilities;
    }

    @Override
    public  void applyHealthBonus(Player player) {
        player.currentHealthPoints += 1;
        if(player.inventory.contains("Magic Bow")) {
            player.currentHealthPoints += (player.currentHealthPoints / 8) - 1;
        }
    }
    
}
