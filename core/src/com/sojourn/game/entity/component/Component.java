package com.sojourn.game.entity.component;

import com.badlogic.gdx.math.Vector2;
import com.sojourn.game.entity.Attribute;
import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.component.weapon.TargetSet;

import java.util.List;

abstract public class Component
{
    // Data

    protected Entity owner;
    protected int useTimer;
    protected TargetSet targets;


    private Attribute damage;
    private Attribute range;

    // Constructors

    protected Component(Entity owner)
    {
        this.owner = owner;
    }

    // Abstract Methods

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
        return useTimer == 0;
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
        damage = new Attribute(baseValue, owner.getTeam().getTeamBonusManager().getDamageBonus());
    }

    protected void setRange(float baseValue)
    {
        range = new Attribute(baseValue, owner.getTeam().getTeamBonusManager().getRangeBonus());
    }


    public void use() {
        if (canUse()) {
            useTimer = getUseTime();
            if(targetsSelf())
            {
                targets = new TargetSet(owner);
            }
            else if(targets == null)
            {
                targets = new TargetSet();
            }
        }

    }

    public void use(Entity e) {
        if (canUse(e)) {
            use();
            targets = new TargetSet(e);
        }
    }

    public void use(List<Entity> entities)
    {
        for(Entity e : entities) {
            if (canUse(e)) {
                use();
                targets = new TargetSet(entities);
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

        // Trigger start of preparation
        if (useTimer == getUseTime()) {
            preparationBegin();
        }

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

    }

    public void preparationEnd()
    {

    }

    public void activationBegin()
    {

    }

    public void activation(float progressPercent)
    {
        owner.turnTo(targets.getCenter());
        owner.turnLock();
    }

    public void activationEnd()
    {

    }

    public void recoveryBegin()
    {

    }

    public void recoveryEnd()
    {

    }

}
