package com.sojourn.game.faction;

public class TeamBonusManager
{
    private TeamBonus health;
    private TeamBonus damage;
    private TeamBonus range;

    TeamBonusManager()
    {
        health = new TeamBonus();
        damage = new TeamBonus();
        range = new TeamBonus();
    }

    public TeamBonus getHealthBonus()
    {
        return health;
    }

    public TeamBonus getDamageBonus()
    {
        return damage;
    }

    public TeamBonus getRangeBonus()
    {
        return range;
    }


}
