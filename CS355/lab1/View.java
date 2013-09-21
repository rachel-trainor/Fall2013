package lab1;

import java.awt.Graphics2D;

import resources.ViewRefresher;
import shapes.*;

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
			switch(s.getClass().getName()) {
			case "shapes.Rectangle": 
				drawRectangle(g2d, (Rectangle) s); break;
			case "shapes.Square":
				drawSquare(g2d, (Square) s); break;
			case "shapes.Ellipse":
				drawEllipse(g2d, (Ellipse) s); break;
			case "shapes.Circle":
				drawCircle(g2d, (Circle) s); break;
			case "shapes.Triangle":
				drawTriangle(g2d, (Triangle) s); break;
			case "shapes.Line":
				drawLine(g2d, (Line) s); break;
			default: 
				System.out.println(s.getClass().getName());
				break;
			}
		}
		System.out.println("");
	}
	
	private void drawRectangle(Graphics2D g2d, Rectangle rectangle) {
		g2d.setColor(rectangle.color());
		g2d.fillRect(rectangle.uL().x, rectangle.uL().y, rectangle.width(), rectangle.height());
	}
	
	private void drawSquare(Graphics2D g2d, Square square) {
		g2d.setColor(square.color());
		int size = Math.min(square.size(), square.size());
		g2d.fillRect(square.uL().x, square.uL().y, size, size);
	}
	
	private void drawEllipse(Graphics2D g2d, Ellipse ellipse) {
		g2d.setColor(ellipse.color());
		g2d.fillOval(ellipse.uL().x, ellipse.uL().y, ellipse.width(), ellipse.height());
	}
	
	private void drawCircle(Graphics2D g2d, Circle circle) {
		g2d.setColor(circle.color());
		g2d.fillOval(circle.uL().x, circle.uL().y, circle.radius()*2, circle.radius()*2);
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
