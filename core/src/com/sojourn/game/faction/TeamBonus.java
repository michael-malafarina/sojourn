package com.sojourn.game.faction;

public class TeamBonus
{
    private float bonusPercent;

    TeamBonus()
    {
        bonusPercent = 1f;
    }

    public void addBonusPercent(float amount)
    {
        bonusPercent += amount;
    }

    public float getBonusPercent() {
        return bonusPercent;
    }
}
