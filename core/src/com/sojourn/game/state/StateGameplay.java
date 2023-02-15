package com.sojourn.game.state;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.sojourn.game.Sojourn;
import com.sojourn.game.Utility;
import com.sojourn.game.display.Display;
import com.sojourn.game.display.Shape;
import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.TestUnit;
import com.sojourn.game.entity.Unit;

import java.util.ArrayList;

public class StateGameplay extends State
{
    ArrayList<Entity> entities;
    protected Rectangle selectionBox;
    protected Vector2 selectionBoxOrigin;

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

    protected void renderGameplayShapes()
    {
        entities.forEach((n)->n.renderShapes());

        if(selectionBox != null)
        {
            Shape.getRenderer().set(ShapeRenderer.ShapeType.Line);
            Shape.getRenderer().setColor(Color.WHITE);
            Shape.getRenderer().rect(selectionBox.x, selectionBox.y, selectionBox.getWidth(), selectionBox.getHeight());
        }
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
        boolean returnValue = false;

        // Determine which units are selected with this event

        if (button == Input.Buttons.LEFT)
        {
            for (Entity e : entities) {

                // Clear selections on all entities
                e.unselect();

                // If I have left-clicked on an entity, let it know and it will determine if it can be selected
                if (e.getRectangle().contains(mouseProjected.x, mouseProjected.y)) {
                    e.clicked();
                    return true;
                }
            }

            // Begin drawing box if a unit was not selected
            selectionBoxOrigin = new Vector2(mouseProjected.x, mouseProjected.y);
        }

        // Issue orders
        for(Entity e : entities)
        {
            // If I have right-clicked on a location, move selected units there
            if(button == Input.Buttons.RIGHT && e.isSelected() && e instanceof Unit)
            {
                ((Unit) e).setDestination(mouseProjected.x, mouseProjected.y);
                returnValue = true;
            }

        }

        return returnValue;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        Vector3 mouseRaw = new Vector3(screenX, screenY, 0);
        Vector3 mouse = Display.getGameCam().unproject(mouseRaw);
        Vector2 origin = selectionBoxOrigin;
        boolean returnValue = false;

        // This means the unit selection box was started
        if(origin != null)
        {
            // Clear all entities again, in case box got smaller
            for (Entity e : entities)
            {
                e.unselect();
            }

            // Create the selection box
            selectionBox = new Rectangle();

            // The mouse is to the right of the origin
            if(mouse.x > origin.x)
            {
                selectionBox.x = origin.x;
                selectionBox.width = mouse.x - origin.x;
            }

            // The mouse is to the left of the origin
            else
            {
                selectionBox.x = mouse.x;
                selectionBox.width = origin.x - mouse.x;
            }


            // The mouse is above the origin
            if(mouse.y > origin.y)
            {
                selectionBox.y = origin.y;
                selectionBox.height = mouse.y - origin.y;
            }

            // The mouse is below the origin
            else
            {
                selectionBox.y = mouse.y;
                selectionBox.height = origin.y - mouse.y;
            }


//            selectionBox.height =  mouseProjected.y - selectionBox.y;
//
//            if(selectionBox.width < 0)
//            {
//                selectionBox.x = selectionBox.x -
//            }

            for (Entity e : entities)
            {
                // Selects all units in the selection box
                if (selectionBox.overlaps(e.getRectangle())) {
                    e.clicked();
                    returnValue = true;
                }
            }
        }

        return returnValue;
    }


    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        Vector3 mouseRaw = new Vector3(screenX, screenY, 0);
        Vector3 mouseProjected = Display.getGameCam().unproject(mouseRaw);

        if(selectionBoxOrigin != null)
        {
            selectionBoxOrigin = null;
            selectionBox = null;
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
