package com.sojourn.game.entity.images;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sojourn.game.Settings;
import com.sojourn.game.display.HealthBar;
import com.sojourn.game.display.Shape;
import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.unit.civilian.Civilian;
import com.sojourn.game.entity.unit.ship.Ship;

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
    }

    public void setAttributes()
    {
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

    public void hideLayer(int layer)
    {
        imageLayers.get(layer).hide();
    }

    public void render()
    {
        if(imageLayers == null)
        {
            return;
        }

        imageLayers.forEach(n -> n.render(owner.getX(), owner.getY(), owner.getWidth(), owner.getHeight(), owner.getTheta()));


//        imageLayers.forEach(n -> Display.draw(n.getTexture(), n.getColor(), owner.getX(), owner.getY(), owner.getWidth(), owner.getHeight(), owner.getTheta()));

        if(healthbar != null && ((owner instanceof Ship && Settings.showUnitHealthbars) || (owner instanceof Civilian && Settings.showCivilianHealthbars)))
        {
            healthbar.render(owner.getX(), owner.getY() + owner.getHeight() + 2, owner.getWidth(), 2);
        }

    }

    public void renderShapes()
    {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        if(owner.isSelected())
        {
            final int SPACE = 10;
            Shape.getRenderer().setColor(new Color(200, 200, 200, .8f));
            Shape.getRenderer().set(ShapeRenderer.ShapeType.Line);
            Shape.getRenderer().ellipse(owner.getX() - SPACE/2, owner.getY() - SPACE/2,
                    owner.getWidth() + SPACE, owner.getHeight() + SPACE);
        }
    }


}
