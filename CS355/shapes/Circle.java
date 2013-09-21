package shapes;

import java.awt.Color;
import java.awt.Point;

public class Circle extends Shape {
	Point center;
	int radius;
	
	public Circle(Color c, Point p) {
		color = c;
		center = p;
	}

	public Point center() {
		return center;
	}
	
	public void setCenter(Point p) {
		center = p;
	}
	
	public int radius() {
		return radius;
	}
	
	public void setRadius(int r) {
		radius = r;
	}
	
	public Point uL() {
		return new Point(center.x-radius, center.y-radius);
	}
	
}
