package com.sojourn.game.state.faction;

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
                    case 0 -> Color.WHITE;
//            case 1 -> new Color(.2f, .4f, 1f, 1f);
                    default -> new Color(.9f, .1f, .05f, 1f);
                };
    }
}
