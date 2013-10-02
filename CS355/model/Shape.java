package model;

import java.awt.Color;

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
	
	public void setOffset(int newx, int newy) {
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
		int x;
		int y;
		
		public Offset(int newx, int newy) {
			x = newx;
			y = newy;
		}
		
		public int x() {
			return x;
		}
		
		public void setX(int newx) {
			x = newx;
		}
		
		public int y() {
			return y;
		}
		
		public void setY(int newy) {
			y = newy;
		}
	}
}
