package com.sojourn.game.faction.wave.distributions;

import com.badlogic.gdx.math.Vector2;
import com.sojourn.game.Utility;
import com.sojourn.game.entity.unit.ship.Ship;
import com.sojourn.game.faction.wave.Distribution;
import com.sojourn.game.faction.wave.TypePosition;

import java.util.List;

public class Scattered extends Distribution {

    public Scattered(List<Class<? extends Ship>> types) {
        super(types);
    }

    @Override
    public void setup() {

    }

    public TypePosition createNewPosition(Class<? extends Ship> type) {
        return new TypePosition(type, new Vector2(Utility.random(-3000, 3000), Utility.random(-3000, 3000)));
    }

}
