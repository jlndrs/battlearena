package de.juliandrees.battlearena.model;

public enum Skill {

    PUNCH("Schlag", 0.6, SkillType.ATTACK),
    KICK("Tritt", 0.4, SkillType.ATTACK),
    SHOOT("Schießen", 0.5, SkillType.ATTACK),
    BURN("Verbrennen", 0.9, SkillType.ATTACK),

    BLOCK("Blocken", 0.2, SkillType.DEFENSE)

    ;

    private String name;
    private double strength;
    private SkillType skillType;

    Skill(final String name, final double strength, final SkillType skillType) {
        this.name = name;
        this.strength = strength;
        this.skillType = skillType;
    }

    public String getName() {
        return name;
    }

    public double getStrength() {
        return strength;
    }

    public SkillType getSkillType() {
        return skillType;
    }

    public enum SkillType {

        ATTACK,
        DEFENSE,

        ;

    }
}
