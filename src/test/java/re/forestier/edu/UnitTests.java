package re.forestier.edu;

import org.junit.jupiter.api.*;
import re.forestier.edu.rpg.player;
import re.forestier.edu.rpg.Affichage;
import re.forestier.edu.rpg.UpdatePlayer;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class UnitTests {

    @Test
    @DisplayName("Main test")
    public void testMain() {

        Main.main(new String[]{});

    }

    @Test
    @DisplayName("Sample test")
    void testPlayerName() {
        player player = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        assertThat(player.playerName, is("Florian"));
    }

    @Test
    @DisplayName("Impossible to have negative money")
    void testNegativeMoney() {
        player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());

        try {
            p.removeMoney(200);
        } catch (IllegalArgumentException e) {
            return;
        }
        fail();
    }

    @Test
    @DisplayName("add money")
    void testAddMoney(){
        player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        p.addMoney((100));
        assertThat(p.money, is(200));
    }

    @Test
    @DisplayName("remove money")
    void testRemoveMoney(){
        player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        p.removeMoney((50));
        assertThat(p.money, is(50));
    }

    @Test
    @DisplayName("test avatar class")
    void testAvatar(){
        
        player p = new player("Florian", "Grognak le barbare", "x", 100, new ArrayList<>());
        assertThat(p.getAvatarClass(),not("x"));

        player p1 = new player("Florian", "Grognak le barbare", "ARCHER", 100, new ArrayList<>());
        assertThat(p1.getAvatarClass(),equalTo("ARCHER"));

        player p2 = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        assertThat(p2.getAvatarClass(),equalTo("ADVENTURER"));

        player p3 = new player("Florian", "Grognak le barbare", "DWARF", 100, new ArrayList<>());
        assertThat(p3.getAvatarClass(),equalTo("DWARF"));
    }

    @Test
    @DisplayName("update xp ")
    void testAddXP(){
        player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        
        UpdatePlayer.addXp(p, 11);
        assertThat(p.getXp(), equalTo(11));
    }

    @Test
    @DisplayName("update xp and level up")
    void testAddXPLevelUp(){
        player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        
        UpdatePlayer.addXp(p, 11);
        assertThat(p.getXp(), equalTo(11));
        assertThat(p.retrieveLevel(), equalTo(2));
    }

    @Test
    @DisplayName("update xp and no level up")
    void testAddXPNoLevelUp(){
        player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        
        UpdatePlayer.addXp(p, 1);
        assertThat(p.retrieveLevel(), equalTo(1));
    }

    @Test
    @DisplayName("retrieve the right level")
    void testRetrieveRightLevel(){
        player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        
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

        player p = new player("John", "Doe", "DWARF", 100, new ArrayList<>());
        p.currentHealthPoints = 0; 

        UpdatePlayer.majFinDeTour(p);

        // Assert
        assertEquals("Le joueur est KO !" + System.lineSeparator(), outContent.toString()); 
    }

    @Test
    @DisplayName("MAJ fin tour : Dwarf with holy elexir")
    public void testMAJFinDwarfHolyElexir() {
        player p = new player("John", "Doe", "DWARF", 100, new ArrayList<>());
        p.inventory.add("Holy Elixir");
        p.currentHealthPoints = 1;
        p.healthPoints = 4;
        UpdatePlayer.majFinDeTour(p);

        assertThat(p.currentHealthPoints, equalTo(3));
    }

    @Test
    @DisplayName("MAJ fin tour : Dwarf with no holy elexir")
    public void testMAJFinDwarfNoHolyElexir() {
        player p = new player("John", "Doe", "DWARF", 100, new ArrayList<>());
        p.currentHealthPoints = 1;
        p.healthPoints = 4;
        UpdatePlayer.majFinDeTour(p);

        assertThat(p.currentHealthPoints, equalTo(2));
    }

    @Test
    @DisplayName("MAJ fin tour : Adventurer with low level")
    public void testMAJFinAdventurerLowLevel() {
        player p = new player("John", "Doe", "ADVENTURER", 100, new ArrayList<>());
        p.currentHealthPoints = 1;
        p.healthPoints = 4;
        UpdatePlayer.majFinDeTour(p);

        assertThat(p.currentHealthPoints, equalTo(2));
    }

    @Test
    @DisplayName("MAJ fin tour : Adventurer with high level")
    public void testMAJFinAdventurerHighLevel() {
        player p = new player("John", "Doe", "ADVENTURER", 100, new ArrayList<>());
        p.currentHealthPoints = 1;
        p.healthPoints = 4;
        UpdatePlayer.addXp(p, 100);
        UpdatePlayer.majFinDeTour(p);

        assertThat(p.currentHealthPoints, equalTo(3));
    }

    @Test
    @DisplayName("MAJ fin tour : ARCHER with Magic Bow")
    public void testMAJFinArcherMagicBow() {
        player p = new player("John", "Doe", "ARCHER", 100, new ArrayList<>());
        p.inventory.add("Magic Bow");
        p.currentHealthPoints = 7;
        p.healthPoints = 20;
        UpdatePlayer.majFinDeTour(p);

        assertThat(p.currentHealthPoints, equalTo(8));
    }

    @Test
    @DisplayName("MAJ fin tour : ARCHER with No Magic Bow")
    public void testMAJFinArcherNoMagicBow() {
        player p = new player("John", "Doe", "ARCHER", 100, new ArrayList<>());
        p.currentHealthPoints = 1;
        p.healthPoints = 4;
        UpdatePlayer.majFinDeTour(p);

        assertThat(p.currentHealthPoints, equalTo(2));
    }

    @Test
    @DisplayName("MAJ fin tour : current health must be inf or equal to health")
    public void testCurrentHealthInfEquToHealth() {
        player p = new player("John", "Doe", "ARCHER", 100, new ArrayList<>());
        p.currentHealthPoints = 5;
        p.healthPoints = 4;
        UpdatePlayer.majFinDeTour(p);

        assertThat(p.currentHealthPoints, equalTo(4));
    }

    @Test
    @DisplayName("MAJ fin tour : current health doesnt change")
    public void testCurrentHealthDoesNotChange() {
        player p = new player("John", "Doe", "ARCHER", 100, new ArrayList<>());
        p.currentHealthPoints = 3;
        p.healthPoints = 4;
        UpdatePlayer.majFinDeTour(p);

        assertThat(p.currentHealthPoints, equalTo(3));
    }

 /* Les tests ajoutés dans cette partie sont fait pour essayer de passer l'étape 'Mutation' à 100% */

    @Test
    public void testAfficherJoueurInventory() {
        player p = new player("John", "Doe", "ARCHER", 100, new ArrayList<>());
        p.inventory.add("Magic Bow");
        p.inventory.add("Hell Sword");

        String expectedOutput = "Joueur Doe joué par John\n" +
                                "Niveau : 1 (XP totale : 0)\n\n" +
                                "Capacités :\n   VIS : 3\n   ATK : 3\n   CHA : 1\n   INT : 1\n\n" +
                                "Inventaire :\n   Magic Bow\n   Hell Sword";

        String actualOutput = Affichage.afficherJoueur(p);

        assertEquals(expectedOutput, actualOutput);
    }

     @Test
    public void testRemoveAllMoney() {
        player p = new player("John", "Doe", "ARCHER", 100, new ArrayList<>());
        p.removeMoney(100);

        assertEquals(0, p.money); 
        
    }

    @Test
    @DisplayName("retrieve the right level fixed")
    void testRetrieveRightLevelFixed(){
        player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        
        UpdatePlayer.addXp(p, 10);
        assertThat(p.retrieveLevel(), equalTo(2));

        UpdatePlayer.addXp(p, 17);
        assertThat(p.retrieveLevel(), equalTo(3));

        UpdatePlayer.addXp(p, 30);
        assertThat(p.retrieveLevel(), equalTo(4));

    }

    @Test
    public void testAddXp_LevelUp() {
        player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());

        assertFalse(UpdatePlayer.addXp(p, 1)); 

        // Now add enough XP to level up
        assertTrue(UpdatePlayer.addXp(p, 11)); // This should level up the player
        assertEquals(2, p.retrieveLevel()); // Check new level
    }

    @Test
    @DisplayName("update inventory after leveling up")
    public void testAddXp_InventoryUpdate() {
        player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        UpdatePlayer.addXp(p, 20);

        assertFalse(p.inventory.isEmpty()); // Inventory should have an item
    }

    @Test
    @DisplayName("MAJ fin tour Mutation : current health doesnt change")
    public void testCurrentHealthDoesNotChangeMutation() {
        player p = new player("John", "Doe", "ARCHER", 100, new ArrayList<>());
        p.currentHealthPoints = 2;
        p.healthPoints = 4;
        UpdatePlayer.majFinDeTour(p);

        assertThat(p.currentHealthPoints, equalTo(2));
    }
}