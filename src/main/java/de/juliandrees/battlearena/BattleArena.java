package de.juliandrees.battlearena;

import de.juliandrees.battlearena.model.Fighter;
import de.juliandrees.battlearena.model.GameMenu;
import de.juliandrees.battlearena.service.BattleService;

import java.text.DecimalFormat;
import java.util.Optional;

public class BattleArena {

    public static void main(String... args) {
        new BattleArena().run();
    }

    private BattleService battleService;

    public BattleArena() {
        this.battleService = new BattleService();
    }

    public void run() {
        DecimalFormat format = new DecimalFormat("#0");

        try (GameMenu menu = new GameMenu(System.in, System.out)) {
            menu.println("--- BattleArena v1.0 ---");
            menu.println("Bitte wähle einen der folgenden Kämper:");
            for (Fighter fighter : battleService.getFighters()) {
                menu.println(fighter.getId() + ". " + fighter.getName());
                menu.println("  » " + format.format(fighter.getHealth()) + " Lebenspunkte");
                menu.println("  » " + format.format(fighter.getStrength()) + " Stärke");
            }

            menu.emptyLine();
            Optional<Integer> optionalFighterId = menu.readInteger("Id des Kämpers");
            Fighter chosen;
            if (!optionalFighterId.isPresent() || (chosen = battleService.getFighter(optionalFighterId.get())) == null) {
                menu.println("Du hast keinen Kämper gewählt, das Programm wird beendet.");
                return;
            }
            menu.println("» Du hast '" + chosen.getName() + "' gewählt");

            menu.emptyLine();
            menu.println("Wähle nun den Namen deines Kämpers. Drücke ENTER, um den Standardnamen zu wählen.");
            String optionalName = menu.readString("Name deines Kämpers").orElse(chosen.getName());
            chosen.setName(optionalName);
            menu.println("» Dein Kämpfer trägt nun den Namen '" + chosen.getName() + "'");

            battleService.startBattleSeries(chosen, menu);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
