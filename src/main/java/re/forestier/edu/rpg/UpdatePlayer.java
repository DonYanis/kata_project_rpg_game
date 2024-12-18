package re.forestier.edu.rpg;

import java.util.Map;

import re.forestier.edu.rpg.object.ObjectList;
import re.forestier.edu.rpg.object.RpgObject;
public class UpdatePlayer {

    private static void giveRandomObject(Player player) {
        RpgObject object = ObjectList.getRandomObject(player.getFreeWeight());
        player.addObject(object.getName());
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

    public static void majFinDeTour(Player player) {

        if (player.currentHealthPoints == 0) {
            System.out.println("Le joueur est KO !");
            return;
        }
    
        if (player.currentHealthPoints < player.healthPoints / 2) {
            player.getAvatarClassObject().applyHealthBonus(player);
        }
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