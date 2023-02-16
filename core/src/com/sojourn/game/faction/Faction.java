package com.sojourn.game.faction;

import com.badlogic.gdx.graphics.Color;

public abstract class Faction
{
    abstract int getTeamID();
    abstract public Color getColor(int layer);

    public boolean isHostile(Faction f)
    {
        // Team zero is neutral
        if(getTeamID() == 0 || f.getTeamID() == 0)
        {
            return false;
        }

        // Same ID means allies
        else if(getTeamID() == f.getTeamID())
        {
            return false;
        }

        // Otherwise they are enemies
        return true;
    }

}
