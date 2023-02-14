package com.sojourn.game.state;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.sojourn.game.Sojourn;
import com.sojourn.game.Utility;
import com.sojourn.game.display.Display;
import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.TestUnit;
import com.sojourn.game.entity.Unit;

import java.util.ArrayList;

public class StateGameplay extends State
{
    ArrayList<Entity> entities;

    public StateGameplay(final Sojourn game)
    {
        super(game);

        entities = new ArrayList<>();

        for(int i = 0; i < 1; i++)
        {
            Unit u = new TestUnit();
            u.setPosition(0, Utility.random(Display.HEIGHT));
            entities.add(u);
        }
    }

    @Override
    public void update(float delta)
    {
        super.update(delta);
        entities.forEach((n)->n.update(delta));
    }

    @Override
    protected void renderBackground(float delta) {
        ScreenUtils.clear(.02f, .02f, .02f, 1);
    }

    @Override
    protected void renderGameplay(float delta)
    {
        entities.forEach((n)->n.render());
    }

    protected void renderGameplayShapes(float delta)
    {
        entities.forEach((n)->n.renderShapes());
    }


    @Override
    protected void renderHud(float delta)    {
        super.renderHud(delta);
    }

    public String toString()    {
        return "Gameplay";
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        Vector3 mouseRaw = new Vector3(screenX, screenY, 0);
        Vector3 mouseProjected = Display.getGameCam().unproject(mouseRaw);

        // Determine which units are selected with this event
        for(Entity e : entities)
        {
            if(button == Input.Buttons.LEFT)
            {
                // Clear selections on all entities
                e.unselect();

                // If I have left-clicked on an entity, let it know and it will determine if it can be selected
                if (e.getRectangle().contains(mouseProjected.x, mouseProjected.y)) {
                    e.clicked();
                    return true;
                }
            }

        }

        // Issue orders
        for(Entity e : entities)
        {
            // If I have right-clicked on a location, move selected units there
            if(button == Input.Buttons.RIGHT && e.isSelected() && e instanceof Unit)
            {
                ((Unit) e).setDestination(mouseProjected.x, mouseProjected.y);
                return true;
            }

        }



        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY)
    {
        super.scrolled(amountX, amountY);
        Display.cameraZoom(amountY);
        return false;
    }

}
