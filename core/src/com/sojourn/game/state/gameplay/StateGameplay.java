package com.sojourn.game.state.gameplay;

import com.sojourn.game.Settings;
import com.sojourn.game.Sojourn;
import com.sojourn.game.button.Button;
import com.sojourn.game.display.Display;
import com.sojourn.game.display.message.EntityMessageManager;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.ambient.EnemyAlert;
import com.sojourn.game.faction.Squad;
import com.sojourn.game.faction.TeamEnemy;
import com.sojourn.game.reward.RewardMenu;
import com.sojourn.game.state.State;

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
    private GameplayRender gameplayRender;

    private boolean paused;

    private static int timer;

    // Constructor

    public StateGameplay(final Sojourn game)
    {
        super(game);

        gameplayInput = new GameplayInput(this);
        gameplayRender = new GameplayRender(this);
        builder = new BuildManager(this);
        combatStartButton = new Button();

        gameSpeed = 2;

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
    public static int getTime()         { return timer;}
    public int getGameSpeed()      { return gameSpeed; }

    public BuildManager getBuilder()
    {
        return builder;
    }

    public RewardMenu getRewardMenu()
    {
        return rewardMenu;
    }

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
        gameplayRender.renderBackground(delta);
    }

    @Override
    protected void renderGameplay(float delta)
    {
        gameplayRender.renderGameplay(delta);
        messages.render();
    }

    protected void renderGameplayShapes()
    {
        gameplayRender.renderGameplayShapes();
        gameplayInput.renderGameplay();
    }

    @Override
    protected void renderHud(float delta)    {

        gameplayRender.renderHud(delta);
        super.renderHud(delta);
    }

    protected void renderHudShapes()
    {
        gameplayRender.renderHudShapes();
    }

    public String toString()    {
        return "Gameplay";
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        builder.mousePressed(screenX, screenY, button);
        return gameplayInput.touchDown(screenX, screenY, pointer, button);
    }

    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return gameplayInput.touchDragged(screenX, screenY, pointer);
    }


    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        return gameplayInput.touchUp(screenX, screenY, pointer, button);
    }


    public boolean scrolled(float amountX, float amountY)
    {
        return gameplayInput.scrolled(amountX, amountY);
    }

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
