package re.forestier.edu.rpg;

import java.util.HashMap;
import java.util.Map;

public class Adventurer implements AvatarClass {
    @Override
    public String getName() {
        return "ADVENTURER";
    }

    @Override
    public Map<Integer, Map<String, Integer>> getAbilitiesPerLevel() {
        Map<Integer, Map<String, Integer>> abilities = new HashMap<>();
        abilities.put(1, Map.of("INT", 1, "DEF", 1, "ATK", 3, "CHA", 2));
        abilities.put(2, Map.of("INT", 2, "CHA", 3));
        abilities.put(3, Map.of("ATK", 5, "ALC", 1));
        abilities.put(4, Map.of("DEF", 3));
        abilities.put(5, Map.of("VIS", 1, "DEF", 4));
        return abilities;
    }

    @Override
    public void applyHealthBonus(Player player) {
        player.currentHealthPoints += 2;
        if (player.retrieveLevel() < 3) {
            player.currentHealthPoints -= 1;
        }
    }
}
