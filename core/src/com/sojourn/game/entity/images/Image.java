package com.sojourn.game.entity.images;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sojourn.game.display.Display;
import com.sojourn.game.entity.Entity;

import java.util.ArrayList;

public class Image
{
    private ArrayList<ImageLayer> imageLayers;

    private Texture sheet;
    private Entity owner;

    public Image(Entity owner)
    {
        this.owner = owner;
        sheet = new Texture(Gdx.files.internal("assault0.png"));
        sheet.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        loadImage();
    }

    public void loadImage()
    {
        imageLayers = new ArrayList<>();
        int width = sheet.getWidth() / owner.getNumLayers();

        for(int i = 0; i < owner.getNumLayers(); i++)
        {
            imageLayers.add(new ImageLayer(new TextureRegion(sheet, i * width, 0, width, sheet.getHeight()), owner.getFaction().getColor(i)));
        }
    }

    public void render()
    {
        imageLayers.forEach(n -> Display.draw(n.getTexture(), n.getColor(), owner.getX(), owner.getY(), owner.getWidth(), owner.getHeight(), owner.getTheta()));
    }
}
