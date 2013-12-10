package lab1;

public class VirtualCamera {
	float x, y, z, rotation;
    VirtualCamera() {
    	x = 0;
    	y = 5;
    	z = 20;
    	rotation = 0;
    }
    void down() { y--; }
    void up() { y++; }
    void turnL() { rotation++; }
    void turnR() { rotation--; }
    void left() {
    	x -= Math.cos(rotation * Math.PI / 180);
    	z += Math.sin(rotation * Math.PI / 180);
    }
    void right() {
    	x += Math.cos(rotation * Math.PI / 180);
    	z -= Math.sin(rotation * Math.PI / 180);
    }
    void backward() {
    	z += Math.cos(rotation * Math.PI / 180);
    	x += Math.sin(rotation * Math.PI / 180);
    }
    void forward() {
    	z -= Math.cos(rotation * Math.PI / 180);
    	x -= Math.sin(rotation * Math.PI / 180);
    }
    void home() {
    	x = 0;
    	y = 5;
    	z = 20;
    	rotation = 0;
    }
}
