package com.sojourn.game.reward;

import com.badlogic.gdx.graphics.Color;

abstract public class RewardTeamBonus extends Reward
{
    protected float bonus;

    public RewardTeamBonus(RewardMenu owner, Rarity rarity) {
        super(owner);
        setRarity(rarity);
       // rewardButton.setColor(new Color(.25f, .55f, 1f, 1f));

        rewardButton.setColor(new Color(.5f, .5f, .5f, 1f));
//        rewardButton.setColor(Color);

    }

    public String getPercentString(float value)
    {
        return Math.round(value * 100) + "%";
    }
}
