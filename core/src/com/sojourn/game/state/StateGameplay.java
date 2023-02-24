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
import com.sojourn.game.display.*;
import com.sojourn.game.entity.ControlGroupSet;
import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.unit.Unit;
import com.sojourn.game.entity.unit.ship.Ship;

import java.util.List;

public class StateGameplay extends State
{
    private ControlGroupSet controlGroups;
    private Rectangle selectionBox;
    private Vector2 selectionBoxOrigin;
    private boolean paused;
    private boolean planning;
    private boolean gridlines;
    private int gameSpeed;


    private static Minimap minimap;

    // Constructor

    public StateGameplay(final Sojourn game)
    {
        super(game);
        controlGroups = new ControlGroupSet();
        planning = true;
        gameSpeed = 2;
        minimap = new Minimap(2, 2, 192*1.7f, 108*1.7f);

    }

    // Accessors

    public boolean inPlanningMode()
    {
        return planning;
    }

    public boolean inCombatMode()
    {
        return !planning;
    }

    @Override
    public void update(float delta)
    {
        super.update(delta);

        if(paused)  {
            return;
        }

        if(inPlanningMode())
        {
            for(int i = 0; i < 3; i++) {
                game.getEntityManager().update(inPlanningMode(), delta);
            }
        }
        else
        {
            for(int i = 0; i < gameSpeed; i++) {
                game.getEntityManager().update(inPlanningMode(), delta);
            }
        }

    }

    @Override
    protected void renderBackground(float delta) {
        ScreenUtils.clear(.02f, .02f, .02f, 1);
    }

    @Override
    protected void renderGameplay(float delta)
    {
        EntityManager.getCivilians().forEach(Entity::render);
        EntityManager.getShips().forEach(Entity::render);
        EntityManager.getProjectiles().forEach(Entity::render);

    }

    protected void renderGameplayShapes()
    {
        EntityManager.getEntities().forEach(Entity::renderShapes);

        // this has to be here to turn off outside of planning phase
        if(planning) {
            for (Ship u : EntityManager.getShips()) {
                if(u != null && u.getCenterPosition() != null && u.getDestination() != null) {
                    Shape.getRenderer().setColor(new Color(.2f, .2f, .2f, 1f));
                    Shape.getRenderer().line(u.getCenterPosition(), u.getDestination());
                }
            }

        }

        // Draw selection box shape
        if(selectionBox != null)
        {
            Shape.getRenderer().set(ShapeRenderer.ShapeType.Line);
            Shape.getRenderer().setColor(Color.WHITE);
            Shape.getRenderer().rect(selectionBox.x, selectionBox.y, selectionBox.getWidth(), selectionBox.getHeight());
        }

        // Draw gridlines
        if(gridlines)
        {
            Shape.getRenderer().set(ShapeRenderer.ShapeType.Line);
            Shape.getRenderer().setColor(new Color(50, 20, 50, 150));
            Shape.getRenderer().line(-5000, 0, 5000, 0);
            Shape.getRenderer().line(0, -5000, 0, 5000);
        }
    }


    @Override
    protected void renderHud(float delta)    {
        super.renderHud(delta);

        if(inPlanningMode()) {
            renderHudPlanning();
        }
        else {
            renderHudCombat();
        }

    }

    protected void renderHudPlanning()
    {
        Text.setFont(Fonts.title);
        Text.setAlignment(Alignment.CENTER, Alignment.TOP);
        Text.draw("Planning ", Display.WIDTH/2, Display.HEIGHT);
    }

    protected void renderHudCombat()
    {
        Text.setFont(Fonts.small);
        Text.setAlignment(Alignment.LEFT, Alignment.TOP);
        Text.draw("Speed " + gameSpeed, 5, Display.HEIGHT - 25);

        Text.setFont(Fonts.title);
        Text.setAlignment(Alignment.CENTER, Alignment.TOP);
        Text.draw("Combat ", Display.WIDTH/2, Display.HEIGHT);
    }

    protected void renderHudShapes()
    {
        minimap.renderShapes();
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
        if(button == Input.Buttons.RIGHT && planning)
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
        // Toggle pause
        if(keycode == Input.Keys.SPACE)
        {
            paused = !paused;
        }

        // Toggle gridlines
        if(keycode == Input.Keys.G)
        {
            gridlines = !gridlines;
        }

        // Toggle planning phase (temporary)
        if(keycode == Input.Keys.C)
        {
            planning = !planning;
        }

        // Spawn a wave of enemies
        if(keycode == Input.Keys.E)
        {
            EntityManager.spawnEnemyWave();
        }

        if(inPlanningMode()) {
            keyDownPlanning(keycode);
        }
        if(inCombatMode())
        {
            keyDownCombat(keycode);
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
        if(keycode == Input.Keys.NUM_1)
        {
            gameSpeed = 1;
        }
        else if(keycode == Input.Keys.NUM_2)
        {
            gameSpeed = 2;
        }
        else if(keycode == Input.Keys.NUM_3)
        {
            gameSpeed = 3;
        }

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
