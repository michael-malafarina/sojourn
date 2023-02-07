package com.sojourn.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Sojourn extends Game
{

	public static final int WIDTH = 800;
	public static final int HEIGHT = 480;

	public Viewport gamePort;
	public OrthographicCamera gameCam;

	public Viewport hudPort;
	public OrthographicCamera hudCam;

	public SpriteBatch batch;
	Texture img;
	BitmapFont font;

	StateTitle title;
	StateGameplay gameplay;

	private FreeTypeFontGenerator fontGenerator;
	private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;


	public float getAverageRatio()
	{
		return (Gdx.graphics.getDisplayMode().width / WIDTH + Gdx.graphics.getDisplayMode().height / HEIGHT)  / 2;
	}

	public float getWidthRatio()
	{
		return Gdx.graphics.getDisplayMode().width / WIDTH;
	}

	public float getHeightRatio()
	{
		return Gdx.graphics.getDisplayMode().height / HEIGHT;
	}

	@Override
	public void create()
	{
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		gameplay = new StateGameplay(this);
		title = new StateTitle(this);


		this.setScreen(title);


		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("TCM.ttf"));
		fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		fontParameter.size = (int) (16 * getAverageRatio());
		fontParameter.borderWidth = 1;
		fontParameter.borderColor = Color.BLACK;
		fontParameter.color = Color.WHITE;
		font = fontGenerator.generateFont(fontParameter);
		font.getData().setScale(1.0f/getAverageRatio());
		font.setUseIntegerPositions(false);

//	System.out.println("Font Size: " + fontParameter.size);


		setupCamera();

	}

	public void setupCamera()
	{

		// Create the camera and port for GAMEPLAY objects
		gameCam = new OrthographicCamera(WIDTH, HEIGHT);
		gamePort = new FitViewport(WIDTH, HEIGHT, gameCam);
		gameCam.setToOrtho(false);

		// Create the camera and port for USER INTERFACE objects
		hudCam = new OrthographicCamera(WIDTH, HEIGHT);
		hudPort = new FitViewport(WIDTH, HEIGHT, hudCam);
		hudCam.setToOrtho(false);

		// Need to wait to go to fullscreen until after start to make scaling work
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

		// a comment was just added what
	}




	@Override
	public void render()
	{
		super.render();

		getScreen().render(Gdx.graphics.getDeltaTime());

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
	public void dispose() {
		batch.dispose();
		font.dispose();
	}

	public void resize(int width, int height) {
		gamePort.update(width, height);
		hudPort.update(width, height);
	}
}