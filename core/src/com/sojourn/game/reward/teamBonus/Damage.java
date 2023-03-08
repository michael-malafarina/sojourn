package com.sojourn.game.reward.teamBonus;

import com.sojourn.game.Sojourn;
import com.sojourn.game.reward.RewardMenu;
import com.sojourn.game.reward.RewardTeamBonus;

public class Damage extends RewardTeamBonus {

    public Damage(RewardMenu owner) {
        super(owner);
        bonus = .1f;
        name = "Weapon Strength";
        description = "Increases unit damage by " + getPercentString(bonus);
    }

    @Override
    public void apply()
    {
        Sojourn.player.getTeamBonusManager().getDamageBonus().addBonusPercent(bonus);
    }
}
