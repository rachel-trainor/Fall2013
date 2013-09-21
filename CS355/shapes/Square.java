package shapes;

import java.awt.Color;
import java.awt.Point;

public class Square extends Shape {
	Point upperLeft;
	int size;
	
	public Square(Color c, Point p) {
		color = c;
		upperLeft = p;
		size = 0;
	}
	
	public Point uL() {
		return upperLeft;
	}
	
	public void setUL(Point p) {
		upperLeft = p;
	}
	
	public int size() {
		return size;
	}
	
	public void setSize(int s) {
		size = s;
	}

}
