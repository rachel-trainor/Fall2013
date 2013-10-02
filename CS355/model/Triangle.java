package model;

import java.awt.Color;
import java.awt.Point;

public class Triangle extends Shape {
	Point p1; // relative to the center of the triangle
	Point p2; // relative to the center of the triangle
	Point p3; // relative to the center of the triangle
	
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

	@Override
	public boolean pointInShape(Point p) {
		double ABC = Math.abs (p1.x * (p2.y - p3.y) + p2.x * (p3.y - p1.y) + p3.x * (p1.y - p2.y));
		double ABP = Math.abs (p1.x * (p2.y - p.y) + p2.x * (p.y - p1.y) + p.x * (p1.y - p2.y));
		double APC = Math.abs (p1.x * (p.y - p3.y) + p.x * (p3.y - p1.y) + p3.x * (p1.y - p.y));
		double PBC = Math.abs (p.x * (p2.y - p3.y) + p2.x * (p3.y - p.y) + p3.x * (p.y - p2.y));

		if(ABP + APC + PBC == ABC) {
			return true;
		}
		return false;
	}
}
