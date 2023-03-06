package com.sojourn.game.menu;

import com.sojourn.game.Sojourn;

public class ControlRadiusScaling extends RewardTeamBonus{

    public ControlRadiusScaling(RewardMenu owner) {
        super(owner);
        bonus = .30f;
        name = "Control Radius Up";
        description = "Increases unit control radius by " + getPercentString(bonus);
    }

    @Override
    public void apply()
    {
        Sojourn.player.getTeamBonusManager().controlRadiusBonus().addBonusPercent(bonus);
    }
}
