package com.sojourn.game.reward;

abstract public class RewardTeamBonus extends Reward
{
    protected float bonus;

    public RewardTeamBonus(RewardMenu owner, Rarity rarity) {
        super(owner);
        //Color c = new Color(.75f, .75f, .35f, 1f);
        setRarity(rarity);
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
