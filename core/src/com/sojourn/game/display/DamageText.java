package com.sojourn.game.display;

import com.sojourn.game.Utility;
import com.sojourn.game.entity.Entity;

public class DamageText extends FloatText
{
    final float LOW_DAMAGE = 1;
    final float HIGH_DAMAGE = 50;

    public DamageText(int damage, Entity e)
    {
        super(""+damage, e);

        dataScaling = Utility.scaleBetweenBounded(damage, .5f, 1, LOW_DAMAGE, HIGH_DAMAGE);

    }
}
