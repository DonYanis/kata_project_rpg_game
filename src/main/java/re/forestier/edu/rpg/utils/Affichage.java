package re.forestier.edu.rpg.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import re.forestier.edu.rpg.Player;
import re.forestier.edu.rpg.object.Object;

public class Affichage {

    private static final List<String> ABILITY_ORDER = Arrays.asList("DEF", "VIS", "ATK", "CHA", "INT", "ALC");

    public static String afficherJoueur(Player player) {
        StringBuilder finalString = new StringBuilder();

        appendPlayerDetails(finalString, player);
        appendAbilities(finalString, player);
        appendInventory(finalString, player);

        return finalString.toString();
    }

    public static String printPlayerInMarkDown(Player player) {
        StringBuilder profile = new StringBuilder();

        appendHeader(profile, player);
        appendPlayerStatus(profile, player);
        appendStats(profile, player);
        appendInventoryStatus(profile, player);
        appendFooter(profile);

        return profile.toString();
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
        player.getInventory().getObjects().forEach(item -> {
            finalString.append("\n   ").append(((Object) item).getName());
    });
    }

    private static void appendHeader(StringBuilder profile, Player player) {
        profile.append("## ============= PLAYER PROFILE =============  \n")
                .append("**+ Name:** ").append(player.getPlayerName()).append("  \n")
                .append("**+ Controlled By:** ").append(player.getAvatarName()).append("  \n")
                .append("**+ Class:** ").append(player.getAvatarClass()).append("  \n")
                .append("# -------------------------------------------  \n");
    }

    private static void appendPlayerStatus(StringBuilder profile, Player player) {
        profile.append("**+ LEVEL:** ").append(player.getLevel()).append("  \n")
                .append("**+ MONEY:** ").append(player.getMoney()).append(" Coins  \n")
                .append("**+ XP:** ").append(player.getXp()).append("  \n")
                .append("**+ HEALTH:** ").append(player.getCurrentHealthPoints()).append("/")
                .append(player.getHealthPoints()).append(" HP  \n")
                .append("# -------------------------------------------  \n");
    }

    private static void appendStats(StringBuilder profile, Player player) {
        profile.append("**+ STATS:**  \n");
        Map<String, Integer> stats = player.getAbilities();
        stats.forEach((key, value) -> {
            profile.append("- **[").append(key).append("]** : ").append(value).append("  \n");
        });
        profile.append("# -------------------------------------------  \n");
    }

    private static void appendInventoryStatus(StringBuilder profile, Player player) {
        profile.append("**+ INVENTORY STATUS:** ").append(player.getInventory().getTotalWeight())
                .append("/").append(player.getMaxWeight()).append(" kg  \n")
                .append("**+ ITEMS HELD:**  \n");
        player.getInventory().getObjects().forEach(item -> {
            profile.append("- ").append(item.getName()).append("  \n");
        });
    }

    private static void appendFooter(StringBuilder profile) {
        profile.append("## =========================================  \n");
    }
}
