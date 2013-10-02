package model;

import java.awt.*;

public class Line extends Shape {
	Point start;
	Point end;
	double TOLERANCE = 4.0;
	
	public Line(Color c, Point p) {
	    color = c;
	    start = p;
	    end = p;
	}
	
	public Line(Color c, Point p1, Point p2) {
	    color = c;
	    start = p1;
	    end = p2;
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
	
}
