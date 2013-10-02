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
	}

	@Override
	public void triangleButtonHit() {
		shapes.setType(Type.TRIANGLE);
	}

	@Override
	public void squareButtonHit() {
		shapes.setType(Type.SQUARE);
	}

	@Override
	public void rectangleButtonHit() {
		shapes.setType(Type.RECTANGLE);
	}

	@Override
	public void circleButtonHit() {
		shapes.setType(Type.CIRCLE);
	}

	@Override
	public void ellipseButtonHit() {
		shapes.setType(Type.ELLIPSE);
	}

	@Override
	public void lineButtonHit() {
		shapes.setType(Type.LINE);
	}

	@Override
	public void selectButtonHit() {
		// TODO Auto-generated method stub
		shapes.setType(Type.SELECT);
	}

	@Override
	public void zoomInButtonHit() {
		// TODO Auto-generated method stub
		shapes.setType(Type.NONE);
		
	}

	@Override
	public void zoomOutButtonHit() {
		// TODO Auto-generated method stub
		shapes.setType(Type.NONE);
		
	}

	@Override
	public void hScrollbarChanged(int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void vScrollbarChanged(int value) {
		// TODO Auto-generated method stub
		
	}

}
