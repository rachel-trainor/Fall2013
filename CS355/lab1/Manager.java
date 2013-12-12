package lab1;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import model.Shape;

import resources.GUIFunctions;

public class Manager {
	private Color color = Color.WHITE;
	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	public enum Type { RECTANGLE, SQUARE, ELLIPSE, CIRCLE, LINE, TRIANGLE, SELECT, NONE };
	private Type shapeType = Type.RECTANGLE;
	private Shape currentlySelected = null;
	private double zoom = 1.0;
	private double xscroll = 0.0;
	private double yscroll = 0.0;
	boolean showImage = false;
	protected Image image;
	
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
	
	int size() {
		return shapes.size();
	}
	
	Shape selected() {
		return currentlySelected;
	}
	
	void setSelected(Shape s) {
		currentlySelected = s;
	}
	
	public int selectedIndex() {
		if(currentlySelected == null) {
			return -1;
		}
		return shapes.indexOf(currentlySelected);
	}
	
	public double zoom() {
		return zoom;
	}
	
	public void setZoom(double z) {
		zoom = z;
	}
	
	public double xscroll() {
		return xscroll;
	}
	
	public void setXScroll(double x) {
		xscroll = x;
	}
	
	public double yscroll() {
		return yscroll;
	}
	
	public void setYScroll(double y) {
		yscroll = y;
	}
	
	public void toggleImage() {
		showImage = !showImage;
	}
	
	public void setImage(BufferedImage bi) {
		int h = bi.getHeight();
		int w = bi.getWidth();
		int[][] p = new int[h][w];
			
		for (int row = 0; row < h; row++) {
			for (int col = 0; col < w; col++) {
				p[row][col] = new Color(bi.getRGB(col, row)).getBlue();
			}
		}
		image = new Image(h, w, p);
	}
}
