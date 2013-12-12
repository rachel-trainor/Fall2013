package lab1;

import java.awt.Color;
import java.awt.image.*;
import java.util.Iterator;

import resources.CS355Controller;
import resources.GUIFunctions;

import lab1.Manager.Type;

public class Controller implements CS355Controller {
	Manager manager;
	ThreeDManager manager3d;
	double maxZoom = 4.0;
	double minZoom = 0.25;
	VirtualCamera cam;
	
	Controller() {
		manager = new Manager();
		manager3d = new ThreeDManager();
		cam = manager3d.camera();
		GUIFunctions.refresh();
	}
	
	Controller(Manager m, ThreeDManager threeD) {
		manager = m;
		manager3d = threeD; 
		cam = manager3d.camera();
	}

	@Override
	public void colorButtonHit(Color c) {
		GUIFunctions.changeSelectedColor(c);
		manager.setColor(c);
		if(manager.selected() != null) {
			manager.selected().setColor(c);
		}
		GUIFunctions.refresh();
	}

	@Override
	public void triangleButtonHit() {
		manager.setType(Type.TRIANGLE);
		manager.setSelected(null);
		GUIFunctions.refresh();
	}

	@Override
	public void squareButtonHit() {
		manager.setType(Type.SQUARE);
		manager.setSelected(null);
		GUIFunctions.refresh();
	}

	@Override
	public void rectangleButtonHit() {
		manager.setType(Type.RECTANGLE);
		manager.setSelected(null);
		GUIFunctions.refresh();
	}

	@Override
	public void circleButtonHit() {
		manager.setType(Type.CIRCLE);
		manager.setSelected(null);
		GUIFunctions.refresh();
	}

	@Override
	public void ellipseButtonHit() {
		manager.setType(Type.ELLIPSE);
		manager.setSelected(null);
		GUIFunctions.refresh();
	}

	@Override
	public void lineButtonHit() {
		manager.setType(Type.LINE);
		manager.setSelected(null);
		GUIFunctions.refresh();
	}

	@Override
	public void selectButtonHit() {
		manager.setSelected(null);
		manager.setType(Type.SELECT);
		GUIFunctions.refresh();
	}

	@Override
	public void zoomInButtonHit() {	
		double newZoom = manager.zoom()*2;
		if(newZoom <= maxZoom) {
			manager.setZoom(newZoom);
			GUIFunctions.setHScrollBarKnob((int) (512/newZoom));
			GUIFunctions.setVScrollBarKnob((int) (512/newZoom));
			manager.setXScroll(manager.xscroll() + (int) (512/(2*newZoom)));
			manager.setYScroll(manager.yscroll() + (int) (512/(2*newZoom)));
			normalizeScrollbars();
		}
		GUIFunctions.refresh();
	}

	@Override
	public void zoomOutButtonHit() {
		double newZoom = manager.zoom()/2;
		if(newZoom >= minZoom) {
			manager.setZoom(newZoom);
			manager.setXScroll(manager.xscroll() - (int) (512/(4*newZoom)));
			manager.setYScroll(manager.yscroll() - (int) (512/(4*newZoom)));
			normalizeScrollbars();
			GUIFunctions.setHScrollBarKnob((int) (512/newZoom));
			GUIFunctions.setVScrollBarKnob((int) (512/newZoom));
		}
		GUIFunctions.refresh();
	}

	@Override
	public void hScrollbarChanged(int value) {
		manager.setXScroll(value);
		GUIFunctions.refresh();
	}

	@Override
	public void vScrollbarChanged(int value) {
		manager.setYScroll(value);
		GUIFunctions.refresh();
	}
	
	public void normalizeScrollbars() {
		int scrollbarsize = (int) (512/manager.zoom());
		if(manager.xscroll() > 2048 - scrollbarsize) {
			manager.setXScroll(2048 - scrollbarsize);
		}
		if(manager.yscroll() > 2048 - scrollbarsize) {
			manager.setYScroll(2048 - scrollbarsize);
		}
		if(manager.xscroll() < 0) {
			manager.setXScroll(0);
		}
		if(manager.yscroll() < 0) {
			manager.setYScroll(0);
		}
		GUIFunctions.setHScrollBarPosit((int) manager.xscroll());
		GUIFunctions.setVScrollBarPosit((int) manager.yscroll());
	}

	@Override
	public void toggle3DModelDisplay() {
		manager3d.toggle3d();
		GUIFunctions.refresh();
	}

	@Override
	public void keyPressed(Iterator<Integer> iterator) {
		if(!manager3d.showHouse()) return;
		
		int tmp = iterator.next();
		char ch = (char) tmp;
		switch (ch) {
		case 'A': cam.left(); break;
		case 'D': cam.right(); break;
		case 'W': if(cam.z > -manager3d.zFar()) cam.forward(); break;
		case 'S': if(cam.z < manager3d.zFar()) cam.backward(); break;
		case 'Q': cam.turnL(); break;
		case 'E': cam.turnR(); break;
		case 'R': cam.up(); break;
		case 'F': cam.down(); break;
		case 'H': cam.home(); break;
		}
		GUIFunctions.refresh();
	}

	@Override
	public void doEdgeDetection() {
		manager.image.detectEdges();
		GUIFunctions.refresh();
	}

	@Override
	public void doSharpen() {
		manager.image.sharpen();
		GUIFunctions.refresh();
	}

	@Override
	public void doMedianBlur() {
		manager.image.medianBlur();
		GUIFunctions.refresh();
	}

	@Override
	public void doUniformBlur() {
		manager.image.uniformBlur();
		GUIFunctions.refresh();
	}

	@Override
	public void doChangeContrast(int contrastAmountNum) {
		manager.image.changeContrast(contrastAmountNum);
		GUIFunctions.refresh();
	}

	@Override
	public void doChangeBrightness(int brightnessAmountNum) {
		manager.image.changeBrightness(brightnessAmountNum);
		GUIFunctions.refresh();
	}

	@Override
	public void doLoadImage(BufferedImage openImage) {
		manager.setImage(openImage);
		GUIFunctions.refresh();
	}

	@Override
	public void toggleBackgroundDisplay() {
		manager.toggleImage();
		GUIFunctions.refresh();
	}

}
