package model;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Line extends Shape {
	Point2D start;
	Point2D end;
	double TOLERANCE = 4.0;
	
	public Line(Color c) {
	    color = c;
	    end = start = new Point2D.Double(0,0);
	}
	
	public Point2D p1() {
		return start;
	}
	
	public Point2D p2() {
		return end;
	}
	
	public void setP1(Point2D p) {
		start = p;
	}
	
	public void setP2(Point2D p) {
		end = p;
	}

	@Override
	public boolean PointInShape(Point2D p) {
		Point2D mid = new Point2D.Double(end.getX() - start.getX(), end.getY() - start.getY());
	    double magnitude = mid.getX()*mid.getX() + mid.getY()*mid.getY();
	    double unitVector = ((p.getX() - start.getX()) * mid.getX() + (p.getY() - start.getY()) * mid.getY()) / magnitude;
	
	    if (unitVector > 1)
	        unitVector = 1;
	    else if (unitVector < 0)
	        unitVector = 0;
	
	    double x = start.getX() + unitVector * mid.getX();
	    double y = start.getY() + unitVector * mid.getY();
	    double dx = x - p.getX();
	    double dy = y - p.getY();
	
	    double dist = Math.sqrt(dx*dx + dy*dy);
	    if(dist < TOLERANCE) {
	    	return true;
	    }
	
	    return false;
	}
	
	@Override
	public ArrayList<Point2D> getHandles() {
		ArrayList<Point2D> handles = new ArrayList<Point2D>();
		handles.add(start);
		handles.add(end);
		return handles;
	}

	@Override
	public Point2D getRotationHandle() {
		double y = Math.max(start.getY(), end.getY());
		return new Point2D.Double(0, -y+8);
	}
}
