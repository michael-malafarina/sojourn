package com.sojourn.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sojourn.game.display.Display;
import com.sojourn.game.state.StateGameplay;
import com.sojourn.game.state.StateTitle;

public class Sojourn extends Game
{

	public static final int WIDTH = 1920/2;
	public static final int HEIGHT = 1080/2;

	public Viewport gamePort;
	public OrthographicCamera gameCam;

	public Viewport hudPort;
	public OrthographicCamera hudCam;

	private Display display;

	Texture img;


	StateTitle title;
	StateGameplay gameplay;




	@Override
	public void create()
	{
		img = new Texture("box.png");

		gameplay = new StateGameplay(this);
		title = new StateTitle(this);
		display = new Display();

		this.setScreen(title);




		setupCamera();



	}

	public void setupCamera()
	{

		// Create the camera and port for GAMEPLAY objects
		gameCam = new OrthographicCamera(WIDTH, HEIGHT);
		gamePort = new StretchViewport(WIDTH, HEIGHT, gameCam);
		gameCam.setToOrtho(false);
		gameCam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);

		// Create the camera and port for USER INTERFACE objects
		hudCam = new OrthographicCamera(WIDTH, HEIGHT);
		hudPort = new StretchViewport(WIDTH, HEIGHT, hudCam);
		hudCam.setToOrtho(false);
		hudCam.position.set(hudPort.getWorldWidth()/2, hudPort.getWorldHeight()/2, 0);

		// Need to wait to go to fullscreen until after start to make scaling work
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

	}




	@Override
	public void render()
	{
		super.render();

		Gdx.gl.glClearColor( 1, 0, 0, 1 );
		Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

//		gameCam.update();
//		hudCam.update();

		getScreen().render(Gdx.graphics.getDeltaTime());

//		gamePort.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//		hudPort.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		gameCam.update();
//
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_1))
		{
			setScreen(title);
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.NUM_2))
		{
			setScreen(gameplay);
		}


	}

	@Override
	public void dispose()
	{
		display.dispose();
	}

	public void resize(int width, int height) {
		gamePort.update(width, height);
		hudPort.update(width, height);
	}
}