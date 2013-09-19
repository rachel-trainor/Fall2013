package shapes;

import java.awt.Color;
import java.awt.Point;

import lab1.Shapes.Type;

public class Square extends Rectangle {
	
	public Square(Color c, Point tl) {
		super(c, tl);
		type = Type.SQUARE;
	}

	public Square(Color c, Point tl, Point br) {
		super(c, tl, br);
		type = Type.SQUARE;
	}

}
