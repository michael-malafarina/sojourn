package com.sojourn.game.reward.teamBonus;

import com.sojourn.game.Sojourn;
import com.sojourn.game.reward.Rarity;
import com.sojourn.game.reward.RewardMenu;
import com.sojourn.game.reward.RewardTeamBonus;

public class MunitionsCapacity extends RewardTeamBonus {

    public MunitionsCapacity(RewardMenu owner) {
        super(owner, Rarity.UNCOMMON);
        bonus = .30f;
        name = "Munitions";
        description = "Increases munitions capacity by " + getPercentString(bonus);
    }

    @Override
    public void apply()
    {
        Sojourn.player.getTeamBonusManager().getMunitionsBonus().addBonusPercent(bonus);
    }
}
