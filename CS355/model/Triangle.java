package model;

import java.awt.Color;
import java.awt.Point;

public class Triangle extends Shape {
	Point p1;
	Point p2;
	Point p3;
	
	public Triangle(Color c, Point p) {
		color = c;
		p1 = p;
		p2 = p;
		p3 = p;
	}
	
	public Point p1() {
		return p1;
	}
	
	public Point p2() {
		return p2;
	}
	
	public Point p3() {
		return p3;
	}
	
	public void setP1(Point newpoint) {
		p1 = newpoint;
	}
	
	public void setP2(Point newpoint) {
		p2 = newpoint;
	}
	
	public void setP3(Point newpoint) {
		p3 = newpoint;
	}
}
