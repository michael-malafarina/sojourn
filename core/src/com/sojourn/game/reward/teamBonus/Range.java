package com.sojourn.game.reward.teamBonus;

import com.sojourn.game.Sojourn;
import com.sojourn.game.reward.RewardMenu;
import com.sojourn.game.reward.RewardTeamBonus;

public class Range extends RewardTeamBonus {

    public Range(RewardMenu owner) {
        super(owner);
        bonus = .20f;
        name = "Weapon Range";
        description = "Increases range by " + getPercentString(bonus);
    }

    @Override
    public void apply()
    {
        Sojourn.player.getTeamBonusManager().getRangeBonus().addBonusPercent(bonus);
    }
}
