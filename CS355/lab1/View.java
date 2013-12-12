package lab1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.*;

import model.*;

import resources.ViewRefresher;

public class View implements ViewRefresher {
	Manager manager;
	ThreeDManager manager3d;
	int H_SIZE = 16;
	AffineTransform selectedShapeAT;
	Shape selectedShape;
	
	View() {
		manager = new Manager();
		manager3d = new ThreeDManager();
	}
	
	View(Manager m, ThreeDManager threeD) {
		manager = m;
		manager3d = threeD;
	}

	@Override
	public void refreshView(Graphics2D g2d) {
		if(manager.showImage && manager.image != null)
			renderImage(g2d, new AffineTransform());
		H_SIZE = 16;
		selectedShapeAT = null;
		selectedShape = null;
		H_SIZE = (int) (H_SIZE/manager.zoom());
		g2d.setStroke(new BasicStroke((float) (2/manager.zoom()))); // not necessary, but it looks better
		int currentlySelected = manager.selectedIndex();
		for(int i=0; i<manager.list().size(); i++) {
			Shape s = manager.get(i);
			boolean isSelected = ((currentlySelected == i) ? true : false);
			AffineTransform oldXForm = g2d.getTransform();
			double zoom = manager.zoom();
			AffineTransform trans = 
					new AffineTransform(zoom*Math.cos(s.rotation()),
										zoom*Math.sin(s.rotation()),
										-zoom*Math.sin(s.rotation()), 
										zoom*Math.cos(s.rotation()),
										zoom*(s.offset().x()-manager.xscroll()),
										zoom*(s.offset().y()-manager.yscroll()));
//			g2d.transform(trans);
			g2d.setTransform(trans);
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
		if(manager3d.showHouse())
			render3d(g2d);
	}
	
	private void drawRectangle(Graphics2D g2d, Rectangle rectangle, boolean isSelected) {
		g2d.setColor(rectangle.color());
		g2d.fillRect((int) -rectangle.width()/2, (int) -rectangle.height()/2, (int) rectangle.width(), (int) rectangle.height());
		if(isSelected) { 
			selectedShape = rectangle;
			selectedShapeAT = g2d.getTransform();
		}
	}
	
	private void drawBoundingBoxHandles(Graphics2D g2d, Shape s) {
		double h = 0;
		double w = 0;
//		Stroke oldStroke = g2d.getStroke();
//		g2d.setStroke(new BasicStroke((int) (1/manager.zoom())));
		
		switch(s.getClass().getName()) {
		case "model.Rectangle": 
			Rectangle rectangle = (Rectangle) s;
			h = rectangle.height();
			w = rectangle.width();
			g2d.drawRect((int) -w/2, (int) -h/2, (int) w, (int) h);
			break;
		case "model.Square":
			Square square = (Square) s;
			h = w = square.size();
			g2d.drawRect((int) -w/2, (int) -h/2, (int) w, (int) h);
			break;
		case "model.Ellipse":
			Ellipse ellipse = (Ellipse) s;
			h = ellipse.height();
			w = ellipse.width();
			g2d.drawOval((int) -w/2, (int) -h/2, (int) w, (int) h);
			break;
		case "model.Circle":
			Circle circle = (Circle) s;
			h = w = circle.radius()*2;
			g2d.drawOval((int) -w/2, (int) -h/2, (int) w, (int) h);
			break;
		default: 
			System.out.println("Error when drawing bounding box handles: No valid shape selected");
			return;
		}
		
		for(Point2D handleCenter: s.getHandles()) {
			g2d.drawOval((int) handleCenter.getX()-H_SIZE/2, (int) handleCenter.getY()-H_SIZE/2, H_SIZE, H_SIZE);
		}
		
		if(s.getClass().getName() != "model.Circle")
			drawRotationHandle(g2d, s);
	}
	
	private void drawRotationHandle(Graphics2D g2d, Shape s) {
		Point2D handle = new Point2D.Double(s.getRotationHandle().getX(), s.getRotationHandle().getY());
		g2d.drawLine(0, 0, (int) handle.getX(), (int) handle.getY()+H_SIZE/2);
		g2d.drawOval((int) handle.getX()-H_SIZE/2, (int) handle.getY()-H_SIZE/2, H_SIZE, H_SIZE);
	}
	
	private void drawSquare(Graphics2D g2d, Square square, boolean isSelected) {
		g2d.setColor(square.color());
		g2d.fillRect((int) -square.size()/2, (int) -square.size()/2, (int) square.size(), (int) square.size());
		if(isSelected) { 
			selectedShape = square;
			selectedShapeAT = g2d.getTransform();
		}
	}
	
	private void drawEllipse(Graphics2D g2d, Ellipse ellipse, boolean isSelected) {
		g2d.setColor(ellipse.color());
		g2d.fillOval((int) -ellipse.width()/2, (int) -ellipse.height()/2, (int) ellipse.width(), (int) ellipse.height());
		if(isSelected) { 
			selectedShape = ellipse;
			selectedShapeAT = g2d.getTransform();
		}
	}
	
	private void drawCircle(Graphics2D g2d, Circle circle, boolean isSelected) {
		g2d.setColor(circle.color());
		g2d.fillOval((int) -circle.radius(), (int) -circle.radius(), (int) circle.radius()*2, (int) circle.radius()*2);
		if(isSelected) { 
			selectedShape = circle;
			selectedShapeAT = g2d.getTransform();
		}
	}
	
	private void drawTriangle(Graphics2D g2d, Triangle triangle, boolean isSelected) {
		g2d.setColor(triangle.color());
		int[] xs = new int[]{(int) triangle.p1().getX(), (int) triangle.p2().getX(), (int) triangle.p3().getX()};
		int[] ys = new int[]{(int) triangle.p1().getY(), (int) triangle.p2().getY(), (int) triangle.p3().getY()};
		g2d.fillPolygon(xs, ys, 3);
		if(isSelected) { 
			selectedShape = triangle;
			selectedShapeAT = g2d.getTransform();
		}
	}
	
	private void drawTriangleHandles(Graphics2D g2d, Triangle triangle) {
		int[] xs = new int[]{(int) triangle.p1().getX(), (int) triangle.p2().getX(), (int) triangle.p3().getX()};
		int[] ys = new int[]{(int) triangle.p1().getY(), (int) triangle.p2().getY(), (int) triangle.p3().getY()};
		g2d.drawPolygon(xs, ys, 3);
		for(Point2D handleCenter: triangle.getHandles()) {
			g2d.drawOval((int) handleCenter.getX()-H_SIZE/2, (int) handleCenter.getY()-H_SIZE/2, H_SIZE, H_SIZE);
		}
		drawRotationHandle(g2d, triangle);
	}
	
	private void drawLine(Graphics2D g2d, Line line, boolean isSelected) {
		g2d.setColor(line.color());
		g2d.drawLine((int) line.p1().getX(), (int) line.p1().getY(), (int) line.p2().getX(), (int) line.p2().getY());
		if(isSelected) { 
			selectedShape = line;
			selectedShapeAT = g2d.getTransform();
		}
	}
	
	private void drawLineHandles(Graphics2D g2d, Line line) {
		Point2D p1 = line.p1();
		Point2D p2 = line.p2();
		g2d.drawOval((int) p1.getX()-H_SIZE/2, (int) p1.getY()-H_SIZE/2, H_SIZE, H_SIZE);
		g2d.drawOval((int) p2.getX()-H_SIZE/2, (int) p2.getY()-H_SIZE/2, H_SIZE, H_SIZE);
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
	
	public void render3d(Graphics2D g2d) {
        g2d.setTransform(worldToView());
        
		for (LWJGL.Line3D line3D : manager3d.houseLines()) {
            double[][][] clipLine = manager3d.cameraToClip(manager3d.objectToCamera(line3D));
            if (!manager3d.inFrustum(clipLine))
            	continue;
            Line2D line2D = manager3d.canonicalToScreen(manager3d.clipToCanonical(clipLine));
            g2d.drawLine((int) line2D.getX1(), (int) line2D.getY1(), 
            			 (int) line2D.getX2(), (int) line2D.getY2());
		}
    }

	public void renderImage(Graphics2D g2d, AffineTransform at) {
		AffineTransform oldXForm = g2d.getTransform();
		g2d.setTransform(worldToView());
		int w = manager.image.getWidth();
		int h = manager.image.getHeight();
		int[][] p = manager.image.getPixels();
		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster wr = bi.getRaster();
		for (int row = 0; row < h; row++) {
			for (int col = 0; col < w; col++) {
				wr.setPixel(col, row, new int[]{p[row][col]});
			}
		}
		g2d.drawImage(bi, 1024 - w/2, 1024 - h/2, w, h, null);
		g2d.setTransform(oldXForm);
	}
	
	public AffineTransform viewToWorld() {
		AffineTransform transform = new AffineTransform();
        transform.concatenate(new AffineTransform(new double[] {1, 0, 0, 1, manager.xscroll(), manager.yscroll()}));
        transform.concatenate(new AffineTransform(new double[] {1/manager.zoom(), 0, 0, 1/manager.zoom()}));
        return transform;
	}
	
	public AffineTransform worldToView() {
        AffineTransform transform = new AffineTransform();
        transform.concatenate(new AffineTransform(new double[] { manager.zoom(), 0, 0, manager.zoom() }));
        transform.concatenate(new AffineTransform(new double[] { 1, 0, 0, 1, -manager.xscroll(), -manager.yscroll() }));
        return transform;
	}
}
