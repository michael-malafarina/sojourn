package com.sojourn.game.menu;

import com.sojourn.game.Sojourn;

public class ExtraHealth extends RewardTeamBonus{

    @Override
    public void apply()
    {
        Sojourn.player.getTeamBonusManager().getHealthBonus().addBonusPercent(.1f);
    }
}
