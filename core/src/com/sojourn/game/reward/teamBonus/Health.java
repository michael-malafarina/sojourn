package com.sojourn.game.reward.teamBonus;

import com.sojourn.game.Sojourn;
import com.sojourn.game.reward.RewardMenu;
import com.sojourn.game.reward.RewardTeamBonus;

public class Health extends RewardTeamBonus {

    public Health(RewardMenu owner) {
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
