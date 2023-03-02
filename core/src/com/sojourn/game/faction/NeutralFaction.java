package com.sojourn.game.faction;

import com.badlogic.gdx.graphics.Color;

public class NeutralFaction extends Faction
{

    public Color getColor(int layerNum)    {
        return switch(layerNum)
        {
                default -> new Color(0f, .1f, .0f, 1f);
        };
    }

    public Color getColorAlternate(int layerNum)
    {
        return switch(layerNum)
                {
                    default -> new Color(0f, 1f, .0f, 1f);
                };
    }


}
