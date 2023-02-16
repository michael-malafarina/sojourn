package com.sojourn.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Textures
{
    public static Texture scout;
    public static Texture raider;

    public static void loadImages()
    {
        scout = new Texture(Gdx.files.internal("unit/scout.png"));
        raider = new Texture(Gdx.files.internal("unit/raider.png"));

    }
}
