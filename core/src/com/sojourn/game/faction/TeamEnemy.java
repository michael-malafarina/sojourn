package com.sojourn.game.faction;

public class TeamEnemy extends Team
{
    public TeamEnemy(Faction faction)
    {
       super(faction);
    }

    @Override
    public int getTeamID() {
        return Team.ID_ENEMY;
    }
}
