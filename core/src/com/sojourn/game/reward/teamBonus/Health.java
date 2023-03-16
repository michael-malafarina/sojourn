package com.sojourn.game.reward.teamBonus;

import com.sojourn.game.Sojourn;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.unit.Unit;
import com.sojourn.game.reward.Rarity;
import com.sojourn.game.reward.RewardMenu;
import com.sojourn.game.reward.RewardTeamBonus;

import java.util.List;

public class Health extends RewardTeamBonus {

    public Health(RewardMenu owner, Rarity rarity) {
        super(owner, rarity);
        bonus = .10f * getRarityScaling();
        name = "Health";
        description = "Increases health by " + getPercentString(bonus);
    }

    @Override
    public void apply()
    {
        Sojourn.player.getTeamBonusManager().getHealthBonus().addBonusPercent(bonus);

        List<Unit> units = EntityManager.getFriendlyUnits(Sojourn.player);
        units.forEach(u -> u.getHealth().increase(u.getHealth().getMaximum() * bonus));
    }
}
