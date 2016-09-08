package desuteam.OsuPlayer;

import java.io.File;

import desuteam.OsuPlayer.logic.Library;
import desuteam.OsuPlayer.logic.Player;

public class Controller {
	private Library model;
	private UIView view;
	private Player player;
	
	public Controller(String[] args,UIView view,Player player) {
		this.player = player;
		model = new Library(args);
		this.view = view;
		view.setController(this);
	}

	public Library getModel() {
		return model;
	}

	public UIView getView() {
		return view;
	}

	public void initLibrary() {
		model.load();
	}

	public File getCurrentBackground() {
		return model.getCurrentMapData().getBackground();
	}
	public String getCurrentTitle() {
		return model.getCurrentMapData().getTitle();
	}

	public void nextTrack() {
		player.stop();
		model.nextTrack();
		view.updateTrackInfo();
		
		playCurrent();
	}

	public void prevTrack() {
		player.stop();
		model.prevTrack();
		view.updateTrackInfo();
		
		playCurrent();
	}

	public void playCurrent() {
		try {
			player.load(model.getCurrentMapData().getMusicFile());
			player.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void update(float dt){
			if(player.isEnd() && !player.isLoading())
				nextTrack();
		
	}

	public void pauseOrPlay() {
		if(player.isPaused())
			player.play();
		else
			player.pause();
		
	}
	
	public int getCurrent(){
		return model.getCurrent();
	}
	public int getAll(){
		return model.getMapData().length;
	}
}
