package desuteam.OsuPlayer.uiGDX;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class Background extends Group {
	private Image image;
	
	public Background() {
		image = new Image();
		addActor(image);
		image.setOrigin(Align.center);
	}
	
	public void setImage(String path){
		Texture texture = new Texture(Gdx.files.absolute(path));
		
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		float scale = Math.max(GdxApp.WIDTH/(float)texture.getWidth(), GdxApp.HEIGHT/(float)texture.getHeight());
		
		float realW = texture.getWidth() * scale;
		float realH = texture.getHeight() * scale;

		float realX = (GdxApp.WIDTH - realW)/2;
		float realY = (GdxApp.HEIGHT - realH)/2;
		image.setDrawable(new TextureRegionDrawable(new TextureRegion(texture)));
		
		image.setPosition(realX, realY);
		image.setSize(realW,realH);
		
	}
	
}
