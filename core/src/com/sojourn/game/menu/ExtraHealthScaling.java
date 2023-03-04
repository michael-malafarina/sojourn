package com.sojourn.game.menu;

import com.sojourn.game.Sojourn;

public class ExtraHealthScaling extends RewardTeamBonus{

    @Override
    public void apply()
    {
        Sojourn.player.getTeamBonusManager().getHealthBonus().addBonusPercent(.1f);
    }
}
