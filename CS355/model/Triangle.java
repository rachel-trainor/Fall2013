package model;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Triangle extends Shape {
	Point2D p1; // relative to the center of the triangle
	Point2D p2; // relative to the center of the triangle
	Point2D p3; // relative to the center of the triangle
	
	public Triangle(Color c) {
		color = c;
		p3 = p2 = p1 = new Point2D.Double(0,0);
	}
	
	public Point2D p1() {
		return p1;
	}
	
	public Point2D p2() {
		return p2;
	}
	
	public Point2D p3() {
		return p3;
	}
	
	public void setP1(Point2D newPoint2D) {
		p1 = newPoint2D;
	}
	
	public void setP2(Point2D newPoint2D) {
		p2 = newPoint2D;
	}
	
	public void setP3(Point2D newPoint2D) {
		p3 = newPoint2D;
	}

	@Override
	public boolean PointInShape(Point2D p) {
		double ABC = Math.abs (p1.getX() * (p2.getY() - p3.getY()) + p2.getX() * (p3.getY() - p1.getY()) + p3.getX() * (p1.getY() - p2.getY()));
		double ABP = Math.abs (p1.getX() * (p2.getY() - p.getY()) + p2.getX() * (p.getY() - p1.getY()) + p.getX() * (p1.getY() - p2.getY()));
		double APC = Math.abs (p1.getX() * (p.getY() - p3.getY()) + p.getX() * (p3.getY() - p1.getY()) + p3.getX() * (p1.getY() - p.getY()));
		double PBC = Math.abs (p.getX() * (p2.getY() - p3.getY()) + p2.getX() * (p3.getY() - p.getY()) + p3.getX() * (p.getY() - p2.getY()));

		if(ABP + APC + PBC == ABC) {
			return true;
		}
		return false;
	}

	@Override
	public ArrayList<Point2D> getHandles() {
		ArrayList<Point2D> handles = new ArrayList<Point2D>();
		handles.add(p1);
		handles.add(p2);
		handles.add(p3);
		return handles;
	}

	@Override
	public Point2D getRotationHandle() {
		double y = Math.min(p1.getY(), p2.getY());
		y = Math.min(y, p3.getY());
		return new Point2D.Double(0, y-20);
	}
}
