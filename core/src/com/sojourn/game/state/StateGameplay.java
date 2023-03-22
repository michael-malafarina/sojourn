package com.sojourn.game.state;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.sojourn.game.Settings;
import com.sojourn.game.Sojourn;
import com.sojourn.game.Textures;
import com.sojourn.game.World;
import com.sojourn.game.button.Button;
import com.sojourn.game.display.*;
import com.sojourn.game.display.message.EntityMessageManager;
import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.ambient.EnemyAlert;
import com.sojourn.game.entity.unit.ship.Ship;
import com.sojourn.game.faction.Squad;
import com.sojourn.game.faction.TeamEnemy;
import com.sojourn.game.reward.RewardMenu;

import java.util.List;

public class StateGameplay extends State
{
    private EntityMessageManager messages;


    private boolean planning;
    private int gameSpeed;
    private static int waveNumber;
    private RewardMenu rewardMenu;
    private Button combatStartButton;

    private BuildManager builder;
    private GameplayInput gameplayInput;

    private boolean paused;

    private static int timer;


    private static Minimap minimap;

    // Constructor

    public StateGameplay(final Sojourn game)
    {
        super(game);


        gameplayInput = new GameplayInput(this);
        builder = new BuildManager(this);
        combatStartButton = new Button();

        gameSpeed = 2;
        //minimap = new Minimap(10, 10, World.getWidth() * .02f, World.getHeight() * .02f);

        float ratio = (float) World.getHeight() / (float) World.getWidth();
        float minimapScale = .13f;
        minimap = new Minimap(10, 10, Display.WIDTH * minimapScale, Display.WIDTH * minimapScale * ratio);

        messages = new EntityMessageManager();
        rewardMenu = new RewardMenu(this);
        startPlanning();


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

        builder.update();
        messages.update(delta);
        timer++;

        EntityManager.getSquads().forEach(Squad::update);

        if(inPlanningMode())
        {
            for(int i = 0; i < gameSpeed * 2; i++) {
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

    public void setGameSpeed(int value)
    {
        gameSpeed = value;
    }

    public void togglePaused()
    {
        paused = !paused;
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


        gameplayInput.renderGameplay();

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

        if(inPlanningMode()) {
            renderHudPlanning();
        }
        else {
            renderHudCombat();
        }

        player.render();
        minimap.render();

        super.renderHud(delta);

        Text.setAlignment(Alignment.CENTER, Alignment.TOP);

        Text.setFont(Fonts.small);
        Text.setAlignment(Alignment.LEFT, Alignment.TOP);
        Text.draw("Speed " + gameSpeed, 5, Display.HEIGHT - 25);

        Text.setAlignment(Alignment.CENTER, Alignment.TOP);


        Text.setFont(Fonts.subtitle);
        Text.draw("Wave " + waveNumber, Display.WIDTH/2, Display.HEIGHT - 45);

        Text.setFont(Fonts.medium);
        Fonts.medium.getData().markupEnabled = true;
        Text.draw(player.getColorCode() + Math.round(player.getTotalWorth()) +
                         "[#" + Color.WHITE + "]" + " | " +
                         currentEnemy.getColorCode() + Math.round(currentEnemy.getTotalWorth()),
                        Display.WIDTH/2,
                        Display.HEIGHT - 70);
        builder.render();

    }

    protected void renderHudPlanning()
    {
        Text.setFont(Fonts.title);
        Text.setAlignment(Alignment.CENTER, Alignment.TOP);
        Text.draw("Planning", Display.WIDTH/2, Display.HEIGHT - 10);

        rewardMenu.render();
    }

    protected void renderHudCombat()
    {
        Text.setFont(Fonts.title);
        Text.setAlignment(Alignment.CENTER, Alignment.TOP);
        Text.draw("Combat", Display.WIDTH/2, Display.HEIGHT - 10);
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
        builder.mousePressed(screenX, screenY, button);
        return gameplayInput.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return gameplayInput.touchDragged(screenX, screenY, pointer);
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        return gameplayInput.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean scrolled(float amountX, float amountY)
    {
        return gameplayInput.scrolled(amountX, amountY);
    }

    @Override
    public boolean keyDown(int keycode)
    {
        return gameplayInput.keyDown(keycode);
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

//        System.out.println(StateGameplay.getWaveNumber() + " Player Value: " + Sojourn.player.getTotalWorth());

        planning = false;

        rewardMenu.end();

        currentEnemy.spawnWave();

        // Clear out old alerts
        List<EnemyAlert> alerts = EntityManager.getEnemyAlerts();
        alerts.forEach(a -> a.setExpired());

        removeButton(combatStartButton);

    }

    public void startPlanning()
    {
        waveNumber++;

        player.addResources(Settings.resourcePerLevel);

        // Remove any remaining ships
        if(EntityManager.getShips() != null) {
            EntityManager.getEnemyShips().forEach(a -> a.die());
        }
        // Remove all projectiles
        if(EntityManager.getProjectiles() != null) {
            EntityManager.getProjectiles().forEach(a -> a.setExpired());
        }
        // Restore missing and damaged ships to squads
        restoreUnits();

        planning = true;
        TeamEnemy cpu = currentEnemy;

        cpu.planWave();

        rewardMenu.begin();

        addButton(combatStartButton);

        combatStartButton.setPosition(Display.WIDTH - 320, 40);
        combatStartButton.setLabel("Battle!");
        combatStartButton.setClickEvent(() -> startCombat());
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
