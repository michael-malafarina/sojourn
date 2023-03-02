package com.sojourn.game.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Fonts
{
    public static final BitmapFont title = createFont("TCM", 28, Color.WHITE, Color.BLACK, 1);
    public static final BitmapFont small = createFont("TCM", 14, Color.WHITE, Color.BLACK, 0);
    public static final BitmapFont floatText = createFont("TCM", 36, Color.WHITE, Color.DARK_GRAY, 1);

    public static BitmapFont createFont(String name, int size, Color color, Color borderColor, int borderWidth)
    {
        // Specify Font Parameters
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(name + ".ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = (int) (size * Display.getAverageRatio());
        fontParameter.borderWidth = borderWidth;
        fontParameter.borderColor = borderColor;
        fontParameter.color = color;

        // Create and return the font object.  Compensate for scaling.
        BitmapFont f = fontGenerator.generateFont(fontParameter);
        f.getData().setScale(1.0f/Display.getWidthRatio(), 1.0f / Display.getHeightRatio());
        f.setUseIntegerPositions(false);
        f.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        return f;
    }

    public void dispose()
    {
        small.dispose();
        title.dispose();
    }
}
