
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;


public class SkyrimBot {

	public static void main(String[] args) throws Exception 
	{
		Robot r = new Robot();
		r.delay(60 * 1000);
		r.delay(10000);
		for(int i = 0;i < 10000;i++)
		{
			if(i % 10 == 0)
				pressKey(r, KeyEvent.VK_F5);
			pressKey(r, KeyEvent.VK_2);
			r.delay(75);
			pressKey(r, KeyEvent.VK_2);
			
			r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			r.mousePress(InputEvent.BUTTON3_DOWN_MASK);
			r.delay(1900);
			r.mouseRelease(InputEvent.BUTTON1_MASK);
			r.mouseRelease(InputEvent.BUTTON3_MASK);
			r.delay(10);
			
			pressKey(r, KeyEvent.VK_1);
			r.delay(75);
			pressKey(r, KeyEvent.VK_1);
			r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			r.mousePress(InputEvent.BUTTON3_DOWN_MASK);
			r.delay(5500);
			r.mouseRelease(InputEvent.BUTTON1_MASK);
			r.mouseRelease(InputEvent.BUTTON3_MASK);
			r.delay(75);
			
		}
		
	}
	
	public static void pressKey(Robot r , int key)
	{
		r.keyPress(key);
		r.delay(75);
		r.keyRelease(key);
	}

}
