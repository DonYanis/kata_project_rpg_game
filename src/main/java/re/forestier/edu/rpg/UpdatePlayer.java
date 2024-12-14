package re.forestier.edu.rpg;

import java.util.Map;
import java.util.Random;

public class UpdatePlayer {

    private final static String[] objectList = {"Lookout Ring : Prevents surprise attacks","Scroll of Stupidity : INT-2 when applied to an enemy", "Draupnir : Increases XP gained by 100%", "Magic Charm : Magic +10 for 5 rounds", "Rune Staff of Curse : May burn your ennemies... Or yourself. Who knows?", "Combat Edge : Well, that's an edge", "Holy Elixir : Recover your HP"};


    private static void giveRandomObject(Player player) {
        Random random = new Random();
        player.inventory.add(objectList[random.nextInt(objectList.length)]);
    }
    
    public static boolean addXp(Player player, int xp) {
        int currentLevel = player.retrieveLevel();
        player.xp += xp;
        int newLevel = player.retrieveLevel();

        if (newLevel != currentLevel) {
            giveRandomObject(player);
            updateAbilities(player, newLevel); 

            return true;
        }
        return false;
    }

    private static void updateAbilities(Player player, int level) {
        Map<String, Integer> newAbilities = player.avatarClass.getAbilitiesPerLevel().get(level);
        newAbilities.forEach(player.abilities::put);
    }


    private static void capHealthAtMaximum(Player player) {
        if (player.currentHealthPoints > player.healthPoints) {
            player.currentHealthPoints = player.healthPoints; 
        }
    }

    public static void majFinDeTour(Player player) {

        if (player.currentHealthPoints == 0) {
            System.out.println("Le joueur est KO !");
            return;
        }
    
        if (player.currentHealthPoints < player.healthPoints / 2) {
            player.getAvatarClassObject().applyHealthBonus(player);
        }
    
        capHealthAtMaximum(player);
    }

    public static void removeMoney(Player player, int amount) {
        if (player.money < amount) {
            throw new IllegalArgumentException("Player can't have a negative money!");
        }
        player.money -= amount;
    }

    public static void addMoney(Player player,int amount) {
        player.money += amount;
    }
}