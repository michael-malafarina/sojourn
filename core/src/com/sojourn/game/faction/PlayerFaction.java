package com.sojourn.game.faction;

import com.badlogic.gdx.graphics.Color;

public class PlayerFaction extends Faction
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
}
