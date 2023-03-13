package com.sojourn.game.entity.component;

import com.badlogic.gdx.math.Vector2;
import com.sojourn.game.entity.Attribute;
import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.component.weapon.TargetSet;
import com.sojourn.game.entity.unit.Unit;

import java.util.List;

abstract public class Component
{
    // Data

    protected Unit owner;
    protected int useTimer;
    protected TargetSet targets;

    private boolean preparing;
    private boolean activating;
    private boolean recovering;

    private Attribute damage;
    private Attribute range;

    // Constructors

    protected Component(Unit owner)
    {
        this.owner = owner;
    }

    // Abstract Methods

    public boolean isPreparing()
    {
        return preparing;
    }

    public boolean isActivating()
    {
        return activating;
    }

    public boolean isRecovering()
    {
        return recovering;
    }

    abstract public int getMunitionCost();

    abstract public int getPreparationTime();
    abstract public int getActivationTime();
    abstract public int getRecoveryTime();
    abstract public boolean targetsSelf();
    abstract public int getNumTargets();
    abstract public void effect(Entity owner, Entity target);

    public Attribute getDamage()
    {
        return damage;
    }

    public Attribute getRange()
    {
        return range;
    }

    public int getMaxRange()
    {
        return Math.round(range.getValue());
    }

    // Accessors

    public int getUseTime()
    {
        return getPreparationTime() + getActivationTime() + getRecoveryTime();
    }

    public boolean canUse()
    {
        return useTimer == 0 && getMunitionCost() <= owner.getMunitions().getCurrent();
    }

    public boolean canUse(Entity e) {
        return  canUse() &&
                inRange(e) &&
                (targetsSelf() || e != owner) ;
    }

    public boolean inRange(Entity e)
    {
        if(e == null) return false;
        return owner.getCenterPosition().dst(e.getCenterPosition()) <  getMaxRange();
    }

    public boolean inRange(Vector2 p)    {
        if(p == null) return false;
        return owner.getCenterPosition().dst(p) < getRange().getValue();
    }

    // Mutators

    protected void setDamage(float baseValue)
    {
        damage = new Attribute(owner.getTeam().getTeamBonusManager().getDamageBonus(), baseValue);
    }

    protected void setRange(float baseValue)
    {
        range = new Attribute(owner.getTeam().getTeamBonusManager().getRangeBonus(), baseValue);
    }


    public void use() {
        if (canUse()) {
            useTimer = getUseTime();

            if(getMunitionCost() > 0)
            {
                owner.getMunitions().decrease(getMunitionCost());
            }
            if(targetsSelf())
            {
                targets = new TargetSet(owner);
                preparationBegin();
            }
        }

    }

    public void use(Entity e) {
        if (canUse(e)) {
            use();
            targets = new TargetSet(e);
            preparationBegin();
        }
    }

    public void use(List<Entity> entities)
    {
        for(Entity e : entities) {
            if (canUse(e)) {
                use();
                targets = new TargetSet(entities);
                preparationBegin();
            }
        }
    }


    public void update()
    {
        if(owner == null)
        {
            return;
        }

        // Update targets
        if(targets != null)
        {
            targets.update();
        }

//        if(useTimer == getUseTime())
//        {
//        }

        // Trigger end of preparation and beginning of activation
        if (useTimer == getUseTime() - getPreparationTime()) {
            preparationEnd();
            activationBegin();
        }

        // Trigger percentage based activation effects  (ie, firing multishots)
        if (useTimer <= getUseTime() - getPreparationTime() && useTimer >= getUseTime() - getPreparationTime() - getActivationTime()) {
            int startingTime = getUseTime() - getPreparationTime();
            int framesIntoActivation = startingTime - useTimer;

            activation((float) framesIntoActivation / (float) getActivationTime());
        }

        // Trigger end of activation and beginning of recovery
        if (useTimer == getUseTime() - getPreparationTime() - getActivationTime()) {
            activationEnd();
            recoveryBegin();
        }

        // Trigger end of recovery
        if (useTimer == 1) {
            recoveryEnd();
        }

        // Update timer

        if (useTimer > 0) {
            useTimer--;
        }


    }

    public void preparationBegin()
    {
        preparing = true;
    }

    public void preparationEnd()
    {
        preparing = false;
    }

    public void activationBegin()
    {
        activating = true;
    }

    public void activation(float progressPercent)
    {
        owner.turnTo(targets.getCenter());
        owner.turnLock();
    }

    public void activationEnd()
    {
        activating = false;
    }

    public void recoveryBegin()
    {
        recovering = true;
    }

    public void recoveryEnd()
    {
        recovering = false;
    }

}
