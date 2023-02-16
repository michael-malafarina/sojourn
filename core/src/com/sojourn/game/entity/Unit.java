package com.sojourn.game.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.sojourn.game.display.Shape;

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

    public void avoidCrowd()
    {

    }

    public void idleMovement()
    {
        turn(1);
        accelerate();
        speed.nor().scl(1f);
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
