package com.sojourn.game.entity.component.module;

import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.component.Component;
import com.sojourn.game.entity.unit.Unit;

abstract public class Module extends Component
{
    protected Module(Unit owner) {
        super(owner);
    }

    @Override
    public int getMunitionCost() {
        return 0;
    }

    @Override
    public int getPreparationTime() {
        return 0;
    }

    @Override
    public int getActivationTime() {
        return 0;
    }

    @Override
    public int getRecoveryTime() {
        return 0;
    }

    @Override
    public boolean targetsSelf() {
        return false;
    }

    @Override
    public int getNumTargets() {
        return 0;
    }

    @Override
    public void effect(Entity target) {

    }
}
