package com.sojourn.game.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.sojourn.game.display.Shape;

import java.util.List;

public abstract class Unit extends Entity
{
    private Vector2 destination;
    private int lastOrder;
    boolean retreat;

    public Unit()
    {
        super();
    }

    public void actionPlanning()
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

    public void actionCombat()
    {



        Vector2 home = new Vector2(0, 0);


        Unit u = getNearestEnemyUnit();
        if(getDistance(u) > 75)
        {
            turnTo(u);
        }
        else
        {
            turnTo(u);
            turnAround();
        }


        if(getDistance(home) > 500)
        {
            retreat = true;
        }

        if(getDistance(home) < 50)
        {
            retreat = false;
        }

        if(retreat)
        {
            turnTo(home);
        }




        accelerate();
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

        float progress = (getTimer() - lastOrder) / 50f;
        if (progress < 1)
        {

            float alpha = Interpolation.pow2In.apply(1-progress);

            Shape.getRenderer().setColor(new Color(.1f, .9f, .1f, alpha));
            float radius = alpha * 20;
            Shape.getRenderer().circle(getDestination().x, getDestination().y, radius);

        }

    }

    protected List<Unit> getEnemyUnits()
    {
        return EntityManager.getEnemyUnits(getTeam());
    }

    protected Unit getNearestUnit()
    {
        return EntityManager.getNearestUnit(this);
    }

    protected Unit getNearestEnemyUnit()
    {
        return EntityManager.getNearestEnemyUnit(this);
    }


}
