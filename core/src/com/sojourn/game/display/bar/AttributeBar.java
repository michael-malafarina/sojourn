package com.sojourn.game.display.bar;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.sojourn.game.Textures;
import com.sojourn.game.display.Alignment;
import com.sojourn.game.display.Display;
import com.sojourn.game.display.Text;
import com.sojourn.game.entity.AttributePool;

public class AttributeBar
{
    protected AttributePool attribute;
    private Texture texture;
    private Color colorDark;
    private Color colorLight;

    AttributeBar(AttributePool attribute, Color color)
    {
        this.attribute = attribute;
        this.texture = Textures.uiBar;

        this.colorLight = color;
        this.colorDark = new Color(color);

        final float GAP = .2f;
        colorDark.mul(GAP, GAP, GAP, 0);

    }

    public void render(float x, float y, float w, float h, BitmapFont font)
    {
        int edge = Math.round(h / 4.0f);

        Display.draw(texture, Color.DARK_GRAY, x-edge, y-edge, w+edge*2, h+edge*2);
        Display.draw(texture, colorDark, x, y, w , h);
        Display.draw(texture, new Color(1, 1, .75f, 1), x, y, w * attribute.getPercent() + w * attribute.getRecentDamagePercent() , h);
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

    public void render(Rectangle box)
    {
        render(box.x, box.y, box.width, box.height);
    }
}
