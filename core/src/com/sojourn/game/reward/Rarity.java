package com.sojourn.game.reward;

import com.badlogic.gdx.graphics.Color;

public enum Rarity
{
    COMMON, UNCOMMON, RARE, EPIC, LEGENDARY;

    public Color getColor()
    {
        return switch(this)
                {
                    case UNCOMMON -> new Color(.3f, .9f, .3f, 1f);
                    case RARE -> new Color(.3f, .4f, 1f, 1f);
                    case EPIC -> new Color(.75f, .45f, 1f, 1f);
                    case LEGENDARY -> new Color(1f, .70f, .05f, 1f);
                    default -> new Color(1f, 1f, 1f, 1f);
                };
    }

    public float getRating()
    {
        return switch(this)
                {
                    case UNCOMMON -> .40f;
                    case RARE -> .7f;
                    case EPIC -> .9f;
                    case LEGENDARY -> .97f;
                    default -> 0;
                };
    }
}
