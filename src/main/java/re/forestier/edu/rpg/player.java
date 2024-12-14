package re.forestier.edu.rpg;

import java.util.ArrayList;
import java.util.HashMap;

public class player {
    public String playerName;
    public String avatarName;
    private AvatarClass avatarClass;

    public Integer money;

    public int level;
    public int healthPoints;
    public int currentHealthPoints;
    protected int xp;

    public HashMap<String, Integer> abilities;
    public ArrayList<String> inventory;

    public player(String playerName, String avatarName, String avatarClassName, int money, ArrayList<String> inventory) {

        this.playerName = playerName;
        this.avatarName = avatarName;
        this.avatarClass = AvatarClassFactory.getAvatarClass(avatarClassName);
        if (this.avatarClass == null) return;

        this.money = money;
        this.inventory = inventory != null ? inventory : new ArrayList<>();
        this.level = 1;
        this.abilities = new HashMap<>(avatarClass.getAbilitiesPerLevel().get(level));

    }
    
    public AvatarClass getAvatarClassObject() {
        return avatarClass;
    }

    public String getAvatarClass() {
        return avatarClass != null ? avatarClass.getName() : null;
    }

    public void removeMoney(int amount) {
        if (money < amount) {
            throw new IllegalArgumentException("Player can't have a negative money!");
        }
        money -= amount;
    }

    public void addMoney(int amount) {
        money += amount;
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

    public int getXp() {
        return this.xp;
    }

}