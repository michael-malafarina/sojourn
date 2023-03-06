package com.sojourn.game.menu;

import com.sojourn.game.Sojourn;

public class ExtraDamageScaling extends RewardTeamBonus{

    public ExtraDamageScaling(RewardMenu owner) {
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
