package com.sojourn.game.faction.wave;

import com.badlogic.gdx.math.Vector2;
import com.sojourn.game.Utility;
import com.sojourn.game.World;
import com.sojourn.game.entity.unit.ship.Ship;

import java.util.List;

public class EastSide extends Distribution
{
    public EastSide(List<Class<? extends Ship>> types)
    {
        super(types);
    }

    public TypePosition createNewPosition(Class<? extends Ship> type)
    {
        return new TypePosition(type, new Vector2(
                Utility.random(World.getEastEdge() - 1000, World.getEastEdge()),
                Utility.random(World.getSouthEdge(), World.getNorthEdge())));
    }
}
