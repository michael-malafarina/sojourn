package com.sojourn.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.ScreenUtils;
import com.sojourn.game.Sojourn;
import com.sojourn.game.display.Fonts;
import com.sojourn.game.display.Text;
import com.sojourn.game.entity.Entity;

public class StateGameplay extends State {

    Entity ship;

    public StateGameplay(final Sojourn game)
    {
        super(game);
        ship = new Entity();
    }

    @Override
    public void update(float delta)
    {
        cameraControls();
        ship.update(delta);
    }

    @Override
    public void renderBackground(float delta)
    {
        ScreenUtils.clear(.02f, .02f, .02f, 1);
       // batch.draw(game.img, 0, 0, Sojourn.WIDTH, Sojourn.HEIGHT);

    }

    @Override
    public void renderGameplay(float delta)
    {
        ship.render();
    }

    @Override
    public void renderHud(float delta)
    {
        Text.setFont(Fonts.small);
        Text.draw("Gameplay", 5, Sojourn.HEIGHT-5);
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
