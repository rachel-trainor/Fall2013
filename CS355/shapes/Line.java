package shapes;

import java.awt.Color;
import java.awt.Point;

public class Line extends Shape {
	Point start;
	Point end;
	
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
	
}
