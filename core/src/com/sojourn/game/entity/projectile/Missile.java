package com.sojourn.game.entity.projectile;

import com.badlogic.gdx.graphics.Texture;
import com.sojourn.game.Textures;
import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.component.weapon.Weapon;

public class Missile extends Projectile
{

    public Missile(Entity owner, Entity target, Weapon weapon) {
        super(owner, target, weapon);
    }


    public void startingAttributes() {
        setSpeed(125);
        setAcceleration(300);

    }

    public void setSize(int size)
    {
       this.width = size;
       this.height = size;
    }


    @Override
    public void actionPlanning() {

    }

    @Override
    public void actionCombat()
    {
        moveTo(target);

        if(getCenterPosition().dst(target.getCenterPosition()) < (getWidth() + getHeight()) / 2)
        {
           setExpired();
           weapon.effect(target);
        }

    }

    @Override
    public Texture getSpriteSheet() {
        return Textures.raider;
    }

    public float getPercentProgress()
    {
        return ((float) getTimer()) / ((float) weapon.getActivationTime());
    }

}
