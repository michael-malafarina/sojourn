package com.sojourn.game.faction;

public class TeamBonusManager
{
    private TeamBonus health;

    public TeamBonus getHealthBonus()
    {
        return health;
    }

    TeamBonusManager()
    {
        health = new TeamBonus();
    }

}
