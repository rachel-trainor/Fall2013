package lab1;

import java.awt.Color;

import resources.CS355Controller;
import resources.GUIFunctions;

import lab1.Shapes.Type;

public class Controller implements CS355Controller {
	Shapes shapes;
	double maxZoom = 4.0;
	double minZoom = 0.25;
	
	Controller() {
		shapes = new Shapes();
		GUIFunctions.refresh();
	}
	
	Controller(Shapes s) {
		shapes = s;
	}

	@Override
	public void colorButtonHit(Color c) {
		GUIFunctions.changeSelectedColor(c);
		shapes.setColor(c);
		if(shapes.selected() != null) {
			shapes.selected().setColor(c);
		}
		GUIFunctions.refresh();
	}

	@Override
	public void triangleButtonHit() {
		shapes.setType(Type.TRIANGLE);
		shapes.setSelected(null);
		GUIFunctions.refresh();
	}

	@Override
	public void squareButtonHit() {
		shapes.setType(Type.SQUARE);
		shapes.setSelected(null);
		GUIFunctions.refresh();
	}

	@Override
	public void rectangleButtonHit() {
		shapes.setType(Type.RECTANGLE);
		shapes.setSelected(null);
		GUIFunctions.refresh();
	}

	@Override
	public void circleButtonHit() {
		shapes.setType(Type.CIRCLE);
		shapes.setSelected(null);
		GUIFunctions.refresh();
	}

	@Override
	public void ellipseButtonHit() {
		shapes.setType(Type.ELLIPSE);
		shapes.setSelected(null);
		GUIFunctions.refresh();
	}

	@Override
	public void lineButtonHit() {
		shapes.setType(Type.LINE);
		shapes.setSelected(null);
		GUIFunctions.refresh();
	}

	@Override
	public void selectButtonHit() {
		shapes.setSelected(null);
		shapes.setType(Type.SELECT);
		GUIFunctions.refresh();
	}

	@Override
	public void zoomInButtonHit() {	
		double newZoom = shapes.zoom()*2;
		if(newZoom <= maxZoom) {
			shapes.setZoom(newZoom);
			GUIFunctions.setHScrollBarKnob((int) (512/newZoom));
			GUIFunctions.setVScrollBarKnob((int) (512/newZoom));
			shapes.setXScroll(shapes.xscroll() + (int) (512/(2*newZoom)));
			shapes.setYScroll(shapes.yscroll() + (int) (512/(2*newZoom)));
			normalizeScrollbars();
		}
		GUIFunctions.refresh();
	}

	@Override
	public void zoomOutButtonHit() {
		double newZoom = shapes.zoom()/2;
		if(newZoom >= minZoom) {
			shapes.setZoom(newZoom);
			shapes.setXScroll(shapes.xscroll() - (int) (512/(4*newZoom)));
			shapes.setYScroll(shapes.yscroll() - (int) (512/(4*newZoom)));
			normalizeScrollbars();
			GUIFunctions.setHScrollBarKnob((int) (512/newZoom));
			GUIFunctions.setVScrollBarKnob((int) (512/newZoom));
		}
		GUIFunctions.refresh();
	}

	@Override
	public void hScrollbarChanged(int value) {
		shapes.setXScroll(value);
		GUIFunctions.refresh();
	}

	@Override
	public void vScrollbarChanged(int value) {
		shapes.setYScroll(value);
		GUIFunctions.refresh();
	}
	
	public void normalizeScrollbars() {
		int scrollbarsize = (int) (512/shapes.zoom());
		if(shapes.xscroll() > 2048 - scrollbarsize) {
			shapes.setXScroll(2048 - scrollbarsize);
		}
		if(shapes.yscroll() > 2048 - scrollbarsize) {
			shapes.setYScroll(2048 - scrollbarsize);
		}
		if(shapes.xscroll() < 0) {
			shapes.setXScroll(0);
		}
		if(shapes.yscroll() < 0) {
			shapes.setYScroll(0);
		}
		GUIFunctions.setHScrollBarPosit((int) shapes.xscroll());
		GUIFunctions.setVScrollBarPosit((int) shapes.yscroll());
	}

}
