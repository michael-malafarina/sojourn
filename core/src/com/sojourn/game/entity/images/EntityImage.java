package com.sojourn.game.entity.images;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sojourn.game.display.Display;
import com.sojourn.game.entity.Entity;

import java.util.ArrayList;

public class EntityImage
{
    private ArrayList<EntityImageLayer> imageLayers;

    private Texture sheet;
    private Entity owner;

    public EntityImage(Entity owner)
    {
        this.owner = owner;
    }

    public EntityImage(Entity owner, Texture sheet)
    {
        this(owner);
        setSpriteSheet(sheet);
        loadImage();

    }

    private void setSpriteSheet(Texture sheet)
    {
        this.sheet = sheet;
        sheet.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
    }

    private void loadImage()
    {
        imageLayers = new ArrayList<>();
        int width = sheet.getWidth() / owner.getNumLayers();

        for(int i = 0; i < owner.getNumLayers(); i++)
        {
            imageLayers.add(new EntityImageLayer(new TextureRegion(sheet, i * width, 0, width, sheet.getHeight()), owner.getFaction().getColor(i)));
        }
    }

    public void render()
    {
        imageLayers.forEach(n -> Display.draw(n.getTexture(), n.getColor(), owner.getX(), owner.getY(), owner.getWidth(), owner.getHeight(), owner.getTheta()));
    }
}
