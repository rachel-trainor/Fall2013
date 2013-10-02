package lab1;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import model.*;

import resources.ViewRefresher;

public class View implements ViewRefresher {
	Shapes shapes;
	
	View() {
		shapes = new Shapes();
	}
	
	View(Shapes s) {
		shapes = s;
	}

	@Override
	public void refreshView(Graphics2D g2d) {
		for(Shape s: shapes.list()) {
			AffineTransform oldXForm = g2d.getTransform();
			g2d.translate(s.offset().x(), s.offset().y());
			g2d.rotate(s.rotation());
			switch(s.getClass().getName()) {
			case "model.Rectangle": 
				drawRectangle(g2d, (Rectangle) s); g2d.setTransform(oldXForm); break;
			case "model.Square":
				drawSquare(g2d, (Square) s); g2d.setTransform(oldXForm); break;
			case "model.Ellipse":
				drawEllipse(g2d, (Ellipse) s); g2d.setTransform(oldXForm); break;
			case "model.Circle":
				drawCircle(g2d, (Circle) s); g2d.setTransform(oldXForm); break;
			case "model.Triangle":
				drawTriangle(g2d, (Triangle) s); g2d.setTransform(oldXForm); break;
			case "model.Line":
				drawLine(g2d, (Line) s); g2d.setTransform(oldXForm); break;
			default: 
				System.out.println(s.getClass().getName());
				g2d.setTransform(oldXForm);
				break;
			}
		}
		System.out.println("");
	}
	
	private void drawRectangle(Graphics2D g2d, Rectangle rectangle) {
		g2d.setColor(rectangle.color());
		g2d.fillRect(-rectangle.width()/2, -rectangle.height()/2, rectangle.width(), rectangle.height());
	}
	
	private void drawSquare(Graphics2D g2d, Square square) {
		g2d.setColor(square.color());
		g2d.fillRect(-square.size()/2, -square.size()/2, square.size(), square.size());
	}
	
	private void drawEllipse(Graphics2D g2d, Ellipse ellipse) {
		g2d.setColor(ellipse.color());
		g2d.fillOval(-ellipse.width()/2, -ellipse.height()/2, ellipse.width(), ellipse.height());
	}
	
	private void drawCircle(Graphics2D g2d, Circle circle) {
		g2d.setColor(circle.color());
		g2d.fillOval(-circle.radius(), -circle.radius(), circle.radius()*2, circle.radius()*2);
	}
	
	private void drawTriangle(Graphics2D g2d, Triangle triangle) {
		g2d.setColor(triangle.color());
		int[] xs = new int[]{triangle.p1().x, triangle.p2().x, triangle.p3().x};
		int[] ys = new int[]{triangle.p1().y, triangle.p2().y, triangle.p3().y};
		g2d.fillPolygon(xs, ys, 3);
	}
	
	private void drawLine(Graphics2D g2d, Line line) {
		g2d.setColor(line.color());
		g2d.drawLine(line.p1().x, line.p1().y, line.p2().x, line.p2().y);
	}

}
