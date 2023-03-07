package com.sojourn.game.entity;

import com.sojourn.game.faction.TeamBonus;
import com.sojourn.game.state.StateGameplay;

import java.util.ArrayList;
import java.util.List;

public class AttributePool extends Attribute
{
    final private static int RECENT = 60;

    private float current;
    private float maximum;
    private float regeneration;

    private List<AttributeDelta> damageTimes;
    private List<AttributeDelta> healingTimes;

    public AttributePool(TeamBonus bonus)
    {
        super(bonus);
        damageTimes = new ArrayList<>();
        healingTimes = new ArrayList<>();
    }

    public AttributePool(TeamBonus bonus, float value)
    {
        this(bonus);
        current = value;
        maximum = value;
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

    public float getRecentDamage()
    {
        float amount = 0;

        for (AttributeDelta damage : damageTimes)
        {
              amount += damage.getAmount();
        }

        return amount;
    }

    public float getRecentDamagePercent()
    {
        return getRecentDamage() / getMaximum();
    }

    /************ MUTATORS *************/

    public void setRegeneration(float amount)
    {
        regeneration = amount;
    }

    public void update()
    {
        increase(getRegeneration() / 60f);

        for (int i = 0; i < damageTimes.size(); i++)
        {
            AttributeDelta current = damageTimes.get(i);

            if(StateGameplay.getTime() - current.getTime() > RECENT)
            {
                damageTimes.remove(i);
                i--;
            }
        }
    }

    public void increase(float amount)
    {
        float actualAmount = amount;

        // Would exceed maximum
        if(getCurrent() + amount > getMaximum())
        {
            actualAmount = getMaximum() - getCurrent();
        }

        current += actualAmount;
        healingTimes.add(new AttributeDelta(StateGameplay.getTime(), actualAmount));
    }

    public void decrease(float amount)
    {
        float actualAmount = amount;

        // Amount exceeds current - would go negative
        if(getCurrent() - amount < 0)
        {
            actualAmount = getCurrent();
        }

        current -= actualAmount;
        damageTimes.add(new AttributeDelta(StateGameplay.getTime(), actualAmount));
    }

}
