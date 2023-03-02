package com.sojourn.game.faction.wave;

import com.sojourn.game.Utility;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.unit.Unit;
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

    public Distribution createNewDistribution(int numPositions)
    {
        int r = Utility.random(2);

        return switch(r) {
                    case 1 -> new EastSide(numPositions);
                    default ->new RandomDistribution(numPositions);
                };
    }

    public void setValue(float value)
    {
        this.value = value;
    }

    public void plan()
    {
        squads.clear();

        int count = 0;
        count +=  calculateNumberOfSquads(Scout.class, value * .6f);
        count +=  calculateNumberOfSquads(Raider.class, value * .6f);

        currentDistribution = createNewDistribution(count);
    }


    public void spawn()
    {
        addSquads(createSquadsByValue(Scout.class, value * .6f));
        addSquads(createSquadsByValue(Raider.class,value * .4f));
    }

    public int calculateNumberOfSquads(Class<? extends Ship> clazz, float totalValue)
    {
        Unit prototype = EntityManager.unitFactory(clazz);
        return ((int) totalValue) / prototype.getValue();
    }

    public List<Squad> createSquadsByValue(Class<? extends Ship> clazz, float value)
    {
        return team.createSquads(clazz, currentDistribution, calculateNumberOfSquads(clazz, value));
    }



}
