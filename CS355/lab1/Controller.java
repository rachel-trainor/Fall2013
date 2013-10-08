package lab1;

import java.awt.Color;

import resources.CS355Controller;
import resources.GUIFunctions;

import lab1.Shapes.Type;

public class Controller implements CS355Controller {
	Shapes shapes;
	
	Controller() {
		shapes = new Shapes();
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
		// TODO Auto-generated method stub
		shapes.setType(Type.NONE);
		GUIFunctions.refresh();
	}

	@Override
	public void zoomOutButtonHit() {
		// TODO Auto-generated method stub
		shapes.setType(Type.NONE);
		GUIFunctions.refresh();
	}

	@Override
	public void hScrollbarChanged(int value) {
		// TODO Auto-generated method stub
		GUIFunctions.refresh();
	}

	@Override
	public void vScrollbarChanged(int value) {
		// TODO Auto-generated method stub
		GUIFunctions.refresh();
	}

}
