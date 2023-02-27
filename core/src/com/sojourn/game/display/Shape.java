package com.sojourn.game.display;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Shape
{
    private static ShapeRenderer shapeRenderer;

    public static void init()
    {
        shapeRenderer = new ShapeRenderer();
        Shape.getRenderer().setAutoShapeType(true);           // may cause performance issues if used carelessly!
    }

    public static void beginShapeGameplay()
    {
        shapeRenderer.setProjectionMatrix(Display.getCamera().getGameCamera().combined);
        shapeRenderer.begin();
    }

    public static void beginShapeHud()
    {
        shapeRenderer.setProjectionMatrix(Display.getCamera().getHudCamera().combined);
        shapeRenderer.begin();
    }

    public static void endShape() {
        shapeRenderer.end();
    }

    public static ShapeRenderer getRenderer()
    {
        return shapeRenderer;
    }
}
