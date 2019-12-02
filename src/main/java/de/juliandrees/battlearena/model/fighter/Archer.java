package de.juliandrees.battlearena.model.fighter;

import de.juliandrees.battlearena.model.Fighter;
import de.juliandrees.battlearena.model.Skill;

import java.util.Arrays;
import java.util.List;

public class Archer extends Fighter {

    public Archer() {
        this(50, 6, null);
    }

    protected Archer(double health, double strength, String name) {
        super(health, strength, name);
    }

    @Override
    protected List<Skill> defineSkills() {
        return Arrays.asList(Skill.SHOOT, Skill.BLOCK);
    }

}
