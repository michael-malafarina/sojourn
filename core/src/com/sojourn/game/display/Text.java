package com.sojourn.game.display;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;


public class Text
{
    private final Fonts fonts;
    private static BitmapFont font;
    private static Alignment alignHorizontal;
    private static Alignment alignVertical;
    private static Color color;

    Text() {
        fonts = new Fonts();
        color = Color.WHITE;
        setFont(fonts.small);
    }

    public static void setFont(BitmapFont font) {
        Text.font = font;
    }

    public static void setColor(Color color) {
        Text.color = new Color(color);
    }

    public static void setAlpha(float alpha) {
        Text.color = new Color(color.r, color.g, color.b, alpha);
    }

    public static void resetAlpha() {
        Text.color = new Color(color.r, color.g, color.b, 1);
    }

    public static void resetColor() {
        Text.color = Color.WHITE;
    }


    public static void draw(String text, float x, float y)
    {
        font.setColor(color);
        font.draw(Display.getBatch(), text, x - getHorizontalOffset(text), y + getVerticalOffset(text));
    }

    private static float getHorizontalOffset(String text) {

        GlyphLayout glyphLayout = new GlyphLayout(font, text);

        return switch(alignHorizontal)
        {
            case RIGHT -> glyphLayout.width;
            case CENTER -> glyphLayout.width/2;
            default -> 0;
        };
    }

    private static float getVerticalOffset(String text)  {

        GlyphLayout glyphLayout = new GlyphLayout(font, text);

        return switch(alignVertical)
        {
            case BOTTOM -> glyphLayout.height;
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
