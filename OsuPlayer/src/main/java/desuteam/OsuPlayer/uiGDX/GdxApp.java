package desuteam.OsuPlayer.uiGDX;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

import desuteam.OsuPlayer.Controller;

public class GdxApp implements ApplicationListener {
	
	private PlayState playState;

	public static int WIDTH,HEIGHT;
	
	public GdxApp(String[] args) {
		WIDTH = Integer.parseInt(args[1]);
		HEIGHT = Integer.parseInt(args[2]);
		playState = new PlayState();
		new Controller(args,playState,new GdxPlayer());
	}
	
	
	@Override
	public void create() {
		playState.init();
		playState.show();
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void render() {
		playState.update(Gdx.graphics.getDeltaTime());
		playState.render();
	}

	@Override
	public void pause() {
		playState.hide();
	}

	@Override
	public void resume() {
		playState.show();
	}

	@Override
	public void dispose() {
		playState.hide();
		playState.dispose();
	}

}
