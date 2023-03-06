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

    TeamBonusManager()
    {
        health = new TeamBonus();
        damage = new TeamBonus();
        range = new TeamBonus();
        squadSize = new TeamBonus();
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

    public TeamBonus getSquadSize()
    {
        return squadSize;
    }

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
    }

    public String getPercentString(float value)
    {
        return Math.round(value * 100) + "%";
    }

}
