package com.sojourn.game.entity.unit.ship;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.sojourn.game.Settings;
import com.sojourn.game.Sojourn;
import com.sojourn.game.display.Shape;
import com.sojourn.game.entity.Attribute;
import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.unit.Unit;
import com.sojourn.game.faction.Squad;

public abstract class Ship extends Unit
{
    private final float HEALTH_REGEN_PLANNING = 1/120f;

    private boolean idle;

    private int lastOrder;
    private float distancing = 25;

    private Vector2 drift;

    boolean retreat;

    private Squad group;

    protected Attribute squadSize;

    private boolean lowMunitions = false;
    private boolean lowHealth = false;

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


    public void die()
    {
        if(!isExpired())
        {
            if(getTeam() == Sojourn.currentEnemy) {
                float val = (getCost().getValueBase() / getSquadSize().getValueBase()) * Settings.resourceFromKill;
                Sojourn.player.addResources(val);
            }
            setExpired();
        }
    }

    @Override
    protected void removalCheck()
    {
        if(getHealth().getValue() <= 0)
        {
            die();
        }
    }

    @Override
    public void upkeep(boolean planning, float delta)
    {
        super.upkeep(planning, delta);

        if(planning && this instanceof Ship)
        {
            getHealth().increase(HEALTH_REGEN_PLANNING *  getHealth().getMaximum());
        }
    }

    public Vector2 getDestination()
    {
        return getSquad().getDestination();
    }

    public boolean hasDestination()
    {
        return getSquad().hasDestination();
    }

    public Attribute getSquadSize()
    {
        return squadSize;
    }

    protected void setSquadSize(int baseValue)
    {
        squadSize = new Attribute(getTeam().getTeamBonusManager().getSquadSizeBonus(), baseValue);
    }

    public Squad getSquad()
    {
        return group;
    }

    @Override
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
        seekHealing();
        restockMunitions();
        avoidNearby();
        //drift();



        Unit u = getNearestEnemyUnit();

//
//        if(getTimer() % 600 <= 300)
//        {
//            moveTo(u);
//            weapons.useAllSingleTargetWeapons(u);
//        }
//        else
//        {
//            moveTo(getTeam().getHomePoint());
//        }


        weapons.useAllSingleTargetWeapons(u);


        if(!inRangeShortest(u))
        {
            moveTo(u);
        }
        else
        {
            moveTo(getTeam().getHomePoint());
        }



    }

    protected void restockMunitions()
    {
        if(getMunitions().getMaximum() == 0)
        {
            return;
        }

        if(getMunitions().getPercent() < .1f)
        {
            lowMunitions = true;
        }

        if(getMunitions().isMaximum())
        {
            lowMunitions = false;
        }

        if(lowMunitions)
        {
            moveTo(EntityManager.getNearestMunitionsDepot(this));
            turnLock();
        }

    }

    protected void seekHealing()
    {
        if(getHealth().getPercent() < .2f)
        {
            lowHealth = true;
        }

        if(getHealth().isMaximum())
        {
            lowHealth = false;
        }

        if(lowHealth)
        {
            moveTo(EntityManager.getNearestHealer(this));
        }

    }


    private void avoidNearby()
    {
        Ship s = getNearestShip();

        // If another ship is my distance radius, try to move away
        if(getDistance(s) < 25)
        {
            avoid(s);
        }

    }

    public void avoid(Entity e)
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
        turnTo(getAnchor());
        turn(90);
        move(5f);
    }

    public void render()
    {
        super.render();
       // Text.draw(""+Math.round(getMunitions().getMaximum()), getX(), getY());
    //    Text.draw(""+Math.round(weapons.getWeapons().get(0).getDamage().getValue()), getX(), getY()+50);

    }

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
