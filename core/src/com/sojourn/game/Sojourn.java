package com.sojourn.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.sojourn.game.display.Display;
import com.sojourn.game.entity.EntityManager;
import com.sojourn.game.state.State;
import com.sojourn.game.state.StateGameplay;
import com.sojourn.game.state.StateTitle;

public class Sojourn extends Game
{
	private Display display;

	public static final int STATE_TITLE_ID = 0;
	public static final int STATE_GAMEPLAY_ID = 1;

	private StateTitle title;
	private StateGameplay gameplay;
	private EntityManager entityManager;

	@Override
	public void create()
	{
		Textures.loadImages();
		entityManager = new EntityManager();

		gameplay = new StateGameplay(this);
		title = new StateTitle(this);
		display = new Display();

		setScreen(title);

	}

	private void setScreen(State state)
	{
		super.setScreen(state);
		Gdx.input.setInputProcessor(state);
	}

	public EntityManager getEntityManager()
	{
		return entityManager;
	}

	@Override
	public void render()
	{
		super.render();

		Gdx.gl.glClearColor( 1, 0, 0, 1 );
		Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

		//System.out.println(Gdx.graphics.getFramesPerSecond() + " " + Gdx.graphics.getDeltaTime());

		display.update();

		getScreen().render(Gdx.graphics.getDeltaTime());

		if (Gdx.input.isKeyPressed(Input.Keys.F1))
		{
			setScreen(title);
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.F2))
		{
			setScreen(gameplay);
		}

	}

	public void exit()
	{
		dispose();
		Gdx.app.exit();
		System.exit(0);
	}

	public void setState(int stateID)
	{
		switch(stateID)
		{
			case STATE_TITLE_ID -> setScreen(title);
			case STATE_GAMEPLAY_ID -> setScreen(gameplay);
		}
	}

	@Override
	public void dispose()
	{
		display.dispose();
	}

	public void resize(int width, int height)
	{
		display.resize(width, height);
	}
}