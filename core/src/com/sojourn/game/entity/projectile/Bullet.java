package com.sojourn.game.entity.projectile;

import com.badlogic.gdx.graphics.Texture;
import com.sojourn.game.Textures;
import com.sojourn.game.entity.Entity;

public class Bullet extends Projectile
{

    Bullet(Entity owner, Entity target) {
        super(owner, target);
    }

    @Override
    public int getWidth() {
        return 10;
    }

    @Override
    public int getHeight() {
        return 10;
    }

    @Override
    public float getMaxSpeedBase() {
        return 50;
    }

    @Override
    public float getAccelerationBase() {
        return 40;
    }

    @Override
    public Texture getSpriteSheet() {
        return Textures.bullet;
    }

    @Override
    public void actionPlanning() {

    }

    @Override
    public void actionCombat() {

    }
}
