package com.sojourn.game.display.bar;

import com.badlogic.gdx.graphics.Color;
import com.sojourn.game.entity.AttributePool;

public class MunitionsBar extends AttributeBar
{
    public MunitionsBar(AttributePool munitions)
    {
        super(munitions, Color.ORANGE);
    }

    public boolean hasMunitions()
    {
        return attribute != null && attribute.getMaximum() > 0;
    }

}
