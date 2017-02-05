package desuteam.neiral;

public class RGB {
	public float r,g,b;
	
	public RGB(int rgb) {
		int r = (rgb & 0xFF0000) >> 16;
		int g = (rgb & 0x00FF00) >>  8;
		int b = (rgb & 0x0000FF) >>  0;

		this.r = r/255.0f;
		this.g = g/255.0f;
		this.b = b/255.0f;
	}
	
	public RGB(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public RGB add(RGB rgb){
		return new RGB(r + rgb.r, g + rgb.g, b + rgb.b);
	}
	public RGB substruct(RGB rgb){
		return new RGB(r - rgb.r, g - rgb.g, b - rgb.b);
	}
	public RGB multiplay(float k){
		return new RGB(r * k, g * k, b * k);
	}
	public RGB multiplay(RGB rgb){
		return new RGB(r * rgb.r, g * rgb.g, b * rgb.b);
	}
	
	
	public void clip(){
		if(r < 0) r = 0;
		else if(r > 1) r = 1;

		if(g < 0) g = 0;
		else if(g > 1) g = 1;

		if(b < 0) b = 0;
		else if(b > 1) b = 1;
	}
	
	public int rgb(){
		int r = (int)(this.r * 255);
		int g = (int)(this.g * 255);
		int b = (int)(this.b * 255);
		
		return (r << 16) | (g << 8) | b;
	}
	
	@Override
	public String toString() {
		return String.format("(r: %.2f, g: %.2f, b: %.2f)", r,g,b);
	}
	
}
