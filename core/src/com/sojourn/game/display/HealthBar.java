package com.sojourn.game.display;

import com.badlogic.gdx.graphics.Color;
import com.sojourn.game.entity.AttributePool;

public class HealthBar extends AttributeBar
{
    public HealthBar(AttributePool health)
    {
        super(health, Color.GREEN);
    }

}
