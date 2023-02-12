package com.sojourn.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sojourn.game.display.Display;

import java.awt.*;

public class Entity
{
    private Rectangle box;
    private Texture sheet;
    private TextureRegion[] imageLayers;
    private TextureRegion image;
    private Color color;

    public Entity()
    {

        sheet = new Texture(Gdx.files.internal("assault0.png"));
        sheet.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        loadImage(5);
        box = new Rectangle(0, 0, image.getRegionWidth(), image.getRegionHeight());

        color = Color.WHITE;


//        imageLayers = new TextureRegion[5];
//        imageLayers[0] = new TextureRegion(sheet, 72, 0, 72, 72 );

    }

    public void loadImage(int numLayers)
    {
        imageLayers = new TextureRegion[numLayers];
        int width = sheet.getWidth() / numLayers;

        for(int i = 0; i < numLayers; i++)
        {
            imageLayers[i] = new TextureRegion(sheet, i * width, 0, width, sheet.getHeight() );
        }

        image = imageLayers[0];
    }

    public void update(float delta)
    {
//        System.out.println("Updating Ship Entity");

        box.y = Display.HEIGHT/2;
        box.x += delta * 50;

        if(box.x > Display.WIDTH)
        {
            box.x = 0;
        }
    }

    public void render()
    {
        Display.draw(image, color, box.x, box.y, box.width, box.height);
    }
}
