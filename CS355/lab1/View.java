package lab1;

import java.awt.Graphics2D;
import java.awt.Point;

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
		for(Shape s: shapes.shapes) {
			switch(s.getType()) {
			case RECTANGLE: 
				drawRectangle(g2d, (Rectangle) s); break;
			case SQUARE:
				drawSquare(g2d, (Square) s); break;
			case ELLIPSE:
				drawEllipse(g2d, (Ellipse) s); break;
			case CIRCLE:
				drawCircle(g2d, (Circle) s); break;
			case TRIANGLE:
				drawTriangle(g2d, (Triangle) s); break;
			case LINE:
				drawLine(g2d, (Line) s); break;
			default: 
				break;
			}
		}
		System.out.println("");
	}
	
	private void drawRectangle(Graphics2D g2d, Rectangle rectangle) {
		Point topLeft = rectangle.getTopLeft();
		g2d.setColor(rectangle.getColor());
		g2d.fillRect(topLeft.x, topLeft.y, rectangle.getWidth(), rectangle.getHeight());
	}
	
	private void drawSquare(Graphics2D g2d, Square square) {
		Point topLeft = square.getTopLeft();
		g2d.setColor(square.getColor());
		g2d.fillRect(topLeft.x, topLeft.y, square.getWidth(), square.getWidth());
	}
	
	private void drawEllipse(Graphics2D g2d, Ellipse ellipse) {
		Point topLeft = ellipse.getTopLeft();
		g2d.setColor(ellipse.getColor());
		g2d.fillOval(topLeft.x, topLeft.y, ellipse.getWidth(), ellipse.getHeight());
	}
	
	private void drawCircle(Graphics2D g2d, Circle circle) {
		Point topLeft = circle.getTopLeft();
		g2d.setColor(circle.getColor());
		g2d.fillOval(topLeft.x, topLeft.y, circle.getWidth(), circle.getWidth());
	}
	
	private void drawTriangle(Graphics2D g2d, Triangle triangle) {
		g2d.setColor(triangle.getColor());
		g2d.fillPolygon(triangle.getXs(), triangle.getYs(), 3);
	}
	
	private void drawLine(Graphics2D g2d, Line line) {
		g2d.setColor(line.getColor());
		g2d.drawLine(line.getTopLeft().x, line.getTopLeft().y, line.getBottomRight().x, line.getBottomRight().y);
	}

}
