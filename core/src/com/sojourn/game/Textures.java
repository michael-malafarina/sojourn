package com.sojourn.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Textures
{
    public static Texture scout;
    public static Texture raider;
    public static Texture base;

    public static Texture uiBar;

    public static Texture bullet;
    public static Texture beam;


    public static void loadImages()
    {
        scout = new Texture(Gdx.files.internal("unit/scout.png"));
        raider = new Texture(Gdx.files.internal("unit/raider2.png"));
        base = new Texture(Gdx.files.internal("unit/base.png"));


        uiBar = new Texture(Gdx.files.internal("ui/bar.png"));

        bullet = new Texture(Gdx.files.internal("bullet.png"));
        beam = new Texture(Gdx.files.internal("beam.png"));


    }
}
