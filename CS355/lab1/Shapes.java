package lab1;

import java.awt.Color;
import java.util.ArrayList;

import resources.GUIFunctions;
import shapes.Shape;

public class Shapes {
	private Color color = Color.WHITE;
	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	public enum Type { RECTANGLE, SQUARE, ELLIPSE, CIRCLE, LINE, TRIANGLE, NONE };
	private Type shapeType = Type.RECTANGLE;
	
	ArrayList<Shape> list() {
		return shapes;
	}
	
	Shape get(int i) {
		return shapes.get(i);
	}
	
	void add(Shape s) {
		shapes.add(s);
	}
	
	void removeLast() {
		shapes.remove(shapes.size()-1);
	}
	
	void remove(Shape s) {
		shapes.remove(s);
	}
	
	void setColor(Color c) {
		color = c;
	}
	
	Color getColor() {
		return color;
	}
	
	void setType(Type t) {
		shapeType = t;
		GUIFunctions.refresh();
	}
	
	Type getType() {
		return shapeType;
	}

}
