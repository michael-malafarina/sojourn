package com.sojourn.game.entity.projectile;

import com.badlogic.gdx.math.Vector2;
import com.sojourn.game.Utility;
import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.component.weapon.Weapon;

abstract public class Projectile extends Entity
{
    protected Entity owner;
    protected Entity target;
    protected Weapon weapon;
    protected Vector2 targetOffset;

    Projectile(Entity owner, Entity target, Weapon weapon)
    {
        this.owner = owner;
        this.target = target;
        this.weapon = weapon;

        targetOffset  = new Vector2(.5f, .5f);
        targetOffset.setToRandomDirection();
        targetOffset.setLength(Utility.random((target.getWidth() + target.getHeight()) / 2));

        setPosition(owner.getPosition());
        setTeam(owner.getTeam());
        setImage();
    }

    abstract public float getPercentProgress();

    @Override
    public int getNumLayers() {
        return 1;
    }

    public void update(boolean planning, float delta)
    {
        super.update(planning, delta);
    }

    public void actionPlanning()
    {
        this.setExpired();
    }

    public void render()
    {
        super.render();
    }

    public void end()
    {
        weapon.effect(owner, target);
        setExpired();
    }

}
