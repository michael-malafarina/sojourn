package com.sojourn.game.reward;

abstract public class RewardTeamBonus extends Reward
{
    protected float bonus;

    public RewardTeamBonus(RewardMenu owner) {
        super(owner);
    }

    public String getPercentString(float value)
    {
        return Math.round(value * 100) + "%";
    }
}
