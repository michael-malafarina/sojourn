package com.sojourn.game.button;

import com.sojourn.game.Sojourn;

public class StartButton extends Button
{
    public StartButton(final Sojourn game)
    {
        super(game);
    }

    @Override
    protected void clicked()
    {
        game.setState(game.STATE_GAMEPLAY_ID);
    }

    @Override
    protected void mouseover()
    {

    }
}
