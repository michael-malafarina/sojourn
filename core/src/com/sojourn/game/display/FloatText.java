package com.sojourn.game.display;

import com.badlogic.gdx.math.Interpolation;
import com.sojourn.game.Utility;
import com.sojourn.game.entity.Entity;

public class FloatText extends EntityMessage
{
    protected float ySpeed;
    protected float xSpeed;

    public FloatText(String message, Entity e) {
        super(message, e);
        xSpeed = Utility.random(-15, 15);
        ySpeed = Utility.random(40, 50);
        myFont = Fonts.floatText;
    }

    public void update(float delta)
    {
        super.update(delta);

        y += ySpeed * delta;
        x += xSpeed * delta;
    }

    public void render()
    {
        float scaling = Interpolation.exp5In.apply(getPercentLeft());
        scaling = Utility.scaleBetweenBounded(scaling, .6f, 1, 0, 1);
        alpha = scaling;
        myFont.getData().setScale(scaling, scaling);

        super.render();

        myFont.getData().setScale(1, 1);

    }
}
