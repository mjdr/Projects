package desuteam.OsuPlayer.uiSwing;

public class Animation {
	
	public interface AnimationListener{
		void before();
		void doAnimation(float k);
		void after();
	};
	
	private final float time;
	private float timer = -1;
	private AnimationListener listener;
	private boolean end,act;
	
	public Animation(AnimationListener listener, float duration) {
		this.listener = listener;
		time = duration;
	}

	public void start(){
		timer = 0;
		listener.before();
		act = true;
		end = false;
		
	}
	public void stop(){
		listener.after();
		act = false;
		end = true;
	}
	
	public void update(float dt){
		if(timer != -1){
			timer += dt;
			if(timer > time){
				timer = -1;
				listener.doAnimation(1);
				listener.after();
				act = false;
				end = true;
			}
			else{
				listener.doAnimation(timer/time);
				act = true;
				end = false;
			}
		}
	}
	
	public boolean isAct() {
		return act;
	}
	public boolean isEnd() {
		return end;
	}
}
