package shapes;

import java.awt.Color;
import java.awt.Point;

import lab1.Shapes.Type;

public class Rectangle extends Shape {
	
	public Rectangle(Color c, Point tl) {
	    color = c;
	    topLeft = tl;
	    bottomRight = tl;
	    type = Type.RECTANGLE;
	}
	
	public Rectangle(Color c, Point tl, Point br) {
	    color = c;
	    topLeft = tl;
	    bottomRight = br;
	    type = Type.RECTANGLE;
	}
}
