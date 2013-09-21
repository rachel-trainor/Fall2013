package shapes;

import java.awt.Color;
import java.awt.Point;

public class Rectangle extends Shape {
	Point upperLeft;
	int height;
	int width;
	
	public Rectangle(Color c, Point ul) {
	    color = c;
	    upperLeft = ul;
	    height = 0;
	    width = 0;
	}
	
	public Point uL() {
		return upperLeft;
	}
	
	public void setUL(Point p) {
		upperLeft = p;
	}
	
	public int height() {
		return height;
	}
	
	public int width() {
		return width;
	}
	
	public void setHeight(int h) {
		height = h;
	}
	
	public void setWidth(int w) {
		width = w;
	}
}
