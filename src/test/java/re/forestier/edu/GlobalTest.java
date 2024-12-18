package re.forestier.edu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import re.forestier.edu.rpg.UpdatePlayer;
import re.forestier.edu.rpg.utils.Display;
import re.forestier.edu.rpg.Player;

import java.util.ArrayList;

import static org.approvaltests.Approvals.verify;

public class GlobalTest {

    @Test
    @DisplayName("test print player profile")
    void testDisplayBase() {
        Player player = new Player("Florian", "Gnognak le Barbare", "ADVENTURER", 200, new ArrayList<>(),5);
        UpdatePlayer.addXp(player, 20);
        player.clearInventory();

        verify(Display.printPlayer(player));
    }

    @Test
    @DisplayName("test print player profile in MarkDown")
    void testDisplayMarkdown() {
        Player player = new Player("Florian", "Gnognak le Barbare", "ADVENTURER", 200, new ArrayList<>(),5);
        UpdatePlayer.addXp(player, 5);
        player.addObject("Magic Bow");
        player.setHealthPoints(100);
        player.setCurrentHealthPoints(70);

        verify(Display.printPlayerInMarkDown(player));
    }
}
