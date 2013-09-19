package shapes;

import java.awt.Color;
import java.awt.Point;

import lab1.Shapes.Type;

public class Ellipse extends Shape {
	
	public Ellipse(Color c, Point tl) {
	    color = c;
	    topLeft = tl;
	    bottomRight = tl;
	    type = Type.ELLIPSE;
	}
	
	public Ellipse(Color c, Point tl, Point br) {
	    color = c;
	    topLeft = tl;
	    bottomRight = br;
	    type = Type.ELLIPSE;
	}
}
