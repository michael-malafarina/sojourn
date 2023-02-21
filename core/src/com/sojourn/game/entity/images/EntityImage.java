package com.sojourn.game.entity.images;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sojourn.game.display.Display;
import com.sojourn.game.display.HealthBar;
import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.unit.civilian.Civilian;

import java.util.ArrayList;

public class EntityImage
{
    private ArrayList<EntityImageLayer> imageLayers;

    private Texture sheet;
    private Entity owner;
    private HealthBar healthbar;

    public EntityImage(Entity owner)
    {
        this.owner = owner;
    }

    public EntityImage(Entity owner, Texture sheet)
    {
        this(owner);
        setSpriteSheet(sheet);
        loadImage();
        healthbar = new HealthBar(owner.getHealth());

    }

    private void setSpriteSheet(Texture sheet)
    {
        if(sheet != null) {
            this.sheet = sheet;
            sheet.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        }
    }

    private void loadImage()
    {
        if(sheet == null)
        {
            return;
        }

        imageLayers = new ArrayList<>();
        int width = sheet.getWidth() / owner.getNumLayers();

        for(int i = 0; i < owner.getNumLayers(); i++)
        {
            Color color =  owner.getTeam().getFaction().getColor(i);

            if(owner instanceof Civilian)
            {
                color = owner.getTeam().getFaction().getColorAlternate(i);
            }

            imageLayers.add(new EntityImageLayer(new TextureRegion(sheet, i * width, 0, width, sheet.getHeight()),color));
        }
    }

    public void render()
    {
        if(imageLayers == null)
        {
            return;
        }

        imageLayers.forEach(n -> Display.draw(n.getTexture(), n.getColor(), owner.getX(), owner.getY(), owner.getWidth(), owner.getHeight(), owner.getTheta()));

        if(!(owner instanceof Civilian))
        {
            healthbar.render(owner.getX(), owner.getY() + owner.getHeight() + 4, owner.getWidth(), 4);
        }

    }


}
