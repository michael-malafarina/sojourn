package com.sojourn.game.entity;

import com.sojourn.game.faction.TeamBonus;

public class AttributePool extends Attribute
{
    float current;
    float maximum;
    float regeneration;

    public AttributePool(TeamBonus bonus)
    {
        super(bonus);
    }

    public AttributePool(float value, TeamBonus bonus)
    {
        this(bonus);
        current = value;
        maximum = value;
    }

    public AttributePool(float current, float maximum, TeamBonus bonus)
    {
        this(bonus);
        this.current = current;
        this.maximum = maximum;
    }

    /************ ACCESSORS *************/

    public float getValue()
    {
        return getCurrent();
    }

    public float getCurrent()
    {
        return current;
    }

    public float getMaximum()
    {
        return maximum * teamBonus.getBonusPercent();
    }

    public float getRegeneration()
    {
        return regeneration;
    }

    public float getPercent()
    {
        return getCurrent() / getMaximum();
    }

    /************ MUTATORS *************/

    public void update()
    {
        increase(getRegeneration());
    }

    public void increase(float amount)
    {
        current = Math.min(getCurrent() + amount,  getMaximum());
    }

    public void decrease(float amount)
    {
        current = Math.max(getCurrent() - amount,  0);
    }

    public void maximize()
    {
        current = maximum;
    }
}
