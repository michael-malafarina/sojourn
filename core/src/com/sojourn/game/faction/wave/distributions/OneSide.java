package com.sojourn.game.faction.wave.distributions;

import com.sojourn.game.Sojourn;
import com.sojourn.game.Utility;
import com.sojourn.game.entity.unit.ship.Ship;
import com.sojourn.game.faction.wave.Distribution;
import com.sojourn.game.faction.wave.TypePosition;

import java.util.List;

public class OneSide extends Distribution
{
    private int side;

    public OneSide(List<Class<? extends Ship>> types)
    {
        super(types);
    }

    public void setup() {
        side = Utility.random(4);
    }

    public TypePosition createNewPosition(Class<? extends Ship> type)
    {
        TypePosition p = new TypePosition(type, getRandomSide(side));

        if(!Sojourn.player.inControlRadius(p.getPosition())) {
            return p;
        }

        return createNewPosition(type);
    }
}
