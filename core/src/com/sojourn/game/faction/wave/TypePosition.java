package com.sojourn.game.faction.wave;

import com.badlogic.gdx.math.Vector2;
import com.sojourn.game.entity.unit.ship.Ship;

public class TypePosition
{
    private Vector2 position;
    private Class<? extends Ship> type;

    public TypePosition(Class<? extends Ship> type, Vector2 position)
    {
        this.type = type;
        this.position = position;
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public Class<? extends Ship> getType()
    {
        return type;
    }
}
