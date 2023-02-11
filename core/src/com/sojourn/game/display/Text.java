package com.sojourn.game.display;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;


public class Text
{
    private final Fonts fonts;
    private static BitmapFont currentFont;
    private static Alignment alignHorizontal;
    private static Alignment alignVertical;

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
        currentFont.draw(Display.getBatch(), text, x - getHorizontalOffset(text), y + getVerticalOffset(text));
    }

    private static float getHorizontalOffset(String text)
    {
        GlyphLayout glyphLayout = new GlyphLayout(currentFont, text);

        if(alignHorizontal == Alignment.RIGHT)
        {
            return glyphLayout.width;
        }
        if(alignHorizontal == Alignment.CENTER)
        {
            return glyphLayout.width/2;
        }
        else
        {
            return 0;
        }
    }

    // VERTICAL ALIGNMENT ISN'T RIGHT MEBBE --> TEST IT!

    private static float getVerticalOffset(String text)
    {
        GlyphLayout glyphLayout = new GlyphLayout(currentFont, text);

        if(alignVertical == Alignment.TOP)
        {
            return glyphLayout.height;
        }
        if(alignVertical == Alignment.CENTER)
        {
            return glyphLayout.height/2;
        }
        else
        {
            return 0;
        }
    }

    public static void setAlignment(Alignment horizontal, Alignment vertical)
    {
        alignHorizontal = horizontal;
        alignVertical = vertical;
    }

    public void dispose()
    {
        fonts.dispose();
    }

}
