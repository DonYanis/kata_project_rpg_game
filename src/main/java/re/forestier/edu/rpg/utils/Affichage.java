package re.forestier.edu.rpg.utils;

import java.util.Arrays;
import java.util.List;

import re.forestier.edu.rpg.Player;

public class Affichage {

    private static final List<String> ABILITY_ORDER = Arrays.asList("DEF", "VIS", "ATK", "CHA", "INT", "ALC");

    public static String afficherJoueur(Player player) {
        StringBuilder finalString = new StringBuilder();

        appendPlayerDetails(finalString, player);
        appendAbilities(finalString, player);
        appendInventory(finalString, player);

        return finalString.toString();
    }


    private static void appendPlayerDetails(StringBuilder finalString, Player player) {
        finalString.append(String.format("Joueur %s joué par %s", player.getAvatarName(), player.getPlayerName()))
                .append("\nNiveau : ").append(player.retrieveLevel())
                .append(" (XP totale : ").append(player.getXp()).append(")\n\nCapacités :");
    }


    private static void appendAbilities(StringBuilder finalString, Player player) {
        ABILITY_ORDER.stream()
                .filter(ability -> player.getAbilities().containsKey(ability)) 
                .forEach(ability -> finalString.append("\n   ").append(ability).append(" : ").append(player.getAbilities().get(ability)));
    }


    private static void appendInventory(StringBuilder finalString, Player player) {
        finalString.append("\n\nInventaire :");
        player.getInventory().forEach(item -> finalString.append("\n   ").append(item));
    }
}
