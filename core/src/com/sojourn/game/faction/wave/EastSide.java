package com.sojourn.game.faction.wave;

import com.badlogic.gdx.math.Vector2;
import com.sojourn.game.Utility;

public class EastSide extends Distribution
{
    EastSide(int numPositions) {
        super(numPositions);
    }

    public Vector2 createNewPosition()
    {
        return new Vector2(Utility.random(2000, 3000), Utility.random(-3000, 3000));
    }
}
