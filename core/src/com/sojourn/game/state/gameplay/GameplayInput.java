package com.sojourn.game.state.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.sojourn.game.Settings;
import com.sojourn.game.Sojourn;
import com.sojourn.game.button.Button;
import com.sojourn.game.display.Camera;
import com.sojourn.game.display.Display;
import com.sojourn.game.display.Shape;
import com.sojourn.game.entity.ControlGroupSet;
import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.unit.Unit;
import com.sojourn.game.entity.unit.ship.Ship;

import java.util.List;

public class GameplayInput {

    StateGameplay state;
    private ControlGroupSet controlGroups;
    private Rectangle selectionBox;
    private Vector2 selectionBoxOrigin;


    public GameplayInput(StateGameplay state)
    {
        this.state = state;
        controlGroups = new ControlGroupSet();

    }

    public void renderGameplay()
    {
        // Draw selection box shape
        if(selectionBox != null)
        {
            Shape.getRenderer().set(ShapeRenderer.ShapeType.Line);
            Shape.getRenderer().setColor(Color.WHITE);
            Shape.getRenderer().rect(selectionBox.x, selectionBox.y, selectionBox.getWidth(), selectionBox.getHeight());
        }
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        Vector3 mouseRaw = new Vector3(screenX, screenY, 0);
        Vector3 mouseProjected = Display.getCamera().getGameCamera().unproject(mouseRaw);
        boolean returnValue = false;

        // Try using buttons first
        for(Button b : state.getButtons())
        {
            if(b.touchDown(screenX, screenY, pointer, button))
            {
                return true;
            }
        }

        // Determine which units are selected with this event

        if (button == Input.Buttons.LEFT)
        {
            if(!Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
            {
                clearSelection();
            }

            // If I have left-clicked on an entity, let it know and it will determine if it can be selected
            for (Entity e : EntityManager.getPlayerShips()) {
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
        if(button == Input.Buttons.RIGHT && state.inPlanningMode() && Sojourn.player.inControlRadius(mouseProjected))
        {
            for (Unit u : getAllSelectedUnits()) {

                if(u instanceof Ship) {
                    ((Ship)u).setDestination(mouseProjected.x, mouseProjected.y);
                    returnValue = true;
                }
            }
        }

        return returnValue;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        Vector3 mouseRaw = new Vector3(screenX, screenY, 0);
        Vector3 mouse = Display.getCamera().getGameCamera().unproject(mouseRaw);
        boolean returnValue = false;

        // This means the unit selection box was started
        if(selectionBoxOrigin != null)
        {

            // Clear all entities again, in case box got smaller
            if(!Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                clearSelection();
            }

            makeSelectionBox(new Vector2(mouse.x, mouse.y));

            // Selects all player ships in the selection box
            for (Ship s : EntityManager.getPlayerShips())
            {
                if (selectionBox.overlaps(s.getRectangle())) {
                    s.clicked();
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

        if(button == Input.Buttons.LEFT && selectionBoxOrigin != null)
        {
            selectionBoxOrigin = null;
            selectionBox = null;
        }

        return false;
    }

    public boolean scrolled(float amountX, float amountY)
    {
        if(amountX != 0 || amountY != 0)
        {
            Camera.cameraZoom(amountY);
            return true;
        }

        return false;
    }

    public boolean keyDown(int keycode)
    {
        // Toggle pause
        if(keycode == Input.Keys.SPACE)
        {
           state.togglePaused();
        }

        // Toggle gridlines
        if(keycode == Input.Keys.G)
        {
            Settings.showGridlines = ! Settings.showGridlines;
        }

        // Toggle borders
        if(keycode == Input.Keys.B)
        {
            Settings.showBorders = ! Settings.showBorders;
        }

        // Toggle planning phase (temporary)
        if(keycode == Input.Keys.E)
        {
            state.togglePlanning();
        }

        if(state.inPlanningMode())
        {
            keyDownPlanning(keycode);
        }
        if(state.inCombatMode())
        {
            keyDownCombat(keycode);
        }

        if(keycode == Input.Keys.F1)
        {
            state.setGameSpeed(1);
        }
        else if(keycode == Input.Keys.F2)
        {
            state.setGameSpeed(2);
        }
        else if(keycode == Input.Keys.F3)
        {
            state.setGameSpeed(3);
        }


        return false;
    }

    public void keyDownPlanning(int keycode)
    {
        // Number keys are used for unit groups
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
    }

    public void keyDownCombat(int keycode)
    {


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
