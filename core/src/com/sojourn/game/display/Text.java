package com.sojourn.game.display;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;


public class Text
{
    private final Fonts fonts;
    private static BitmapFont currentFont;
    private static Alignment alignHorizontal;
    private static Alignment alignVertical;

    Text() {
        fonts = new Fonts();
        setFont(fonts.small);
    }

    public static void setFont(BitmapFont f) {
        currentFont = f;
    }

    public static void draw(String text, float x, float y) {
        currentFont.draw(Display.getBatch(), text, x - getHorizontalOffset(text), y + getVerticalOffset(text));
    }

    private static float getHorizontalOffset(String text) {

        GlyphLayout glyphLayout = new GlyphLayout(currentFont, text);

        return switch(alignHorizontal)
        {
            case RIGHT -> glyphLayout.width;
            case CENTER -> glyphLayout.width/2;
            default -> 0;
        };
    }

    private static float getVerticalOffset(String text)  {

        GlyphLayout glyphLayout = new GlyphLayout(currentFont, text);

        return switch(alignVertical)
        {
            case TOP -> glyphLayout.height;
            case CENTER -> glyphLayout.height/2;
            default -> 0;
        };
    }

    public static void setAlignment(Alignment horizontal, Alignment vertical)  {
        alignHorizontal = horizontal;
        alignVertical = vertical;
    }

    public void dispose() {
        fonts.dispose();
    }

}
