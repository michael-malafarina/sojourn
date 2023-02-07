package com.sojourn.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;

abstract public class State implements Screen {

    final Sojourn game;

    public State(final Sojourn game)
    {
        this.game = game;
    }

    abstract protected void update();

    abstract protected void renderBackground(Batch batch, float delta);

    abstract protected void renderGameplay(Batch batch, float delta);
    abstract protected void renderHud(Batch batch, float delta);

    @Override
    public void render(float delta)
    {
        update();
        game.batch.begin();
        renderBackground(game.batch, delta);
        game.batch.end();

        // Gameplay Elements
        game.batch.setProjectionMatrix(game.gameCam.combined);
        game.batch.begin();
        renderGameplay(game.batch, delta);
        game.batch.end();

        // User Interface
        game.batch.setProjectionMatrix(game.hudCam.combined);
        game.batch.begin();
        renderHud(game.batch, delta);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {

    }


    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
