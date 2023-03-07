package com.sojourn.game.entity.component.weapon;

import com.badlogic.gdx.math.Vector2;
import com.sojourn.game.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class TargetSet
{
    List<Entity> targets;

    public TargetSet()
    {
        targets = new ArrayList<>();
    }

    public TargetSet(Entity entity)
    {
        this();
        this.targets.add(entity);
    }

    public TargetSet(List<Entity> entities)
    {
        this();
        targets.addAll(entities);
    }

    public void update()
    {
        // Remove out of date targets
        for(int i = 0; i < targets.size(); i++)
        {
            if(targets.get(i) == null || targets.get(i).isExpired())
            {
                targets.remove(i);
                i--;
            }
        }
    }

    public List<Entity> getTargets()
    {
        return targets;
    }

    public int countTargets()
    {
        return targets.size();
    }

    public boolean hasTargets()
    {
        return targets != null && targets.size() != 0;
    }

    public void clear()
    {
        targets = null;
    }

    public Vector2 getCenter()
    {
        if(targets == null || targets.size() == 0)
        {
            return null;
        }

        float totalX = 0;
        float totalY = 0;

        for(Entity e : targets)
        {
            totalX += e.getX();
            totalY += e.getY();
        }

        return new Vector2(totalX, totalY);
    }



}


