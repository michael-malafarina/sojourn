package com.sojourn.game.menu;

import com.sojourn.game.Sojourn;

public class ExtraSquadSizeScaling extends RewardTeamBonus{

    public ExtraSquadSizeScaling(RewardMenu owner) {
        super(owner);
        bonus = .10f;
        name = "Manufacturing";
        description = "Increases squad size by " + getPercentString(bonus);
    }

    @Override
    public void apply()
    {
        Sojourn.player.getTeamBonusManager().getSquadSizeBonus().addBonusPercent(bonus);
    }
}
