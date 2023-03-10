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

    public Attribute(TeamBonus bonus, float value)
    {
        this(bonus);
        this.value = value;
    }

    /************ ACCESSORS *************/

    public float getValue()
    {
        return value * teamBonus.getBonusPercent();
    }

    public float getValueBase()
    {
        return value;
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
