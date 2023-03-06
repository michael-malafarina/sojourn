package com.sojourn.game.menu;

import com.sojourn.game.Sojourn;

public class ExtraRangeScaling extends RewardTeamBonus{

    public ExtraRangeScaling(RewardMenu owner) {
        super(owner);
        bonus = .15f;
        name = "Weapon Range";
        description = "Increases unit range by " + getPercentString(bonus);
    }

    @Override
    public void apply()
    {
        Sojourn.player.getTeamBonusManager().getRangeBonus().addBonusPercent(bonus);
    }
}
