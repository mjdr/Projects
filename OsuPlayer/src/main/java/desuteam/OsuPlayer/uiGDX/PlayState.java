package desuteam.OsuPlayer.uiGDX;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import desuteam.OsuPlayer.Controller;
import desuteam.OsuPlayer.UIView;

public class PlayState implements UIView {
	
	private Controller controller;
	private Stage stage;
	private Background background;
	
	public PlayState() {
		background = new Background();
	}
	
	public void init(){
		controller.initLibrary();
		stage = new Stage(new ScreenViewport());
		
		
		stage.addActor(background);
		
		
		updateTrackInfo();
		controller.playCurrent();
		
		stage.setDebugAll(true);
		
		stage.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				controller.nextTrack();
			}
		});
	}
	public void show(){
		updateTrackInfo();
		
		Gdx.input.setInputProcessor(stage);
	}
	
	public void render(){
		stage.draw();
	}
	public void update(float dt){
		stage.act(dt);
		
		
		
	}
	@Override
	public void updateTrackInfo() {
		background.setImage(controller.getCurrentBackground().getAbsolutePath());
		
	}
	@Override
	public void setController(Controller controller) {
		this.controller = controller;
	}
	public void hide(){}
	public void dispose(){
		stage.dispose();
	}
}
