package re.forestier.edu.rpg;

import java.util.ArrayList;
import java.util.HashMap;

import re.forestier.edu.rpg.avatar.AvatarClass;
import re.forestier.edu.rpg.avatar.AvatarClassFactory;
import re.forestier.edu.rpg.object.Inventory;
import re.forestier.edu.rpg.object.ObjectList;
import re.forestier.edu.rpg.object.Object;

public class Player {
    protected String playerName;
    protected String avatarName;
    protected AvatarClass avatarClass;

    protected Integer money;

    protected int maxWeight;
    protected int level;
    public int healthPoints;
    public int currentHealthPoints;
    protected int xp;

    protected HashMap<String, Integer> abilities;
    protected Inventory inventory;

    public Player(String playerName, String avatarName, String avatarClassName, int money, ArrayList<String> inventory, int maxWeight) {

        this.playerName = playerName;
        this.avatarName = avatarName;
        this.avatarClass = AvatarClassFactory.getAvatarClass(avatarClassName);
        if (this.avatarClass == null) return;

        this.money = money;
        this.maxWeight = maxWeight;
        this.inventory = new Inventory(inventory, maxWeight);
        this.level = 1;
        this.abilities = new HashMap<>(avatarClass.getAbilitiesPerLevel().get(level));

    }
    
    public int retrieveLevel() {
        int level = 2;      
        int previousLvlXp = 0;

        while (true) {
            previousLvlXp = (level - 1) * 10 + (level * previousLvlXp) / 4;

            if (xp < previousLvlXp) {
                return level-1;  
            }
            level++;
        }
    }

    public AvatarClass getAvatarClassObject() {
        return avatarClass;
    }

    public String getAvatarClass() {
        return avatarClass != null ? avatarClass.getName() : null;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public int getXp() {
        return this.xp;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Integer getMoney() {
        return money;
    }

    public int getLevel() {
        return level;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getCurrentHealthPoints() {
        return currentHealthPoints;
    }

    public HashMap<String, Integer> getAbilities() {
        return abilities;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public int getFreeWeight(){
        return maxWeight - inventory.getTotalWeight();
    }

    public void addObject(String name) {
        inventory.addObject(name, getFreeWeight());
    }

    public void clearInventory(){
        inventory.clear();
    }
}