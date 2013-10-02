package model;

import java.awt.Color;
import java.awt.Point;

public class Square extends Shape {
	int size;
	
	public Square(Color c, Point p) {
		color = c;
		size = 0;
	}
	
	public int size() {
		return size;
	}
	
	public void setSize(int s) {
		size = s;
	}

}
