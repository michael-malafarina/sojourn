package com.sojourn.game.faction;

import com.badlogic.gdx.graphics.Color;

public class RedFaction extends Faction
{

    public Color getColor(int layerNum)    {
        return switch(layerNum)
        {
            case 0 -> new Color(.9f, .1f, .05f, 1f);
            case 1 -> Color.YELLOW;
            default -> Color.WHITE;
        };
    }
}
