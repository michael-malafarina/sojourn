package com.sojourn.game.display;

import com.badlogic.gdx.graphics.Color;
import com.sojourn.game.entity.Attribute;

public class HealthBar extends AttributeBar
{
    public HealthBar(Attribute health)
    {
        super(health, Color.GREEN);
    }

}
