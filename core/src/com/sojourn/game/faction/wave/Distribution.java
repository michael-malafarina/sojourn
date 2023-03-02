package com.sojourn.game.faction.wave;

import com.badlogic.gdx.math.Vector2;

import javax.swing.text.Position;
import java.util.List;

abstract public class Distribution
{
    List<Position> positions;
    int numPositions;

    Distribution(int numPositions)
    {
        this.numPositions = numPositions;
    }

    abstract public Vector2 getNextPosition();
}
