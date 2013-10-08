package model;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class Triangle extends Shape {
	Point p1; // relative to the center of the triangle
	Point p2; // relative to the center of the triangle
	Point p3; // relative to the center of the triangle
	
	public Triangle(Color c) {
		color = c;
		p3 = p2 = p1 = new Point(0,0);
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

	@Override
	public ArrayList<Point> getHandles() {
		ArrayList<Point> handles = new ArrayList<Point>();
		handles.add(p1);
		handles.add(p2);
		handles.add(p3);
		return handles;
	}

	@Override
	public Point getRotationHandle() {
		int y = Math.min(p1.y, p2.y);
		y = Math.min(y, p3.y);
		return new Point(0, y-20);
	}
}
