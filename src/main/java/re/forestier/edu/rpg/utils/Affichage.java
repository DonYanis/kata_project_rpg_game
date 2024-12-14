package re.forestier.edu.rpg.utils;

import java.util.Arrays;
import java.util.List;

import re.forestier.edu.rpg.Player;

public class Affichage {

    private static final List<String> abilityOrder = Arrays.asList("DEF","VIS", "ATK", "CHA", "INT", "ALC");

    public static String afficherJoueur(Player player) {
        final StringBuilder finalString = new StringBuilder("Joueur " + player.getAvatarName() + " joué par " + player.getPlayerName());
        finalString.append("\nNiveau : " + player.retrieveLevel() + " (XP totale : " + player.getXp() + ")");
        finalString.append("\n\nCapacités :");

        for (String ability : abilityOrder) {
            if (player.getAbilities().containsKey(ability)) {
                finalString.append("\n   " + ability + " : " + player.getAbilities().get(ability));
            }
        }

        finalString.append("\n\nInventaire :");
        player.getInventory().forEach(item -> finalString.append("\n   " + item));

        return finalString.toString();
    }
}
