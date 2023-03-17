package com.sojourn.game.reward;

import com.badlogic.gdx.graphics.Color;

public enum Rarity
{
    COMMON, UNCOMMON, RARE;

    public Color getColor()
    {
        return switch(this)
                {
                    case UNCOMMON -> new Color(.5f, .7f, .9f, 1f);
                    case RARE -> new Color(.9f, .9f, .3f, 1f);
                    default -> new Color(1f, 1f, 1f, 1f);
                };
    }

    public float getRating()
    {
        return switch(this)
        {
            case UNCOMMON -> .70f;
            case RARE -> .9f;
            default -> 0;
        };
    }
}
