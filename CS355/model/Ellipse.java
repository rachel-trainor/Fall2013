package model;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class Ellipse extends Shape {
	int height;
	int width;
	
	public Ellipse(Color c) {
	    color = c;
	    height = 0;
	    width = 0;
	}
	
	public int height() {
		return height;
	}
	
	public int width() {
		return width;
	}
	
	public void setHeight(int h) {
		height = h;
	}
	
	public void setWidth(int w) {
		width = w;
	}

	@Override
	public boolean pointInShape(Point p) {
		double rx2 = (width/2)*(width/2);
		double ry2 = (height/2)*(height/2);
		if(Math.abs(p.x) > width/2 || Math.abs(p.y) > height/2) {
			return false;
		} else if(p.x*p.x*ry2 + p.y*p.y*rx2 <= rx2*ry2) {
			return true;
		}
		return false;
	}
	
	@Override
	public ArrayList<Point> getHandles() {
		ArrayList<Point> handles = new ArrayList<Point>();
		handles.add(new Point(-width/2, -height/2));
		handles.add(new Point(width/2, -height/2));
		handles.add(new Point(-width/2, height/2));
		handles.add(new Point(width/2, height/2));
		return handles;
	}

	@Override
	public Point getRotationHandle() {
		return new Point(0, -((height/2)+16+8));
	}
}
