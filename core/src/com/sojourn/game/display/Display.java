package com.sojourn.game.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Display
{
    public static final int WIDTH = 480*2;
    public static final int HEIGHT = 270*2;
    private static final float ZOOM_RATE = .1f;


    public static final float ZOOM_DISTANT = 4f;
    public static final float ZOOM_MEDIUM = 2f;
    public static final float ZOOM_CLOSE = 1;


    private static SpriteBatch batch;
    private static Text text;
    private static Shape shape;

    private static Viewport gamePort;
    private static OrthographicCamera gameCam;

    private static Viewport hudPort;
    private static OrthographicCamera hudCam;


    private static float targetZoom;

    public Display()
    {
        batch = new SpriteBatch();
        text = new Text();
        shape = new Shape();
        targetZoom = 1;


        setupCamera();
    }

    public static OrthographicCamera getGameCam()
    {
        return gameCam;
    }

    public void update()
    {
        controlCamera();
        updateZoom();
    }


    public void setupCamera() {

        // Create the camera and port for GAMEPLAY objects
        gameCam = new OrthographicCamera(WIDTH, HEIGHT);
        gamePort = new StretchViewport(WIDTH, HEIGHT, gameCam);
        gameCam.setToOrtho(false);
        gameCam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);

        // Create the camera and port for USER INTERFACE objects
        hudCam = new OrthographicCamera(WIDTH, HEIGHT);
        hudPort = new StretchViewport(WIDTH, HEIGHT, hudCam);
        hudCam.setToOrtho(false);
        hudCam.position.set(hudPort.getWorldWidth()/2, hudPort.getWorldHeight()/2, 0);

        // Need to wait to go to fullscreen until after start to make scaling work
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

    }

    public void controlCamera() {
        int translateSpeed = 50;

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            gameCam.translate(-translateSpeed, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            gameCam.translate(translateSpeed, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            gameCam.translate(0, -translateSpeed, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            gameCam.translate(0, translateSpeed, 0);
        }


        gameCam.update();
    }

    public void updateZoom()
    {
        if(gameCam.zoom < targetZoom)
        {
            gameCam.zoom = Math.min(gameCam.zoom + ZOOM_RATE, targetZoom);
        }

        if(gameCam.zoom > targetZoom)
        {
            gameCam.zoom = Math.max(gameCam.zoom - ZOOM_RATE, targetZoom);
        }
    }

    public static void cameraZoom(float amount)
    {
        // Increase by a zoom level
        if(amount < 0)
        {
            if(targetZoom == ZOOM_MEDIUM)
            {
                targetZoom = ZOOM_CLOSE;
            }
            else if(targetZoom == ZOOM_DISTANT)
            {
                targetZoom = ZOOM_MEDIUM;
            }
        }

        // Decrease by a zoom level
        else if(amount > 0)
        {
            if(targetZoom == ZOOM_CLOSE)
            {
                targetZoom = ZOOM_MEDIUM;
            }
            else if(targetZoom == ZOOM_MEDIUM)
            {
                targetZoom = ZOOM_DISTANT;
            }
        }

//        gameCam.zoom = MathUtils.lerp(gameCam.zoom,targetZoom,amount/20);
    }

    public static float getAverageRatio() {
        return ((float) Gdx.graphics.getDisplayMode().width / (float) WIDTH +
                (float) Gdx.graphics.getDisplayMode().height / (float) HEIGHT)  / 2.0f;
    }

    public static float getWidthRatio() {
        return (float) Gdx.graphics.getDisplayMode().width / (float) WIDTH;
    }

    public static float getHeightRatio() {
        return (float) Gdx.graphics.getDisplayMode().height / (float) HEIGHT;
    }

    public static float getMouseX() {
        return Gdx.input.getX() / getWidthRatio();
    }

    public static float getMouseY() {
        return HEIGHT - Gdx.input.getY() / getHeightRatio();
    }

    public static void beginBatchGameplay() {
        batch.setProjectionMatrix(gameCam.combined);
        batch.begin();
    }

    public static void beginBatchHUD() {
        batch.setProjectionMatrix(hudCam.combined);
        batch.begin();
    }

    public static SpriteBatch getBatch() {
        return batch;
    }

    public static void draw(Texture t, float x, float y) {
        batch.draw(t, x, y);
    }

    public static void draw(TextureRegion t, float x, float y) {
        batch.draw(t, x, y);
    }

    public static void draw(Texture t, float x, float y, float w, float h) {
        batch.draw(t, x, y, w, h);
    }

    public static void draw(TextureRegion t, float x, float y, float w, float h) {
        batch.draw(t, x, y, w, h);
    }

    public static void draw(Texture t, Color c, float x, float y, float w, float h) {
        batch.setColor(c);
        batch.draw(t, x, y, w, h);
        batch.setColor(Color.WHITE);
    }

    public static void draw(TextureRegion t, Color c, float x, float y, float w, float h, float theta) {
        batch.setColor(c);

        batch.draw(t, x, y, w/2, h/2, w, h, 1, 1, theta);

        batch.setColor(Color.WHITE);
    }




    public static void endBatch() {
        batch.end();
    }

    public static Camera getHUDCamera() {
        return hudCam;
    }

    public void dispose()
    {
        batch.dispose();
        text.dispose();
    }

    public void resize(int width, int height)
    {
        gamePort.update(width, height);
        hudPort.update(width, height);
    }
}
