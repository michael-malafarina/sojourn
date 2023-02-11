package com.sojourn.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.sojourn.game.display.Display;
import com.sojourn.game.state.StateGameplay;
import com.sojourn.game.state.StateTitle;

public class Sojourn extends Game
{
	private Display display;

	public final int STATE_TITLE_ID = 0;
	public final int STATE_GAMEPLAY_ID = 1;

	private StateTitle title;
	private StateGameplay gameplay;

	@Override
	public void create()
	{
		gameplay = new StateGameplay(this);
		title = new StateTitle(this);
		display = new Display();

		this.setScreen(title);

	}
	@Override
	public void render()
	{
		super.render();

		Gdx.gl.glClearColor( 1, 0, 0, 1 );
		Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

		getScreen().render(Gdx.graphics.getDeltaTime());

		if (Gdx.input.isKeyPressed(Input.Keys.NUM_1))
		{
			setScreen(title);
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
			setScreen(gameplay);
		}


	}

	public void setState(int stateID)
	{
		switch(stateID)
		{
			case STATE_TITLE_ID -> setScreen(title);
			case STATE_GAMEPLAY_ID -> setScreen(gameplay);
		};
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