package shapes;

import java.awt.Color;
import java.awt.Point;

import lab1.Shapes.Type;

public class Circle extends Ellipse {
	
	public Circle(Color c, Point start) {
		super(c, start);
		type = Type.CIRCLE;
	}

}
