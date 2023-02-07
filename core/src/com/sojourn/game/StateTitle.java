package com.sojourn.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.ScreenUtils;

public class StateTitle extends State {
    static Button test;

    public StateTitle(final Sojourn game)
    {
        super(game);
        test = new Button();
    }

    @Override
    public void update()
    {

    }
    @Override
    public void renderBackground(Batch batch, float delta)
    {
        ScreenUtils.clear(.1f, .1f, .6f, 1);
    }
    @Override
    public void renderGameplay(Batch batch, float delta)
    {

    }

    @Override
    public void renderHud(Batch batch, float delta)
    {
        game.font.draw(batch, "Title", 50, 50);

    }



}
