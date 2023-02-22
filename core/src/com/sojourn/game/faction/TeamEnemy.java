package com.sojourn.game.faction;

import com.badlogic.gdx.math.Vector2;

public class TeamEnemy extends Team
{
    public TeamEnemy(Faction faction)
    {
       super(faction);
        setHomePoint(new Vector2(500, 0));
    }

    @Override
    public int getTeamID() {
        return Team.ID_ENEMY;
    }
}
