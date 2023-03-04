package com.sojourn.game.menu;

import com.badlogic.gdx.graphics.Texture;

abstract public class Reward
{
    protected String name;
    protected String description;
    protected Texture icon;

    abstract public void apply();

    public void render()
    {

    }

}
