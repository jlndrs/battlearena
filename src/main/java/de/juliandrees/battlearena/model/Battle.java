package de.juliandrees.battlearena.model;

import java.io.IOException;

public class Battle {

    private Fighter fighter, enemy;
    private GameMenu menu;

    public Battle(final Fighter fighter, final Fighter enemy, final GameMenu menu) {
        this.fighter = fighter;
        this.enemy = enemy;
        this.menu = menu;
    }

    public final boolean start() throws IOException {
        boolean endBattle = false;
        while (!endBattle) {
            double random = Math.random();
            if (random < 0.5) {
                endBattle = fighter.attackEnemy(enemy);
            } else {
                endBattle = enemy.attackEnemy(fighter);
            }
        }

        if (fighter.getHealth() <= 0) {
            menu.println("Du hast VERLOREN, dein Kämpfer ist tot.");
            return true;
        } else if (enemy.getHealth() <= 0) {
            menu.println("Du hast den Kampf gegen " + enemy.getName() + " GEWONNEN.");
            return false;
        } else {
            return false;
        }
    }

    public void resetFighters() {
        fighter.reset();
        enemy.reset();
    }

    public Fighter getFighter() {
        return fighter;
    }

    public Fighter getEnemy() {
        return enemy;
    }

}
