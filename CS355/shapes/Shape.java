package shapes;

import java.awt.Color;

public abstract class Shape {
	Color color;
	
	public void setColor(Color newcolor) {
		color = newcolor;
	}
	public Color color() {
		return color;
	}
}
