package com.sojourn.game.entity.ambient;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sojourn.game.Sojourn;
import com.sojourn.game.display.Shape;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.unit.ship.Ship;

public class EnemyAlert extends Ambient
{
    final float VALUE_SCALING = 1f;
    Texture sheet;
    Class<? extends Ship> clazz;

    public void setClazz(Class<? extends Ship> clazz)
    {
        Ship prototype = (Ship) EntityManager.entityFactory(clazz);
        prototype.setTeam(Sojourn.currentEnemy);
        sheet = prototype.getSpriteSheet();
        this.clazz = clazz;
//
//        box.width = 50;
//        box.height = 50;
    }

    public void startingAttributes()
    {
        Ship prototype = (Ship) EntityManager.entityFactory(clazz);
        prototype.setTeam(Sojourn.currentEnemy);
        setCost(Math.round(prototype.getCost().getValueBase()));

//        box.width = getWidth();
//        box.height = getHeight();
    }

    public Class<? extends Ship> getClazz()
    {
        return clazz;
    }

    public int getNumLayers() {
        return 5;
    }

    @Override
    public int getWidth() {
        return Math.round(getCost().getValue() *  VALUE_SCALING);
    }

    @Override
    public int getHeight() {
        return Math.round(getCost().getValue() * VALUE_SCALING);
    }

    @Override
    public Texture getSpriteSheet() {
        return sheet;
    }

    public void setImage()
    {
        super.setImage();
        getImage().hideLayer(3);
        getImage().hideLayer(4);
    }

    public void upkeep(float delta)
    {
        turnTo(Sojourn.player.getHomePoint());
    }

    public void renderShapes()
    {
        super.renderShapes();

        Shape.getRenderer().set(ShapeRenderer.ShapeType.Line);
        Shape.getRenderer().setColor(getTeam().getFaction().getColor(0));
        Shape.getRenderer().ellipse(getX()-getWidth() * .5f, getY()-getHeight() * .5f, getWidth()*2, getHeight()*2);
    }
}
