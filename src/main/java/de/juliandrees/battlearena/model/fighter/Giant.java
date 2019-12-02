package de.juliandrees.battlearena.model.fighter;

import de.juliandrees.battlearena.model.Fighter;
import de.juliandrees.battlearena.model.Skill;

import java.util.Arrays;
import java.util.List;

public class Giant extends Fighter {

    public Giant() {
        this(100, 4, null);
    }

    protected Giant(double health, double strength, String name) {
        super(health, strength, name);
    }

    @Override
    protected List<Skill> defineSkills() {
        return Arrays.asList(Skill.PUNCH, Skill.KICK);
    }

}
