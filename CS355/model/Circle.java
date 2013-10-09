package model;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Circle extends Shape {
	double radius;
	
	public Circle(Color c) {
		color = c;
	}
	
	public double radius() {
		return radius;
	}
	
	public void setRadius(double r) {
		radius = r;
	}

	@Override
	public boolean PointInShape(Point2D p) {
		if(Math.abs(p.getX()) > radius || Math.abs(p.getY()) > radius) {
			return false;
		} else if(p.getX()*p.getX() + p.getY()*p.getY() < radius*radius) {
			return true;
		}
		return false;
	}
	
	@Override
	public ArrayList<Point2D> getHandles() {
		ArrayList<Point2D> handles = new ArrayList<Point2D>();
		handles.add(new Point2D.Double(-radius, -radius));
		handles.add(new Point2D.Double(radius, -radius));
		handles.add(new Point2D.Double(-radius, radius));
		handles.add(new Point2D.Double(radius, radius));
		return handles;
	}

	@Override
	public Point2D getRotationHandle() {
		return new Point2D.Double(0, -(radius+16+8));
	}
}
