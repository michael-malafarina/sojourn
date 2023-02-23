package com.sojourn.game.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Camera
{
    public static OrthographicCamera gameCam;
    public static OrthographicCamera hudCam;
    private static Viewport gamePort;
    private static Viewport hudPort;

    private static final float ZOOM_RATE = .1f;
    private static float targetZoom;


    public static final float ZOOM_DISTANT = 3f;
    public static final float ZOOM_MEDIUM = 2f;
    public static final float ZOOM_CLOSE = 1f;

    public Camera()
    {
        targetZoom = ZOOM_MEDIUM;
        setupCamera();
    }

    public void update()
    {
        controlCamera();
        updateZoom();
    }

    public void setupCamera()
    {

    // Create the camera and port for GAMEPLAY objects
    gameCam = new OrthographicCamera(Display.WIDTH, Display.HEIGHT);
    gamePort = new StretchViewport(Display.WIDTH, Display.HEIGHT, gameCam);
    gameCam.setToOrtho(false);
    gameCam.position.set(0, 0, 0);

    // Create the camera and port for USER INTERFACE objects
    hudCam = new OrthographicCamera(Display.WIDTH, Display.HEIGHT);
    hudPort = new StretchViewport(Display.WIDTH, Display.HEIGHT, hudCam);
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

    public void resize(int width, int height)
    {
        gamePort.update(width, height);
        hudPort.update(width, height);
    }
}
