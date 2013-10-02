package model;

import java.awt.Color;
import java.awt.Point;

public class Ellipse extends Shape {
	int height;
	int width;
	
	public Ellipse(Color c, Point p) {
	    color = c;
	    height = 0;
	    width = 0;
	}
	
	public int height() {
		return height;
	}
	
	public int width() {
		return width;
	}
	
	public void setHeight(int h) {
		height = h;
	}
	
	public void setWidth(int w) {
		width = w;
	}
}
