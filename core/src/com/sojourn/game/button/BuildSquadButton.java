package com.sojourn.game.button;

import com.badlogic.gdx.graphics.Color;
import com.sojourn.game.entity.Attribute;

public class BuildSquadButton extends BuildButton
{
    public BuildSquadButton(Attribute cost)
    {
        super(cost);
    }

    public void update()
    {
        super.update();
    }

    public void enable()
    {
        super.enable();
        color = new Color(.25f, .55f, 1f, 1f);
    }
}
