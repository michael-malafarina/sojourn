package com.sojourn.game.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.sojourn.game.display.Shape;

import java.util.ArrayList;

public abstract class Unit extends Entity
{
    private Vector2 destination;
    private int lastOrder;

    public Unit()
    {
        super();
    }

    public void action()
    {

        // Planning Phase Movement Rules

        if(hasDestination())
        {
            if(getDistance(getNearestUnit()) < 25)
            {
                avoid(getNearestUnit());
            }
            if(getDistance(destination) < 75)
            {
                destination = null;
            }
            else
            {
                turnTo(destination);
            }

            accelerate();

        }
        else
        {
            idleMovement();
        }



    }


    public final Unit getNearestUnit()
    {
        float nearestDistance = Float.MAX_VALUE;
        Unit nearestUnit = null;
        ArrayList<Entity> entities =  EntityManager.getEntities();

        for(Entity u : entities)
        {
            if(this != u && this instanceof Unit && getDistance(u) < nearestDistance)
            {
                nearestUnit = (Unit) u;
                nearestDistance = getDistance(u);
            }
        }

        return nearestUnit;
    }

    public void avoid(Unit u)
    {
        turnTo(u);
        turnAround();
        accelerate();
    }

    public void idleMovement()
    {
        turn(.5f);
        accelerate(.5f);
    }

    public void render()
    {
        super.render();
      //  Text.draw(""+a, getX(), getY());
    }



    public Vector2 getDestination()
    {
        return destination;
    }

    public boolean hasDestination()
    {
        return destination != null;
    }

    public void setDestination(float x, float y)
    {
        destination = new Vector2(x, y);
        lastOrder = getTimer();
    }

    public void setDestination(Vector2 p)
    {
        destination = p;
    }

    public int getNumLayers()
    {
        return 5;
    }



    public void renderShapes()
    {
        super.renderShapes();

        if(!hasDestination()) {
            return;
        }

        Shape.getRenderer().setColor(new Color(.2f, .2f, .2f, 1f));
        Shape.getRenderer().line(getCenterPosition(), getDestination());


        float progress = (getTimer() - lastOrder) / 50f;
        if (progress < 1)
        {

            float alpha = Interpolation.pow2In.apply(1-progress);

            Shape.getRenderer().setColor(new Color(.1f, .9f, .1f, alpha));
            float radius = alpha * 20;
            Shape.getRenderer().circle(getDestination().x, getDestination().y, radius);

        }

    }



}
