package com.sojourn.game.menu;

import com.sojourn.game.Sojourn;

public class ExtraDamageScaling extends RewardTeamBonus{

    @Override
    public void apply()
    {
        Sojourn.player.getTeamBonusManager().getDamageBonus().addBonusPercent(.1f);
    }
}
