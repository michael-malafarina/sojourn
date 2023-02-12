package com.sojourn.game.entity;

import com.badlogic.gdx.graphics.Color;

public class Unit extends Entity
{
    public Color getColor(int layerNum)
    {
        return switch(layerNum)
        {
            case 0 -> Color.BLUE;
            case 1 -> Color.YELLOW;
            default -> Color.WHITE;
        };
    }

    public int getNumLayers()
    {
        return 5;
    }
}
