package re.forestier.edu.rpg;

import java.util.HashMap;
import java.util.Map;

public class Dwarf implements AvatarClass {

    @Override
    public String getName() {
        return "DWARF";
    }

    @Override
    public Map<Integer, Map<String, Integer>> getAbilitiesPerLevel() {
        Map<Integer, Map<String, Integer>> abilities = new HashMap<>();
        abilities.put(1, Map.of("ALC", 4, "INT", 1, "ATK", 3));
        abilities.put(2, Map.of("DEF", 1, "ALC", 5));
        abilities.put(3, Map.of("ATK", 4));
        abilities.put(4, Map.of("DEF", 2));
        abilities.put(5, Map.of("CHA", 1));
        return abilities;
    }

    @Override
    public void applyHealthBonus(player player) {
        if(player.inventory.contains("Holy Elixir")) {
            player.currenthealthpoints += 1;
        }
        player.currenthealthpoints += 1;
    }
}
