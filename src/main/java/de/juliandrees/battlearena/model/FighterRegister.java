package de.juliandrees.battlearena.model;

import de.juliandrees.battlearena.model.fighter.Archer;
import de.juliandrees.battlearena.model.fighter.Dragon;
import de.juliandrees.battlearena.model.fighter.Giant;
import de.juliandrees.battlearena.model.fighter.Magician;

import java.util.Arrays;

public enum FighterRegister {

    ARCHER(1, Archer.class, "Bogenschütze"),
    GIANT(2, Giant.class, "Riese"),
    DRAGON(3, Dragon.class, "Drache"),
    MAGICIAN(4, Magician.class, "Magier"),

    ;

    private final int id;
    private final Class<? extends Fighter> fighterClass;
    private final String label;

    FighterRegister(final int id, final Class<? extends Fighter> fighterClass, final String label) {
        this.id = id;
        this.fighterClass = fighterClass;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public Class<? extends Fighter> getFighterClass() {
        return fighterClass;
    }

    public String getLabel() {
        return label;
    }

    public static int getFighterId(final Class<? extends Fighter> fighterClazz) {
        return Arrays.stream(FighterRegister.values()).filter(fighterRegister -> fighterRegister.getFighterClass().equals(fighterClazz)).findFirst().orElseThrow().getId();
    }

    public static String getFighterLabel(final Class<? extends Fighter> fighterClazz) {
        return Arrays.stream(FighterRegister.values()).filter(fighterRegister -> fighterRegister.getFighterClass().equals(fighterClazz)).findFirst().orElseThrow().getLabel();
    }

}
