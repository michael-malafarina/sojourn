package com.sojourn.game.faction;

import com.badlogic.gdx.math.Vector2;
import com.sojourn.game.Utility;
import com.sojourn.game.entity.unit.Unit;
import com.sojourn.game.entity.unit.ship.Raider;
import com.sojourn.game.entity.unit.ship.Scout;

public class TeamEnemy extends Team
{
    public TeamEnemy(Faction faction)
    {
       super(faction);
        setHomePoint(new Vector2(2000, 0));
    }

    @Override
    public int getTeamID() {
        return Team.ID_ENEMY;
    }

    public Wave getWave(int value)
    {
        Wave one = new Wave(this);

        one.addGroup(getUniformGroup(Scout.class, getRandomPosition(), value * .6f));
        one.addGroup(getUniformGroup(Raider.class, getRandomPosition(), value * .4f));

        return one;

    }

    public Squad getUniformGroup(Class<? extends Unit> type, Vector2 position, float value)
    {
        Squad group = new Squad(this, position);
        group.buildUnits(type, value);
        return group;
    }

    public Vector2 getRandomPosition()
    {
        return new Vector2(Utility.random(-3000, 3000), Utility.random(-3000, 3000));
    }

}
