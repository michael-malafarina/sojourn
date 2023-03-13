package com.sojourn.game.faction;

public class TeamBonus
{
    private float bonusPercent;
    private float bonusAdded;

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

    public void addBonusAdded(float amount)
    {
        bonusAdded += amount;
        modified = true;
    }



    public float getBonusPercent() {
        return bonusPercent;
    }
    public float getBonusAdded() {
        return bonusAdded;
    }

    public boolean isModified()
    {
        return modified;
    }
}
