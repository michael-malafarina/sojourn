package com.sojourn.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.sojourn.game.Sojourn;
import com.sojourn.game.display.Camera;
import com.sojourn.game.display.Display;
import com.sojourn.game.display.Shape;
import com.sojourn.game.entity.ControlGroupSet;
import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.Unit;

import java.util.List;

public class StateGameplay extends State
{
    private ControlGroupSet controlGroups;
    private Rectangle selectionBox;
    private Vector2 selectionBoxOrigin;
    private boolean paused;
    private boolean planning;

    public StateGameplay(final Sojourn game)
    {
        super(game);
        controlGroups = new ControlGroupSet();
    }

    @Override
    public void update(float delta)
    {
        super.update(delta);

        if(paused)  {
            return;
        }

        game.getEntityManager().update(planning, delta);

    }

    @Override
    protected void renderBackground(float delta) {
        ScreenUtils.clear(.02f, .02f, .02f, 1);
    }

    @Override
    protected void renderGameplay(float delta)
    {
        EntityManager.getEntities().forEach(Entity::render);
    }

    protected void renderGameplayShapes()
    {
        EntityManager.getEntities().forEach(Entity::renderShapes);

        // this has to be here to turn off outside of planning phase
        if(planning) {
            for (Unit u : EntityManager.getUnits()) {
                if(u != null && u.getCenterPosition() != null && u.getDestination() != null) {
                    Shape.getRenderer().setColor(new Color(.2f, .2f, .2f, 1f));
                    Shape.getRenderer().line(u.getCenterPosition(), u.getDestination());
                }
            }

        }

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
            if(!Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
            {
                clearSelection();
            }

            for (Entity e : EntityManager.getEntities()) {
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

        // If I have right-clicked on a location, move selected units there
        if(button == Input.Buttons.RIGHT && planning)
        {
            System.out.println(getAllSelectedUnits());


            for (Unit u : getAllSelectedUnits()) {

                u.setDestination(mouseProjected.x, mouseProjected.y);
                returnValue = true;
            }
            System.out.println("Right click");
        }



        return returnValue;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        Vector3 mouseRaw = new Vector3(screenX, screenY, 0);
        Vector3 mouse = Display.getGameCam().unproject(mouseRaw);
        boolean returnValue = false;

        // This means the unit selection box was started
        if(selectionBoxOrigin != null)
        {

            // Clear all entities again, in case box got smaller
            if(!Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                clearSelection();
            }

            makeSelectionBox(new Vector2(mouse.x, mouse.y));


            for (Entity e : EntityManager.getEntities())
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

    public void makeSelectionBox(Vector2 mouse)
    {
        Vector2 origin = selectionBoxOrigin;

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
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {

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
        if(amountX != 0 || amountY != 0)
        {
            super.scrolled(amountX, amountY);
            Camera.cameraZoom(amountY);
            return true;
        }

        return false;
    }

    @Override
    public boolean keyDown(int keycode)
    {
        if(keycode == Input.Keys.SPACE)
        {
            paused = !paused;
        }

        if(keycode == Input.Keys.C)
        {
            planning = !planning;
        }

        // Keys are used for unit groups
        if(keycode >= Input.Keys.NUM_0 && keycode <= Input.Keys.NUM_9)
        {
            // Holding control adds a new group
            if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
                controlGroups.addGroup(getAllSelectedUnits(), keycode);
            }

            // Otherwise we activate the corresponding group, if it exists
            else
            {
                clearSelection();
                controlGroups.activate(keycode);
            }

        }

        return false;
    }

    public List<Unit> getAllSelectedUnits()
    {
        return EntityManager.getUnits().stream().filter(Entity::isSelected).toList();
    }

    public void clearSelection()
    {
        EntityManager.getEntities().forEach(Entity::unselect);
    }

}
