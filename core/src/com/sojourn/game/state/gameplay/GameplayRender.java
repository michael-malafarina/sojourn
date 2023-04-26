package com.sojourn.game.state.gameplay;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.sojourn.game.Settings;
import com.sojourn.game.Sojourn;
import com.sojourn.game.Textures;
import com.sojourn.game.World;
import com.sojourn.game.display.*;
import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.unit.ship.Ship;
import com.sojourn.game.faction.Squad;

public class GameplayRender {

    StateGameplay state;
    private static Minimap minimap;

    public GameplayRender(StateGameplay state) {
        this.state = state;
        float ratio = (float) World.getHeight() / (float) World.getWidth();
        float minimapScale = .13f;

        minimap = new Minimap(10, 10, Display.WIDTH * minimapScale, Display.WIDTH * minimapScale * ratio);

    }

    protected void renderBackground(float delta)
    {
        ScreenUtils.clear(.02f, .02f, .02f, 1);
        float bgScale = 1.5f;
        Display.draw(Textures.background, new Color(1, 1,1, Settings.backgroundBrightness),
                -World.getWidthBase() * bgScale * .5f, -World.getHeightBase() * bgScale * .5f,
                World.getWidthBase() * bgScale, World.getHeightBase() * bgScale);
    }

    protected void renderGameplay(float delta)
    {
        EntityManager.getEnemyAlerts().forEach(Entity::render);

        EntityManager.getCivilians().forEach(Entity::render);
        EntityManager.getShips().forEach(Entity::render);
        EntityManager.getProjectiles().forEach(Entity::render);
        EntityManager.getSquads().forEach(Squad::render);
    }

    protected void renderGameplayShapes()
    {
        EntityManager.getEntities().forEach(Entity::renderShapes);
        EntityManager.getSquads().forEach(Squad::renderShape);

        // this has to be here to turn off outside of planning phase
        if(state.inPlanningMode()) {
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


    protected void renderHud(float delta)    {

        if(state.inPlanningMode()) {
            renderHudPlanning();
        }
        else {
            renderHudCombat();
        }

        state.getPlayer().render();
        minimap.render();


        Text.setAlignment(Alignment.CENTER, Alignment.TOP);

        Text.setFont(Fonts.small);
        Text.setAlignment(Alignment.LEFT, Alignment.TOP);
        Text.draw("Speed " + state.getGameSpeed(), 5, Display.HEIGHT - 25);

        Text.setAlignment(Alignment.CENTER, Alignment.TOP);

        // WAVE

        Text.setFont(Fonts.subtitle);
        Text.draw("Wave " + StateGameplay.getWaveNumber(), Display.WIDTH/2, Display.HEIGHT - 45);

        // POWER

        Text.setFont(Fonts.medium);
        Fonts.medium.getData().markupEnabled = true;
        Text.draw(state.getPlayer().getColorCode() + Math.round(state.getPlayer().getTotalWorth()) +
                        "[#" + Color.WHITE + "]" + " | " +
                        state.getEnemy().getColorCode() + Math.round(state.getEnemy().getTotalWorth()),
                Display.WIDTH/2,
                Display.HEIGHT - 70);





        state.getBuilder().render();

    }

    protected void renderHudPlanning()
    {
        Text.setFont(Fonts.title);
        Text.setAlignment(Alignment.CENTER, Alignment.TOP);
        Text.draw("Planning", Display.WIDTH/2, Display.HEIGHT - 10);

        state.getRewardMenu().render();
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

}

