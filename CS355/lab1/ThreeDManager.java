package lab1;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;

import LWJGL.HouseModel;
import LWJGL.Line3D;
import LWJGL.Point3D;

public class ThreeDManager {
	private VirtualCamera cam;
	protected HouseModel model;
	private float zNear, zFar;
	private double[][] clip, worldToCamera;
	private boolean showHouse = false;
	MatrixOps m = new MatrixOps();
	
	public ThreeDManager() {
		cam = new VirtualCamera();
		model = new HouseModel();
		zNear = 0.1f;
		zFar = 500;
		clip = new double[4][4];
		showHouse = false;
		
		double zoomX = 1 / Math.tan(35 * Math.PI / 180);
        double zoomY = zoomX * 3 / 4;
		clip[0][0] = zoomX;
        clip[1][1] = zoomY;
        clip[2][2] = (zFar + zNear) / (zFar - zNear);
        clip[2][3] = (2 * zFar * zNear) / (zFar - zNear);
        clip[3][2] = 1;
        
        double[][] translate = new double[3][3];
        translate[0][0] = 1;
        translate[1][1] = -1;
        translate[2][2] = 1;
        translate[0][2] = 1;
        translate[1][2] = 1;

        double[][] scale = new double[3][3];
        scale[0][0] = 1024;
        scale[1][1] = 1024;
        scale[2][2] = 1;

        worldToCamera = m.multiply(scale, translate);
	}
	
	public VirtualCamera camera() {
		return cam;
	}
	
	public float zNear() {
		return zNear;
	}
	
	public float zFar() {
		return zFar;
	}
	
	public void toggle3d() {
		showHouse = !showHouse;
	}
	
	public boolean showHouse() {
		return showHouse;
	}
	
	public List<LWJGL.Line3D> houseLines() {
		return model.lines();
	}
	
	private static double[][] pointToHomogenous(Point3D point) {
        return new double[][] {{point.x}, {point.y}, {point.z}, {1}};
	}
	
	Line3D objectToCamera(Line3D line) {
        double[][] translation = m.translation3d(-cam.x(), -cam.y(), -cam.z());
        double[][] rotation = m.rotation3d(cam.rotation());
        double[][] combined = m.multiply(rotation, translation);

        double[][] start = m.multiply(combined, pointToHomogenous(line.start));
        double[][] end = m.multiply(combined, pointToHomogenous(line.end));

        return new Line3D(new Point3D(start[0][0], start[1][0], start[2][0]),
                        new Point3D(end[0][0], end[1][0], end[2][0]));
	}
	
	double[][][] cameraToClip(Line3D line) {
        double[][] start = m.multiply(clip, pointToHomogenous(line.start));
        double[][] end = m.multiply(clip, pointToHomogenous(line.end));

        return new double[][][] { start, end };
	}
	
	Line2D clipToCanonical(double[][][] line) {
		Point2D start = new Point2D.Double(line[0][0][0] / Math.abs(line[0][3][0]), 
										   line[0][1][0] / Math.abs(line[0][3][0]));
		Point2D end = new Point2D.Double(line[1][0][0] / Math.abs(line[1][3][0]), 
										 line[1][1][0] / Math.abs(line[1][3][0]));
        return new Line2D.Double(start, end);
	}
	
	Line2D canonicalToScreen(Line2D line) {
		double[][] l1 = {{line.getX1()}, {line.getY1()}, {1}};
        double[][] start = m.multiply(worldToCamera, l1);
        double[][] l2 = {{line.getX2()}, {line.getY2()}, {1}};
        double[][] end = m.multiply(worldToCamera, l2);

        return new Line2D.Double(new Point2D.Double(start[0][0], start[1][0]),
                        		 new Point2D.Double(end[0][0], end[1][0]));
	}
	
	boolean inFrustum(double[][][] line) {
        if (line[0][3][0] == 0 || line[1][3][0] == 0)
                return false;
        if (line[0][0][0] < -line[0][3][0] && line[1][0][0] < -line[1][3][0])
                return false;
        if (line[0][0][0] > line[0][3][0] && line[1][0][0] > line[1][3][0])
                return false;
        if (line[0][1][0] < -line[0][3][0] && line[1][1][0] < -line[1][3][0])
                return false;
        if (line[0][1][0] > line[0][3][0] && line[1][1][0] > line[1][3][0])
                return false;
        if (line[0][2][0] < -line[0][3][0] || line[1][2][0] < -line[1][3][0])
                return false;
        if (line[0][2][0] / line[0][2][0] > line[0][3][0]
                        && line[1][2][0] / line[1][2][0] > line[1][3][0]) {
                return false;
        }

        return true;
	}
}
