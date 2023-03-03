package com.sojourn.game.faction.wave;

import com.sojourn.game.Utility;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.ambient.EnemyAlert;
import com.sojourn.game.entity.unit.ship.Raider;
import com.sojourn.game.entity.unit.ship.Scout;
import com.sojourn.game.entity.unit.ship.Ship;
import com.sojourn.game.faction.Squad;
import com.sojourn.game.faction.Team;

import java.util.ArrayList;
import java.util.List;

public class Wave
{

    List<Squad> squads;
    Team team;
    float value;
    Distribution currentDistribution;

    public Wave(Team team)
    {
        squads = new ArrayList<>();
        this.team = team;
    }

    public Wave(Team team, float value)
    {
        this(team);
        setValue(value);
    }

    public void addSquad(Squad squad)
    {
        squads.add(squad);
    }

    public void addSquads(List<Squad> squads)
    {
        squads.addAll(squads);
    }

    public Distribution createNewDistribution(List<Class<? extends Ship>> types)
    {
        int r = Utility.random(2);

        return switch(r) {
                    case 1 -> new EastSide(types);
                    default ->new RandomDistribution(types);
                };
    }

    public void setValue(float value)
    {
        this.value = value;
    }

    public void plan()
    {
        squads.clear();

        List<Class<? extends Ship>> types = new ArrayList<>();

        int count = calculateNumberOfSquads(Scout.class, value * .5f);

        for(int i = 0; i < count; i++)
        {
            types.add(Scout.class);
        }

        count = calculateNumberOfSquads(Raider.class, value * .5f);

        for(int i = 0; i < count; i++)
        {
            types.add(Raider.class);
        }

        currentDistribution = createNewDistribution(types);

        List<TypePosition> pairs = currentDistribution.getAllPositions();

        for(int i = 0; i < pairs.size(); i++)
        {
            EnemyAlert e = new EnemyAlert();
            e.setClazz(pairs.get(i).getType());
            e.setPosition(pairs.get(i).getPosition());
            e.setTeam(team);
            EntityManager.addEntity(e);
        }

    }


    public void spawn()
    {
        currentDistribution.getAllPositions().forEach(p -> team.createSquad(p.getType(), p.getPosition()));
    }

    public int calculateNumberOfSquads(Class<? extends Ship> clazz, float totalValue)
    {
        Ship prototype = (Ship) EntityManager.entityFactory(clazz);
        return ((int) totalValue) / (prototype.getValue() * prototype.getSquadSize());
    }



}