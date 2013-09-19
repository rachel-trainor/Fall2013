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
	
	MyMouseListener() {
		shapes = new Shapes();
		newShape = null;
		newTriangle = null;
	}
	
	MyMouseListener(Shapes s) {
		shapes = s;
		newShape = null;
		newTriangle = null;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		Color c = shapes.getColor();
		Point p = arg0.getPoint();
		
		switch(shapes.getType()) {
		case RECTANGLE:
			newShape = new Rectangle(c, p); break;
		case SQUARE:
			newShape = new Square(c, p); break;
		case ELLIPSE:
			newShape = new Ellipse(c, p); break;
		case CIRCLE:
			newShape = new Circle(c, p); break;
		case TRIANGLE:
			if(newTriangle == null) {
				newShape = newTriangle = new Triangle(c, p);
			} else {
				newShape = newTriangle = updateTriangle(newTriangle, p);
				if(newTriangle.getPointOne() != newTriangle.getPointThree()) {
					shapes.shapes.add(newTriangle);
					GUIFunctions.refresh();
//					System.out.println("Added triangle with points" + newTriangle.getPointOne() + newTriangle.getPointTwo() + newTriangle.getPointThree());
					newShape = null;
					newTriangle = null;
				} 
			}
			break;
		case LINE:
			newShape = new Line(c, p); break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		Color c = shapes.getColor();
		Point p = arg0.getPoint();
		Shape s = null;
		if(newShape != null) {
			s = newShape;
		} else {
			return;
		}
		
		if(s.getType() == Type.TRIANGLE) {

		} else {
			updateShape(s, p);
			shapes.shapes.add(s);
			GUIFunctions.refresh();
			newShape = null;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Color c = shapes.getColor();
		Point p = e.getPoint();
		Shape s = null;
		if(newShape != null) {
			s = newShape;
		} else {
			return;
		}
		
		if(s.getType() == Type.TRIANGLE) {
			
		} else {
			updateShape(s,p);
			shapes.shapes.add(s);
			GUIFunctions.refresh();
			shapes.shapes.remove(shapes.shapes.size()-1);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(shapes.getType() == Type.TRIANGLE) {
			if(newTriangle != null && newTriangle.getPointOne() != newTriangle.getPointTwo()) {
				Triangle tmp = new Triangle(newTriangle.getColor(), newTriangle.getPointOne());
				tmp.setPointTwo(newTriangle.getPointTwo());
				tmp.setPointThree(e.getPoint());
				shapes.shapes.add(tmp);
				GUIFunctions.refresh();
				shapes.shapes.remove(shapes.shapes.size()-1);
			}
		}
		 else {
			newTriangle = null;
			GUIFunctions.refresh();
		}
	}
	
	private void updateShape(Shape s, Point p) {
		if(p.x > s.getTopLeft().x && p.y < s.getTopLeft().y) {
			Point tl = new Point(s.getTopLeft().x, p.y);
			Point br = new Point(p.x, s.getTopLeft().y);
			s.setTopLeft(tl);
			s.setBottomRight(br);
		} else if (p.x < s.getTopLeft().x && p.y < s.getTopLeft().y) {
			s.setBottomRight(s.getTopLeft());
			s.setTopLeft(p);
		} else if (p.x < s.getTopLeft().x && p.y > s.getTopLeft().y) {
			Point tl = new Point(p.x, s.getTopLeft().y);
			Point br = new Point(s.getTopLeft().x, p.y);
			s.setTopLeft(tl);
			s.setBottomRight(br);
		} else {
			s.setBottomRight(p);
		}
	}
	
	private Triangle updateTriangle(Triangle tri, Point p) {
		if(tri.getPointOne() == tri.getPointTwo()) {
			tri.setPointTwo(p);
//			System.out.println("Set point 2");
//			System.out.println("" + tri.getPointOne() + tri.getPointTwo() + tri.getPointThree());
			return tri;
		} else if(tri.getPointOne() == tri.getPointThree()) {
			tri.setPointThree(p);
//			System.out.println("Set point 3");
//			System.out.println("" + tri.getPointOne() + tri.getPointTwo() + tri.getPointThree());
			return tri;
		} else {
			System.out.println("Tried to set triangle point, but they are all already set");
			return null;
		}
	}

}
