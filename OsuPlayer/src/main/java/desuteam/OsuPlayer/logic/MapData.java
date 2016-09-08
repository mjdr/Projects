package desuteam.OsuPlayer.logic;

import java.io.File;

public class MapData {
	private File folder;
	private String title;
	private File background;
	private File musicFile;
	
	public MapData(File folder,String title, File background, File musicFile) {
		this.title = title;
		this.background = background;
		this.musicFile = musicFile;
		this.folder = folder;
	}

	public String getTitle() {
		return title;
	}

	public File getBackground() {
		return background;
	}

	public File getMusicFile() {
		return musicFile;
	}
	
	public File getFolder() {
		return folder;
	}
}
