package com.sojourn.game.faction;

public class TeamPlayer extends Team
{
    public TeamPlayer(Faction faction)
    {
        super(faction);
    }

    @Override
    public int getTeamID() {
        return Team.ID_PLAYER;
    }
}
