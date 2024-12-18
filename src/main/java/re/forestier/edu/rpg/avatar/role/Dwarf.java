package re.forestier.edu.rpg.avatar.role;

import java.util.Map;

import re.forestier.edu.rpg.Player;
import re.forestier.edu.rpg.avatar.AvatarClass;

public class Dwarf implements AvatarClass {

    @Override
    public String getName() {
        return "DWARF";
    }

    @Override
    public Map<Integer, Map<String, Integer>> getAbilitiesPerLevel() {
        return Map.of(
            1, Map.of("ALC", 4, "INT", 1, "ATK", 3),
            2, Map.of("DEF", 1, "ALC", 5),
            3, Map.of("ATK", 4),
            4, Map.of("DEF", 2),
            5, Map.of("CHA", 1)
        );
    }

    @Override
    public void applyHealthBonus(Player player) {
        int points = player.getCurrentHealthPoints();
        if(player.getInventory().contains("Holy Elixir")) {
            points += 1;
        }
        points += 1;
        player.setCurrentHealthPoints(points);
    }
}
