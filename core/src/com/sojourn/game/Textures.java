package com.sojourn.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Textures
{
    public static Texture scout;
    public static Texture raider;
    public static Texture boxy;
    public static Texture bubble;
    public static Texture pincer;

    public static Texture base;
    public static Texture uiBar;

    public static Texture bullet;
    public static Texture beam;

    public static Texture title;
    public static Texture titleName;
    public static Texture titleBlack;

    public static Texture background;

    public static void loadImages()
    {
        title = new Texture(Gdx.files.internal("ui/spaceTitleTemp.png"));
        titleName = new Texture(Gdx.files.internal("ui/titleName.png"));
        titleBlack = new Texture(Gdx.files.internal("ui/titleBlack.png"));

        background = new Texture(Gdx.files.internal("ui/background2.png"));

        uiBar = new Texture(Gdx.files.internal("ui/bar.png"));

        scout = new Texture(Gdx.files.internal("unit/scout.png"));
        raider = new Texture(Gdx.files.internal("unit/raider2.png"));
        boxy = new Texture(Gdx.files.internal("unit/boxy.png"));
        bubble = new Texture(Gdx.files.internal("unit/bubble.png"));
        pincer = new Texture(Gdx.files.internal("unit/pincer.png"));

        base = new Texture(Gdx.files.internal("unit/base.png"));

        bullet = new Texture(Gdx.files.internal("bullet.png"));
        beam = new Texture(Gdx.files.internal("beam.png"));


    }
}
