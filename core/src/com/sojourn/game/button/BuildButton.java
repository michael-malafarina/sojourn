package com.sojourn.game.button;

import com.sojourn.game.entity.Attribute;
import com.sojourn.game.state.gameplay.BuildManager;

public class BuildButton extends Button
{
    Attribute cost;

    public BuildButton(Attribute cost)
    {
        this.cost = cost;
    }

    public void update()
    {
        super.update();
        enable();

        if(BuildManager.isPlacingCivilian())
        {
            disable();
        }

        if(!BuildManager.canPay(cost))
        {
            disable();
        }


    }

}
