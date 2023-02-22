package com.sojourn.game.faction;

import com.badlogic.gdx.graphics.Color;

public class BlueFaction extends Faction
{

    public Color getColor(int layerNum)
    {
        return switch(layerNum)
        {
            case 0 -> new Color(.2f, .4f, 1f, 1f);
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
            default -> new Color(.2f, .4f, .6f, 1f);
        };
    }



}
