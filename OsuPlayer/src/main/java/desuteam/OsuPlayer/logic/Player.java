package desuteam.OsuPlayer.logic;

import java.io.File;

public interface Player {
	public void load(File file) throws Exception;
	public void play();
	public void stop();
	public void pause();
	public boolean isEnd();
	public boolean isPaused();
	public boolean isLoading();
}
