package com.sojourn.game.entity.unit.ship;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.sojourn.game.display.Shape;
import com.sojourn.game.entity.unit.Unit;
import com.sojourn.game.faction.Squad;

public abstract class Ship extends Unit
{
    private boolean idle;

    private int lastOrder;
    private float distancing = 25;

    private Vector2 drift;

    boolean retreat;

    private Squad group;

    public void setGroup(Squad group) {
        this.group = group;
    }

    public Ship()
    {
        super();
        drift = new Vector2(.5f, .5f);
        drift.setToRandomDirection();

        //   drift.setLength(1);
    }

    public abstract int getSquadSizeBase();

    public Vector2 getDestination()
    {
        return getSquad().getDestination();
    }

    public boolean hasDestination()
    {
        return getSquad().hasDestination();
    }

    public int getSquadSize()
    {
        return getSquadSizeBase();
    }

    public Squad getSquad()
    {
        return group;
    }

    public void clicked()
    {
        super.clicked();
        group.clicked();
    }

    public void actionPlanning()
    {
        drift();

        // Planning Phase Movement Rules




        if(nearAnchor(25))
        {
            idle = true;
        }
        else if(!nearAnchor(250))
        {
            idle = false;
        }

        if(idle)
        {
            idleMovement();
        }
        else
        {
            moveTo(getAnchor());
        }

        if(getTimer() % 300 == 0)
        {
            drift.setToRandomDirection();
        }

    }

    public void actionCombat()
    {
        avoidNearby();
        //drift();


        Unit u = getNearestEnemyShip();

        if(!inRangeShortest(u))
        {
            turnTo(u);
        }
        else
        {
            turnTo(getTeam().getHomePoint());
        }


        weapons.useAllSingleTargetWeapons(u);

        move();
    }


    private void avoidNearby()
    {
        Ship s = getNearestShip();

        // If another ship is my distance radius, try to move away and increase that radius slightly
        if(getDistance(s) < 25)
        {
            avoid(s);
        }

    }

    public void avoid(com.sojourn.game.entity.Entity e)
    {
        turnTo(e);
        turnAround();
        move();
    }

    public void drift()
    {
        float dist = getDistance( getNearestShip());

        if(dist < 10)
        {
            drift.setLength(.1f);
            setPosition(getX() + drift.x, getY() + drift.y);

        }

        if(dist < 20)
        {
            drift.setLength(.05f);
            setPosition(getX() + drift.x, getY() + drift.y);

        }



        //setPosition(getX() + drift.x, getY() + drift.y);
    }

    public void idleMovement()
    {
//        float percentOrbitLimit = getDistance(anchor) / 75f;
//        float turnAmount = percentOrbitLimit * .45f + .45f;

//        if(getDistance(getNearestShip()) < (getWidth() + getHeight()) * .5f)
//        {
//            turn(Utility.random(0, turnAmount * -1));
//        }


turnTo(getAnchor());
turn(90);


//        turn(.2f * getDistance(anchor) / 100);

       // turn(.45f);
        move(10f);
    }

    public void render()
    {
        super.render();
       // Text.draw(""+isSelected(), getX(), getY());
    }

//    public Vector2 getDestination()
//    {
//        return destination;
//    }

    public Vector2 getAnchor()
    {
        return getSquad().getAnchor();
    }

    public boolean nearAnchor(int amount)
    {
        return getDistance(getAnchor()) < amount + distancing;
    }



    public void setDestination(float x, float y)
    {
        getSquad().setDestination(x, y);
        lastOrder = getTimer();
        idle = false;
    }

    public void setDestination(Vector2 p)
    {
        setDestination(p.x, p.y);
    }

    public int getNumLayers()
    {
        return 5;
    }



    public void renderShapes()
    {
        super.renderShapes();

        if(!getSquad().hasDestination()) {
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


}
