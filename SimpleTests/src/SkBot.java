import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;


public class SkBot {
	
	public static void main(String[] args) throws Exception
	{
		Thread.sleep(90 * 1000);
		
		Robot r = new Robot();

		for(int j = 0;j<1000;j++)
		{
			for(int i = 0;i < 140/4;i++)
			{
				Point p = MouseInfo.getPointerInfo().getLocation();
				r.mouseMove(p.x, p.y + 20);
				r.delay(34);
			}
			press(r, KeyEvent.VK_E);
			r.delay(35*1000);
			if(j%100 == 0)
			{
				save(r);
			}
		}
		
		
		
		
	}
	public static void save(Robot r)
	{
		press(r, KeyEvent.VK_ESCAPE);
		press(r, KeyEvent.VK_ENTER);
		press(r, KeyEvent.VK_S);
		press(r, KeyEvent.VK_S);
		press(r, KeyEvent.VK_ENTER);
		press(r, KeyEvent.VK_ENTER);
		r.delay(3*1000);
		
		press(r, KeyEvent.VK_ESCAPE);
		press(r, KeyEvent.VK_ESCAPE);
	}
	public static void press(Robot r , int key) 
	{
		r.keyPress(key);
		r.delay(50);
		r.keyRelease(key);
		r.delay(500);
	}
}
