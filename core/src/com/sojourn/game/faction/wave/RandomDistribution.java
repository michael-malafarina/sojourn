package com.sojourn.game.faction.wave;

import com.badlogic.gdx.math.Vector2;
import com.sojourn.game.Utility;
import com.sojourn.game.entity.unit.ship.Ship;

import java.util.List;

public class RandomDistribution extends Distribution {
    public RandomDistribution(List<Class<? extends Ship>> types) {
        super(types);
    }

    public TypePosition createNewPosition(Class<? extends Ship> type) {
        return new TypePosition(type, new Vector2(Utility.random(-3000, 3000), Utility.random(-3000, 3000)));
    }

}
