package model;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class Circle extends Shape {
	int radius;
	
	public Circle(Color c) {
		color = c;
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
	
	@Override
	public ArrayList<Point> getHandles() {
		ArrayList<Point> handles = new ArrayList<Point>();
		handles.add(new Point(-radius, -radius));
		handles.add(new Point(radius, -radius));
		handles.add(new Point(-radius, radius));
		handles.add(new Point(radius, radius));
		return handles;
	}

	@Override
	public Point getRotationHandle() {
		return new Point(0, -(radius+16+8));
	}
}
