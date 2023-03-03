package com.sojourn.game.faction.wave;

import com.sojourn.game.entity.unit.ship.Ship;

import java.util.ArrayList;
import java.util.List;

abstract public class Distribution
{
    private List<TypePosition> positions;
    private int index;

    abstract public TypePosition createNewPosition(Class<? extends Ship> type);

    public Distribution(List<Class<? extends Ship>> types)
    {
        positions = new ArrayList<>();

        for(int i  = 0; i < types.size(); i++)
        {
            positions.add(createNewPosition(types.get(i)));
        }
    }


    public TypePosition getNextPosition()
    {
        TypePosition p = positions.get(index);
        index++;
        return p;
    }

    public List<TypePosition> getAllPositions()
    {
        return positions;
    }
}
