package com.sojourn.game.reward;

import com.badlogic.gdx.graphics.Color;
import com.sojourn.game.Sojourn;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.unit.ship.Ship;
import com.sojourn.game.state.BuildManager;

public class RewardUnlockShip extends Reward
{
    Class<? extends Ship> type;

    public RewardUnlockShip(RewardMenu owner, Class<? extends Ship> clazz) {
        super(owner);
        type = clazz;
        Ship prototype = (Ship) EntityManager.entityFactory(type);
        name = prototype.getName();
        description = "Learn to make " + prototype.getName() + "";
        color = new Color(.15f, .35f, .65f, 1f);
    }

    @Override
    public void apply()
    {
        BuildManager.unlockShip(type);
       // EntityManager.addSquad(type, Sojourn.player.getHomePoint(), Sojourn.player);
        owner.removeShipUnlock(this);
        Sojourn.player.spendResearch(1);
    }
}
