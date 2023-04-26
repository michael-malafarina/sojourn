package com.sojourn.game.state.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.sojourn.game.Sojourn;
import com.sojourn.game.button.BuildCivilianButton;
import com.sojourn.game.button.BuildSquadButton;
import com.sojourn.game.button.Button;
import com.sojourn.game.display.Alignment;
import com.sojourn.game.display.Display;
import com.sojourn.game.display.Fonts;
import com.sojourn.game.display.Text;
import com.sojourn.game.entity.Attribute;
import com.sojourn.game.entity.Entity;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.entity.unit.civilian.Civilian;
import com.sojourn.game.entity.unit.civilian.SupplyShip;
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

    private static Civilian currentCivilian;

    public BuildManager(StateGameplay game)
    {
        this.game = game;
        buttons = new ArrayList<>();
        unlockedShips = new ArrayList<>();
        unlockedCivilians = new ArrayList<>();
        unlockedCivilians.add(SupplyShip.class);

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

       // updateBuildingButtons();

       // if(Gdx.input.())
//        {
//            System.out.println();
//        }

    }

//    public void updateBuildingButtons()
//    {
//        for(Button b : buttons)
//        {
//            if(isPlacingCivilian())
//            {
//                b.disable();
//            }
//            else if(game.inCombatMode() && b.)
//            {
//                b.disable();
//            }
//            else
//            {
//                b.enable();
//            }
//        }
//    }

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
        // Resources
        Text.setFont(Fonts.large);
        Text.setAlignment(Alignment.LEFT, Alignment.CENTER);
        Text.setColor(Color.WHITE);
        Text.draw("Resources: " + Math.round(Sojourn.player.getResources()), 50, 760);

        // SUPPLY
        int usedSupply = Math.round(game.getPlayer().getSupply().getMaximum() - game.getPlayer().getSupply().getCurrent());
        Text.draw( "Supply: " + usedSupply + " / " + Math.round(game.getPlayer().getSupply().getMaximum()), 50, 800);
    }

    public static boolean canPay(Attribute cost)
    {
        return Sojourn.player.getResources() >= cost.getValue();
    }

    private void addButtons()
    {
        buttons = new ArrayList<>();

        for(int i = 0; i < unlockedShips.size(); i++)
        {
            Class<? extends Ship> ship = unlockedShips.get(i);

            Ship prototype = (Ship) EntityManager.entityFactory(ship);
            prototype.setTeam(Sojourn.player);

            Button b = new BuildSquadButton(prototype.getCost());
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

            Button b = new BuildCivilianButton(prototype.getCost());
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

    public void clearPlacingCivilian()
    {
        currentCivilian.setExpired();
        currentCivilian = null;
    }

    public void beginPlacingCivilian(Class<? extends Civilian> clazz)
    {
        currentCivilian = (Civilian) EntityManager.entityFactory(clazz);
        currentCivilian.setTeam(Sojourn.player);
        currentCivilian.setAttributes();

        if(game.inPlanningMode() && canPay(currentCivilian.getCost()))
        {
            currentCivilian.getImage().setAlpha(.7f);
            Sojourn.player.spendResources(currentCivilian.getCost().getValue());
            EntityManager.addEntity(currentCivilian);
        }
        else
        {
            clearPlacingCivilian();
        }

    }

    public void mousePressed(float mouseX, float mouseY, int button)
    {
        if(isPlacingCivilian() && canPlaceCurrentCivilian())
        {
            if(button == Input.Buttons.LEFT)
            {
                currentCivilian.getImage().resetColor();
                currentCivilian.getImage().showHealthbar();
                currentCivilian.onBuild();
                currentCivilian = null;
            }
            else
            {
                Sojourn.player.addResources(currentCivilian.getCost().getValue());
                clearPlacingCivilian();
            }

        }

    }

    public static boolean isPlacingCivilian()
    {
        return currentCivilian != null;
    }


}
