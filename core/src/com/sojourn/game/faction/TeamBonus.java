package com.sojourn.game.faction;

public class TeamBonus
{
    private float bonusPercent;
    private boolean modified;

    TeamBonus()
    {
        bonusPercent = 1f;
    }

    public void addBonusPercent(float amount)
    {
        bonusPercent += amount;
        modified = true;
    }

    public float getBonusPercent() {
        return bonusPercent;
    }

    public boolean isModified()
    {
        return modified;
    }
}
