package com.sojourn.game.faction.wave;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

abstract public class Distribution
{
    List<Vector2> positions;
    int index;

    abstract public Vector2 createNewPosition();


    public Distribution(int numPositions)
    {
        positions = new ArrayList<>();

        for(int i  = 0; i < numPositions; i++)
        {
            positions.add(createNewPosition());
        }
    }


    public Vector2 getNextPosition()
    {
        Vector2 p = positions.get(index);
        index++;
        return p;
    }

    public List<Vector2> getAllPositions()
    {
        return positions;
    }
}
