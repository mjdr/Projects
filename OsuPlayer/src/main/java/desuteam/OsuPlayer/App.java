package desuteam.OsuPlayer;

import javax.swing.JFrame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import desuteam.OsuPlayer.uiGDX.GdxApp;
import desuteam.OsuPlayer.uiSwing.MainPanel;
import desuteam.OsuPlayer.uiSwing.PlayerMP3;

public class App 
{
    public App(String[] args) {
    	libGdxStart(args);
    	//swingStart(args);
	}
    
    private void swingStart(String[] args){
    	
    	MainPanel panel = new MainPanel(args);
    	new Controller(args,panel,new PlayerMP3());
    	panel.init();
    	
    	JFrame frame = new JFrame("Osu player");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setResizable(true);
    	frame.getContentPane().add(panel);
    	frame.setUndecorated(true);
    	frame.pack();
    	frame.setVisible(true);
    	
    }
    
    private void libGdxStart(String[] args){
    	
    	LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();

    	cfg.width = Integer.parseInt(args[1]);
    	cfg.height = Integer.parseInt(args[2]);
    	
    	cfg.title = "Osu player";
    	cfg.resizable = false;
    	cfg.gles30ContextMajorVersion = 3;
    	cfg.gles30ContextMinorVersion = 2;
    	
    	new LwjglApplication(new GdxApp(args), cfg);
    	
    }
    
    public static void main(String[] args){
    	
    	if(args.length != 3){
    		System.out.println("Run program with 3 args:\n 1.Path to osu sound dir.\n 2.Width.\n 3.Height.");
    		System.exit(0);
    	}
    	
    	new App(args);
    }
}
