package com.sojourn.game.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.unit.Unit;

import java.util.List;

public class Camera
{
    private OrthographicCamera gameCam;
    private OrthographicCamera hudCam;
    private OrthographicCamera backgroundCam;

    private Viewport gamePort;
    private Viewport hudPort;
    private Viewport backgroundPort;

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

    public OrthographicCamera getGameCamera()
    {
        return gameCam;
    }
    public OrthographicCamera getHudCamera()
    {
        return hudCam;
    }

    public OrthographicCamera getBackgroundCamera()
    {
        return backgroundCam;
    }

//    public Viewport getGamePort()
//    {
//        return gamePort;
//    }
//
//    public Viewport getHudPort()
//    {
//        return hudPort;
//    }

    public void update()
    {
        controlCamera();
        updateZoom();
    }

    public void setupCamera()
    {

        // Create the camera and port for GAMEPLAY objects
        backgroundCam = new OrthographicCamera(Display.WIDTH, Display.HEIGHT);
        backgroundPort = new StretchViewport(Display.WIDTH, Display.HEIGHT, backgroundCam);
        backgroundCam.setToOrtho(false);
        backgroundCam.position.set(0, 0, 0);


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

    public void controlCamera()
    {
        controlCameraKeyboard();
        List<Unit> units = EntityManager.getUnits();
        if(units != null && units.stream().filter(com.sojourn.game.entity.Entity::isSelected).toList().isEmpty())
        {
            controlCameraMouse();
        }
    }

    public void controlCameraKeyboard()
    {
        int translateSpeed = 50;
        int backgroundSpeed = translateSpeed / 5;

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            gameCam.translate(-translateSpeed, 0, 0);
            backgroundCam.translate(-backgroundSpeed, 0, 0);

        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            gameCam.translate(translateSpeed, 0, 0);
            backgroundCam.translate(backgroundSpeed, 0, 0);


        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            gameCam.translate(0, -translateSpeed, 0);
            backgroundCam.translate(0, -backgroundSpeed, 0);

        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            gameCam.translate(0, translateSpeed, 0);
            backgroundCam.translate(0, backgroundSpeed, 0);

        }


        gameCam.update();
        backgroundCam.update();
    }

    public void controlCameraMouse()
    {
        final float SCALE = 4;
        if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT))
        {
            gameCam.translate(-Gdx.input.getDeltaX() * SCALE, Gdx.input.getDeltaY() * SCALE, 0);
            backgroundCam.translate(-Gdx.input.getDeltaX() * SCALE/5, Gdx.input.getDeltaY() * SCALE/2, 0);

            gameCam.update();
            backgroundCam.update();

        }

    }

    public void updateZoom()
    {
        if(gameCam.zoom < targetZoom)
        {
            gameCam.zoom = Math.min(gameCam.zoom + ZOOM_RATE, targetZoom);
            backgroundCam.zoom = Math.min(backgroundCam.zoom + ZOOM_RATE, targetZoom);

        }

        if(gameCam.zoom > targetZoom)
        {
            gameCam.zoom = Math.max(gameCam.zoom - ZOOM_RATE, targetZoom);
            backgroundCam.zoom = Math.max(backgroundCam.zoom - ZOOM_RATE, targetZoom);
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
        backgroundPort.update(width, height);

    }
}
