package shapes;

import java.awt.Color;
import java.awt.Point;

import lab1.Shapes.Type;

public abstract class Shape {
	Color color;
	Type type;
	
	public void setColor(Color newcolor) {
		color = newcolor;
	}
	public Color getColor() {
		return color;
	}
	public Type getType() {
		return type;
	}
	
	Point topLeft;
	Point bottomRight;
	
	public Point getTopLeft() {
		return topLeft;
	}
	
	public void setTopLeft(Point tl) {
		topLeft = tl;
	}
	
	public Point getBottomRight() {
		return bottomRight;
	}
	
	public void setBottomRight(Point br) {
		bottomRight = br;
	}
	
	public int getHeight() {
		return bottomRight.y-topLeft.y;
	}
	
	public int getWidth() {
		return bottomRight.x-topLeft.x;
	}
	
	public String toString() {
		return "" + type + " (" + topLeft.x + "," + topLeft.y + ") height:" + getHeight() + " width:" + getWidth();
	}
	
	public void prettyPrint() {
		System.out.printf("%s (%d,%d) height:%d width:%d", type, topLeft.x, topLeft.y, getHeight(), getWidth());
	}
}
