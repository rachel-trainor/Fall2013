package shapes;

import java.awt.Color;
import java.awt.Point;

import lab1.Shapes.Type;

public class Triangle extends Shape {
	Point p1;
	Point p2;
	Point p3;
	
	public Triangle(Color c, Point p) {
		color = c;
		p1 = p;
		p2 = p;
		p3 = p;
		type = Type.TRIANGLE;
	}
	
	public Triangle(Color c, Point point1, Point point2, Point point3) {
		color = c;
		p1 = point1;
		p2 = point2;
		p3 = point3;
		type = Type.TRIANGLE;
	}
	
	public Point getPointOne() {
		return p1;
	}
	
	public Point getPointTwo() {
		return p2;
	}
	
	public Point getPointThree() {
		return p3;
	}
	
	public void setPointOne(Point newpoint) {
		p1 = newpoint;
	}
	
	public void setPointTwo(Point newpoint) {
		p2 = newpoint;
	}
	
	public void setPointThree(Point newpoint) {
		p3 = newpoint;
	}
	
	public int[] getXs() {
		return new int[]{p1.x, p2.x, p3.x};
	}
	
	public int[] getYs() {
		return new int[]{p1.y, p2.y, p3.y};
	}
}
