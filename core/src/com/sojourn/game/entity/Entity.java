package com.sojourn.game.entity;


import com.sojourn.game.display.Display;
import com.sojourn.game.entity.images.Image;
import com.sojourn.game.faction.Faction;
import com.sojourn.game.faction.PlayerFaction;

import java.awt.*;

abstract public class Entity
{
    private Faction faction;
    private com.sojourn.game.entity.images.Image image;
    private Rectangle box;

    abstract public int getNumLayers();

    public Entity()
    {
        faction = new PlayerFaction();
        image = new Image(this);
        box = new Rectangle(0, 0, 32, 32);
    }

    public int getX() {
        return box.x;
    }

    public int getY() {
        return box.y;
    }
    public int getWidth() {
        return box.width;
    }

    public int getHeight() {
        return box.height;
    }

    public Faction getFaction()
    {
        return faction;
    }

    public Rectangle getRectangle()
    {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    public void update(float delta)
    {
        box.y = Display.HEIGHT/2;
        box.x += delta * 50;

        if(box.x > Display.WIDTH)
        {
            box.x = 0;
        }
    }

    public void render()
    {
        image.render();
    }
}
