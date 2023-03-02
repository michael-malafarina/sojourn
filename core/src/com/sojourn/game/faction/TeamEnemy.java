package com.sojourn.game.faction;

import com.badlogic.gdx.math.Vector2;
import com.sojourn.game.faction.wave.Wave;

public class TeamEnemy extends Team
{
    Wave nextWave;
    Wave currentWave;

    public TeamEnemy(Faction faction)
    {
       super(faction);
       nextWave = new Wave(this);
        setHomePoint(new Vector2(2000, 0));
    }

    @Override
    public int getTeamID() {
        return Team.ID_ENEMY;
    }

    public void planWave(float value)
    {
        nextWave = new Wave(this, value);
        nextWave.plan();
    }

    public void spawnWave()
    {
        if(nextWave != null)
        {
            nextWave.spawn();
            currentWave = nextWave;
            nextWave = null;
        }

//        Wave one = new Wave(this);
////        one.addSquad(createSquad(Scout.class, getRandomPosition()));
////        one.addSquad(createSquad(Raider.class, getRandomPosition()));



    }





}
