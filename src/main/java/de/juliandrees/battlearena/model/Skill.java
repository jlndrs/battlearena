package de.juliandrees.battlearena.model;

public enum Skill {

    PUNCH(0.6, SkillType.ATTACK),
    KICK( 0.4, SkillType.ATTACK),
    SHOOT(0.5, SkillType.ATTACK),
    BURN(0.9, SkillType.ATTACK),

    BLOCK(0.2, SkillType.DEFENSE)

    ;

    private double strength;
    private SkillType skillType;

    Skill(final double strength, final SkillType skillType) {
        this.strength = strength;
        this.skillType = skillType;
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
