package com.sojourn.game.menu;

import com.sojourn.game.Sojourn;

public class ControlRadiusScaling extends RewardTeamBonus{

    public ControlRadiusScaling(RewardMenu owner) {
        super(owner);
        bonus = .25f;
        name = "Communications";
        description = "Increases unit control radius by " + getPercentString(bonus);
    }

    @Override
    public void apply()
    {
        Sojourn.player.getTeamBonusManager().getControlRadiusBonus().addBonusPercent(bonus);
    }
}
