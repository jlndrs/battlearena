package de.juliandrees.battlearena.model.fighter;

import de.juliandrees.battlearena.model.Fighter;
import de.juliandrees.battlearena.model.Skill;

import java.util.Collections;
import java.util.List;

public class Dragon extends Fighter {

    public Dragon() {
        this(30, 8, null);
    }

    protected Dragon(double health, double strength, String name) {
        super(health, strength, name);
    }

    @Override
    protected List<Skill> defineSkills() {
        return Collections.singletonList(Skill.BURN);
    }
}
