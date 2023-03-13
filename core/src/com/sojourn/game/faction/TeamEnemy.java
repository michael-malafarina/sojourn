package com.sojourn.game.faction;

import com.badlogic.gdx.math.Vector2;
import com.sojourn.game.Settings;
import com.sojourn.game.faction.wave.Wave;
import com.sojourn.game.state.StateGameplay;

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

    public void planWave()
    {
        int value = (int) (Settings.waveValueStarting +
                Math.pow(StateGameplay.getWaveNumber(), Settings.waveExponentiation)
                        * Settings.waveScalar);
        nextWave = new Wave(this, value);
        nextWave.plan();
    }

    public void spawnWave() {
        if (nextWave != null) {
            nextWave.spawn();
            currentWave = nextWave;
            nextWave = null;
        }
    }

    public float getTotalWorth()
    {
        if(nextWave == null) {
            return currentWave.getValue();
        }
        return nextWave.getValue();

    }
}
