package com.sojourn.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.sojourn.game.Sojourn;
import com.sojourn.game.display.Display;

abstract public class State implements Screen {

    final Sojourn game;

    public State(final Sojourn game)
    {
        this.game = game;
    }

    abstract protected void update(float delta);

    abstract protected void renderBackground(float delta);

    abstract protected void renderGameplay(float delta);
    abstract protected void renderHud(float delta);

    @Override
    public void render(float delta)
    {
        update(delta);

        Gdx.gl.glClearColor(0f, 0f, 0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderBackgroundLayer(delta);
        renderGameplayLayer(delta);
        renderHudLayer(delta);
    }

    private void renderBackgroundLayer(float delta)
    {
        Display.begin();
        renderBackground(delta);
        Display.end();
    }

    private void renderGameplayLayer(float delta)
    {
        Display.setProjectionMatrix(game.gameCam.combined);
        Display.begin();
        renderGameplay(delta);
        Display.end();
    }

    private void renderHudLayer(float delta)
    {
        Display.setProjectionMatrix(game.hudCam.combined);
        Display.begin();
        renderHud(delta);
        Display.end();
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
