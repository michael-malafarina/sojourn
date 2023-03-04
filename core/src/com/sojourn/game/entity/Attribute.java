package com.sojourn.game.entity;

import com.sojourn.game.faction.TeamBonus;

public class Attribute
{
    protected TeamBonus teamBonus;
    private float value;

    public Attribute(TeamBonus bonus)
    {
        this.teamBonus = bonus;
    }

    public Attribute(float value, TeamBonus bonus)
    {
        this(bonus);
        this.value = value;
    }

    /************ ACCESSORS *************/

    public float getValue()
    {
        return value * teamBonus.getBonusPercent();
    }

    /************ MUTATORS *************/

    public void update()
    {

    }

    public void increase(float amount)
    {
        value += amount;
    }

    public void decrease(float amount)
    {
        value = Math.max(getValue() - amount,  0);
    }

}
