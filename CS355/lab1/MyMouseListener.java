package lab1;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
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
	MatrixOps m = new MatrixOps();
	
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
		Point2D worldP = viewToWorld(arg0.getPoint());
		origin = worldP;
		
		switch(shapes.getType()) {
		case RECTANGLE:
			newShape = new Rectangle(c); newShape.setOffset(worldP.getX(), worldP.getY()); break;
		case SQUARE:
			newShape = new Square(c); newShape.setOffset(worldP.getX(), worldP.getY()); break;
		case ELLIPSE:
			newShape = new Ellipse(c); newShape.setOffset(worldP.getX(), worldP.getY()); break;
		case CIRCLE:
			newShape = new Circle(c); newShape.setOffset(worldP.getX(), worldP.getY()); break;
		case TRIANGLE:
			if(newShape == null) {
				newShape = new Triangle(c);
				newShape.setOffset(worldP.getX(), worldP.getY());
			}
			break; //do nothing
		case LINE:
			newShape = new Line(c); newShape.setOffset(worldP.getX(), worldP.getY()); break;
		case SELECT:
			select(worldP); GUIFunctions.refresh(); break;
		default:
			System.out.println("something went wrong: " + shapes.getType()); break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		Point2D worldP = viewToWorld(arg0.getPoint());
		
		if(newShape != null) {
			Point2D objectP = worldToObject(worldP, newShape);
			if(shapes.getType() == Type.TRIANGLE) {
				makeTriangle((Triangle) newShape, objectP, true);
			} else {
				updateShape(newShape, objectP);
				shapes.add(newShape);
				GUIFunctions.refresh();
				newShape = null;
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point2D worldP = viewToWorld(e.getPoint());
		
		if(shapes.getType() == Type.SELECT) {
			if(moveShape && shapes.selected() != null) {
				Point2D objectP = worldToObject(worldP, shapes.selected());
				if(useHandle) {
					updateShape(shapes.selected(), objectP);
				} else if(rotate) {
					Shape s = shapes.selected();
					Point2D translatedP = new Point2D.Double(worldP.getX()-s.offset().x(), worldP.getY()-s.offset().y());
					rotateShape(s, translatedP);
				} else {
					shapes.selected().setOffset(worldP.getX(), worldP.getY());
				}
				GUIFunctions.refresh();
			}
			return;
		}
		
		if(newShape != null) {
			Point2D objectP = worldToObject(worldP, newShape);
			if(shapes.getType() == Type.TRIANGLE) {
				makeTriangle((Triangle) newShape, objectP, false);
			} else {
				updateShape(newShape, objectP);
				shapes.add(newShape);
				GUIFunctions.refresh();
				shapes.removeLast();
			}
		} 
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(shapes.getType() == Type.TRIANGLE && newShape != null) {
			Point2D worldP = viewToWorld(e.getPoint());
			Point2D objectP = worldToObject(worldP, newShape);
			makeTriangle((Triangle) newShape, objectP, false);
		} 
	}
	
	private void updateShape(Shape s, Point2D objectP) {
		switch(s.getClass().getName()) {
		case "model.Rectangle":
			update((Rectangle) s, objectP); break;
		case "model.Square":
			update((Square) s, objectP); break;
		case "model.Ellipse":
			update((Ellipse) s, objectP); break;
		case "model.Circle":
			update((Circle) s, objectP); break;
		case "model.Triangle":
			update((Triangle) s, objectP); break;
		case "model.Line":
			update((Line) s, objectP); break;
		}
	}
	
	private void update(Rectangle r, Point2D objectP) {
		double h = Math.abs(objectP.getY())+r.height()/2;
		double w = Math.abs(objectP.getX())+r.width()/2;
		
		r.setHeight(h);
		r.setWidth(w);
		
		double oldX = ((objectP.getX() > 0) ? w/2 : -w/2);
		double oldY = ((objectP.getY() > 0) ? h/2 : -h/2);
		double rotatedX = Math.cos(r.rotation())*oldX - Math.sin(r.rotation())*oldY;
		double rotatedY = Math.sin(r.rotation())*oldX + Math.cos(r.rotation())*oldY;
		r.setOffset(origin.getX()+(int)rotatedX, origin.getY()+(int)rotatedY);
	}
	
	private void update(Square s, Point2D objectP) {
		double h = Math.abs(objectP.getY())+s.size()/2;
		double w = Math.abs(objectP.getX())+s.size()/2;
		
		s.setSize(Math.min(Math.abs(h), Math.abs(w)));
		
		double oldX = ((objectP.getX() > 0) ? s.size()/2 : -s.size()/2);
		double oldY = ((objectP.getY() > 0) ? s.size()/2 : -s.size()/2);
		double rotatedX = Math.cos(s.rotation())*oldX - Math.sin(s.rotation())*oldY;
		double rotatedY = Math.sin(s.rotation())*oldX + Math.cos(s.rotation())*oldY;
		s.setOffset(origin.getX()+(int)rotatedX, origin.getY()+(int)rotatedY);
	}
	
	private void update(Ellipse e, Point2D objectP) {
		double h = Math.abs(objectP.getY())+e.height()/2;
		double w = Math.abs(objectP.getX())+e.width()/2;
		
		e.setHeight(Math.abs(h));
		e.setWidth(Math.abs(w));
		
		double oldX = ((objectP.getX() > 0) ? w/2 : -w/2);
		double oldY = ((objectP.getY() > 0) ? h/2 : -h/2);
		double rotatedX = Math.cos(e.rotation())*oldX - Math.sin(e.rotation())*oldY;
		double rotatedY = Math.sin(e.rotation())*oldX + Math.cos(e.rotation())*oldY;
		e.setOffset(origin.getX()+(int)rotatedX, origin.getY()+(int)rotatedY);
	}
	
	private void update(Circle c, Point2D objectP) {
		double h = Math.abs(objectP.getY())+c.radius();
		double w = Math.abs(objectP.getX())+c.radius();
		double diameter = Math.min(Math.abs(h), Math.abs(w));
		
		c.setRadius(diameter/2);
		
		double oldX = ((objectP.getX() > 0) ? c.radius() : -c.radius());
		double oldY = ((objectP.getY() > 0) ? c.radius() : -c.radius());
		double rotatedX = Math.cos(c.rotation())*oldX - Math.sin(c.rotation())*oldY;
		double rotatedY = Math.sin(c.rotation())*oldX + Math.cos(c.rotation())*oldY;
		c.setOffset(origin.getX()+(int)rotatedX, origin.getY()+(int)rotatedY);
	}
	
	private void update(Line l, Point2D objectP) {
		if(objectP.distance(l.p1()) < objectP.distance(l.p2())) {
			l.setP1(objectP);
		} else {
			l.setP2(objectP);
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
	
	private void update(Triangle t, Point2D objectP) {
		double distToP1 = t.p1().distance(objectP);
		double distToP2 = t.p2().distance(objectP);
		double distToP3 = t.p3().distance(objectP);
		double shortestDistance = Math.min(distToP1, distToP2);
		shortestDistance = Math.min(shortestDistance, distToP3);
		
		if(shortestDistance == distToP1) {
			t.setP1(objectP);
		} else if(shortestDistance == distToP2) {
			t.setP2(objectP);
		} else if(shortestDistance == distToP3) {
			t.setP3(objectP);
		}
		updateTriangleCenter(t);
	}
	
	private void makeTriangle(Triangle t, Point2D objectP, boolean setNewPoint2D) {
		if(t.p1().distance(t.p2()) < 5) { // p2 is not set
			if(setNewPoint2D) {
				t.setP2(objectP);
			} else {
				Line tmpLine = new Line(t.color());
				tmpLine.setOffset(t.offset().x(), t.offset().y());
				tmpLine.setP2(objectP);
				shapes.add(tmpLine);
				GUIFunctions.refresh();
				shapes.removeLast();
			}
		} else { // p2 is set
			if(setNewPoint2D) {
				t.setP3(objectP);
				updateTriangleCenter(t);
				shapes.add(t);
				GUIFunctions.refresh();
				newShape = null;
			} else {
				Triangle tmpTri = new Triangle(t.color());
				tmpTri.setOffset(t.offset().x(), t.offset().y());
				tmpTri.setP2(t.p2());
				tmpTri.setP3(objectP);
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
	
	public void select(Point2D worldP) {
		useHandle = foundHandle(worldP);
		if(!useHandle) {
			rotate = foundRotateHandle(worldP);
			if(!rotate) {
				moveShape = foundShape(worldP);
			}
		}
	}
	
	public boolean foundShape(Point2D worldP) {
		for(int i=shapes.size()-1; i >= 0; i--) {
			Shape tmpShape = shapes.get(i);
			
			Point2D objectP = worldToObject(worldP, tmpShape);
			
			if(tmpShape.PointInShape(objectP)) {
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
	
	public boolean foundRotateHandle(Point2D worldP) {
		if(shapes.selected() == null) { // I don't already have a selected shape
			return false;
		} else if(shapes.selected().getClass().getName() == "model.Circle") { // can't rotate circles
			return false;
		} else if(shapes.selected().getClass().getName() == "model.Line") { // can't rotate lines
			return false;
		}
		
		Point2D objectP = worldToObject(worldP, shapes.selected());
		Point2D rotationHandleCenter = shapes.selected().getRotationHandle();
		if(Point2DInHandle(objectP, rotationHandleCenter)) {
			return true;
		}
		return false;
	}
	
	public boolean foundHandle(Point2D worldP) {
		if(shapes.selected() == null) { // I don't already have a selected shape
			return false;
		}
		
		Point2D objectP = worldToObject(worldP, shapes.selected());
		if(handleToUse(objectP) != null) {
			// set the origin's coordinates in world space
			origin = objectToWorld(objectP, shapes.selected());
			return true;
		} else {
			return false;
		}
	}
	
	public Point2D handleToUse(Point2D objectP) {
		if(shapes.selected() == null) {
			return null;
		}
		for(Point2D handleCenter : shapes.selected().getHandles()) {
			if(Point2DInHandle(objectP, handleCenter)) {
				return handleCenter;
			}
		}
		return null;
	}
	
	public boolean Point2DInHandle(Point2D objectP, Point2D handleCenter) {
		double radius = 8/shapes.zoom();
		double deltaX = objectP.getX() - handleCenter.getX();
		double deltaY = objectP.getY() - handleCenter.getY();
		if(Math.abs(deltaX) > radius || Math.abs(deltaY) > radius) {
			return false;
		} else if(deltaX*deltaX + deltaY*deltaY < radius*radius) {
			return true;
		}
		return false;
	}
	
	public Point2D worldToObject(Point2D worldP, Shape s) {
//		Point2D translatedP = new Point2D.Double(worldP.getX()-s.offset().x(), worldP.getY()-s.offset().y());
//		// rotate relative to the center of the shape
//		double rotatedX = Math.cos(-s.rotation())*translatedP.getX() - Math.sin(-s.rotation())*translatedP.getY();
//		double rotatedY = Math.sin(-s.rotation())*translatedP.getX() + Math.cos(-s.rotation())*translatedP.getY();
//		Point2D rotatedP = new Point2D.Double(rotatedX, rotatedY);
//		return rotatedP;
		
		double[][] rTc = m.dot(m.rThetaInv(s.rotation()), m.tCInv(s.offset().x(), s.offset().y()));
		AffineTransform at = new AffineTransform(rTc[0][0], rTc[1][0], rTc[0][1], rTc[1][1], rTc[0][2], rTc[1][2]);
		Point2D objectP = new Point2D.Double();
		return at.transform(worldP, objectP);
	}
	
	public Point2D objectToWorld(Point2D objectP, Shape s) {
		double rotatedX = Math.cos(s.rotation()) * (objectP.getX()) - Math.sin(s.rotation()) * (objectP.getY());
		double rotatedY = Math.sin(s.rotation()) * (objectP.getX()) + Math.cos(s.rotation()) * (objectP.getY());
		Point2D rotatedP = new Point2D.Double(rotatedX, rotatedY);
		Point2D translatedP = new Point2D.Double(s.offset().x()-rotatedP.getX(), s.offset().y()-rotatedP.getY());
		return translatedP;

//		double[][] tCR = m.dot(m.tC(s.offset().x(), s.offset().y()), m.rTheta(s.rotation()));
//		AffineTransform at = new AffineTransform(tCR[0][0], tCR[1][0], tCR[0][1], tCR[1][1], tCR[0][2], tCR[1][2]);
//		Point2D worldP = new Point2D.Double();
//		return at.transform(objectP, worldP);
	}
	
	public Point2D worldToView(Point2D worldP) {
//		Point2D translatedP = new Point2D.Double(worldP.getX()+shapes.xscroll(), worldP.getY()+shapes.yscroll());
//		Point2D scaledP = new Point2D.Double(translatedP.getX()*shapes.zoom(), translatedP.getY()*shapes.zoom());
//		return scaledP;
		
		double[][] sFtP = m.dot(m.sF(shapes.zoom()), m.tCInv(shapes.xscroll(), shapes.yscroll()));
		AffineTransform at = new AffineTransform(sFtP[0][0], sFtP[1][0], sFtP[0][1], sFtP[1][1], sFtP[0][2], sFtP[1][2]);
		Point2D viewP = new Point2D.Double();		
		return at.transform(worldP, viewP);
	}
	
	public Point2D viewToWorld(Point2D viewP) {
//		Point2D scaledP = new Point2D.Double(viewP.getX()/shapes.zoom(), viewP.getY()/shapes.zoom());
//		Point2D translatedP = new Point2D.Double(scaledP.getX()+shapes.xscroll(), scaledP.getY()+shapes.yscroll());
//		return translatedP;
		
		double[][] tPsF = m.dot(m.tC(shapes.xscroll(), shapes.yscroll()), m.sFInv(shapes.zoom()));
		AffineTransform at = new AffineTransform(tPsF[0][0], tPsF[1][0], tPsF[0][1], tPsF[1][1], tPsF[0][2], tPsF[1][2]);
		Point2D worldP = new Point2D.Double();		
		return at.transform(viewP, worldP);
	}
	
//	public Point2D objectToView(Point2D objectP, Shape s) {
//		Point2D worldP = objectToWorld(objectP,s);
//		Point2D viewP = worldToView(worldP);
//		return viewP;
//	}
//	
//	public Point2D viewToObject(Point2D viewP, Shape s) {
//		Point2D worldP = viewToWorld(viewP);
//		Point2D objectP = worldToObject(worldP,s);
//		return objectP;
//	}
	
	public void rotateShape(Shape s, Point2D translatedP) {
		double rotateAngle = Math.atan2(translatedP.getY(), translatedP.getX());
		rotateAngle = (rotateAngle + Math.PI/2);
		s.setRotation(rotateAngle);
	}
}
