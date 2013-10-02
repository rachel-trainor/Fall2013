package model;

import java.awt.Color;
import java.awt.Point;

public class Circle extends Shape {
	int radius;
	
	public Circle(Color c, Point p) {
		color = c;
		offset = new Offset(p.x, p.y);
	}
	
	public int radius() {
		return radius;
	}
	
	public void setRadius(int r) {
		radius = r;
	}

	@Override
	public boolean pointInShape(Point p) {
		if(Math.abs(p.x) > radius || Math.abs(p.y) > radius) {
			return false;
		} else if(p.x*p.x + p.y*p.y < radius*radius) {
			return true;
		}
		return false;
	}
	
}
