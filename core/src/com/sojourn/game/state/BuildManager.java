package com.sojourn.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.sojourn.game.Sojourn;
import com.sojourn.game.button.Button;
import com.sojourn.game.display.Alignment;
import com.sojourn.game.display.Display;
import com.sojourn.game.display.Fonts;
import com.sojourn.game.display.Text;
import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.unit.civilian.Civilian;
import com.sojourn.game.entity.unit.ship.Ship;

import java.util.ArrayList;
import java.util.List;

public class BuildManager
{
    private static List<Class<? extends Ship>> unlockedShips;
    private static List<Class<? extends Civilian>> unlockedCivilians;

    private List<Button> buttons;

    private StateGameplay game;
    private static boolean needsRefresh;

    private Civilian currentCivilian;

    public BuildManager(StateGameplay game)
    {
        this.game = game;

        unlockedShips = new ArrayList<>();
        unlockedCivilians = new ArrayList<>();

    }

    public void update()
    {
        if(needsRefresh) {
            removeButtons();
            addButtons();
            needsRefresh = false;
        }

        Vector3 mouseRaw = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        Vector3 mouse = Display.getCamera().getGameCamera().unproject(mouseRaw);

        if(currentCivilian != null)
        {
            currentCivilian.setPosition(mouse.x - currentCivilian.getWidth()/2, mouse.y - currentCivilian.getHeight()/2);


            if(canPlaceCurrentCivilian())
            {
                currentCivilian.getImage().setColor(new Color(0f, 1f, 0f, .5f));
            }
            else
            {
                currentCivilian.getImage().setColor(new Color(1f, 0f, 0f, .5f));
            }



            currentCivilian.getImage().hideHealthbar();
        }

       // if(Gdx.input.())
//        {
//            System.out.println();
//        }

    }

    public boolean canPlaceCurrentCivilian()
    {
        // Must be in the control radius of the base ship
        if(!inControlRadius(currentCivilian))
        {
            return false;
        }

        for(Civilian c : EntityManager.getCivilians())
        {
            // Avoids checking collisions with the current civilian
            if(c == currentCivilian)
            {
                continue;
            }

            // Cannot overlap with another civilian ship
            if(currentCivilian.getRectangle().overlaps(c.getRectangle()))
            {
                return false;
            }
        }

        return true;
    }

    public boolean inControlRadius(Entity e)
    {
        Circle control = new Circle(0, 0, Sojourn.player.getControlDistance());
        Rectangle placement = e.getRectangle();

        return  control.contains(placement.getX(), placement.getY()) &&
                control.contains(placement.getX() + placement.getWidth(), placement.getY()) &&
                control.contains(placement.getX(), placement.getY() + placement.getHeight()) &&
                control.contains(placement.getX() + placement.getWidth(), placement.getY() + placement.getHeight());
    }


    public void render()
    {
        Text.setFont(Fonts.large);
        Text.setAlignment(Alignment.LEFT, Alignment.CENTER);
        Text.draw("Resources: " + Math.round(Sojourn.player.getResources()), 50, 700);
    }

    private void addButtons()
    {
        buttons = new ArrayList<>();

        for(int i = 0; i < unlockedShips.size(); i++)
        {
            Class<? extends Ship> ship = unlockedShips.get(i);

            Ship prototype = (Ship) EntityManager.entityFactory(ship);
            prototype.setTeam(Sojourn.player);

            Button b = new Button();
            b.setPosition(50, 600 - i * 60);
            b.setSize(150, 40);
            b.setFont(Fonts.small);
            b.setLabel("Build " + prototype.getName() + " (" + Math.round(prototype.getCost().getValue())+ ")");
            b.setClickEvent(() -> Sojourn.player.buildSquad(ship));
            game.addButton(b);
            buttons.add(b);
        }

        for(int i = 0; i < unlockedCivilians.size(); i++)
        {
            Class<? extends Civilian> civilian = unlockedCivilians.get(i);

            Civilian prototype = (Civilian) EntityManager.entityFactory(civilian);
            prototype.setTeam(Sojourn.player);

            Button b = new Button();
            b.setPosition(250, 600 - i * 60);
            b.setSize(150, 40);
            b.setFont(Fonts.small);
            b.setLabel("Build " + prototype.getName() + " (" + Math.round(prototype.getCost().getValue())+ ")");
            b.setClickEvent(() -> beginPlacingCivilian(civilian));
            game.addButton(b);
            buttons.add(b);
        }
    }

    private void removeButtons()
    {
        if(buttons == null)
        {
            return;
        }
        buttons.forEach(b -> game.removeButton(b));
    }

    public static void unlockShip(Class<? extends Ship> clazz)
    {
        unlockedShips.add(clazz);
        needsRefresh = true;
    }

    public static void unlockCivilian(Class<? extends Civilian> clazz)
    {
        unlockedCivilians.add(clazz);
        needsRefresh = true;
    }

    public void beginPlacingCivilian(Class<? extends Civilian> clazz)
    {
        currentCivilian = (Civilian) EntityManager.entityFactory(clazz);
        currentCivilian.setTeam(Sojourn.player);
        currentCivilian.getImage().setAlpha(.7f);
        EntityManager.addEntity(currentCivilian);

    }

    public void mousePressed(float mouseX, float mouseY, int button)
    {
        if(isPlacingCivilian() && canPlaceCurrentCivilian())
        {
            currentCivilian.getImage().resetColor();
            currentCivilian.getImage().showHealthbar();
            currentCivilian = null;
        }

    }

    public boolean isPlacingCivilian()
    {
        return currentCivilian != null;
    }


}
