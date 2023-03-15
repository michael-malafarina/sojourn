package com.sojourn.game.faction.wave.distributions;

import com.sojourn.game.Sojourn;
import com.sojourn.game.Utility;
import com.sojourn.game.entity.unit.ship.Ship;
import com.sojourn.game.faction.wave.Distribution;
import com.sojourn.game.faction.wave.TypePosition;

import java.util.List;

public class TwoSides extends Distribution
{
    private int sideOne;
    private int sideTwo;

    public TwoSides(List<Class<? extends Ship>> types)
    {
        super(types);
    }

    @Override
    public void setup() {
        sideOne = Utility.random(4);

        // Side two cannot be the same as side one
        do {
            sideTwo = Utility.random(4);
        } while(sideTwo == sideOne);
    }

    public TypePosition createNewPosition(Class<? extends Ship> type)
    {
        int r = Utility.random(2);

        TypePosition p = switch (r) {
            case 1 -> new TypePosition(type, getRandomSide(sideOne));
            default -> new TypePosition(type, getRandomSide(sideTwo));
        };

        if(!Sojourn.player.inControlRadius(p.getPosition())) {
            return p;
        }

        return createNewPosition(type);

    }


}
