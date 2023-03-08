package com.sojourn.game.reward.teamBonus;

import com.sojourn.game.Sojourn;
import com.sojourn.game.reward.RewardMenu;
import com.sojourn.game.reward.RewardTeamBonus;

public class ControlRadius extends RewardTeamBonus {

    public ControlRadius(RewardMenu owner) {
        super(owner);
        bonus = .25f;
        name = "Communications";
        description = "Increases control radius by " + getPercentString(bonus);
    }

    @Override
    public void apply()
    {
        Sojourn.player.getTeamBonusManager().getControlRadiusBonus().addBonusPercent(bonus);
    }
}
