package com.sojourn.game.reward.teamBonus;

import com.sojourn.game.Sojourn;
import com.sojourn.game.reward.RewardMenu;
import com.sojourn.game.reward.RewardTeamBonus;

public class Damage extends RewardTeamBonus {

    public Damage(RewardMenu owner) {
        super(owner);
        bonus = .15f;
        name = "Weapon Strength";
        description = "Increases damage by " + getPercentString(bonus);
    }

    @Override
    public void apply()
    {
        Sojourn.player.getTeamBonusManager().getDamageBonus().addBonusPercent(bonus);
    }
}
