package re.forestier.edu.rpg;

import java.util.HashMap;
import java.util.Random;

public class UpdatePlayer {

    private final static String[] objectList = {"Lookout Ring : Prevents surprise attacks","Scroll of Stupidity : INT-2 when applied to an enemy", "Draupnir : Increases XP gained by 100%", "Magic Charm : Magic +10 for 5 rounds", "Rune Staff of Curse : May burn your ennemies... Or yourself. Who knows?", "Combat Edge : Well, that's an edge", "Holy Elixir : Recover your HP"
    };

    public static HashMap<String, HashMap<Integer, HashMap<String, Integer>>> abilitiesPerTypeAndLevel() {
        HashMap<String, HashMap<Integer, HashMap<String, Integer>>> abilitiesPerTypeAndLevel = new HashMap<>();

        HashMap<Integer, HashMap<String, Integer>> adventurerMap = new HashMap<>();
        HashMap<String, Integer> adventurerLevel1 = new HashMap<>();
        adventurerLevel1.put("INT", 1);
        adventurerLevel1.put("DEF", 1);
        adventurerLevel1.put("ATK", 3);
        adventurerLevel1.put("CHA", 2);
        adventurerMap.put(1, adventurerLevel1);

        HashMap<String, Integer> adventurerLevel2 = new HashMap<>();
        adventurerLevel2.put("INT", 2);
        adventurerLevel2.put("CHA", 3);
        adventurerMap.put(2, adventurerLevel2);

        HashMap<String, Integer> adventurerLevel3 = new HashMap<>();
        adventurerLevel3.put("ATK", 5);
        adventurerLevel3.put("ALC", 1);
        adventurerMap.put(3, adventurerLevel3);

        HashMap<String, Integer> adventurerLevel4 = new HashMap<>();
        adventurerLevel4.put("DEF", 3);
        adventurerMap.put(4, adventurerLevel4);

        HashMap<String, Integer> adventurerLevel5 = new HashMap<>();
        adventurerLevel5.put("VIS", 1);
        adventurerLevel5.put("DEF", 4);
        adventurerMap.put(5, adventurerLevel5);

        abilitiesPerTypeAndLevel.put("ADVENTURER", adventurerMap);


        HashMap<Integer, HashMap<String, Integer>> archerMap = new HashMap<>();
        HashMap<String, Integer> archerLevel1 = new HashMap<>();
        archerLevel1.put("INT", 1);
        archerLevel1.put("ATK", 3);
        archerLevel1.put("CHA", 1);
        archerLevel1.put("VIS", 3);
        archerMap.put(1, archerLevel1);

        HashMap<String, Integer> archerLevel2 = new HashMap<>();
        archerLevel2.put("DEF", 1);
        archerLevel2.put("CHA", 2);
        archerMap.put(2, archerLevel2);

        HashMap<String, Integer> archerLevel3 = new HashMap<>();
        archerLevel3.put("ATK", 3);
        archerMap.put(3, archerLevel3);

        HashMap<String, Integer> archerLevel4 = new HashMap<>();
        archerLevel4.put("DEF", 2);
        archerMap.put(4, archerLevel4);

        HashMap<String, Integer> archerLevel5 = new HashMap<>();
        archerLevel5.put("ATK", 4);
        archerMap.put(5, archerLevel5);

        abilitiesPerTypeAndLevel.put("ARCHER", archerMap);


        HashMap<Integer, HashMap<String, Integer>> dwarf = new HashMap<>();
        HashMap<String, Integer> dwarfLevel1 = new HashMap<>();
        dwarfLevel1.put("ALC", 4);
        dwarfLevel1.put("INT", 1);
        dwarfLevel1.put("ATK", 3);
        dwarf.put(1, dwarfLevel1);

        HashMap<String, Integer> dwarfLevel2 = new HashMap<>();
        dwarfLevel2.put("DEF", 1);
        dwarfLevel2.put("ALC", 5);
        dwarf.put(2, dwarfLevel2);

        HashMap<String, Integer> dwarfLevel3 = new HashMap<>();
        dwarfLevel3.put("ATK", 4);
        dwarf.put(3, dwarfLevel3);

        HashMap<String, Integer> dwarfLevel4 = new HashMap<>();
        dwarfLevel4.put("DEF", 2);
        dwarf.put(4, dwarfLevel4);

        HashMap<String, Integer> dwarfLevel5 = new HashMap<>();
        dwarfLevel5.put("CHA", 1);
        dwarf.put(5, dwarfLevel5);

        abilitiesPerTypeAndLevel.put("DWARF", dwarf);

        return abilitiesPerTypeAndLevel;
    }

    //Refactor addXp
    //1- extract giving new object 

    private static void giveRandomObject(player player) {
        Random random = new Random();
        player.inventory.add(objectList[random.nextInt(objectList.length)]);
    }

    //2- extract updating abilities
    private static void upgradePlayerAbilities(player player, int newLevel) {
        HashMap<String, Integer> abilities = abilitiesPerTypeAndLevel()
            .get(player.getAvatarClass())
            .get(newLevel);
        
        abilities.forEach(player.abilities::put);
    }
    
    //3- the final refactored method
    public static boolean addXp(player player, int xp) {
        int currentLevel = player.retrieveLevel();
        player.xp += xp;
        int newLevel = player.retrieveLevel();

        if (newLevel != currentLevel) {
            giveRandomObject(player);
            upgradePlayerAbilities(player, newLevel);
            return true;
        }
        return false;
    }

    //refactor MAJ fin tour : 
    //1- extract health modification : 
    private static void applyDwarfHealthBonus(player player) {
        if(player.inventory.contains("Holy Elixir")) {
            player.currenthealthpoints += 1;
        }
        player.currenthealthpoints += 1;
    }
    
    private static void applyAdventurerHealthBonus(player player) {
        player.currenthealthpoints += 2;
        if(player.retrieveLevel() < 3) {
            player.currenthealthpoints -= 1; 
        }
    }
    
    private static void applyArcherHealthBonus(player player) {
        player.currenthealthpoints += 1;
        if(player.inventory.contains("Magic Bow")) {
            player.currenthealthpoints += (player.currenthealthpoints / 8) - 1;
        }
    }

    //2- apply health modification according to player
    private static void applyClassHealthBonus(player player) {
        switch (player.getAvatarClass()) {
            case "DWARF":
                applyDwarfHealthBonus(player);
                break;
            case "ADVENTURER":
                applyAdventurerHealthBonus(player);
                break;
            case "ARCHER":
                applyArcherHealthBonus(player);
                break;
            default:
                break;
        }
    }

    //3- extract the part that caps the health
    private static void capHealthAtMaximum(player player) {
        if (player.currenthealthpoints > player.healthpoints) {
            player.currenthealthpoints = player.healthpoints; 
        }
    }

    //4- now code the new MAJf fin de tour function
    public static void majFinDeTour(player player) {

        // Check if player is KO
        if (player.currenthealthpoints == 0) {
            System.out.println("Le joueur est KO !");
            return;
        }
    
        // Apply health restoration logic if below half health
        if (player.currenthealthpoints < player.healthpoints / 2) {
            applyClassHealthBonus(player);  // Handle class-specific health bonuses
        }
    
        // Ensure the health doesn't exceed the max health
        capHealthAtMaximum(player);
    }

}