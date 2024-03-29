package com.sojourn.game.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Display
{
    public static final int WIDTH = 1920;//480*2;
    public static final int HEIGHT = 1080;//270*2;

    private static SpriteBatch batch;
    private static Text text;
    private static Camera camera;

    public Display()
    {
        batch = new SpriteBatch();
        batch.enableBlending(); // Enable alpha
        text = new Text();
        camera = new Camera();
        Shape.init();
    }

    public static Camera getCamera()
    {
        return camera;
    }

    public void update(float delta)
    {
        camera.update();

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

    public static void beginBatchGameplay() {
        batch.setProjectionMatrix(camera.getGameCamera().combined);
        batch.begin();
    }

    public static void beginBatchHUD() {
        batch.setProjectionMatrix(camera.getHudCamera().combined);
        batch.begin();
    }

    public static void beginBatchBackground() {
        batch.setProjectionMatrix(camera.getBackgroundCamera().combined);
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

    public static void drawCentered(Texture t, float x, float y, float w, float h) {
        draw(t, x-w/2, y-h/2, w, h);
    }

    public static void drawCentered(Texture t, Color c, float x, float y, float w, float h) {
        draw(t, c, x-w/2, y-h/2, w, h);
    }





    public static void endBatch() {
        batch.end();
    }


    public void dispose()
    {
        batch.dispose();
        text.dispose();
    }

    public void resize(int width, int height)
    {
        camera.resize(width, height);

    }
}
