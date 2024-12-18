package re.forestier.edu.rpg;

import java.util.ArrayList;
import java.util.HashMap;

import re.forestier.edu.rpg.avatar.AvatarClass;
import re.forestier.edu.rpg.avatar.AvatarClassFactory;
import re.forestier.edu.rpg.exception.NoEnoughFreeWeightException;
import re.forestier.edu.rpg.exception.NoEnoughMoneyException;
import re.forestier.edu.rpg.exception.ObjectNotFoundException;
import re.forestier.edu.rpg.object.Inventory;
import re.forestier.edu.rpg.object.ObjectList;
import re.forestier.edu.rpg.object.Object;

public class Player {
    protected String playerName;
    protected String avatarName;
    protected AvatarClass avatarClass;

    protected Integer money;

    protected int maxWeight;
    protected int healthPoints;
    protected int currentHealthPoints;
    protected int xp;

    protected HashMap<String, Integer> abilities;
    protected Inventory inventory;

    public Player(String playerName, String avatarName, String avatarClassName, int money, ArrayList<String> inventory, int maxWeight) {

        this.playerName = playerName;
        this.avatarName = avatarName;
        this.avatarClass = AvatarClassFactory.getAvatarClass(avatarClassName);
        this.money = money;
        this.maxWeight = maxWeight;
        this.inventory = new Inventory(inventory, maxWeight);
        this.abilities = new HashMap<>(avatarClass.getAbilitiesPerLevel().get(1));
        this.healthPoints = 0;
        this.currentHealthPoints = 0;
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
        return avatarClass.getName();
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

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int points) {
        healthPoints = points;
    }

    public int getCurrentHealthPoints() {
        return currentHealthPoints;
    }

    public void setCurrentHealthPoints(int points) {
        currentHealthPoints = points > healthPoints ? healthPoints : points;
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

    public void sellObject(String name, Player buyer) {

        Object objectToSell = getSellingObject(name,buyer);

        UpdatePlayer.removeMoney(buyer, objectToSell.getValue());
        money+=objectToSell.getValue();
        buyer.addObject(name);
        inventory.remove(name);
    }

    private Object getSellingObject(String name, Player buyer){
        if (!inventory.contains(name))
            throw new ObjectNotFoundException("Player doesn't own '" + name + "'");

        Object objectToSell = ObjectList.getObject(name);
        if (buyer.getMoney() < objectToSell.getValue()) 
            throw new NoEnoughMoneyException("Buyer does not have enough money");

        if (buyer.getFreeWeight() < objectToSell.getWeight()) 
            throw new NoEnoughFreeWeightException("Buyer does not have enough free weight");

        return objectToSell;
    }
}