package shapes;

import java.awt.Color;
import java.awt.Point;

import lab1.Shapes.Type;

public class Line extends Shape {
	
	public Line(Color c, Point start) {
	    color = c;
	    topLeft = start;
	    bottomRight = start;
	    type = Type.LINE;
	}
	
	public Line(Color c, Point start, Point end) {
	    color = c;
	    topLeft = start;
	    bottomRight = end;
	    type = Type.LINE;
	}
}
