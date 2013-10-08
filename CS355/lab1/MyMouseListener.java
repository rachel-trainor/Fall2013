package lab1;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import lab1.Shapes.Type;
import model.*;

import resources.GUIFunctions;

public class MyMouseListener implements MouseListener, MouseMotionListener {
	Shapes shapes;
	Shape newShape;
	Point origin;
	boolean moveShape = false;
	boolean useHandle = false;
	boolean rotate = false;
	
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
			newShape = new Rectangle(c); newShape.setOffset(p.x, p.y); break;
		case SQUARE:
			newShape = new Square(c); newShape.setOffset(p.x, p.y); break;
		case ELLIPSE:
			newShape = new Ellipse(c); newShape.setOffset(p.x, p.y); break;
		case CIRCLE:
			newShape = new Circle(c); newShape.setOffset(p.x, p.y); break;
		case TRIANGLE:
			if(newShape == null) {
				newShape = new Triangle(c);
				newShape.setOffset(p.x, p.y);
			}
			break; //do nothing
		case LINE:
			newShape = new Line(c); newShape.setOffset(p.x, p.y); break;
		case SELECT:
			select(p); GUIFunctions.refresh(); break;
		default:
			System.out.println("something went wrong: " + shapes.getType()); break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		Point p = arg0.getPoint();
		
		if(newShape != null) {
			Point relativeP = inObjectSpace(p, newShape);
			if(shapes.getType() == Type.TRIANGLE) {
				makeTriangle((Triangle) newShape, relativeP, true);
			} else {
				updateShape(newShape, relativeP);
				shapes.add(newShape);
				GUIFunctions.refresh();
				newShape = null;
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point p = e.getPoint();
		
		if(shapes.getType() == Type.SELECT) {
			if(moveShape && shapes.selected() != null) {
				Point relativeP = inObjectSpace(p, shapes.selected());
				if(useHandle) {
					updateShape(shapes.selected(), relativeP);
				} else if(rotate) {
					rotateShape(shapes.selected(), relativeP);
				} else {
					shapes.selected().setOffset(p.x, p.y);
				}
				GUIFunctions.refresh();
			}
			return;
		}
		
		if(newShape != null) {
			Point relativeP = inObjectSpace(p, newShape);
			if(shapes.getType() == Type.TRIANGLE) {
				makeTriangle((Triangle) newShape, relativeP, false);
			} else {
				updateShape(newShape, relativeP);
				shapes.add(newShape);
				GUIFunctions.refresh();
				shapes.removeLast();
			}
		} 
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(shapes.getType() == Type.TRIANGLE && newShape != null) {
			Point p = e.getPoint();
			Point relativeP = inObjectSpace(p, newShape);
			makeTriangle((Triangle) newShape, relativeP, false);
		} 
	}
	
	private void updateShape(Shape s, Point relativeP) {
		switch(s.getClass().getName()) {
		case "model.Rectangle":
			update((Rectangle) s, relativeP); break;
		case "model.Square":
			update((Square) s, relativeP); break;
		case "model.Ellipse":
			update((Ellipse) s, relativeP); break;
		case "model.Circle":
			update((Circle) s, relativeP); break;
		case "model.Triangle":
			update((Triangle) s, relativeP); break;
		case "model.Line":
			update((Line) s, relativeP); break;
		}
	}
	
	private void update(Rectangle r, Point p) {
		int h = Math.abs(p.y)+r.height()/2;
		int w = Math.abs(p.x)+r.width()/2;
		
		r.setHeight(h);
		r.setWidth(w);
		r.setOffset(origin.x+((p.x > 0) ? w/2 : -w/2), origin.y+((p.y > 0) ? h/2 : -h/2));
	}
	
	private void update(Square s, Point p) {
		int h = Math.abs(p.y)+s.size()/2;
		int w = Math.abs(p.x)+s.size()/2;
		
		s.setSize(Math.min(Math.abs(h), Math.abs(w)));
		s.setOffset(origin.x+((p.x > 0) ? s.size()/2 : -s.size()/2), origin.y+((p.y > 0) ? s.size()/2 : -s.size()/2));
	}
	
	private void update(Ellipse e, Point p) {
		int h = Math.abs(p.y)+e.height()/2;
		int w = Math.abs(p.x)+e.width()/2;
		
		e.setHeight(Math.abs(h));
		e.setWidth(Math.abs(w));
		e.setOffset(origin.x+((p.x > 0) ? w/2 : -w/2), origin.y+((p.y > 0) ? h/2 : -h/2));
	}
	
	private void update(Circle c, Point p) {
		int h = Math.abs(p.y)+c.radius();
		int w = Math.abs(p.x)+c.radius();
		int diameter = Math.min(Math.abs(h), Math.abs(w));
		
		c.setRadius(diameter/2);
		c.setOffset(origin.x+((p.x > 0) ? c.radius() : -c.radius()), origin.y+((p.y > 0) ? c.radius() : -c.radius()));
	}
	
	private void update(Line l, Point p) {
		if(p.distance(l.p1()) < p.distance(l.p2())) {
			l.setP1(p);
		} else {
			l.setP2(p);
		}
		Point p1 = l.p1();
		Point p2 = l.p2();
		int aveX = (p1.x + p2.x)/2;
		int aveY = (p1.y + p2.y)/2;
		l.setP1(new Point(p1.x-aveX, p1.y-aveY));
		l.setP2(new Point(p2.x-aveX, p2.y-aveY));
		int prevXOffset = l.offset().x();
		int prevYOffset = l.offset().y();
		l.setOffset(prevXOffset+aveX, prevYOffset+aveY);
	}
	
	private void update(Triangle t, Point p) {
		double distToP1 = t.p1().distance(p);
		double distToP2 = t.p2().distance(p);
		double distToP3 = t.p3().distance(p);
		double shortestDistance = Math.min(distToP1, distToP2);
		shortestDistance = Math.min(shortestDistance, distToP3);
		
		if(shortestDistance == distToP1) {
			t.setP1(p);
		} else if(shortestDistance == distToP2) {
			t.setP2(p);
		} else if(shortestDistance == distToP3) {
			t.setP3(p);
		}
		updateTriangleCenter(t);
	}
	
	private void makeTriangle(Triangle t, Point p, boolean setNewPoint) {
		if(t.p1().distance(t.p2()) < 5) { // p2 is not set
			if(setNewPoint) {
				t.setP2(p);
			} else {
				Line tmpLine = new Line(t.color());
				tmpLine.setOffset(t.offset().x(), t.offset().y());
				tmpLine.setP2(p);
				shapes.add(tmpLine);
				GUIFunctions.refresh();
				shapes.removeLast();
			}
		} else { // p2 is set
			if(setNewPoint) {
				t.setP3(p);
				updateTriangleCenter(t);
				shapes.add(t);
				GUIFunctions.refresh();
				newShape = null;
			} else {
				Triangle tmpTri = new Triangle(t.color());
				tmpTri.setOffset(t.offset().x(), t.offset().y());
				tmpTri.setP2(t.p2());
				tmpTri.setP3(p);
				updateTriangleCenter(tmpTri);
				shapes.add(tmpTri);
				GUIFunctions.refresh();
				shapes.removeLast();
			}
		}
	}
	
	public void updateTriangleCenter(Triangle t) {
		Point p1 = t.p1();
		Point p2 = t.p2();
		Point p3 = t.p3();
		int aveX = (p1.x + p2.x + p3.x)/3;
		int aveY = (p1.y + p2.y + p3.y)/3;
		t.setP1(new Point(p1.x-aveX, p1.y-aveY));
		t.setP2(new Point(p2.x-aveX, p2.y-aveY));
		t.setP3(new Point(p3.x-aveX, p3.y-aveY));
		int prevXOffset = t.offset().x();
		int prevYOffset = t.offset().y();
		t.setOffset(prevXOffset+aveX, prevYOffset+aveY);
	}
	
	public void select(Point p) {
		useHandle = foundHandle(p);
		if(!useHandle) {
			rotate = foundRotateHandle(p);
			if(!rotate) {
				moveShape = foundShape(p);
			}
		}
	}
	
	public boolean foundShape(Point p) {
		for(int i=shapes.size()-1; i >= 0; i--) {
			Shape tmpShape = shapes.get(i);
			
			// convert the point's coordinates to object space
			Point relativeP = inObjectSpace(p, tmpShape);
			
			if(tmpShape.pointInShape(relativeP)) {
				shapes.setSelected(tmpShape);
				shapes.setColor(tmpShape.color());
				GUIFunctions.changeSelectedColor(tmpShape.color());
				return true;
			} else {
				shapes.setSelected(null);
			}
		}
		return false;
	}
	
	public boolean foundRotateHandle(Point p) {
		if(shapes.selected() == null) { // I don't already have a selected shape
			return false;
		} else if(shapes.selected().getClass().getName() == "model.Circle") { // can't rotate circles
			return false;
		} else if(shapes.selected().getClass().getName() == "model.Line") { // can't rotate lines
			return false;
		}
		
		// convert the point's coordinates to object space
		Point relativeP = inObjectSpace(p, shapes.selected());
		Point rotationHandleCenter = shapes.selected().getRotationHandle();
		if(pointInHandle(relativeP, rotationHandleCenter)) {
			return true;
		}
		return false;
	}
	
	public boolean foundHandle(Point p) {
		if(shapes.selected() == null) { // I don't already have a selected shape
			return false;
		}
		
		// convert the point's coordinates to object space
		Point relativeP = inObjectSpace(p, shapes.selected());
		if(handleToUse(relativeP) != null) {
			// set the origin's coordinates in world space
			origin = inWorldSpace(relativeP, shapes.selected());
			return true;
		} else {
			return false;
		}
	}
	
	public Point handleToUse(Point p) {
		if(shapes.selected() == null) {
			return null;
		}
		for(Point handleCenter : shapes.selected().getHandles()) {
			if(pointInHandle(p, handleCenter)) {
				return handleCenter;
			}
		}
		return null;
	}
	
	public boolean pointInHandle(Point p, Point handleCenter) {
		int radius = 8;
		int deltaX = p.x - handleCenter.x;
		int deltaY = p.y - handleCenter.y;
		if(Math.abs(deltaX) > radius || Math.abs(deltaY) > radius) {
			return false;
		} else if(deltaX*deltaX + deltaY*deltaY < radius*radius) {
			return true;
		}
		return false;
	}
	
	public Point inObjectSpace(Point p, Shape s) {
		Point translatedP = new Point(p.x-s.offset().x(), p.y-s.offset().y());
		// rotate relative to the center of the shape
		double rotatedX = Math.cos(-s.rotation())*translatedP.x - Math.sin(-s.rotation())*translatedP.y;
		double rotatedY = Math.sin(-s.rotation())*translatedP.x + Math.cos(-s.rotation())*translatedP.y;
		Point rotatedP = new Point((int) rotatedX, (int) rotatedY);
		return rotatedP;
	}
	
	public Point inWorldSpace(Point relativeP, Shape s) {
		double rotatedX = Math.cos(s.rotation()) * (relativeP.x) - Math.sin(s.rotation()) * (relativeP.y);
		double rotatedY = Math.sin(s.rotation()) * (relativeP.x) + Math.cos(s.rotation()) * (relativeP.y);
		Point rotatedP = new Point((int) rotatedX, (int) rotatedY);
		Point translatedP = new Point(s.offset().x()-rotatedP.x, s.offset().y()-rotatedP.y);
		return translatedP;
	}
	
	public void rotateShape(Shape s, Point p) {
		double rotateAngle = Math.atan2(p.y, p.x);
		rotateAngle = (rotateAngle + Math.PI/2);
		s.setRotation(rotateAngle);
	}
}
