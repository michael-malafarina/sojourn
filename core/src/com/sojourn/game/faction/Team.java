package com.sojourn.game.faction;

abstract public class Team
{
    private Faction faction;
    abstract public int getTeamID();

    final public static int ID_NEUTRAL = 0;
    final public static int ID_PLAYER = 1;
    final public static int ID_ENEMY = 2;
    final public static int ID_HOSTILE = 3;

    public Team(Faction faction)
    {
        this.faction = faction;
    }

    public Faction getFaction()
    {
        return faction;
    }

    public void setFaction(Faction faction)
    {
        this.faction = faction;
    }

    public boolean isHostile(Team other)
    {
         if(getTeamID() == 0 || other.getTeamID() == 0)
        {
            return false;
        }

        else if(getTeamID() == other.getTeamID())
        {
            return false;
        }

        return true;
    }

}
