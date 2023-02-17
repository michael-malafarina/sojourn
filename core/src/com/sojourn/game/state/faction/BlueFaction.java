package com.sojourn.game.state.faction;

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


}
