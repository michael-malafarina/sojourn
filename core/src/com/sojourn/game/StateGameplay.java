package com.sojourn.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.ScreenUtils;

public class StateGameplay extends State {

    Entity ship;

    public StateGameplay(final Sojourn game)
    {
        super(game);
        ship = new Entity();
    }

    @Override
    public void update()
    {
        ship.update();
    }

    @Override
    public void renderBackground(Batch batch, float delta)
    {
        ScreenUtils.clear(.50f, .50f, .50f, 1);
    }

    @Override
    public void renderGameplay(Batch batch, float delta)
    {
        ship.render(batch);
    }

    @Override
    public void renderHud(Batch batch, float delta)
    {
        game.font.draw(batch, "Gameplay", 50, 50);
    }

    public void cameraControls()
    {
        int translateSpeed = 10;
        if (Gdx.input.isKeyPressed(Input.Keys.E))
        {
            game.gameCam.zoom += 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q))
        {
            game.gameCam.zoom -= 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A))
        {
            game.gameCam.translate(-translateSpeed, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D))
        {
            game.gameCam.translate(translateSpeed, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S))
        {
            game.gameCam.translate(0, -translateSpeed, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W))
        {
            game.gameCam.translate(0, translateSpeed, 0);
        }
        game.gameCam.update();
    }

}
