package com.sojourn.game.reward;

import com.badlogic.gdx.graphics.Color;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.unit.civilian.Civilian;
import com.sojourn.game.state.BuildManager;

public class RewardUnlockCivilian extends Reward
{
    Class<? extends Civilian> type;

    public RewardUnlockCivilian(RewardMenu owner, Class<? extends Civilian> clazz) {
        super(owner);
        type = clazz;
        Civilian prototype = (Civilian) EntityManager.entityFactory(type);
        name = prototype.getName();
        rewardButton.setIcon(prototype.getIcon());
        description = prototype.getDescription();
        rewardButton.setColor(new Color(.75f, .45f, 1f, 1f));

    }

    @Override
    public void apply()
    {
        BuildManager.unlockCivilian(type);
       // EntityManager.addSquad(type, Sojourn.player.getHomePoint(), Sojourn.player);
        owner.removeCivilianUnlock(this);
    }

}
