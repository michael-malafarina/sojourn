package com.sojourn.game.entity.images;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.sojourn.game.display.HealthBar;
import com.sojourn.game.faction.Squad;

public class SquadImage
{

    private Texture sheet;
    private Squad owner;
    private HealthBar healthbar;
    Vector2 position;

    public SquadImage(Squad owner)
    {
        this.owner = owner;
        position = new Vector2(0, 0);
        healthbar = new HealthBar(owner.getHealth());
    }

    public SquadImage(Squad owner, Texture sheet)
    {
        this(owner);
        setSpriteSheet(sheet);
    }

    private void setSpriteSheet(Texture sheet)
    {
        if(sheet != null) {
            this.sheet = sheet;
            sheet.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        }
    }

    public void update()
    {
        position.set(owner.getCenter());
        healthbar = new HealthBar(owner.getHealth());
    }


    public void render()
    {
//        healthbar.render(position.x-16, position.y + 10, 32, 6);
    }

    public void renderShape()
    {
//        int size = 16;
//        Shape.getRenderer().set(ShapeRenderer.ShapeType.Filled);
//        Shape.getRenderer().setColor(owner.getTeam().getFaction().getColor(0));
//        Shape.getRenderer().rect(position.x-size/2, position.y-size/2, size, size);
//        Shape.getRenderer().set(ShapeRenderer.ShapeType.Line);
//        Shape.getRenderer().setColor(owner.getTeam().getFaction().getColor(1));
//        Shape.getRenderer().rect(position.x-size/2, position.y-size/2, size, size);
    }


}
