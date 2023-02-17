package com.sojourn.game.display;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.sojourn.game.Textures;
import com.sojourn.game.entity.Attribute;

public class AttributeBar
{
    Attribute attribute;
    Texture texture;
    Color colorDark;
    Color colorLight;

    AttributeBar(Attribute attribute, Color color)
    {
        this.attribute = attribute;
        this.texture = Textures.uiBar;

        this.colorLight = color;
        this.colorDark = new Color(color);

        final float GAP = .6f;
        colorDark.sub(GAP, GAP, GAP, 0);

    }

    public void render(float x, float y, float w, float h, BitmapFont font)
    {

        Display.draw(texture, colorDark, x, y, w , h);
        Display.draw(texture, colorLight, x, y, w * attribute.getPercent(), h);

        if(font != null)
        {
            Text.setFont(font);
            Text.setAlignment(Alignment.LEFT, Alignment.CENTER);
            Text.draw(""+attribute.getCurrent(), x, y + h/2);
        }
    }

    public void render(float x, float y, float w, float h)
    {
        render(x, y, w, h, null);
    }

    public void render(Rectangle box, boolean text)
    {
        render(box.x, box.y, box.width, box.height, null);
    }

    public void render(Rectangle box)
    {
        render(box, false);
    }
}
