package de.juliandrees.battlearena.model;

import java.util.*;

public abstract class Fighter {

    private final int id;

    private String name;

    /**
     * health = Lebenspunkte (0 < hp <= 100)
     * strength = Stärke des Kämpfers (0 < strength <= 10)
     */
    private double health, strength;

    private double fallbackHealth, fallbackStrength;

    private List<Skill> attacks = new ArrayList<>();
    private List<Skill> defenses = new ArrayList<>();

    public Fighter() {
        this.id = FighterRegister.getFighterId(getClass());
    }

    protected Fighter(final double health, final double strength, final String name) {
        this();
        this.health = health;
        this.strength = strength;
        this.name = name;

        this.fallbackHealth = health;
        this.fallbackStrength = strength;
    }

    /**
     * Initialisiert den Kämper und weist die Skills den Listen zu.
     */
    public void initialize() {
        defineSkills().forEach(skill -> {
            if (skill.getSkillType() == Skill.SkillType.ATTACK) {
                attacks.add(skill);
            } else {
                defenses.add(skill);
            }
        });
    }

    /**
     * Greift den Gegner an. Wenn das Leben nach dem Angriff kleiner als 0 ist,
     * wird true zurückgegeben und der aktuelle Fighter hat gewonnen.
     *
     * @param enemy der Gegner, der angegriffen werden soll
     * @return true, wenn der aktueller Kämper den Gegner besieht hat, false wenn der Gegner noch lebt
     */
    protected boolean attackEnemy(final Fighter enemy) {
        double value = this.strength * getRandomSkill(this.attacks).getStrength();

        if (Math.random() > 0.7)
            value = enemy.defendAttack(value);

        enemy.recudeHealth(value);
        return enemy.getHealth() <= 0;
    }

    /**
     * Verteidigt eine Attacke eines Gegners mit den definierten Skills.
     *
     * @param attackValue der reine Angriffswert der Attacke
     * @return der neue Angriffswert (die Attacke wurde abgewehrt)
     */
    double defendAttack(final double attackValue) {
        Skill defense = getRandomSkill(this.defenses);
        if (defense == null) {
            return attackValue;
        }

        return attackValue - attackValue * defense.getStrength();
    }

    /**
     * Definiert die Skills, die ein Kämpfer hat.
     *
     * @return die Liste an Angriffen und Verteidigungen
     */
    protected abstract List<Skill> defineSkills();

    /**
     * Der Standardname des Kämpers (wird nur für die Gegner verwendet, wenn der Benutzer
     * den Kämper wählt wird ein benutzerdefinierter Name gewählt.
     *
     * @return der Standardname des Kämpfers
     */
    protected String getDefaultName() {
        return FighterRegister.getFighterLabel(getClass());
    };

    /**
     * Reduziert das Leben den Kämpers um den angegebenen Wert.
     *
     * @param reduce der Wert, um den das Leben reduziert werden soll
     */
    protected void recudeHealth(double reduce) {
        this.health -= reduce;
    }

    /**
     * Gibt einen zufälligen Skill zurück.
     *
     * @param skillList die Liste, aus dem der Skill gewählt werden soll (attacks, defenses)
     * @return der zufällig gewählte Skill
     */
    protected Skill getRandomSkill(final List<Skill> skillList) {
        if (skillList.isEmpty())
            return null;
        return skillList.get(new Random().nextInt(skillList.size()));
    }

    /**
     * @return die Lebenspunkte des Kämpers
     */
    public double getHealth() {
        return this.health;
    }

    public double getStrength() {return strength; }

    /**
     * @return die Id des Fighters
     */
    public int getId() {
        return id;
    }

    /**
     * @return den Namen des Kämpers. Wenn der Name nicht null ist wird dieser zurückgegeben, ansonsten der Name des
     * Kämpers
     */
    public String getName() {
        return this.name == null ? getDefaultName() : this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void reset() {
        setName(null);
        this.health = fallbackHealth;
        this.strength = fallbackStrength;
    }

}
