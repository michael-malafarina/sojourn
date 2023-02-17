package com.sojourn.game.state.faction;

public class TeamNeutral extends Team
{
    public TeamNeutral(Faction faction)
    {
        super(faction);
    }

    @Override
    public int getTeamID() {
        return Team.ID_NEUTRAL;
    }
}
