package com.sojourn.game.reward.teamBonus;

import com.sojourn.game.Sojourn;
import com.sojourn.game.reward.Rarity;
import com.sojourn.game.reward.RewardMenu;
import com.sojourn.game.reward.RewardTeamBonus;

public class Speed extends RewardTeamBonus {

    public Speed(RewardMenu owner, Rarity rarity) {
        super(owner, rarity);
        bonus = .20f * getRarityScaling();
        name = "Speed";
        description = "Increases speed and acceleration by " + getPercentString(bonus);
    }

    @Override
    public void apply()
    {
        Sojourn.player.getTeamBonusManager().getSpeedBonus().addBonusPercent(bonus);
        Sojourn.player.getTeamBonusManager().getAcceleration().addBonusPercent(bonus);
    }
}
