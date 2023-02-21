package com.sojourn.game.entity.projectile;

import com.sojourn.game.entity.Entity;

abstract public class Projectile extends Entity
{
    Entity owner;
    Entity target;

    Projectile(Entity owner, Entity target)
    {
        this.owner = owner;
        this.target = target;

        setPosition(owner.getPosition());
        setTeam(owner.getTeam());
        setImage();
    }

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

}
