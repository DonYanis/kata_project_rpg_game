package re.forestier.edu.rpg;

import java.util.Arrays;
import java.util.List;

public class Affichage {

    private static final List<String> abilityOrder = Arrays.asList("DEF","VIS", "ATK", "CHA", "INT", "ALC");

    public static String afficherJoueur(Player player) {
        final StringBuilder finalString = new StringBuilder("Joueur " + player.avatarName + " joué par " + player.playerName);
        finalString.append("\nNiveau : " + player.retrieveLevel() + " (XP totale : " + player.xp + ")");
        finalString.append("\n\nCapacités :");

        for (String ability : abilityOrder) {
            if (player.abilities.containsKey(ability)) {
                finalString.append("\n   " + ability + " : " + player.abilities.get(ability));
            }
        }

        finalString.append("\n\nInventaire :");
        player.inventory.forEach(item -> finalString.append("\n   " + item));

        return finalString.toString();
    }
}
