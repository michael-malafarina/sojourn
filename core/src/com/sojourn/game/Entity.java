package com.sojourn.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Entity
{
    float x;
    float y;
    Texture image;

    Entity()
    {
        image = new Texture(Gdx.files.internal("player_ship.png"));
        y = Sojourn.HEIGHT;
    }

    public void update()
    {
        x++;

        if(x > Sojourn.WIDTH)
        {
            x = 0;
        }
    }

    public void render(Batch b)
    {
        b.draw(image, x, y);
    }
}
