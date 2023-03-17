package com.sojourn.game.reward.teamBonus;

import com.sojourn.game.Sojourn;
import com.sojourn.game.reward.Rarity;
import com.sojourn.game.reward.RewardMenu;
import com.sojourn.game.reward.RewardTeamBonus;

public class CritDamage extends RewardTeamBonus {

    public CritDamage(RewardMenu owner) {
        super(owner, Rarity.UNCOMMON);
        bonus = .50f;
        name = "Crit Damage";
        description = "Increases critical hit damage by " + getPercentString(bonus);
    }

    @Override
    public void apply()
    {
        Sojourn.player.getTeamBonusManager().getCritDamage().addBonusAdded(bonus);
    }
}
