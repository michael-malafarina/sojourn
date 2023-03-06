package com.sojourn.game.menu;

import com.sojourn.game.Sojourn;

public class ExtraHealthScaling extends RewardTeamBonus{

    public ExtraHealthScaling(RewardMenu owner) {
        super(owner);
        bonus = .1f;
        name = "Structure";
        description = "Increases unit structure by " + getPercentString(bonus);
    }

    @Override
    public void apply()
    {
        Sojourn.player.getTeamBonusManager().getHealthBonus().addBonusPercent(bonus);
    }
}
