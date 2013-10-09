package model;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public abstract class Shape {
	Color color;
	Offset offset = new Offset(0, 0); // x, y (both ints)
	double rotation = 0.0; // in radians
	
	public void setColor(Color newcolor) {
		color = newcolor;
	}
	
	public Color color() {
		return color;
	}
	
	public void setOffset(double newx, double newy) {
		offset.setX(newx);
		offset.setY(newy);
	}
	
	public Offset offset() {
		return offset;
	}
	
	public void setRotation(double a) {
		rotation = a;
	}
	
	public double rotation() {
		return rotation;
	}
	
	public class Offset {
		double x;
		double y;
		
		public Offset(double newx, double newy) {
			x = newx;
			y = newy;
		}
		
		public double x() {
			return x;
		}
		
		public void setX(double newx) {
			x = newx;
		}
		
		public double y() {
			return y;
		}
		
		public void setY(double newy) {
			y = newy;
		}
	}
	
	public abstract boolean PointInShape(Point2D p);
	
	public abstract ArrayList<Point2D> getHandles();
	
	public abstract Point2D getRotationHandle();
}
