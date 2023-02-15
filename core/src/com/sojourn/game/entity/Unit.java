package com.sojourn.game.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.sojourn.game.display.Shape;

public abstract class Unit extends Entity
{
    private Vector2 destination;

    public Unit()
    {
        super();
    }

    public void action()
    {

        if(hasDestination())
        {
            //moveTo(destination);
           // pidTurn(destination);
           turnTo(destination);
        }
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
    }

    public void setDestination(Vector2 p)
    {
        destination = p;
    }

    public int getNumLayers()
    {
        return 5;
    }

    @Override
    public int getMaxSpeedBase()
    {
        return 6;
    }

    @Override
    public int getAccelerationBase()
    {
        return 6;
    }

    public void renderShapes()
    {
        super.renderShapes();

        // Don't draw the destination if I am close to it based on my own size
        if(getCenterPosition().dst(getDestination()) > getWidth() + getHeight())
        {
            Shape.getRenderer().setColor(new Color(.2f, .2f, .2f, 1f));
            Shape.getRenderer().line(getCenterPosition(), getDestination());
        }


    }



}
