package de.juliandrees.battlearena.service;

import de.juliandrees.battlearena.model.Battle;
import de.juliandrees.battlearena.model.Fighter;
import de.juliandrees.battlearena.model.FighterRegister;
import de.juliandrees.battlearena.model.GameMenu;
import de.juliandrees.battlearena.model.fighter.Archer;
import de.juliandrees.battlearena.model.fighter.Dragon;
import de.juliandrees.battlearena.model.fighter.Giant;
import de.juliandrees.battlearena.model.fighter.Magician;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BattleService {

    private List<Fighter> fighters = new ArrayList<>();

    public BattleService() {
        fighters.add(new Archer());
        fighters.add(new Giant());
        fighters.add(new Dragon());
        fighters.add(new Magician());

        fighters.forEach(Fighter::initialize);
    }

    public Fighter getFighter(final int fighterId) {
        Optional<FighterRegister> optionalFighter = Arrays.stream(FighterRegister.values()).filter(fighterRegister -> fighterRegister.getId() == fighterId).findAny();
        if (!optionalFighter.isPresent()) {
            return null;
        }
        final Class<? extends Fighter> fighterClass = optionalFighter.get().getFighterClass();
        return fighters.stream().filter(fighter -> fighter.getClass().equals(fighterClass)).findAny().orElse(null);

    }

    public void startBattleSeries(final Fighter fighter, final GameMenu menu) throws Exception {
        boolean lostBattle = false;

        for (Fighter enemy : getEnemies(fighter)) {
            if (lostBattle) {
                return;
            }

            Battle battle = new Battle(fighter, enemy, menu);
            menu.emptyLine();
            menu.println("Es wird gegen '" + enemy.getName() + "' gekämpft, bitte warten...");
            Thread.sleep(1500);
            lostBattle = battle.start();
            battle.resetFighters();
        }
    }

    public List<Fighter> getFighters() {
        return fighters;
    }

    public List<Fighter> getEnemies(final Fighter player) {
        return fighters.stream().filter(fighter -> !fighter.getClass().equals(player.getClass())).collect(Collectors.toList());
    }

}
