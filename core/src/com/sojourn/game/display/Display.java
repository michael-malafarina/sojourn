package com.sojourn.game.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.sojourn.game.Sojourn;

public class Display
{
    private static SpriteBatch batch;
    private final Text text;

    public Display()
    {
        batch = new SpriteBatch();
        text = new Text();
    }


    public static float getAverageRatio()
    {
        return ((float) Gdx.graphics.getDisplayMode().width / (float) Sojourn.WIDTH +
                (float) Gdx.graphics.getDisplayMode().height / (float) Sojourn.HEIGHT)  / 2.0f;
    }

    public static float getWidthRatio()
    {
        return (float) Gdx.graphics.getDisplayMode().width / (float) Sojourn.WIDTH;
    }

    public static float getHeightRatio()
    {
        return (float) Gdx.graphics.getDisplayMode().height / (float) Sojourn.HEIGHT;
    }

    public static void begin()
    {
        batch.begin();
    }

    public static SpriteBatch getBatch()
    {
        return batch;
    }

    public static void draw(Texture t, float x, float y)
    {
        batch.draw(t, x, y);
    }



    public static void end()
    {
        batch.end();
    }

    public static void setProjectionMatrix(Matrix4 projectionMatrix)
    {
        batch.setProjectionMatrix(projectionMatrix);
    }

    public void dispose()
    {
        batch.dispose();
        text.dispose();
    }
}
