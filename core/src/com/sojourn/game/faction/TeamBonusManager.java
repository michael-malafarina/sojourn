package com.sojourn.game.faction;

import com.sojourn.game.display.Alignment;
import com.sojourn.game.display.Fonts;
import com.sojourn.game.display.Text;

public class TeamBonusManager
{
    private TeamBonus health;
    private TeamBonus damage;
    private TeamBonus range;
    private TeamBonus squadSize;
    private TeamBonus controlRadius;
    private TeamBonus munitions;
    private TeamBonus speed;
    private TeamBonus acceleration;
    private TeamBonus cost;
    private TeamBonus critChance;
    private TeamBonus critDamage;


    TeamBonusManager()
    {
        health = new TeamBonus();
        damage = new TeamBonus();
        range = new TeamBonus();
        squadSize = new TeamBonus();
        controlRadius = new TeamBonus();
        munitions = new TeamBonus();
        speed = new TeamBonus();
        acceleration = new TeamBonus();
        cost = new TeamBonus();
        critChance = new TeamBonus();
        critDamage = new TeamBonus();

    }

    public TeamBonus getHealthBonus()
    {
        return health;
    }
    public TeamBonus getDamageBonus()
    {
        return damage;
    }
    public TeamBonus getRangeBonus()
    {
        return range;
    }
    public TeamBonus getSquadSizeBonus()
    {
        return squadSize;
    }
    public TeamBonus getControlRadiusBonus()
    {
        return controlRadius;
    }
    public TeamBonus getMunitionsBonus()
    {
        return munitions;
    }
    public TeamBonus getSpeedBonus()    {        return speed;    }
    public TeamBonus getAcceleration()    {        return acceleration;    }
    public TeamBonus getCostBonus()    {        return cost;    }
    public TeamBonus getCritChance()    {        return critChance;    }
    public TeamBonus getCritDamage()    {        return critDamage;    }

    public void render()
    {
        int i = 0;
        int base = 50;
        int space = 25;

        Text.setFont(Fonts.medium);
        Text.setAlignment(Alignment.LEFT, Alignment.BOTTOM);

        if(health.isModified())
        {
            Text.draw("Health: +" + getPercentString(health.getBonusPercent()-1), 350, base + i * space);
            i++;
        }

        if(damage.isModified())
        {
            Text.draw("Damage: +" + getPercentString(damage.getBonusPercent()-1), 350, base + i * space);
            i++;
        }

        if(range.isModified())
        {
            Text.draw("Range: +" + getPercentString(range.getBonusPercent()-1), 350, base + i * space);
            i++;
        }

        if(squadSize.isModified())
        {
            Text.draw("Squad Size: +" + getPercentString(squadSize.getBonusPercent()-1), 350, base + i * space);
            i++;
        }

        if(controlRadius.isModified())
        {
            Text.draw("Control Radius: +" + getPercentString(controlRadius.getBonusPercent()-1), 350, base + i * space);
            i++;
        }

        if(munitions.isModified())
        {
            Text.draw("Munitions: +" + getPercentString(munitions.getBonusPercent()-1), 350, base + i * space);
            i++;
        }

        if(speed.isModified())
        {
            Text.draw("Speed: +" + getPercentString(speed.getBonusPercent()-1), 350, base + i * space);
            i++;
        }

        if(acceleration.isModified())
        {
            Text.draw("Acceleration: +" + getPercentString(acceleration.getBonusPercent()-1), 350, base + i * space);
            i++;
        }

        if(cost.isModified())
        {
            Text.draw("Cost: +" + getPercentString(cost.getBonusPercent()-1), 350, base + i * space);
            i++;
        }

        if(critChance.isModified())
        {
            Text.draw("Crit Chance: +" + getPercentString(critChance.getBonusAdded()), 350, base + i * space);
            i++;
        }

        if(critDamage.isModified())
        {
            Text.draw("Crit Damage: +" + getPercentString(critDamage.getBonusAdded()), 350, base + i * space);
            i++;
        }
    }

    public String getPercentString(float value)
    {
        return Math.round(value * 100) + "%";
    }

}
