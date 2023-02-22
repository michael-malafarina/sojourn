package com.sojourn.game.entity.component.weapon;

// Weapons can have
// Any number of projectiles divided across any number of targets (including self or a point)
// Projectiles may be all at once or staggered
// Try and handle as many of the old Astraeus subclasses in the core as possible

//

import com.badlogic.gdx.math.Vector2;
import com.sojourn.game.entity.Entity;

import java.util.List;

abstract public class Weapon
{
    // Data

    protected Entity owner;
    protected TargetSet targets;
    private int useTimer;

    // Constructor

    public Weapon(Entity owner) {
        this.owner = owner;
    }

    // Abstract Methods

    abstract public int getRangeBase();
    abstract public int getPreparationTime();
    abstract public int getActivationTime();
    abstract public int getRecoveryTime();
    abstract public boolean targetsSelf();
    abstract public void effect(Entity owner, Entity target);

    public int getNumTargets()
    {
        return 1;
    }

    // Accessors

    public boolean canUse() {
        return useTimer == 0;
    }
    public boolean canUse(Entity e) {
        return  canUse() &&
                inRange(e) &&
                (targetsSelf() || e != owner) ;
    }

    public int getTotalTime() {
        return getPreparationTime() + getActivationTime() + getRecoveryTime();
    }

    public int getRange()    {
        return getRangeBase();
    }

    public boolean inRange(Entity e)
    {
        if(e == null) return false;
        return owner.getCenterPosition().dst(e.getCenterPosition()) < getRange();
    }

    public boolean inRange(Vector2 p)    {
       if(p == null) return false;
        return owner.getCenterPosition().dst(p) < getRange();
    }

    // Mutators




    public void use() {
        if (canUse()) {
            useTimer = getTotalTime();
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
        if (useTimer == getTotalTime()) {
            preparationBegin();
        }

        // Trigger end of preparation and beginning of activation
        if (useTimer == getTotalTime() - getPreparationTime()) {
            preparationEnd();
            activationBegin();
        }

        // Trigger percentage based activation effects  (ie, firing multishots)
        if (useTimer <= getTotalTime() - getPreparationTime() && useTimer >= getTotalTime() - getPreparationTime() - getActivationTime()) {
            int startingTime = getTotalTime() - getPreparationTime();
            int framesIntoActivation = startingTime - useTimer;

            activation((float) framesIntoActivation / (float) getActivationTime());
        }

        // Trigger end of activation and beginning of recovery
        if (useTimer == getTotalTime() - getPreparationTime() - getActivationTime()) {
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

            owner.turnTo(targets.getCenter());

    }

    public void preparationEnd()
    {

    }

    public void activationBegin()
    {
//        if(getDistance(getNearestEnemyUnit()) < 120)
//        {

      // targets.getTargets().forEach(n -> n.takeDamage(5, owner));

//        }
    }

    public void activation(float progressPercent)
    {

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
