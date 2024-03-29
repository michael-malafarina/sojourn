package com.sojourn.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.sojourn.game.Sojourn;
import com.sojourn.game.button.Button;
import com.sojourn.game.display.*;
import com.sojourn.game.faction.TeamEnemy;
import com.sojourn.game.faction.TeamPlayer;

import java.util.ArrayList;
import java.util.List;

abstract public class State implements Screen, InputProcessor
{
    private List<Button> buttons;
    protected final Sojourn game;
    protected TeamPlayer player;
    protected TeamEnemy currentEnemy;

    public State(final Sojourn game) {
        this.game = game;
        buttons = new ArrayList<>();

        player = Sojourn.player;
        currentEnemy = Sojourn.currentEnemy;
    }

    public TeamPlayer getPlayer()
    {
        return player;
    }

    public TeamEnemy getEnemy()
    {
        return currentEnemy;
    }

    protected void update(float delta)
    {
        buttons.forEach(Button::update);
    }

    abstract protected void renderBackground(float delta);
    abstract protected void renderGameplay(float delta);
    abstract protected void renderGameplayShapes();
    abstract protected void renderHudShapes();

    protected void renderHud(float delta)
    {
        Text.setFont(Fonts.small);
        Text.setAlignment(Alignment.LEFT, Alignment.TOP);
        Text.draw(this + " FPS: " + Gdx.graphics.getFramesPerSecond(), 5, Display.HEIGHT-5);

        buttons.forEach(Button::render);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0f, 0f, 0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderBackgroundLayer(delta);
        renderGameplayLayer(delta);
        renderHudLayer(delta);
    }

    private void renderBackgroundLayer(float delta) {
        Display.beginBatchBackground();       // change later for a background layer / parallax
        renderBackground(delta);
        Display.endBatch();
    }

    private void renderGameplayLayer(float delta) {
        Display.beginBatchGameplay();
        renderGameplay(delta);
        Display.endBatch();
        Shape.beginShapeGameplay();
        renderGameplayShapes();
        Shape.endShape();
    }

    private void renderHudLayer(float delta) {
        Display.beginBatchHUD();
        renderHud(delta);
        Display.endBatch();
        Shape.beginShapeHud();
        renderHudShapes();
        Shape.endShape();
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

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        buttons.forEach((n) -> n.touchDown(screenX, screenY, pointer, button));
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        buttons.forEach((n) -> n.mouseMoved(screenX, screenY));
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY)
    {
        return false;
    }

    public void addButton(Button b)
    {
        buttons.add(b);
    }

    public void removeButton(Button b)
    {
        buttons.remove(b);
    }

    public List<Button> getButtons()
    {
        return buttons;
    }
}
