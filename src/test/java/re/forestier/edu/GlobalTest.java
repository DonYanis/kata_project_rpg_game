package re.forestier.edu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import re.forestier.edu.rpg.UpdatePlayer;
import re.forestier.edu.rpg.utils.Affichage;
import re.forestier.edu.rpg.Player;

import java.util.ArrayList;

import static org.approvaltests.Approvals.verify;

public class GlobalTest {

    @Test
    void testAffichageBase() {
        Player player = new Player("Florian", "Gnognak le Barbare", "ADVENTURER", 200, new ArrayList<>(),5);
        UpdatePlayer.addXp(player, 20);
        player.clearInventory();

        verify(Affichage.afficherJoueur(player));
    }

    @Test
    @DisplayName("test print player profile in MarkDown")
    void testAffichageMarkdown() {
        Player player = new Player("Florian", "Gnognak le Barbare", "ADVENTURER", 200, new ArrayList<>(),5);
        UpdatePlayer.addXp(player, 5);
        player.addObject("Magic Bow");
        player.setHealthPoints(100);
        player.setCurrentHealthPoints(70);

        verify(Affichage.printPlayerInMarkDown(player));
    }
}
