package com.sojourn.game.button;

import com.badlogic.gdx.graphics.Color;
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

    public void enable()
    {
        super.enable();
        color = new Color(.75f, .45f, 1f, 1f);
    }
}
