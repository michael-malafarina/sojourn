package com.sojourn.game.display.message;

import com.badlogic.gdx.math.Interpolation;
import com.sojourn.game.Utility;
import com.sojourn.game.display.Fonts;
import com.sojourn.game.entity.Entity;

public class FloatText extends EntityMessage
{
    private final int BASE_TIME = 120;


    protected float ySpeed;
    protected float xSpeed;
    protected float dataScaling;

    public FloatText(String message, Entity e) {
        super(message, e);
        this.x = e.getX() + Utility.random(0, e.getWidth());
        this.y = e.getY() + Utility.random(e.getHeight(), e.getHeight() * 1.2f);
        xSpeed = Utility.random(-15, 15);
        ySpeed = Utility.random(40, 50);
        myFont = Fonts.floatText;
        dataScaling = 1;

        duration = BASE_TIME;

    }

    public void update(float delta)
    {
        super.update(delta);

        y += ySpeed * delta;
        x += xSpeed * delta;
    }

    public void render()
    {
        alpha = Interpolation.exp5In.apply(getPercentLeft());

        float timeScaling = Utility.scaleBetweenBounded(alpha, .6f, 1, 0, 1);

        myFont.getData().setScale(timeScaling * dataScaling, timeScaling * dataScaling);

        super.render();

        myFont.getData().setScale(1, 1);

    }
}
