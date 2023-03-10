package com.sojourn.game.reward;

import com.badlogic.gdx.graphics.Color;
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

    protected Color color;
    protected String name;
    protected String description;
    protected Texture icon;

    private Button accept;
    protected RewardMenu owner;

    abstract public void apply();


    public Reward(RewardMenu owner)
    {
        this.owner = owner;
        accept = new Button();
        accept.setClickEvent(this::apply);
        accept.setClickEventTwo(owner::end);
        accept.setFont(Fonts.large);
        description = "";

        color = Color.WHITE;


    }

    public void end()
    {
        owner.removeButton(accept);
    }

    public void begin(int i)
    {

        int numSections = owner.getNumberOfChoices();
        int menuLeftEdge = Display.WIDTH / 3;
        int menuWidth = (int) (Display.WIDTH * .45f) ;
        int sectionWidth = menuWidth / (numSections+1);
        int sectionSpacing = (menuWidth - sectionWidth * numSections) / (numSections - 1);

        x = menuLeftEdge + (i * sectionSpacing) + (i * sectionWidth) - sectionSpacing/2;
        y = (int) (Display.HEIGHT * .36f);

        accept.setLabel(name);
        accept.setSize(sectionWidth, 60);
        accept.setColor(color);
        accept.setPosition(x, y);
        accept.center();

        owner.addButton(accept);

    }

    public void render()
    {
        Text.setFont(Fonts.subtitle);
        Text.setAlignment(Alignment.CENTER, Alignment.CENTER);
        Text.draw(description, x, y - 60);
    }

}
