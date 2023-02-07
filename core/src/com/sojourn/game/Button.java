package com.sojourn.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Button
{
    Texture image;
    private float x;
    private float y;

    public Button()
    {
        image = new Texture(Gdx.files.internal("droplet.png"));

        System.out.println("Making a mal button");

        x = 0;
        y = 0;
        System.out.println(x + ", " + y);

    }

    public void setPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public void render(Batch b)
    {
        b.draw(image, x, y, 100, 100);
    }



}