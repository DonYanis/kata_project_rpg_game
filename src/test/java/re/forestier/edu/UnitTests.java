package re.forestier.edu;

import org.junit.jupiter.api.*;
import re.forestier.edu.rpg.Player;
import re.forestier.edu.rpg.UpdatePlayer;
import re.forestier.edu.rpg.exception.NoEnoughFreeWeightException;
import re.forestier.edu.rpg.exception.NoEnoughMoneyException;
import re.forestier.edu.rpg.exception.ObjectNotFoundException;
import re.forestier.edu.rpg.utils.Affichage;
import re.forestier.edu.rpg.object.*;
import re.forestier.edu.rpg.object.Object;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Map;

public class UnitTests {

    @Test
    @DisplayName("Main test")
    public void testMain() {

        Main.main(new String[]{});

    }

    @Test
    @DisplayName("Sample test")
    void testPlayerName() {
        Player player = new Player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>(),5);
        assertThat(player.getPlayerName(), is("Florian"));
    }

    @Test
    @DisplayName("Impossible to have negative money")
    void testNegativeMoney() {
        Player p = new Player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>(),5);

        try {
            UpdatePlayer.removeMoney(p, 200);
        } catch (IllegalArgumentException e) {
            return;
        }
        fail();
    }

    @Test
    @DisplayName("add money")
    void testAddMoney(){
        Player p = new Player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>(),5);
        UpdatePlayer.addMoney(p, 100);
        assertThat(p.getMoney(), is(200));
    }

    @Test
    @DisplayName("remove money")
    void testRemoveMoney(){
        Player p = new Player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>(),5);
        UpdatePlayer.removeMoney(p, 50);
        assertThat(p.getMoney(), is(50));
    }

    @Test
    @DisplayName("test avatar class")
    void testAvatar(){
        
        Player p = new Player("Florian", "Grognak le barbare", "x", 100, new ArrayList<>(),5);
        assertThat(p.getAvatarClass(),not("x"));

        Player p1 = new Player("Florian", "Grognak le barbare", "ARCHER", 100, new ArrayList<>(),5);
        assertThat(p1.getAvatarClass(),equalTo("ARCHER"));

        Player p2 = new Player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>(),5);
        assertThat(p2.getAvatarClass(),equalTo("ADVENTURER"));

        Player p3 = new Player("Florian", "Grognak le barbare", "DWARF", 100, new ArrayList<>(),5);
        assertThat(p3.getAvatarClass(),equalTo("DWARF"));
    }

    @Test
    @DisplayName("update xp ")
    void testAddXP(){
        Player p = new Player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>(),5);
        
        UpdatePlayer.addXp(p, 11);
        assertThat(p.getXp(), equalTo(11));
    }

    @Test
    @DisplayName("update xp and level up")
    void testAddXPLevelUp(){
        Player p = new Player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>(),5);
        
        UpdatePlayer.addXp(p, 11);
        assertThat(p.getXp(), equalTo(11));
        assertThat(p.retrieveLevel(), equalTo(2));
    }

    @Test
    @DisplayName("update xp and no level up")
    void testAddXPNoLevelUp(){
        Player p = new Player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>(),5);
        
        UpdatePlayer.addXp(p, 1);
        assertThat(p.retrieveLevel(), equalTo(1));
    }

    @Test
    @DisplayName("retrieve the right level")
    void testRetrieveRightLevel(){
        Player p = new Player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>(),5);
        
        assertThat(p.retrieveLevel(), equalTo(1));
        UpdatePlayer.addXp(p, 11);
        assertThat(p.retrieveLevel(), equalTo(2));
        UpdatePlayer.addXp(p, 20);
        assertThat(p.retrieveLevel(), equalTo(3));
        UpdatePlayer.addXp(p, 30);
        assertThat(p.retrieveLevel(), equalTo(4));
        UpdatePlayer.addXp(p, 50);
        assertThat(p.retrieveLevel(), equalTo(5));
    }

    // For capturing System.out.println output
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("player KO")
    public void testMajFinDeTour_PlayerKO() {

        Player p = new Player("John", "Doe", "DWARF", 100, new ArrayList<>(),5);
        p.currentHealthPoints = 0; 

        UpdatePlayer.majFinDeTour(p);

        // Assert
        assertEquals("Le joueur est KO !" + System.lineSeparator(), outContent.toString()); 
    }

    @Test
    @DisplayName("MAJ fin tour : Dwarf with holy elexir")
    public void testMAJFinDwarfHolyElexir() {
        Player p = new Player("John", "Doe", "DWARF", 100, new ArrayList<>(),5);
        p.addObject("Holy Elixir");
        p.currentHealthPoints = 1;
        p.healthPoints = 4;
        UpdatePlayer.majFinDeTour(p);

        assertThat(p.getCurrentHealthPoints(), equalTo(3));
    }

    @Test
    @DisplayName("MAJ fin tour : Dwarf with no holy elexir")
    public void testMAJFinDwarfNoHolyElexir() {
        Player p = new Player("John", "Doe", "DWARF", 100, new ArrayList<>(),5);
        p.currentHealthPoints = 1;
        p.healthPoints = 4;
        UpdatePlayer.majFinDeTour(p);

        assertThat(p.getCurrentHealthPoints(), equalTo(2));
    }

    @Test
    @DisplayName("MAJ fin tour : Adventurer with low level")
    public void testMAJFinAdventurerLowLevel() {
        Player p = new Player("John", "Doe", "ADVENTURER", 100, new ArrayList<>(),5);
        p.currentHealthPoints = 1;
        p.healthPoints = 4;
        UpdatePlayer.majFinDeTour(p);

        assertThat(p.getCurrentHealthPoints(), equalTo(2));
    }

    @Test
    @DisplayName("MAJ fin tour : Adventurer with high level")
    public void testMAJFinAdventurerHighLevel() {
        Player p = new Player("John", "Doe", "ADVENTURER", 100, new ArrayList<>(),5);
        p.currentHealthPoints = 1;
        p.healthPoints = 4;
        UpdatePlayer.addXp(p, 100);
        UpdatePlayer.majFinDeTour(p);

        assertThat(p.getCurrentHealthPoints(), equalTo(3));
    }

    @Test
    @DisplayName("MAJ fin tour : ARCHER with Magic Bow")
    public void testMAJFinArcherMagicBow() {
        Player p = new Player("John", "Doe", "ARCHER", 100, new ArrayList<>(),5);
        p.addObject("Magic Bow");
        p.currentHealthPoints = 7;
        p.healthPoints = 20;
        UpdatePlayer.majFinDeTour(p);

        assertThat(p.getCurrentHealthPoints(), equalTo(8));
    }

    @Test
    @DisplayName("MAJ fin tour : ARCHER with No Magic Bow")
    public void testMAJFinArcherNoMagicBow() {
        Player p = new Player("John", "Doe", "ARCHER", 100, new ArrayList<>(),5);
        p.currentHealthPoints = 1;
        p.healthPoints = 4;
        UpdatePlayer.majFinDeTour(p);

        assertThat(p.getCurrentHealthPoints(), equalTo(2));
    }

    @Test
    @DisplayName("MAJ fin tour : current health must be inf or equal to health")
    public void testCurrentHealthInfEquToHealth() {
        Player p = new Player("John", "Doe", "ARCHER", 100, new ArrayList<>(),5);
        p.currentHealthPoints = 5;
        p.healthPoints = 4;
        UpdatePlayer.majFinDeTour(p);

        assertThat(p.getCurrentHealthPoints(), equalTo(4));
    }

    @Test
    @DisplayName("MAJ fin tour : current health doesnt change")
    public void testCurrentHealthDoesNotChange() {
        Player p = new Player("John", "Doe", "ARCHER", 100, new ArrayList<>(),5);
        p.currentHealthPoints = 3;
        p.healthPoints = 4;
        UpdatePlayer.majFinDeTour(p);

        assertThat(p.getCurrentHealthPoints(), equalTo(3));
    }

 /* Les tests ajoutés dans cette partie sont fait pour essayer de passer l'étape 'Mutation' à 100% */

    @Test
    public void testAfficherJoueurInventory() {
        Player p = new Player("John", "Doe", "ARCHER", 100, new ArrayList<>(),10);
        p.addObject("Magic Bow");
        p.addObject("Holy Elixir");

        String expectedOutput = "Joueur Doe joué par John\n" +
                                "Niveau : 1 (XP totale : 0)\n\n" +
                                "Capacités :\n   VIS : 3\n   ATK : 3\n   CHA : 1\n   INT : 1\n\n" +
                                "Inventaire :\n   Holy Elixir\n   Magic Bow";

        String actualOutput = Affichage.afficherJoueur(p);

        assertEquals(expectedOutput, actualOutput);
    }

     @Test
    public void testRemoveAllMoney() {
        Player p = new Player("John", "Doe", "ARCHER", 100, new ArrayList<>(),5);

        UpdatePlayer.removeMoney(p, 100);
        assertEquals(0, p.getMoney()); 
        
    }

    @Test
    @DisplayName("retrieve the right level fixed")
    void testRetrieveRightLevelFixed(){
        Player p = new Player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>(),5);
        
        UpdatePlayer.addXp(p, 10);
        assertThat(p.retrieveLevel(), equalTo(2));

        UpdatePlayer.addXp(p, 17);
        assertThat(p.retrieveLevel(), equalTo(3));

        UpdatePlayer.addXp(p, 30);
        assertThat(p.retrieveLevel(), equalTo(4));

    }

    @Test
    public void testAddXp_LevelUp() {
        Player p = new Player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>(),5);

        assertFalse(UpdatePlayer.addXp(p, 1)); 

        // Now add enough XP to level up
        assertTrue(UpdatePlayer.addXp(p, 11)); // This should level up the player
        assertEquals(2, p.retrieveLevel()); // Check new level
    }

    @Test
    @DisplayName("update inventory after leveling up")
    public void testAddXp_InventoryUpdate() {
        Player p = new Player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>(),5);
        UpdatePlayer.addXp(p, 20);

        assertFalse(p.getInventory().isEmpty()); // Inventory should have an item
    }

    @Test
    @DisplayName("MAJ fin tour Mutation : current health doesnt change")
    public void testCurrentHealthDoesNotChangeMutation() {
        Player p = new Player("John", "Doe", "ARCHER", 100, new ArrayList<>(),5);
        p.currentHealthPoints = 2;
        p.healthPoints = 4;
        UpdatePlayer.majFinDeTour(p);

        assertThat(p.getCurrentHealthPoints(), equalTo(2));
    }



    // Test before adding Goblin class : 
    @DisplayName("Test Goblin Name")
    void testGoblinName() {
        Player p = new Player("John", "Doe", "GOBLIN", 100, new ArrayList<>(),5);
        assertThat(p.getAvatarClass(), is("GOBLIN"));
    }

    @Test
    @DisplayName("Test Goblin Abilities at each Level")
    void testGetAbilitiesLevel1() {
        Player p = new Player("John", "Doe", "GOBLIN", 100, new ArrayList<>(),5);
        Map<String, Integer> level1Abilities = p.getAvatarClassObject().getAbilitiesPerLevel().get(1);
        Map<String, Integer> level2Abilities = p.getAvatarClassObject().getAbilitiesPerLevel().get(2);
        Map<String, Integer> level3Abilities = p.getAvatarClassObject().getAbilitiesPerLevel().get(3);
        Map<String, Integer> level4Abilities = p.getAvatarClassObject().getAbilitiesPerLevel().get(4);
        Map<String, Integer> level5Abilities = p.getAvatarClassObject().getAbilitiesPerLevel().get(5);

        assertThat(level1Abilities, hasEntry("ALC", 1));
        assertThat(level1Abilities, hasEntry("INT", 2));
        assertThat(level1Abilities, hasEntry("ATK", 2));

        assertThat(level2Abilities, hasEntry("ATK", 3));
        assertThat(level2Abilities, hasEntry("ALC", 4));

        assertThat(level3Abilities, hasEntry("VIS", 1));

        assertThat(level4Abilities, hasEntry("DEF", 1));

        assertThat(level5Abilities, hasEntry("DEF", 2));
        assertThat(level5Abilities, hasEntry("ATK", 4));
    }

    // Test before ceating object package : 
    @Test
    @DisplayName("Test Object class getters")
    void testObjectGetters() {
        Object object = new Object("Lookout Ring", "Prevents surprise attacks", 1, 50);
        assertEquals("Lookout Ring", object.getName());

        assertEquals("Prevents surprise attacks", object.getDescription());

        assertEquals(1, object.getWeight());

        assertEquals(50, object.getValue());
    }

    @Test
    @DisplayName("Test get object")
    void testGetObjectByName() {
        Object object = ObjectList.getObject("Lookout Ring");
        assertNotNull(object);
        assertEquals("Lookout Ring", object.getName());
    }

    @Test
    @DisplayName("Test get Nonexistent object")
    void testGetObjectByNameNotFound() {
        Object object = ObjectList.getObject("Nonexistent Item");
        assertNull(object);
    }

    @Test
    @DisplayName("Test objectlist.contains")
    void testContains() {
        assertTrue(ObjectList.contains("Draupnir"));
        assertFalse(ObjectList.contains("Magic"));
    }


    @Test
    @DisplayName("random object's weight should be inf or eq to given weight")
    void testGetRandomObject() {
        Object randomObject = ObjectList.getRandomObject(2);
        assertNotNull(randomObject );
        assertTrue(randomObject.getWeight() < 2);
    }

    @Test
    @DisplayName("Object should be null when no objects fit the weight condition")
    void testGetRandomObjectNoMatching() {
        Object randomObject = ObjectList.getRandomObject(0);
        assertNull(randomObject);
    }

    private Inventory inventory;
    @BeforeEach
    void setUpInventory() {
        inventory = new Inventory();
        inventory.addObject("Lookout Ring",10);
        inventory.addObject("Draupnir",10);
        inventory.addObject("Combat Edge",10);
    }

    @Test
    @DisplayName("Test Adding Objects to Inventory")
    void testAddObject() {
        assertTrue(inventory.contains("Lookout Ring"), "Inventory should contain 'Lookout Ring'");
        assertTrue(inventory.contains("Draupnir"), "Inventory should contain 'Draupnir'");
        assertTrue(inventory.contains("Combat Edge"), "Inventory should contain 'Combat Edge'");
    }

    @Test
    @DisplayName("Test Adding Unknown Object")
    void testAddUnknownObject() {
        Exception exception = assertThrows(ObjectNotFoundException.class, () -> {
            inventory.addObject("NonExistentItem", 10);
        });
        assertEquals("Object 'NonExistentItem' does not exist in ObjectList.", exception.getMessage());
    }

    @Test
    @DisplayName("Test Removing Object from Inventory")
    void testRemoveObject() {
        inventory.remove("Combat Edge");
        assertFalse(inventory.contains("Combat Edge"));
    }


    @Test
    @DisplayName("Test Contains Method for Non-Existing Items")
    void testContainsFalse() {
        assertFalse(inventory.contains("Nonexistent Item"));
    }

    @Test
    @DisplayName("Test Calculating Total Weight of Inventory")
    void testGetTotalWeight() {
        assertEquals(4, inventory.getTotalWeight());
    }

    @Test
    @DisplayName("Test Calculating Total Value of Inventory")
    void testGetTotalValue() {
        assertEquals(180, inventory.getTotalValue());
    }

    @Test
    @DisplayName("Test Removing Non-Existent Object from Inventory")
    void testRemoveNonExistentObject() {
        inventory.remove("Nonexistent Item"); 
        assertEquals(3, inventory.getInventory().size());
    }

    //tests before linking object package to project

    @Test
    @DisplayName("Test retrieving  maximum weight")
    void testGetMaxWeight() {
        Player player = new Player("John", "Doe", "GOBLIN", 100, new ArrayList<>(),5);
        assertEquals(5, player.getMaxWeight());
    }

    @Test
    @DisplayName("Test calculating the free weight in the inventory")
    void testGetFreeWeight() {
        ArrayList<String> startingItems = new ArrayList<>();
        startingItems.add("Lookout Ring"); // Weight = 1
        startingItems.add("Draupnir");     // Weight = 2
        
        Player player = new Player("John", "Doe", "GOBLIN", 100, startingItems,10);
        assertEquals(7, player.getFreeWeight());
    }

    @Test
    @DisplayName("Test adding an object that exceeds the free weight")
    void testAddObjectExceedsWeight() {
        ArrayList<String> startingItems = new ArrayList<>();
        startingItems.add("Lookout Ring"); // Weight = 1
        
        Player player = new Player("John", "Doe", "GOBLIN", 100, startingItems,3);

        player.addObject("Rune Staff of Curse"); // Weight = 3
        assertFalse(player.getInventory().contains("Rune Staff of Curse"));
    }

    @Test
    @DisplayName("Test adding a non-existent object")
    void testAddNonExistentObject() {
        Player player = new Player("John", "Doe", "GOBLIN", 100, new ArrayList<>(),5);
        
        Exception exception = assertThrows(ObjectNotFoundException.class, () -> {
            player.addObject("NonExistentItem");
        });
        assertEquals("Object 'NonExistentItem' does not exist in ObjectList.", exception.getMessage());
    }

    @Test
    @DisplayName("Test clearing inventory")
    void testClearInventory() {
        Player player = new Player("John", "Doe", "GOBLIN", 100, new ArrayList<>(),5);

        player.addObject("Lookout Ring");
        assertTrue(player.getInventory().contains("Lookout Ring"));
        player.clearInventory();
        assertTrue(player.getInventory().isEmpty());
    }

    //test before creating sell method
    @Test
    @DisplayName("Test: Successfully selling an object")
    void testSellObjectSuccess() {
        Player seller = new Player("Seller", "Warrior", "DWARF", 100, new ArrayList<>(), 1000);
        Player buyer = new Player("Buyer", "Mage", "DWARF", 200, new ArrayList<>(), 1000);
        Object  objectToSell = ObjectList.getObject("Lookout Ring"); 
        
        seller.addObject("Lookout Ring");
        
        assertTrue(seller.sellObject("Lookout Ring", buyer));
        // Verify that the object is transferred
        assertTrue(buyer.getInventory().contains("Lookout Ring"));
        // Verify that money is transferred
        assertEquals(200 - objectToSell.getValue(), buyer.getMoney());
        // Verify that object is removed from seller
        assertFalse(seller.getInventory().contains("Lookout Ring"));
    }

    @Test
    @DisplayName("Test: Seller does not own the object")
    void testSellObjectSellerDoesNotOwn() {
        Player seller = new Player("Seller", "Warrior", "DWARF", 100, new ArrayList<>(), 1000);
        Player buyer = new Player("Buyer", "Mage", "DWARF", 200, new ArrayList<>(), 1000);
        
        Exception exception = assertThrows(ObjectNotFoundException.class, () -> {
            seller.sellObject("NonExistentItem", buyer);
        });
        assertEquals("Player doesn't own 'NonExistentItem'", exception.getMessage());

        assertFalse(buyer.getInventory().contains("NonExistentItem"));
    }

    @Test
    @DisplayName("Test: Buyer does not have enough money")
    void testSellObjectBuyerLacksMoney() {

        Player seller = new Player("Seller", "Warrior", "DWARF", 100, new ArrayList<>(), 1000);
        Player buyer = new Player("Buyer", "Mage", "DWARF", 0, new ArrayList<>(), 1000);
        
        seller.addObject("Lookout Ring");

        Exception exception = assertThrows(NoEnoughMoneyException.class, () -> {
            seller.sellObject("Lookout Ring", buyer);
        });
        assertEquals("Buyer does not have enough money", exception.getMessage());

        assertFalse(buyer.getInventory().contains("Lookout Ring"));
        assertEquals(100, seller.getMoney());
    }

    @Test
    @DisplayName("Test: Buyer does not have enough free weight")
    void testSellObjectBuyerLacksWeight() {
        Player seller = new Player("Seller", "Warrior", "DWARF", 100, new ArrayList<>(), 10);
        Player buyer = new Player("Buyer", "Mage", "DWARF", 100, new ArrayList<>(), 0);
        
        seller.addObject("Lookout Ring");
        Exception exception = assertThrows(NoEnoughFreeWeightException.class, () -> {
            seller.sellObject("Lookout Ring", buyer);
        });
        assertEquals("Buyer does not have enough free weight", exception.getMessage());

        assertFalse(buyer.getInventory().contains("Lookout Ring"));
    }
}