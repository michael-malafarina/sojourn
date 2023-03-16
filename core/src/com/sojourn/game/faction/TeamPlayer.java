package com.sojourn.game.faction;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.sojourn.game.Settings;
import com.sojourn.game.Sojourn;
import com.sojourn.game.Utility;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.unit.ship.Ship;

public class TeamPlayer extends Team
{
    private static final int CONTROL_DISTANCE_BASE = 1000;

    private float resources = Settings.resourceStarting;
//    private float shipResearch = 1;

    final private int SPAWN_X_WIDTH = 600;
    final private int SPAWN_Y_HEIGHT = 400;

    public TeamPlayer(Faction faction)
    {
        super(faction);
        setHomePoint(new Vector2(0, 0));
    }

    @Override
    public int getTeamID() {
        return Team.ID_PLAYER;
    }

    public int getControlDistance()
    {
        return Math.round(CONTROL_DISTANCE_BASE * getTeamBonusManager().getControlRadiusBonus().getBonusPercent());
    }

    public float getResources()
    {
        return resources;
    }

//    public float getShipResearch()
//    {
//        return shipResearch;
//    }

    public boolean inControlRadius(Vector2 point)
    {
        return point.dst(0, 0) <= getControlDistance();
    }

    public boolean inControlRadius(Vector3 point)
    {
        return point.dst(0, 0, 0) <= getControlDistance();
    }

    public void addResources(float amount)
    {
        resources += amount;
    }

//    public void addResearch(float amount)
//    {
//        shipResearch += amount;
//    }

//    public void spendResearch(float amount)
//    {
//        shipResearch -= amount;
//    }

    public Vector2 getSpawnDestination()
    {
        return new Vector2(getSpawnPoint().x + Utility.random(-SPAWN_X_WIDTH, SPAWN_X_WIDTH), getSpawnPoint().y + Utility.random(-SPAWN_Y_HEIGHT, SPAWN_Y_HEIGHT));
    }

    public void buildSquad(Class<? extends Ship> clazz)
    {
        Ship prototype = (Ship) EntityManager.entityFactory(clazz);
        prototype.setTeam(Sojourn.player);

        if(Math.round(resources) >= Math.round(prototype.getCost().getValue()))
        {
            EntityManager.addSquad(clazz, getSpawnPoint(), this);
            resources -= prototype.getCost().getValue();
        }
    }

    public float getTotalWorth()
    {
        float worth = resources;

        for(Squad s : EntityManager.getSquads())
        {
            if(s.getTeam() == this)
            {
                worth += s.getCost().getValueBase();
            }
        }

        return worth;
    }

}
