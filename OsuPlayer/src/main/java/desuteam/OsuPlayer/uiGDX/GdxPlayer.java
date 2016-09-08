package desuteam.OsuPlayer.uiGDX;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import desuteam.OsuPlayer.logic.Player;

public class GdxPlayer implements Player {

	private Music music;
	private boolean end = false;
	
	@Override
	public void load(File file) throws Exception {
		end = false;
		if(music != null)
			music.dispose();
		music = Gdx.audio.newMusic(Gdx.files.absolute(file.getAbsolutePath()));
		music.setOnCompletionListener((music)-> end = true);
	}

	@Override
	public void play() {
		music.play();
	}

	@Override
	public void stop() {
		music.stop();
	}

	@Override
	public void pause() {
		music.pause();
	}

	@Override
	public boolean isEnd() {
		return end;
	}

	@Override
	public boolean isPaused() {
		return !music.isPlaying();
	}

	@Override
	public boolean isLoading() {
		return false;
	}

}
