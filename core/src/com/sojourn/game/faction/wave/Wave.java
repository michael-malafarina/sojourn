package com.sojourn.game.faction.wave;

import com.sojourn.game.Sojourn;
import com.sojourn.game.Utility;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.ambient.EnemyAlert;
import com.sojourn.game.entity.unit.ship.*;
import com.sojourn.game.faction.Team;
import com.sojourn.game.faction.wave.distributions.OneSide;
import com.sojourn.game.faction.wave.distributions.Scattered;
import com.sojourn.game.faction.wave.distributions.TwoSides;

import java.util.ArrayList;
import java.util.List;

public class Wave
{
    private Team team;
    private float value;
    private float baseValue;
    private Distribution currentDistribution;
    private List<Class<? extends Ship>> types;

    public Wave(Team team)
    {
        this.team = team;
        types = new ArrayList<>();
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
        baseValue = value;
    }

    public void plan()
    {
        types.clear();

        // Always build at least one carrier

        buildSquad(Carrier.class);

        // Keep building squads of units as long as there is value remaining
        while(value > 50)
        {

            int r = Utility.random(4);
            switch(r) {
                case 1 -> buildSquad(Guardian.class);
                case 2 -> buildSquad(Raider.class);
                case 3 -> buildSquad(Lancer.class);
                default -> buildSquad(Scout.class);

            }
        }

        // Select a type of distribution for units based on these units
        currentDistribution = createNewDistribution(types);
        List<TypePosition> pairs = currentDistribution.getAllPositions();


        // Find all pairings of unit types and positions and create alerts at those positions
        for(int i = 0; i < pairs.size(); i++)
        {
            EnemyAlert e = new EnemyAlert();
            e.setClazz(pairs.get(i).getType());
            e.setPosition(pairs.get(i).getPosition());
            e.setTeam(team);
            EntityManager.addEntity(e);

        }

    }

    public void buildSquad(Class<? extends Ship> clazz)
    {
        Ship prototype = (Ship) EntityManager.entityFactory(clazz);
        prototype.setTeam(Sojourn.player);

        if(Math.round(value) >= Math.round(prototype.getCost().getValue()))
        {
            types.add(clazz);
            value -= prototype.getCost().getValue();
        }
    }

    public void spawn()
    {
        currentDistribution.getAllPositions().forEach(p -> team.createSquad(p.getType(), p.getPosition()));
    }

    public float getValue()
    {
        return baseValue;
    }


}
