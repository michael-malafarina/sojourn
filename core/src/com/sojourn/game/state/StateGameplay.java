package com.sojourn.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.sojourn.game.Settings;
import com.sojourn.game.Sojourn;
import com.sojourn.game.Textures;
import com.sojourn.game.World;
import com.sojourn.game.button.Button;
import com.sojourn.game.display.*;
import com.sojourn.game.display.message.EntityMessageManager;
import com.sojourn.game.entity.ControlGroupSet;
import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.ambient.EnemyAlert;
import com.sojourn.game.entity.projectile.Projectile;
import com.sojourn.game.entity.unit.Unit;
import com.sojourn.game.entity.unit.ship.Ship;
import com.sojourn.game.faction.Squad;
import com.sojourn.game.faction.TeamEnemy;
import com.sojourn.game.reward.RewardMenu;

import java.util.List;

public class StateGameplay extends State
{
    private EntityMessageManager messages;

    private ControlGroupSet controlGroups;
    private Rectangle selectionBox;
    private Vector2 selectionBoxOrigin;
    private boolean paused;
    private boolean planning;
    private int gameSpeed;
    private static int waveNumber;
    private RewardMenu rewardMenu;
    Button combatStartButton = new Button();
    private static int timer;


    private static Minimap minimap;

    // Constructor

    public StateGameplay(final Sojourn game)
    {
        super(game);
        controlGroups = new ControlGroupSet();
        startPlanning();
        gameSpeed = 2;
        //minimap = new Minimap(10, 10, World.getWidth() * .02f, World.getHeight() * .02f);

        float ratio = (float) World.getHeight() / (float) World.getWidth();
        float minimapScale = .13f;
        minimap = new Minimap(10, 10, Display.WIDTH * minimapScale, Display.WIDTH * minimapScale * ratio);

        messages = new EntityMessageManager();
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

    public static int getWaveNumber()
    {
        return waveNumber;
    }
    public static int getTime()    { return timer;}

    @Override
    public void update(float delta)
    {
        super.update(delta);

        if(paused)  {
            return;
        }

        messages.update(delta);
        timer++;

        EntityManager.getSquads().forEach(Squad::update);

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

        if(EntityManager.getEnemyShips().isEmpty() && !inPlanningMode())
        {
            startPlanning();
        }

    }

    @Override
    protected void renderBackground(float delta)
    {
        ScreenUtils.clear(.02f, .02f, .02f, 1);
        float bgScale = 1.5f;
        Display.draw(Textures.background, new Color(1, 1,1, Settings.backgroundBrightness),
                -World.getWidthBase() * bgScale * .5f, -World.getHeightBase() * bgScale * .5f,
                World.getWidthBase() * bgScale, World.getHeightBase() * bgScale);
    }

    @Override
    protected void renderGameplay(float delta)
    {
        EntityManager.getEnemyAlerts().forEach(Entity::render);

        EntityManager.getCivilians().forEach(Entity::render);
        EntityManager.getShips().forEach(Entity::render);
        EntityManager.getProjectiles().forEach(Entity::render);
        EntityManager.getSquads().forEach(Squad::render);



        messages.render();
    }

    protected void renderGameplayShapes()
    {
        EntityManager.getEntities().forEach(Entity::renderShapes);
        EntityManager.getSquads().forEach(Squad::renderShape);

        // this has to be here to turn off outside of planning phase
        if(planning) {
            for (Ship u : EntityManager.getShips()) {
                if(u != null && u.getCenterPosition() != null && u.getDestination() != null) {
                    Shape.getRenderer().setColor(new Color(.2f, .2f, .2f, 1f));
                    Shape.getRenderer().line(u.getCenterPosition(), u.getDestination());
                }
            }

            int radius = Sojourn.player.getControlDistance();
            Shape.getRenderer().set(ShapeRenderer.ShapeType.Line);
            Shape.getRenderer().setColor(Color.WHITE);
            Shape.getRenderer().ellipse(-radius, -radius, radius*2, radius*2);
        }

        // Draw selection box shape
        if(selectionBox != null)
        {
            Shape.getRenderer().set(ShapeRenderer.ShapeType.Line);
            Shape.getRenderer().setColor(Color.WHITE);
            Shape.getRenderer().rect(selectionBox.x, selectionBox.y, selectionBox.getWidth(), selectionBox.getHeight());
        }

        // Draw gridlines
        if(Settings.showGridlines)
        {
            Shape.getRenderer().set(ShapeRenderer.ShapeType.Line);
            Shape.getRenderer().setColor(new Color(.50f, .20f, .50f, 1));
            Shape.getRenderer().line(World.getWestEdge(), 0, World.getEastEdge(), 0);
            Shape.getRenderer().line(0, World.getSouthEdge(), 0, World.getNorthEdge());


            //Shape.getRenderer().line(0, -5000, 0, 5000);
        }

        if(Settings.showBorders)
        {
            Shape.getRenderer().set(ShapeRenderer.ShapeType.Line);
            Shape.getRenderer().setColor(new Color(1, 1, 1, 1));
            Shape.getRenderer().line(World.getNorthWestCorner(), World.getNorthEastCorner());
            Shape.getRenderer().line(World.getNorthEastCorner(), World.getSouthEastCorner());
            Shape.getRenderer().line(World.getSouthEastCorner(), World.getSouthWestCorner());
            Shape.getRenderer().line(World.getSouthWestCorner(), World.getNorthWestCorner());
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

        Sojourn.player.render();
        minimap.render();


    }

    protected void renderHudPlanning()
    {

        Text.setFont(Fonts.title);
        Text.setAlignment(Alignment.CENTER, Alignment.TOP);
        Text.draw("Planning", Display.WIDTH/2, Display.HEIGHT - 10);

        Text.setFont(Fonts.subtitle);
        Text.draw("Wave " + waveNumber, Display.WIDTH/2, Display.HEIGHT - 45);

        if(rewardMenu != null)
        {
            rewardMenu.render();
        }


    }

    protected void renderHudCombat()
    {
        Text.setFont(Fonts.small);
        Text.setAlignment(Alignment.LEFT, Alignment.TOP);
        Text.draw("Speed " + gameSpeed, 5, Display.HEIGHT - 25);

        Text.setFont(Fonts.title);
        Text.setAlignment(Alignment.CENTER, Alignment.TOP);
        Text.draw("Combat", Display.WIDTH/2, Display.HEIGHT - 10);

        Text.setFont(Fonts.subtitle);
        Text.draw("Wave " + waveNumber, Display.WIDTH/2, Display.HEIGHT - 45);
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
        Vector3 mouseProjected = Display.getCamera().getGameCamera().unproject(mouseRaw);
        boolean returnValue = false;

        // Try using buttons first
        for(Button b : buttons)
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
        if(button == Input.Buttons.RIGHT && planning && Sojourn.player.inControlRadius(mouseProjected))
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
            togglePlanning();
        }

        if(inPlanningMode())
        {
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

    public void togglePlanning()
    {
        if(inPlanningMode())
        {
            startCombat();
        }
        else
        {
            startPlanning();
        }
    }

    public void startCombat()
    {
        planning = false;
        if(rewardMenu != null)
        {
            rewardMenu.done();
        }

        TeamEnemy cpu = (TeamEnemy) Sojourn.currentEnemy;



        cpu.spawnWave();

        // Clear out old alerts
        List<EnemyAlert> alerts = EntityManager.getEnemyAlerts();
        alerts.forEach(a -> a.setExpired());

        buttons.remove(combatStartButton);


    }

    public void startPlanning()
    {
        // Temp code - in actual gameplay these will already be gone
        List<Ship> enemyShips = EntityManager.getEnemyShips();
        enemyShips.forEach(a -> a.setExpired());

        // Remove all projectiles
        List<Projectile> projectiles = EntityManager.getProjectiles();
        projectiles.forEach(a -> a.setExpired());

        // Restore missing and damaged ships to squads
        restoreUnits();

        planning = true;
        TeamEnemy cpu = (TeamEnemy) Sojourn.currentEnemy;
        cpu.planWave(waveNumber);
        waveNumber++;

        if(waveNumber > 1)
        {
            rewardMenu = new RewardMenu(this);
        }

        combatStartButton.setPosition(Display.WIDTH - 320, 40);
        combatStartButton.setLabel("Battle!");
        combatStartButton.setClickEvent(() -> startCombat());
        buttons.add(combatStartButton);

    }

    public List<Unit> getAllSelectedUnits()
    {
        return EntityManager.getUnits().stream().filter(Entity::isSelected).toList();
    }

    public void clearSelection()
    {
        EntityManager.getEntities().forEach(Entity::unselect);
    }


    public void restoreUnits()
    {
        List<Squad> playerSquads = EntityManager.getPlayerSquads();
        playerSquads.forEach(s -> s.restore());

    }

    public void dispose()
    {

    }


}
