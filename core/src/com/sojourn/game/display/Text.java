package com.sojourn.game.display;

import com.badlogic.gdx.graphics.g2d.BitmapFont;


public class Text
{
    private final Fonts fonts;
    private static BitmapFont currentFont;

    Text()
    {
        fonts = new Fonts();
        setFont(fonts.small);
    }

    public static void setFont(BitmapFont f)
    {
        currentFont = f;
    }

    public static void draw(String text, float x, float y)
    {
        currentFont.draw(Display.getBatch(), text, x, y);
    }

    public void dispose()
    {
        fonts.dispose();
    }
}
