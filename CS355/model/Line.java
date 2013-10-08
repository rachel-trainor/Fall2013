package model;

import java.awt.*;
import java.util.ArrayList;

public class Line extends Shape {
	Point start;
	Point end;
	double TOLERANCE = 4.0;
	
	public Line(Color c) {
	    color = c;
	    end = start = new Point(0,0);
	}
	
	public Point p1() {
		return start;
	}
	
	public Point p2() {
		return end;
	}
	
	public void setP1(Point p) {
		start = p;
	}
	
	public void setP2(Point p) {
		end = p;
	}

	@Override
	public boolean pointInShape(Point p) {
		Point mid = new Point(end.x - start.x, end.y - start.y);
	    double magnitude = mid.x*mid.x + mid.y*mid.y;
	    double unitVector = ((p.x - start.x) * mid.x + (p.y - start.y) * mid.y) / magnitude;
	
	    if (unitVector > 1)
	        unitVector = 1;
	    else if (unitVector < 0)
	        unitVector = 0;
	
	    double x = start.x + unitVector * mid.x;
	    double y = start.y + unitVector * mid.y;
	    double dx = x - p.x;
	    double dy = y - p.y;
	
	    double dist = Math.sqrt(dx*dx + dy*dy);
	    if(dist < TOLERANCE) {
	    	return true;
	    }
	
	    return false;
	}
	
	@Override
	public ArrayList<Point> getHandles() {
		ArrayList<Point> handles = new ArrayList<Point>();
		handles.add(start);
		handles.add(end);
		return handles;
	}

	@Override
	public Point getRotationHandle() {
		int y = Math.max(start.y, end.y);
		return new Point(0, -y+8);
	}
}
