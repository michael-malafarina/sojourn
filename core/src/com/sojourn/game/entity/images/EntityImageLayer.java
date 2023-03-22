package com.sojourn.game.entity.images;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sojourn.game.display.Display;

public class EntityImageLayer
{
    private TextureRegion texture;
    private Color color;
    private boolean hidden;

    EntityImageLayer(TextureRegion texture)    {
        this.texture = texture;
    }

    EntityImageLayer(TextureRegion texture, Color color)    {
        this.texture = texture;
        this.color = color;
    }

    public void setAlpha(float alpha)
    {
        this.color = new Color(color.r, color.g, color.b, alpha);
    }

    public void hide()   {
        this.hidden = true;
    }

    public void setTexture(TextureRegion texture)    {
        this.texture = texture;
    }

    public void setColor(Color color)    {
        this.color = color;
    }

    public TextureRegion getTexture()    {
        return texture;
    }

    public Color getColor()    {
        return color;
    }

    public int getWidth()    {
        return texture.getRegionWidth();
    }

    public int getHeight()    {
        return texture.getRegionHeight();
    }

    public void render(float x, float y, float w, float h, float theta)
    {
        if(!hidden) {
            Display.draw(getTexture(), getColor(), x, y, w, h, theta);
        }
    }




}
