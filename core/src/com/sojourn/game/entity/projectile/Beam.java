package com.sojourn.game.entity.projectile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.sojourn.game.display.Shape;
import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.component.weapon.Weapon;

public class Beam extends Projectile
{
    int width;
    float alpha;
    boolean burst;

    public Beam(Entity owner, Entity target, Weapon weapon) {
        super(owner, target, weapon);

        setPosition(owner.getPosition());
        width = 1;
        alpha = .5f;
        burst = false;
    }

    public void setWidth (int width)
    {
        this.width = width;
    }
    public void setAlpha (float alpha)
    {
        this.alpha = alpha;
    }
    public void setBurst(boolean burst) { this.burst = burst;   }

    @Override
    public int getWidth()
    {
        if(burst)
        {
            return Math.round(width * centerPeakProgress() * 1.5f);
        }
        else
        {
            return width;
        }
    }

    public float getAlpha()
    {
        if(burst)
        {
            return alpha * centerPeakProgress() * 1.5f;
        }
        else
        {
            return alpha;
        }
    }

    private float centerPeakProgress()
    {
        return Interpolation.pow3In.apply(1 - (Math.abs(getPercentProgress() - .5f) * 2)) ;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public float getMaxSpeedBase() {
        return 0;
    }

    @Override
    public float getAccelerationBase() {
        return 0;
    }

    @Override
    public Texture getSpriteSheet() {
        return null;
    }

    @Override
    public void actionCombat()
    {

    }

    public float getPercentProgress()
    {
        return ((float) getTimer()) / ((float) weapon.getActivationTime());
    }

    public void renderShapes()
    {
        if(owner == null || owner.isExpired() || target == null || target.isExpired())
        {
            return;
        }

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        Color c = getTeam().getFaction().getColor(0);
        c.set(c.r, c.g, c.b, getAlpha()/2);
        Shape.getRenderer().setColor(c);
        Shape.getRenderer().set(ShapeRenderer.ShapeType.Filled);

        Vector2 offsetTarget = new Vector2(target.getPosition().x + targetOffset.x, target.getPosition().y + targetOffset.y);

        Shape.getRenderer().rectLine(owner.getPosition(), offsetTarget, getWidth());
        Shape.getRenderer().rectLine(owner.getPosition(), offsetTarget, getWidth()/2);
    }

}
