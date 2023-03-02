package com.sojourn.game.entity.ambient;

import com.badlogic.gdx.graphics.Texture;
import com.sojourn.game.Textures;

public class EnemyAlert extends Ambient
{
    @Override
    public int getWidth() {
        return 100;
    }

    @Override
    public int getHeight() {
        return 100;
    }

    @Override
    public Texture getSpriteSheet() {
        return Textures.base;
    }
}
