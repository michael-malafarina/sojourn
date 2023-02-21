package com.sojourn.game.entity.projectile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sojourn.game.display.Shape;
import com.sojourn.game.entity.Entity;

public class Beam extends Projectile
{
    public Beam(Entity owner, Entity target) {
        super(owner, target);

        setPosition(owner.getPosition());

    }

    @Override
    public int getWidth() {
        return 6;
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

    public void renderShapes()
    {
        if(owner == null || owner.isExpired() || target == null || target.isExpired())
        {
            return;
        }

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        Shape.getRenderer().setColor(Color.WHITE);
        Shape.getRenderer().set(ShapeRenderer.ShapeType.Filled);
        Shape.getRenderer().rectLine(owner.getPosition(), target.getPosition(), getWidth());

    }
}
