package com.sojourn.game.menu;

import com.badlogic.gdx.graphics.Texture;
import com.sojourn.game.button.Button;
import com.sojourn.game.display.Alignment;
import com.sojourn.game.display.Display;
import com.sojourn.game.display.Fonts;
import com.sojourn.game.display.Text;

abstract public class Reward
{

    private int x;
    private int y;

    protected String name;
    protected String description;
    protected Texture icon;

    private Button accept;
    private RewardMenu owner;

    abstract public void apply();


    public Reward(RewardMenu owner)
    {
        this.owner = owner;
        accept = new Button();
        accept.setClickEvent(this::apply);
        accept.setClickEventTwo(owner::done);
        description = "";

        owner.addButton(accept);

    }

    public void done()
    {
        owner.removeButton(accept);
    }

    public void setup(int i)
    {
        accept.setLabel(name);

        int numSections = owner.getNumberOfChoices();
        int menuLeftEdge = Display.WIDTH / 3;
        int menuWidth = (int) (Display.WIDTH * .45f) ;
        int sectionWidth = menuWidth / (numSections+1);
        int sectionSpacing = (menuWidth - sectionWidth * numSections) / (numSections - 1);

        x = menuLeftEdge + (i * sectionSpacing) + (i * sectionWidth) - sectionSpacing/2;
        y = Display.HEIGHT/3;


        accept.setSize(sectionWidth, 60);
        accept.setPosition(x- sectionWidth/2, y);

    }

    public void render()
    {
        Text.setFont(Fonts.subtitle);
        Text.setAlignment(Alignment.CENTER, Alignment.CENTER);
        Text.draw(description, x, y - 30);
    }

}
