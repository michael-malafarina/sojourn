package com.sojourn.game.reward.teamBonus;

import com.sojourn.game.Sojourn;
import com.sojourn.game.reward.Rarity;
import com.sojourn.game.reward.RewardMenu;
import com.sojourn.game.reward.RewardTeamBonus;

public class SquadSize extends RewardTeamBonus {

    public SquadSize(RewardMenu owner) {
        super(owner, Rarity.RARE);
        bonus = .10f;
        name = "Manufacturing";
        description = "Increases squad size by " + getPercentString(bonus);
    }

    @Override
    public void apply()
    {
        Sojourn.player.getTeamBonusManager().getSquadSizeBonus().addBonusPercent(bonus);
    }
}
