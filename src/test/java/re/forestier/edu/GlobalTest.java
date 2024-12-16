package re.forestier.edu;

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
}
