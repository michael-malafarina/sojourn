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

    public float getRarityScaling()
    {
        return switch(getRarity())
        {
            case COMMON -> 1f;
            case UNCOMMON -> 1.5f;
            case RARE ->  2f;
            case EPIC -> 2.5f;
            default -> 3f;
        };
    }


    public String getPercentString(float value)
    {
        return Math.round(value * 100) + "%";
    }
}
