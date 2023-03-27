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

    public Color getColorAlternate(int layerNum)
    {
        return switch(layerNum)
                {
                    case 0 -> Color.GRAY;
                    case 1 -> Color.GRAY;
                    case 2 -> new Color(.9f, .1f, .05f, 1f);
                    default -> Color.WHITE;

                };
    }


}
