package com.sojourn.game.button;

import com.sojourn.game.entity.Attribute;
import com.sojourn.game.state.gameplay.StateGameplay;

public class BuildCivilianButton extends BuildButton
{
    public BuildCivilianButton(Attribute cost)
    {
        super(cost);
    }

    public void update()
    {
        super.update();

        if(StateGameplay.inCombatMode())
        {
            disable();
        }
    }
}
