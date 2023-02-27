package com.sojourn.game.display;

import com.sojourn.game.entity.Entity;

public class DamageText extends FloatText
{
    public DamageText(int damage, Entity e) {
        super(""+damage, e);
    }
}
