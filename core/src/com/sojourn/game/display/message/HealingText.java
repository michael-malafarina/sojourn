package com.sojourn.game.display.message;

import com.badlogic.gdx.graphics.Color;
import com.sojourn.game.Utility;
import com.sojourn.game.entity.Entity;

public class HealingText extends FloatText
{
    final float LOW = 1;
    final float HIGH = 50;

    public HealingText(int damage, Entity e)
    {
        super(""+damage, e);
        color = new Color(0f, 1f, 0f, 1f);

        dataScaling = Utility.scaleBetweenBounded(damage, .5f, 1, LOW, HIGH);

//        if(isCritical)
//        {
//            color = new Color(1f, 1f, 0f, 1f);
//        }


    }
}
