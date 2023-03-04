package com.sojourn.game.menu;

import com.sojourn.game.Sojourn;

public class ExtraRangeScaling extends RewardTeamBonus{

    @Override
    public void apply()
    {
        Sojourn.player.getTeamBonusManager().getRangeBonus().addBonusPercent(.1f);
    }
}
