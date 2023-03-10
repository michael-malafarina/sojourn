package com.sojourn.game.faction;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.sojourn.game.Settings;
import com.sojourn.game.Sojourn;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.unit.ship.Ship;

public class TeamPlayer extends Team
{
    private static final int CONTROL_DISTANCE_BASE = 1000;

    private float resources = Settings.resourceStarting;
    private float research = 1;

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

    public float getResearch()
    {
        return research;
    }

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

    public void addResearch(float amount)
    {
        research += amount;
    }

    public void spendResearch(float amount)
    {
        research -= amount;
    }

    public void buildSquad(Class<? extends Ship> clazz)
    {
        Ship prototype = (Ship) EntityManager.entityFactory(clazz);
        prototype.setTeam(Sojourn.player);

        if(Math.round(resources) >= Math.round(prototype.getCost().getValue()))
        {
            EntityManager.addSquad(clazz, getHomePoint(), this);
            resources -= prototype.getCost().getValue();
        }
    }

    public float getTotalWorth()
    {
        float worth = resources;

        for(Squad s : EntityManager.getPlayerSquads())
        {
            System.out.println(s.getCost().getValueBase());
            worth += s.getCost().getValueBase();
        }

        return worth;
    }

}
