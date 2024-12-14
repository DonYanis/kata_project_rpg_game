package re.forestier.edu.rpg;


import java.util.Random;

public class UpdatePlayer {

    private final static String[] objectList = {"Lookout Ring : Prevents surprise attacks","Scroll of Stupidity : INT-2 when applied to an enemy", "Draupnir : Increases XP gained by 100%", "Magic Charm : Magic +10 for 5 rounds", "Rune Staff of Curse : May burn your ennemies... Or yourself. Who knows?", "Combat Edge : Well, that's an edge", "Holy Elixir : Recover your HP"
    };


    private static void giveRandomObject(player player) {
        Random random = new Random();
        player.inventory.add(objectList[random.nextInt(objectList.length)]);
    }
    
    //3- the final refactored method
    public static boolean addXp(player player, int xp) {
        int currentLevel = player.retrieveLevel();
        player.xp += xp;
        int newLevel = player.retrieveLevel();

        if (newLevel != currentLevel) {
            giveRandomObject(player);
            player.getAvatarClassObject().updateAbilities(player, newLevel); 

            return true;
        }
        return false;
    }


    //3- extract the part that caps the health
    private static void capHealthAtMaximum(player player) {
        if (player.currentHealthPoints > player.healthPoints) {
            player.currentHealthPoints = player.healthPoints; 
        }
    }

    //4- now code the new MAJf fin de tour function
    public static void majFinDeTour(player player) {

        // Check if player is KO
        if (player.currentHealthPoints == 0) {
            System.out.println("Le joueur est KO !");
            return;
        }
    
        // Apply health restoration logic if below half health
        if (player.currentHealthPoints < player.healthPoints / 2) {
            player.getAvatarClassObject().applyHealthBonus(player);
        }
    
        // Ensure the health doesn't exceed the max health
        capHealthAtMaximum(player);
    }

}