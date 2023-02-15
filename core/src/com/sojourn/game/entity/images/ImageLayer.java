package com.sojourn.game.entity.images;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ImageLayer
{
    TextureRegion texture;
    Color color;

    ImageLayer(TextureRegion texture)    {
        this.texture = texture;
    }

    ImageLayer(TextureRegion texture, Color color)    {
        this.texture = texture;
        this.color = color;
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

}