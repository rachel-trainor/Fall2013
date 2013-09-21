package shapes;

import java.awt.Color;
import java.awt.Point;

public class Ellipse extends Shape {
	Point center;
	int height;
	int width;
	
	public Ellipse(Color c, Point p) {
	    color = c;
	    center = p;
	    height = 0;
	    width = 0;
	}
	
	public Point center() {
		return center;
	}
	
	public void setCenter(Point p) {
		center = p;
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
	
	public Point uL() {
		return new Point(center.x-(width/2), center.y-(height/2));
	}
}
