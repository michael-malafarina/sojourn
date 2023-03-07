package com.sojourn.game.faction;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class TeamPlayer extends Team
{
    private static final int CONTROL_DISTANCE_BASE = 1000;


    public TeamPlayer(Faction faction)
    {
        super(faction);
        setHomePoint(new Vector2(0, 0));
    }

    @Override
    public int getTeamID() {
        return Team.ID_PLAYER;
    }

    public int getControlDistance()
    {
        return Math.round(CONTROL_DISTANCE_BASE * getTeamBonusManager().getControlRadiusBonus().getBonusPercent());
    }

    public boolean inControlRadius(Vector2 point)
    {
        return point.dst(0, 0) <= getControlDistance();
    }

    public boolean inControlRadius(Vector3 point)
    {
        return point.dst(0, 0, 0) <= getControlDistance();
    }

}
