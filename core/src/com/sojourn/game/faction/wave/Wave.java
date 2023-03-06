package com.sojourn.game.faction.wave;

import com.sojourn.game.Utility;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.ambient.EnemyAlert;
import com.sojourn.game.entity.unit.ship.Scout;
import com.sojourn.game.entity.unit.ship.Ship;
import com.sojourn.game.entity.unit.ship.Sniper;
import com.sojourn.game.entity.unit.ship.Tank;
import com.sojourn.game.faction.Team;
import com.sojourn.game.faction.wave.distributions.OneSide;
import com.sojourn.game.faction.wave.distributions.Scattered;
import com.sojourn.game.faction.wave.distributions.TwoSides;

import java.util.ArrayList;
import java.util.List;

public class Wave
{
    Team team;
    float value;
    Distribution currentDistribution;

    public Wave(Team team)
    {
        this.team = team;
    }

    public Wave(Team team, float value)
    {
        this(team);
        setValue(value);
    }

    public Distribution createNewDistribution(List<Class<? extends Ship>> types)
    {
        int r = Utility.random(3);

        return switch(r) {
            case 1 -> new OneSide(types);
            case 2 -> new TwoSides(types);
            default ->new Scattered(types);
         };
    }

    public void setValue(float value)
    {
        this.value = value;
    }

    public void plan()
    {
        List<Class<? extends Ship>> types = new ArrayList<>();

        int count = calculateNumberOfSquads(Scout.class, value * .4f);

        for(int i = 0; i < count; i++)
        {
            types.add(Scout.class);
        }

        count = calculateNumberOfSquads(Tank.class, value * .4f);

        for(int i = 0; i < count; i++)
        {
            types.add(Tank.class);
        }

        count = calculateNumberOfSquads(Sniper.class, value * .2f);

        for(int i = 0; i < count; i++)
        {
            types.add(Sniper.class);
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
        prototype.setTeam(team);
        return ((int) totalValue) / (prototype.getValue() * Math.round(prototype.getSquadSize().getValue()));
    }



}
