package lab1;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import lab1.Shapes.Type;
import model.*;
import model.Shape.Offset;

import resources.GUIFunctions;

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
		origin = p;
		
		switch(shapes.getType()) {
		case RECTANGLE:
			newShape = new Rectangle(c, p); newShape.setOffset(p.x, p.y); break;
		case SQUARE:
			newShape = new Square(c, p); newShape.setOffset(p.x, p.y); break;
		case ELLIPSE:
			newShape = new Ellipse(c, p); newShape.setOffset(p.x, p.y); break;
		case CIRCLE:
			newShape = new Circle(c, p); newShape.setOffset(p.x, p.y); break;
		case TRIANGLE:
			break; //do nothing
		case LINE:
			newShape = new Line(c, p); break;
		case SELECT:
			selectShape(p); break;
		default:
			System.out.println("something went wrong: " + shapes.getType()); break;
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
		case "model.Rectangle":
			newShape = update((Rectangle) s, p); break;
		case "model.Square":
			newShape = update((Square) s, p); break;
		case "model.Ellipse":
			newShape = update((Ellipse) s, p); break;
		case "model.Circle":
			newShape = update((Circle) s, p); break;
		case "model.Triangle":
			break; // do nothing
		case "model.Line":
			newShape = update((Line) s, p); break;
		}
	}
	
	private Rectangle update(Rectangle r, Point p) {
		int h = p.y-origin.y;
		int w = p.x-origin.x;
		
		r.setHeight(Math.abs(h));
		r.setWidth(Math.abs(w));
		r.setOffset(origin.x+w/2, origin.y+h/2);

		return r;
	}
	
	private Square update(Square s, Point p) {
		int h = p.y-origin.y;
		int w = p.x-origin.x;
		
		s.setSize(Math.min(Math.abs(h), Math.abs(w)));
		s.setOffset(origin.x+((w > 0) ? s.size()/2 : -s.size()/2), origin.y+((h > 0) ? s.size()/2 : -s.size()/2));

		return s;
	}
	
	private Ellipse update(Ellipse e, Point p) {
		int h = p.y-origin.y;
		int w = p.x-origin.x;
		
		e.setHeight(Math.abs(h));
		e.setWidth(Math.abs(w));
		e.setOffset(origin.x+w/2, origin.y+h/2);
		
		return e;
	}
	
	private Circle update(Circle c, Point p) {
		int h = p.y-origin.y;
		int w = p.x-origin.x;
		int diameter = Math.min(Math.abs(h), Math.abs(w));
		
		c.setRadius(diameter/2);
		c.setOffset(origin.x+((w > 0) ? c.radius() : -c.radius()), origin.y+((h > 0) ? c.radius() : -c.radius()));

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
					Point p1 = newTriangle.p1();
					Point p2 = newTriangle.p2();
					Point p3 = newTriangle.p3();
					int aveX = (p1.x + p2.x + p3.x)/3;
					int aveY = (p1.y + p2.y + p3.y)/3;
					newTriangle.setP1(new Point(p1.x-aveX, p1.y-aveY));
					newTriangle.setP2(new Point(p2.x-aveX, p2.y-aveY));
					newTriangle.setP3(new Point(p3.x-aveX, p3.y-aveY));
					newTriangle.setOffset(aveX, aveY);
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
	
	public void selectShape(Point p) {
		for(int i=shapes.size()-1; i >= 0; i--) {
			Shape tmpShape = shapes.get(i);
			
			// convert the point's coordinates to object space
			Point relativeP = new Point(p.x-tmpShape.offset().x(), p.y-tmpShape.offset().y());
			
			if(tmpShape.pointInShape(relativeP)) {
				System.out.println("The point is in the shape!!! " + tmpShape.toString());
				
				return;
			}
		}
	}
}
