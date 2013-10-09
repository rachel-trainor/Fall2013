package lab1;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import lab1.Shapes.Type;
import model.*;

import resources.GUIFunctions;

public class MyMouseListener implements MouseListener, MouseMotionListener {
	Shapes shapes;
	Shape newShape;
	Point2D origin;
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
		Point2D p = arg0.getPoint();
		origin = p;
		
		switch(shapes.getType()) {
		case RECTANGLE:
			newShape = new Rectangle(c); newShape.setOffset(p.getX(), p.getY()); break;
		case SQUARE:
			newShape = new Square(c); newShape.setOffset(p.getX(), p.getY()); break;
		case ELLIPSE:
			newShape = new Ellipse(c); newShape.setOffset(p.getX(), p.getY()); break;
		case CIRCLE:
			newShape = new Circle(c); newShape.setOffset(p.getX(), p.getY()); break;
		case TRIANGLE:
			if(newShape == null) {
				newShape = new Triangle(c);
				newShape.setOffset(p.getX(), p.getY());
			}
			break; //do nothing
		case LINE:
			newShape = new Line(c); newShape.setOffset(p.getX(), p.getY()); break;
		case SELECT:
			select(p); GUIFunctions.refresh(); break;
		default:
			System.out.println("something went wrong: " + shapes.getType()); break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		Point2D p = arg0.getPoint();
		
		if(newShape != null) {
			Point2D relativeP = inObjectSpace(p, newShape);
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
		Point2D p = e.getPoint();
		
		if(shapes.getType() == Type.SELECT) {
			if(moveShape && shapes.selected() != null) {
				Point2D relativeP = inObjectSpace(p, shapes.selected());
				if(useHandle) {
					updateShape(shapes.selected(), relativeP);
				} else if(rotate) {
					Shape s = shapes.selected();
					Point2D translatedP = new Point2D.Double(p.getX()-s.offset().x(), p.getY()-s.offset().y());
					rotateShape(s, translatedP);
				} else {
					shapes.selected().setOffset(p.getX(), p.getY());
				}
				GUIFunctions.refresh();
			}
			return;
		}
		
		if(newShape != null) {
			Point2D relativeP = inObjectSpace(p, newShape);
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
			Point2D p = e.getPoint();
			Point2D relativeP = inObjectSpace(p, newShape);
			makeTriangle((Triangle) newShape, relativeP, false);
		} 
	}
	
	private void updateShape(Shape s, Point2D relativeP) {
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
	
	private void update(Rectangle r, Point2D p) {
		double h = Math.abs(p.getY())+r.height()/2;
		double w = Math.abs(p.getX())+r.width()/2;
		
		r.setHeight(h);
		r.setWidth(w);
		
		double oldX = ((p.getX() > 0) ? w/2 : -w/2);
		double oldY = ((p.getY() > 0) ? h/2 : -h/2);
		double rotatedX = Math.cos(r.rotation())*oldX - Math.sin(r.rotation())*oldY;
		double rotatedY = Math.sin(r.rotation())*oldX + Math.cos(r.rotation())*oldY;
		r.setOffset(origin.getX()+(int)rotatedX, origin.getY()+(int)rotatedY);
	}
	
	private void update(Square s, Point2D p) {
		double h = Math.abs(p.getY())+s.size()/2;
		double w = Math.abs(p.getX())+s.size()/2;
		
		s.setSize(Math.min(Math.abs(h), Math.abs(w)));
		
		double oldX = ((p.getX() > 0) ? s.size()/2 : -s.size()/2);
		double oldY = ((p.getY() > 0) ? s.size()/2 : -s.size()/2);
		double rotatedX = Math.cos(s.rotation())*oldX - Math.sin(s.rotation())*oldY;
		double rotatedY = Math.sin(s.rotation())*oldX + Math.cos(s.rotation())*oldY;
		s.setOffset(origin.getX()+(int)rotatedX, origin.getY()+(int)rotatedY);
	}
	
	private void update(Ellipse e, Point2D p) {
		double h = Math.abs(p.getY())+e.height()/2;
		double w = Math.abs(p.getX())+e.width()/2;
		
		e.setHeight(Math.abs(h));
		e.setWidth(Math.abs(w));
		
		double oldX = ((p.getX() > 0) ? w/2 : -w/2);
		double oldY = ((p.getY() > 0) ? h/2 : -h/2);
		double rotatedX = Math.cos(e.rotation())*oldX - Math.sin(e.rotation())*oldY;
		double rotatedY = Math.sin(e.rotation())*oldX + Math.cos(e.rotation())*oldY;
		e.setOffset(origin.getX()+(int)rotatedX, origin.getY()+(int)rotatedY);
	}
	
	private void update(Circle c, Point2D p) {
		double h = Math.abs(p.getY())+c.radius();
		double w = Math.abs(p.getX())+c.radius();
		double diameter = Math.min(Math.abs(h), Math.abs(w));
		
		c.setRadius(diameter/2);
		
		double oldX = ((p.getX() > 0) ? c.radius() : -c.radius());
		double oldY = ((p.getY() > 0) ? c.radius() : -c.radius());
		double rotatedX = Math.cos(c.rotation())*oldX - Math.sin(c.rotation())*oldY;
		double rotatedY = Math.sin(c.rotation())*oldX + Math.cos(c.rotation())*oldY;
		c.setOffset(origin.getX()+(int)rotatedX, origin.getY()+(int)rotatedY);
	}
	
	private void update(Line l, Point2D p) {
		if(p.distance(l.p1()) < p.distance(l.p2())) {
			l.setP1(p);
		} else {
			l.setP2(p);
		}
		Point2D p1 = l.p1();
		Point2D p2 = l.p2();
		double aveX = (p1.getX() + p2.getX())/2;
		double aveY = (p1.getY() + p2.getY())/2;
		l.setP1(new Point2D.Double(p1.getX()-aveX, p1.getY()-aveY));
		l.setP2(new Point2D.Double(p2.getX()-aveX, p2.getY()-aveY));
		double prevXOffset = l.offset().x();
		double prevYOffset = l.offset().y();
		l.setOffset(prevXOffset+aveX, prevYOffset+aveY);
	}
	
	private void update(Triangle t, Point2D p) {
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
	
	private void makeTriangle(Triangle t, Point2D p, boolean setNewPoint2D) {
		if(t.p1().distance(t.p2()) < 5) { // p2 is not set
			if(setNewPoint2D) {
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
			if(setNewPoint2D) {
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
		Point2D p1 = t.p1();
		Point2D p2 = t.p2();
		Point2D p3 = t.p3();
		double aveX = (p1.getX() + p2.getX() + p3.getX())/3;
		double aveY = (p1.getY() + p2.getY() + p3.getY())/3;
		t.setP1(new Point2D.Double(p1.getX()-aveX, p1.getY()-aveY));
		t.setP2(new Point2D.Double(p2.getX()-aveX, p2.getY()-aveY));
		t.setP3(new Point2D.Double(p3.getX()-aveX, p3.getY()-aveY));
		double prevXOffset = t.offset().x();
		double prevYOffset = t.offset().y();
		
		double rotatedX = Math.cos(t.rotation()) * (aveX) - Math.sin(t.rotation()) * (aveY);
		double rotatedY = Math.sin(t.rotation()) * (aveX) + Math.cos(t.rotation()) * (aveY);
		Point2D rotatedP = new Point2D.Double(rotatedX, rotatedY);
		
		t.setOffset(prevXOffset+rotatedP.getX(), prevYOffset+rotatedP.getY());
	}
	
	public void select(Point2D p) {
		useHandle = foundHandle(p);
		if(!useHandle) {
			rotate = foundRotateHandle(p);
			if(!rotate) {
				moveShape = foundShape(p);
			}
		}
	}
	
	public boolean foundShape(Point2D p) {
		for(int i=shapes.size()-1; i >= 0; i--) {
			Shape tmpShape = shapes.get(i);
			
			// convert the Point2D's coordinates to object space
			Point2D relativeP = inObjectSpace(p, tmpShape);
			
			if(tmpShape.PointInShape(relativeP)) {
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
	
	public boolean foundRotateHandle(Point2D p) {
		if(shapes.selected() == null) { // I don't already have a selected shape
			return false;
		} else if(shapes.selected().getClass().getName() == "model.Circle") { // can't rotate circles
			return false;
		} else if(shapes.selected().getClass().getName() == "model.Line") { // can't rotate lines
			return false;
		}
		
		// convert the Point2D's coordinates to object space
		Point2D relativeP = inObjectSpace(p, shapes.selected());
		Point2D rotationHandleCenter = shapes.selected().getRotationHandle();
		if(Point2DInHandle(relativeP, rotationHandleCenter)) {
			return true;
		}
		return false;
	}
	
	public boolean foundHandle(Point2D p) {
		if(shapes.selected() == null) { // I don't already have a selected shape
			return false;
		}
		
		// convert the Point2D's coordinates to object space
		Point2D relativeP = inObjectSpace(p, shapes.selected());
		if(handleToUse(relativeP) != null) {
			// set the origin's coordinates in world space
			origin = inWorldSpace(relativeP, shapes.selected());
			return true;
		} else {
			return false;
		}
	}
	
	public Point2D handleToUse(Point2D p) {
		if(shapes.selected() == null) {
			return null;
		}
		for(Point2D handleCenter : shapes.selected().getHandles()) {
			if(Point2DInHandle(p, handleCenter)) {
				return handleCenter;
			}
		}
		return null;
	}
	
	public boolean Point2DInHandle(Point2D p, Point2D handleCenter) {
		double radius = 8;
		double deltaX = p.getX() - handleCenter.getX();
		double deltaY = p.getY() - handleCenter.getY();
		if(Math.abs(deltaX) > radius || Math.abs(deltaY) > radius) {
			return false;
		} else if(deltaX*deltaX + deltaY*deltaY < radius*radius) {
			return true;
		}
		return false;
	}
	
	public Point2D inObjectSpace(Point2D p, Shape s) {
		Point2D translatedP = new Point2D.Double(p.getX()-s.offset().x(), p.getY()-s.offset().y());
		// rotate relative to the center of the shape
		double rotatedX = Math.cos(-s.rotation())*translatedP.getX() - Math.sin(-s.rotation())*translatedP.getY();
		double rotatedY = Math.sin(-s.rotation())*translatedP.getX() + Math.cos(-s.rotation())*translatedP.getY();
		Point2D rotatedP = new Point2D.Double(rotatedX, rotatedY);
		return rotatedP;
	}
	
	public Point2D inWorldSpace(Point2D relativeP, Shape s) {
		double rotatedX = Math.cos(s.rotation()) * (relativeP.getX()) - Math.sin(s.rotation()) * (relativeP.getY());
		double rotatedY = Math.sin(s.rotation()) * (relativeP.getX()) + Math.cos(s.rotation()) * (relativeP.getY());
		Point2D rotatedP = new Point2D.Double(rotatedX, rotatedY);
		Point2D translatedP = new Point2D.Double(s.offset().x()-rotatedP.getX(), s.offset().y()-rotatedP.getY());
		return translatedP;
	}
	
	public void rotateShape(Shape s, Point2D p) {
		double rotateAngle = Math.atan2(p.getY(), p.getX());
		rotateAngle = (rotateAngle + Math.PI/2);
		s.setRotation(rotateAngle);
	}
}
