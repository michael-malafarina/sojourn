package com.sojourn.game.faction.wave;

import com.badlogic.gdx.math.Vector2;
import com.sojourn.game.World;
import com.sojourn.game.entity.unit.ship.Ship;

import java.util.ArrayList;
import java.util.List;

abstract public class Distribution
{
    protected final int BAND_SIZE = (int) ((World.WIDTH + World.HEIGHT) / 2 * .1f);

    private List<TypePosition> positions;

    abstract public TypePosition createNewPosition(Class<? extends Ship> type);

    public Distribution(List<Class<? extends Ship>> types)
    {
        setup();

        positions = new ArrayList<>();

        for(int i  = 0; i < types.size(); i++)
        {
            positions.add(createNewPosition(types.get(i)));
        }
    }

    public abstract void setup();

    public List<TypePosition> getAllPositions()
    {
        return positions;
    }

    public Vector2 getRandomSide(int sideNumber)
    {
        return switch(sideNumber) {
            case 1 -> new Vector2(World.getRandomWestSide(BAND_SIZE));
            case 2 -> new Vector2(World.getRandomEastSide(BAND_SIZE));
            case 3 -> new Vector2(World.getRandomNorthSide(BAND_SIZE));
            default -> new Vector2(World.getRandomSouthSide(BAND_SIZE));
        };
    }
}
