package com.sojourn.game.display.message;

import com.badlogic.gdx.graphics.Color;
import com.sojourn.game.Utility;
import com.sojourn.game.entity.Entity;

public class DamageText extends FloatText
{
    final float LOW_DAMAGE = 1;
    final float HIGH_DAMAGE = 50;

    public DamageText(int damage, boolean isCritical, Entity e)
    {
        super(""+damage, e);

        dataScaling = Utility.scaleBetweenBounded(damage, .5f, 1, LOW_DAMAGE, HIGH_DAMAGE);

        if(isCritical)
        {
            color = new Color(1f, 1f, 0f, 1f);
        }


    }
}
