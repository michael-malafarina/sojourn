package com.sojourn.game.reward.teamBonus;

import com.sojourn.game.Sojourn;
import com.sojourn.game.reward.Rarity;
import com.sojourn.game.reward.RewardMenu;
import com.sojourn.game.reward.RewardTeamBonus;

public class CritChance extends RewardTeamBonus {

    public CritChance(RewardMenu owner, Rarity rarity) {
        super(owner, rarity);
        bonus = .10f * getRarityScaling();
        name = "Crit Chance";
        description = "Increases critical hit chance by " + getPercentString(bonus);
    }

    @Override
    public void apply()
    {
        Sojourn.player.getTeamBonusManager().getCritChance().addBonusAdded(bonus);
    }
}
