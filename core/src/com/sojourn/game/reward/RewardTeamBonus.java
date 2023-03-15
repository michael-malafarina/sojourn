package com.sojourn.game.reward;

import com.badlogic.gdx.graphics.Color;

abstract public class RewardTeamBonus extends Reward
{
    protected float bonus;

    public RewardTeamBonus(RewardMenu owner) {
        super(owner);
        rewardButton.setColor(new Color(.65f, .65f, .35f, 1f));
    }

    public String getPercentString(float value)
    {
        return Math.round(value * 100) + "%";
    }
}
