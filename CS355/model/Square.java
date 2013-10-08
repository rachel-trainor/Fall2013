package model;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class Square extends Shape {
	int size;
	
	public Square(Color c) {
		color = c;
		size = 0;
	}
	
	public int size() {
		return size;
	}
	
	public void setSize(int s) {
		size = s;
	}

	@Override
	public boolean pointInShape(Point p) {
		if(Math.abs(p.x) <= size/2 && Math.abs(p.y) <= size/2) {
			return true;
		}
		return false;
	}

	@Override
	public ArrayList<Point> getHandles() {
		ArrayList<Point> handles = new ArrayList<Point>();
		handles.add(new Point(-size/2, -size/2));
		handles.add(new Point(size/2, -size/2));
		handles.add(new Point(-size/2, size/2));
		handles.add(new Point(size/2, size/2));
		return handles;
	}

	@Override
	public Point getRotationHandle() {
		return new Point(0, -((size/2)+16+8));
	}
}
