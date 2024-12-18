package re.forestier.edu;
import re.forestier.edu.rpg.UpdatePlayer;
import re.forestier.edu.rpg.utils.Display;
import re.forestier.edu.rpg.Player;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Player firstPlayer = new Player("Florian", "Ruzberg de Rivehaute", "DWARF", 200, new ArrayList<>(),5);
        UpdatePlayer.addMoney(firstPlayer, 400);
        UpdatePlayer.addXp(firstPlayer, 15);
        //System.out.println(Affichage.printPlayer(firstPlayer));
        System.out.println("------------------");
        UpdatePlayer.addXp(firstPlayer, 20);
        System.out.println(Display.printPlayer(firstPlayer));
        System.out.println(Display.printPlayerInMarkDown(firstPlayer));
    }
}