package model;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Square extends Shape {
	double size;
	
	public Square(Color c) {
		color = c;
		size = 0;
	}
	
	public double size() {
		return size;
	}
	
	public void setSize(double s) {
		size = s;
	}

	@Override
	public boolean PointInShape(Point2D p) {
		if(Math.abs(p.getX()) <= size/2 && Math.abs(p.getY()) <= size/2) {
			return true;
		}
		return false;
	}

	@Override
	public ArrayList<Point2D> getHandles() {
		ArrayList<Point2D> handles = new ArrayList<Point2D>();
		handles.add(new Point2D.Double(-size/2, -size/2));
		handles.add(new Point2D.Double(size/2, -size/2));
		handles.add(new Point2D.Double(-size/2, size/2));
		handles.add(new Point2D.Double(size/2, size/2));
		return handles;
	}

	@Override
	public Point2D getRotationHandle() {
		return new Point2D.Double(0, -((size/2)+16+8));
	}
}
