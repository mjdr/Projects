package ss;

class RGB {

	int r, g, b;

	public RGB(int r, int g, int b) {
		super();
		this.r = r;
		this.g = g;
		this.b = b;
	}

	RGB(int rgb) {
		r = (rgb & 0x00FF0000) >> 16;
		g = (rgb & 0x0000FF00) >> 8;
		b = (rgb & 0x000000FF) >> 0;
	}

	int getRGB() {
		return (r >> 16) | (g << 8) | b;
	}

	RGB minus(RGB rgb) {
		return new RGB(r - rgb.r, g - rgb.g, b - rgb.b);
	}

	RGB abs() {
		return new RGB(Math.abs(r), Math.abs(g), Math.abs(b));
	}

	double module() {
		return Math.sqrt(r * r + g * g + b * b);
	}

	@Override
	public String toString() {
		return String.format("r: %d; g: %d; b: %d", r, g, b);
	}
}