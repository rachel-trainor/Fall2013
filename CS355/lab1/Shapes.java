package lab1;

import java.awt.Color;
import java.util.ArrayList;

import shapes.Shape;

public class Shapes {
	private Color color = Color.WHITE;
	protected ArrayList<Shape> shapes = new ArrayList<Shape>();
	public enum Type { RECTANGLE, SQUARE, ELLIPSE, CIRCLE, LINE, TRIANGLE, NONE };
	private Type shapeType = Type.RECTANGLE;
	
	void setColor(Color c) {
		color = c;
	}
	
	Color getColor() {
		return color;
	}
	
	void setType(Type t) {
		shapeType = t;
	}
	
	Type getType() {
		return shapeType;
	}

}
