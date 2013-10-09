package model;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Rectangle extends Shape {
	double height;
	double width;
	
	public Rectangle(Color c) {
		color = c;
	    height = 0;
	    width = 0;
	}
	
	public double height() {
		return height;
	}
	
	public double width() {
		return width;
	}
	
	public void setHeight(double h) {
		height = h;
	}
	
	public void setWidth(double w) {
		width = w;
	}

	@Override
	public boolean PointInShape(Point2D p) {
		if(Math.abs(p.getX()) <= width/2 && Math.abs(p.getY()) <= height/2) {
			return true;
		}
		return false;
	}
	
	@Override
	public ArrayList<Point2D> getHandles() {
		ArrayList<Point2D> handles = new ArrayList<Point2D>();
		handles.add(new Point2D.Double(-width/2, -height/2));
		handles.add(new Point2D.Double(width/2, -height/2));
		handles.add(new Point2D.Double(-width/2, height/2));
		handles.add(new Point2D.Double(width/2, height/2));
		return handles;
	}

	@Override
	public Point2D getRotationHandle() {
		return new Point2D.Double(0, -((height/2)+16+8));
	}
}
