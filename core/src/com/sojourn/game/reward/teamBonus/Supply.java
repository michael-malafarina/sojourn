package com.sojourn.game.reward.teamBonus;

import com.sojourn.game.Sojourn;
import com.sojourn.game.reward.Rarity;
import com.sojourn.game.reward.RewardMenu;
import com.sojourn.game.reward.RewardTeamBonus;

public class Supply extends RewardTeamBonus {

    public Supply(RewardMenu owner) {
        super(owner, Rarity.RARE);
        bonus = 1;
        name = "Supply Cap";
        description = "Increases supply cap by " + bonus;
    }

    @Override
    public void apply()
    {
        Sojourn.player.getTeamBonusManager().getSquadSizeBonus().addBonusAdded(bonus);
    }
}
