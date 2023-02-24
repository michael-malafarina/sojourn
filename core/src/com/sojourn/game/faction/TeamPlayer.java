package com.sojourn.game.faction;

import com.badlogic.gdx.math.Vector2;

public class TeamPlayer extends Team
{
    public TeamPlayer(Faction faction)
    {
        super(faction);
        setHomePoint(new Vector2(0, 0));
    }

    @Override
    public int getTeamID() {
        return Team.ID_PLAYER;
    }


}
