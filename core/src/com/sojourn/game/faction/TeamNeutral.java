package com.sojourn.game.faction;

import com.sojourn.game.entity.EntityManager;

public class TeamNeutral extends Team
{
    public TeamNeutral(Faction faction)
    {
        super(faction);
    }

    @Override
    public int getTeamID() {
        return ID_NEUTRAL;
    }

    public float getTotalWorth()
    {
        float worth = 0;

        for(Squad s : EntityManager.getSquads())
        {
            if(s.getTeam() == this)
            {
                worth += s.getCost().getValueBase();
            }
        }

        return worth;
    }
}
