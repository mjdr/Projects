package desuteam.OsuPlayer.uiSwing;

import java.io.File;

import desuteam.OsuPlayer.logic.Player;
import jaco.mp3.player.MP3Player;

public class PlayerMP3 implements Player {
	
	private MP3Player player;
	private boolean actStop,loading;
	
	public void load(File file) throws Exception{
		loading = true;
		player = new MP3Player(file);
		actStop = false;
		loading = false;
	}
	public void play(){
		player.play();
       
	}
	public void stop(){
		actStop = true;
		player.stop();
	}
	public void pause(){
		player.pause();
		
	}
	public boolean isEnd(){
		return player.isStopped() && !actStop;
	}
	public boolean isPaused() {
		return player.isPaused();
	}
	public boolean isLoading() {
		return loading;
	}
}
