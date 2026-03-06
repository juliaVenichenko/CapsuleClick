package com.mygdx.game;

import static com.mygdx.game.GameSettings.SCR_HEIGHT;
import static com.mygdx.game.GameSettings.SCR_WIDTH;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.screens.BackgroundsScreen;
import com.mygdx.game.screens.ColorsScreen;
import com.mygdx.game.screens.CustomizationScreen;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.HatsScreen;
import com.mygdx.game.screens.PowerScreen;
import com.mygdx.game.screens.ShopScreen;
import com.mygdx.game.screens.UpgradeScreen;

public class MyGdxGame extends Game {
	public SpriteBatch batch;
	public World world;
	public OrthographicCamera camera;
	public BitmapFont defaultFont;
	public BitmapFont scoreFont;
	public Vector3 touch;
	public GameScreen gameScreen;
	public ShopScreen shopScreen;
	public CustomizationScreen customizationScreen;
	public UpgradeScreen upgradeScreen;
	public HatsScreen hatsScreen;
	public ColorsScreen colorsScreen;
	public BackgroundsScreen backgroundsScreen;
	public PowerScreen powerScreen;

	@Override
	public void create () {
		Box2D.init();
		world = new World(new Vector2(0, 0), true);
		batch = new SpriteBatch();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, GameSettings.SCR_WIDTH, GameSettings.SCR_HEIGHT);

		defaultFont = FontBuilder.generate(30, Color.WHITE, GameResources.FONT_MULTI);
		scoreFont = FontBuilder.generate(60, Color.YELLOW, GameResources.FONT);

		gameScreen = new GameScreen(this);
		shopScreen = new ShopScreen(this);
		customizationScreen = new CustomizationScreen(this);
		upgradeScreen = new UpgradeScreen(this);
		hatsScreen = new HatsScreen(this);
		colorsScreen = new ColorsScreen(this);
		backgroundsScreen = new BackgroundsScreen(this);
		powerScreen = new PowerScreen(this);

		setScreen(gameScreen);
	}

	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();

		gameScreen.dispose();
		shopScreen.dispose();
		customizationScreen.dispose();
		upgradeScreen.dispose();
		hatsScreen.dispose();
		colorsScreen.dispose();
		backgroundsScreen.dispose();
		powerScreen.dispose();
	}
}
