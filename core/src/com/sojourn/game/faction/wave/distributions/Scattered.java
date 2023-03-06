package com.sojourn.game.faction.wave.distributions;

import com.badlogic.gdx.math.Vector2;
import com.sojourn.game.Sojourn;
import com.sojourn.game.World;
import com.sojourn.game.entity.unit.ship.Ship;
import com.sojourn.game.faction.wave.Distribution;
import com.sojourn.game.faction.wave.TypePosition;

import java.util.List;

public class Scattered extends Distribution {

    public Scattered(List<Class<? extends Ship>> types) {
        super(types);
    }

    @Override
    public void setup() {

    }

    public TypePosition createNewPosition(Class<? extends Ship> type)
    {
        Vector2 v = new Vector2(World.getRandomX(), World.getRandomY());

        if(!Sojourn.player.inControlRadius(v)) {
            return new TypePosition(type, v);
        }
        return createNewPosition(type);
   }

}
