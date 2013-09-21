package lab1;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import lab1.Shapes.Type;

import resources.GUIFunctions;
import shapes.*;

public class MyMouseListener implements MouseListener, MouseMotionListener {
	Shapes shapes;
	Shape newShape;
	Triangle newTriangle;
	Point origin;
	
	MyMouseListener() {
		shapes = new Shapes();
	}
	
	MyMouseListener(Shapes s) {
		shapes = s;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {
		Color c = shapes.getColor();
		Point p = arg0.getPoint();
		
		switch(shapes.getType()) {
		case RECTANGLE:
			newShape = new Rectangle(c, p); origin = new Point(p.x, p.y); break;
		case SQUARE:
			newShape = new Square(c, p); origin = new Point(p.x, p.y); break;
		case ELLIPSE:
			newShape = new Ellipse(c, p); origin = new Point(p.x, p.y); break;
		case CIRCLE:
			newShape = new Circle(c, p); origin = new Point(p.x, p.y); break;
		case TRIANGLE:
			break; //do nothing
		case LINE:
			newShape = new Line(c, p); break;
		default:
			System.out.println("something went wrong"); break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		Point p = arg0.getPoint();
		
		if(newShape != null) {
			updateShape(newShape, p);
			shapes.add(newShape);
			GUIFunctions.refresh();
			newShape = null;
		} else if(shapes.getType() == Type.TRIANGLE) {
			updateTriangle(arg0.getPoint(), true);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point p = e.getPoint();
		
		if(newShape != null) {
			updateShape(newShape,p);
			shapes.add(newShape);
			GUIFunctions.refresh();
			shapes.removeLast();
		} else if (newTriangle != null) {
			updateTriangle(e.getPoint(), false);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(shapes.getType() == Type.TRIANGLE && newTriangle != null) {
			updateTriangle(e.getPoint(), false);
		} 
	}
	
	private void updateShape(Shape s, Point p) {
		switch(s.getClass().getName()) {
		case "shapes.Rectangle":
			newShape = update((Rectangle) s, p); break;
		case "shapes.Square":
			newShape = update((Square) s, p); break;
		case "shapes.Ellipse":
			newShape = update((Ellipse) s, p); break;
		case "shapes.Circle":
			newShape = update((Circle) s, p); break;
		case "shapes.Triangle":
			break; // do nothing
		case "shapes.Line":
			newShape = update((Line) s, p); break;
		}
	}
	
	private Rectangle update(Rectangle r, Point p) {
		int h = p.y-origin.y;
		int w = p.x-origin.x;
		
		r.setHeight(Math.abs(h));
		r.setWidth(Math.abs(w));
		r.setUL(new Point(Math.min(origin.x, p.x), Math.min(origin.y, p.y)));

		return r;
	}
	
	private Square update(Square s, Point p) {
		int h = p.y-origin.y;
		int w = p.x-origin.x;
		int size = Math.min(Math.abs(h), Math.abs(w));
		
		s.setSize(size);
		s.setUL(new Point((w > 0) ? origin.x : origin.x-size, (h > 0) ? origin.y : origin.y-size));

		return s;
	}
	
	private Ellipse update(Ellipse e, Point p) {
		int h = p.y-origin.y;
		int w = p.x-origin.x;
		
		e.setHeight(Math.abs(h));
		e.setWidth(Math.abs(w));
		e.setCenter(new Point((p.x+origin.x)/2,(p.y+origin.y)/2));
		
		return e;
	}
	
	private Circle update(Circle c, Point p) {
		int h = p.y-origin.y;
		int w = p.x-origin.x;
		int diameter = Math.min(Math.abs(h), Math.abs(w));
		
		c.setRadius(diameter/2);
		Point ul = new Point((w > 0) ? origin.x : origin.x-diameter, (h > 0) ? origin.y : origin.y-diameter);
		c.setCenter(new Point(ul.x + c.radius(), ul.y + c.radius()));

		return c;
	}
	
	private Line update(Line l, Point p) {
		l.setP2(p);
		return l;
	}
	
	private void updateTriangle(Point p, boolean setNewPoint) {
		if(newTriangle == null) {
			newTriangle = new Triangle(shapes.getColor(), p);
		} else {
			if(newTriangle.p1().distance(newTriangle.p2()) < 5) { // p2 is not set
				if(setNewPoint) {
					newTriangle.setP2(p);
				} else {
					Line tmpLine = new Line(newTriangle.color(), newTriangle.p1());
					tmpLine.setP2(p);
					shapes.add(tmpLine);
					GUIFunctions.refresh();
					shapes.removeLast();
				}
			} else { // p2 is set
				if(setNewPoint) {
					newTriangle.setP3(p);
					shapes.add(newTriangle);
					GUIFunctions.refresh();
					newTriangle = null;
				} else {
					Triangle tmpTri = new Triangle(newTriangle.color(), newTriangle.p1());
					tmpTri.setP2(newTriangle.p2());
					tmpTri.setP3(p);
					shapes.add(tmpTri);
					GUIFunctions.refresh();
					shapes.removeLast();
				}
			}
		}
	}

}
