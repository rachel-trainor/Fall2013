package lab1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

import model.*;

import resources.ViewRefresher;

public class View implements ViewRefresher {
	Shapes shapes;
	int H_SIZE = 16;
	AffineTransform selectedShapeAT;
	Shape selectedShape;
	
	View() {
		shapes = new Shapes();
	}
	
	View(Shapes s) {
		shapes = s;
	}

	@Override
	public void refreshView(Graphics2D g2d) {
		selectedShapeAT = null;
		selectedShape = null;
		g2d.setStroke(new BasicStroke(2)); // not necessary, but it looks better
		int currentlySelected = shapes.selectedIndex();
		for(int i=0; i<shapes.list().size(); i++) {
			Shape s = shapes.get(i);
			boolean isSelected = ((currentlySelected == i) ? true : false);
			AffineTransform oldXForm = g2d.getTransform();
			g2d.translate(s.offset().x(), s.offset().y());
			g2d.rotate(s.rotation());
			switch(s.getClass().getName()) {
			case "model.Rectangle": 
				drawRectangle(g2d, (Rectangle) s, isSelected); g2d.setTransform(oldXForm); break;
			case "model.Square":
				drawSquare(g2d, (Square) s, isSelected); g2d.setTransform(oldXForm); break;
			case "model.Ellipse":
				drawEllipse(g2d, (Ellipse) s, isSelected); g2d.setTransform(oldXForm); break;
			case "model.Circle":
				drawCircle(g2d, (Circle) s, isSelected); g2d.setTransform(oldXForm); break;
			case "model.Triangle":
				drawTriangle(g2d, (Triangle) s, isSelected); g2d.setTransform(oldXForm); break;
			case "model.Line":
				drawLine(g2d, (Line) s, isSelected); g2d.setTransform(oldXForm); break;
			default: 
				System.out.println(s.getClass().getName());
				g2d.setTransform(oldXForm);
				break;
			}
			g2d.setTransform(oldXForm);
		}
		drawSelected(g2d);
	}
	
	private void drawRectangle(Graphics2D g2d, Rectangle rectangle, boolean isSelected) {
		g2d.setColor(rectangle.color());
		g2d.fillRect(-rectangle.width()/2, -rectangle.height()/2, rectangle.width(), rectangle.height());
		if(isSelected) { 
			selectedShape = rectangle;
			selectedShapeAT = g2d.getTransform();
		}
	}
	
	private void drawBoundingBoxHandles(Graphics2D g2d, Shape s) {
		int h = 0;
		int w = 0;
		
		switch(s.getClass().getName()) {
		case "model.Rectangle": 
			Rectangle rectangle = (Rectangle) s;
			h = rectangle.height();
			w = rectangle.width();
			g2d.drawRect(-w/2, -h/2, w, h);
			break;
		case "model.Square":
			Square square = (Square) s;
			h = w = square.size();
			g2d.drawRect(-w/2, -h/2, w, h);
			break;
		case "model.Ellipse":
			Ellipse ellipse = (Ellipse) s;
			h = ellipse.height();
			w = ellipse.width();
			g2d.drawOval(-w/2, -h/2, w, h);
			break;
		case "model.Circle":
			Circle circle = (Circle) s;
			h = w = circle.radius()*2;
			g2d.drawOval(-w/2, -h/2, w, h);
			break;
		default: 
			System.out.println("Error when drawing bounding box handles: No valid shape selected");
			return;
		}
		
		for(Point handleCenter: s.getHandles()) {
			g2d.drawOval(handleCenter.x-H_SIZE/2, handleCenter.y-H_SIZE/2, H_SIZE, H_SIZE);
		}
		
		if(s.getClass().getName() != "model.Circle")
			drawRotationHandle(g2d, s);
	}
	
	private void drawRotationHandle(Graphics2D g2d, Shape s) {
		Point handle = new Point(s.getRotationHandle().x, s.getRotationHandle().y);
		g2d.drawLine(0, 0, handle.x, handle.y+H_SIZE/2);
		g2d.drawOval(handle.x-H_SIZE/2, handle.y-H_SIZE/2, H_SIZE, H_SIZE);
	}
	
	private void drawSquare(Graphics2D g2d, Square square, boolean isSelected) {
		g2d.setColor(square.color());
		g2d.fillRect(-square.size()/2, -square.size()/2, square.size(), square.size());
		if(isSelected) { 
			selectedShape = square;
			selectedShapeAT = g2d.getTransform();
		}
	}
	
	private void drawEllipse(Graphics2D g2d, Ellipse ellipse, boolean isSelected) {
		g2d.setColor(ellipse.color());
		g2d.fillOval(-ellipse.width()/2, -ellipse.height()/2, ellipse.width(), ellipse.height());
		if(isSelected) { 
			selectedShape = ellipse;
			selectedShapeAT = g2d.getTransform();
		}
	}
	
	private void drawCircle(Graphics2D g2d, Circle circle, boolean isSelected) {
		g2d.setColor(circle.color());
		g2d.fillOval(-circle.radius(), -circle.radius(), circle.radius()*2, circle.radius()*2);
		if(isSelected) { 
			selectedShape = circle;
			selectedShapeAT = g2d.getTransform();
		}
	}
	
	private void drawTriangle(Graphics2D g2d, Triangle triangle, boolean isSelected) {
		g2d.setColor(triangle.color());
		int[] xs = new int[]{triangle.p1().x, triangle.p2().x, triangle.p3().x};
		int[] ys = new int[]{triangle.p1().y, triangle.p2().y, triangle.p3().y};
		g2d.fillPolygon(xs, ys, 3);
		if(isSelected) { 
			selectedShape = triangle;
			selectedShapeAT = g2d.getTransform();
		}
	}
	
	private void drawTriangleHandles(Graphics2D g2d, Triangle triangle) {
		int[] xs = new int[]{triangle.p1().x, triangle.p2().x, triangle.p3().x};
		int[] ys = new int[]{triangle.p1().y, triangle.p2().y, triangle.p3().y};
		g2d.drawPolygon(xs, ys, 3);
		for(Point handleCenter: triangle.getHandles()) {
			g2d.drawOval(handleCenter.x-H_SIZE/2, handleCenter.y-H_SIZE/2, H_SIZE, H_SIZE);
		}
		drawRotationHandle(g2d, triangle);
	}
	
	private void drawLine(Graphics2D g2d, Line line, boolean isSelected) {
		g2d.setColor(line.color());
		g2d.drawLine(line.p1().x, line.p1().y, line.p2().x, line.p2().y);
		if(isSelected) { 
			selectedShape = line;
			selectedShapeAT = g2d.getTransform();
		}
	}
	
	private void drawLineHandles(Graphics2D g2d, Line line) {
		Point p1 = line.p1();
		Point p2 = line.p2();
		g2d.drawOval(p1.x-H_SIZE/2, p1.y-H_SIZE/2, H_SIZE, H_SIZE);
		g2d.drawOval(p2.x-H_SIZE/2, p2.y-H_SIZE/2, H_SIZE, H_SIZE);
//		drawRotationHandle(g2d, line);
	}
	
	private void drawHandles(Graphics2D g2d, Shape s) {
		AffineTransform oldXForm = g2d.getTransform();
		g2d.setColor(complement(s.color()));
		switch(s.getClass().getName()) {
		case "model.Rectangle": 
			drawBoundingBoxHandles(g2d, (Rectangle) s); break;
		case "model.Square":
			drawBoundingBoxHandles(g2d, (Square) s); break;
		case "model.Ellipse":
			drawBoundingBoxHandles(g2d, (Ellipse) s); break;
		case "model.Circle":
			drawBoundingBoxHandles(g2d, (Circle) s); break;
		case "model.Triangle":
			drawTriangleHandles(g2d, (Triangle) s); break;
		case "model.Line":
			drawLineHandles(g2d, (Line) s); break;
		default: 
			System.out.println("Error when drawing handles: No valid shape selected");
			g2d.setTransform(oldXForm);
			return;
		}
		g2d.setTransform(oldXForm);
	}
	
	private Color complement(Color c) {
		int red = 255-c.getRed();
		int green = 255-c.getGreen();
		int blue = 255-c.getBlue();
		int average = (red+green+blue)/3;
		if(Math.abs(red-average) < 25 && Math.abs(green-average) < 25 && Math.abs(blue-average) < 25) {
			red = (red + 256/2) % 256;
			if(red < 200) {
				red += 50;
			}
		}
		return new Color(red, green, blue);
	}
	
	private void drawSelected(Graphics2D g2d) {
		if(selectedShape == null) {
//			System.out.println("Some error in drawing the selected shape");
			return;
		} else if(selectedShapeAT == null) {
			System.out.println("Some error in getting the selected shape affine transform");
			return;
		}
		AffineTransform oldAT = g2d.getTransform();
		g2d.setTransform(selectedShapeAT);
		drawHandles(g2d, selectedShape);
		g2d.setTransform(oldAT);
	}

}
