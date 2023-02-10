package com.sojourn.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.sojourn.game.Sojourn;
import com.sojourn.game.display.Display;

public class Entity
{
    private float x;
    private float y;
    private Texture image;

    public Entity()
    {
        image = new Texture(Gdx.files.internal("droplet.png"));
        y = Sojourn.HEIGHT/2;
        image.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
    }

    public void update(float delta)
    {
//        System.out.println("Updating Ship Entity");

        x  += delta * 50;

        if(x > Sojourn.WIDTH)
        {
            x = 0;
        }
    }

    public void render()
    {
        Display.draw(image, x, y);
    }
}
