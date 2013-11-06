import javax.vecmath.*;

public class cs355hw3 {
	
	public static void main(String[] args) {
		problemOne();
		problemTwo();
		problemThree();
		problemFour();
	}
	
	public static void problemOne() {
		System.out.println("***************** PROBLEM 1 *****************");
		Vector3f tmp = new Vector3f();
		Vector3f pFrom = new Vector3f(10,0,0);
		Vector3f pAt = new Vector3f(0,0,10);
		Vector3f vUp = new Vector3f(0,1,0);
		
		tmp.sub(pAt,pFrom);
		Vector3f e3 = (Vector3f) tmp.clone();
		e3.normalize();
		
		tmp.cross(e3, vUp);
		Vector3f e1 = (Vector3f) tmp.clone();
		e1.normalize();
		
		tmp.cross(e1, e3);
		Vector3f e2 = (Vector3f) tmp.clone();
		e2.normalize();
		
		float[][] translate = new float[][]{{1,0,0,-pFrom.x}, {0,1,0,-pFrom.y}, {0,0,1,-pFrom.z}, {0,0,0,1}};
		float[][] rotate = new float[][]{{e1.x, e1.y, e1.z, 0}, {e2.x, e2.y, e2.z, 0}, {e3.x, e3.y, e3.z, 0}, {0,0,0,1}};
		
		float[][] wToc = mult(rotate, translate);
		
		printM(rotate,"rotation matrix");
		printM(translate, "translation matrix");
		printM(wToc, "World to Camera matrix");
		
		float[][] pw = new float[][]{{0},{10},{50},{1}};
		
		printPoint(mult(wToc, pw), "world-space point pw in camera coordinates");
		System.out.println("***************** PROBLEM 1 *****************\n\n");
	}
	
	static float x;
	static float y;
	static float z;
	static float w;
	public static void problemTwo() {
		System.out.println("----------------- PROBLEM 2 -----------------");
		float horizFOV = 60; // horizontal field of view of 60 degrees (full angle from side to side)
		float aspect = (float) (16.0/9.0); // aspect ratio of 16:9 (horizontal to vertical)
		float n = 1;         // near plane n = 1
		float f = 1000;      // far plane f = 1000
		float zoomX = (float) (1/Math.tan(Math.toRadians(horizFOV/2)));
		float zoomY = (float) (zoomX/aspect);
		float zoomZ = (f+n)/(f-n);
		float other = (-2*n*f)/(f-n);
		
		float[][] clip = new float[][]{{zoomX,0,0,0}, {0,zoomY,0,0}, {0,0,zoomZ,0}, {0,0,other,0}};
		printM(clip, "clip matrix");
		
		float[][] coord = new float[][]{{10},{10},{20},{1}};
		float[][] clipSpaceCoord = mult(clip, coord); // x,y,z,q
		printPoint(clipSpaceCoord, "clip-space coordinates of the camera-space point pc = (10,10,20)");
		x = clipSpaceCoord[0][0];
		y = clipSpaceCoord[1][0];
		z = clipSpaceCoord[2][0];
		w = clipSpaceCoord[3][0];
		System.out.printf("Is this point within the viewing frustum? \n%s\n\n", isWithinFrustrum(x,y,z,w) ? "Yes" : "No");
		
		clipSpaceCoord[0][0] = x/w;
		clipSpaceCoord[1][0] = y/w;
		clipSpaceCoord[2][0] = z/w;
		
		printPointNoZ(clipSpaceCoord, "canonical view coordinates of point pc = (10,10,20)");
						
		System.out.println("----------------- PROBLEM 2 -----------------\n\n");
	}
	
	public static void problemThree() {
		System.out.println("''''''''''''''''' PROBLEM 3 '''''''''''''''''");
		
		float width = (float) 1920;
		float height = (float) 1080;
		float xscreen = (x/w + 1)*(width/2);
		float yscreen = (y/w + 1)*(height/2);
		
		float[][] viewport = new float[][]{{width/2,0,0,1},{0,height/2,0,1},{0,0,0,0},{0,0,0,0}};
		printM(viewport, "viewport matrix");
		
		printPointNoZ(new float[][]{{xscreen},{yscreen},{0},{0}}, "onscreen coordinates of the point (10,10,20)");

		System.out.println("''''''''''''''''' PROBLEM 3 '''''''''''''''''\n\n");
	}
	
	public static void problemFour() {
		System.out.println("^^^^^^^^^^^^^^^^^ PROBLEM 4 ^^^^^^^^^^^^^^^^^");
		
		// if V = P2 - P1 and W = P3 - P1, and N is the surface normal, then:
		Vector3f v = new Vector3f(4-3,2-4,3-1); // V = P2 - P1
		Vector3f w = new Vector3f(5-3,6-4,1-1); // W = P3 - P1
		Vector3f n = new Vector3f((v.y*w.z)-(v.z*w.y),
								  (v.z*w.x)-(v.x*w.z),
								  (v.x*w.y)-(v.y*w.x)); // N is the surface normal
		System.out.println("normal to the triangle");
		System.out.println(n + "\n");
		
		Vector3f a = new Vector3f(n.x/(n.x+n.y+n.z),
								  n.y/(n.x+n.y+n.z),
								  n.z/(n.x+n.y+n.z)); // a is the normalized surface normal
		
		System.out.printf("If the camera’s optical axis points points in the same direction \n"
				+ "as the vector (1,1,1), this triangle is %s-facing.\n\n", a.x>0 && a.y>0 && a.z>0 ? "front" : "back");
		
		System.out.println("^^^^^^^^^^^^^^^^^ PROBLEM 4 ^^^^^^^^^^^^^^^^^\n\n");
	}
	
	public static void printM(float[][] m, String label) {
		System.out.println(label);
		for(int i=0; i<m.length; i++) {
			System.out.print("|");
			for(int j=0; j<m[0].length; j++) {
				if(m[i][j] >= 0)
					System.out.print(" ");
				if(m[i][j] > -10 && m[i][j] < 10)
					System.out.print(" ");
				if(m[i][j] > -.000001 && m[i][j] < .000001)
					System.out.printf("0.00 ");
				else
					System.out.printf("%3.2f ", m[i][j]);
			}
			System.out.println("|");
		}
		System.out.println();
	}
	
	public static float[][] mult(float[][] a, float[][] b) {
		if(a[0].length != b.length)
			return null;
		float[][] result = new float[a.length][b[0].length];
		for(int i=0; i<a.length; i++) {
			for(int j=0; j<b[0].length; j++) {
				result[i][j] = a[i][0]*b[0][j] + a[i][1]*b[1][j] + a[i][2]*b[2][j] + a[i][3]*b[3][j];
			}
		}
		
		return result;
	}
	
	public static void printPoint(float[][] m, String label) {
		System.out.println(label);
		System.out.printf("(%3.2f, %3.2f, %3.2f)\n", m[0][0], m[1][0], m[2][0]);
		System.out.println();
	}
	
	public static void printPointNoZ(float[][] m, String label) {
		System.out.println(label);
		System.out.printf("(%3.2f, %3.2f)\n", m[0][0], m[1][0]);
		System.out.println();
	}
	
	public static boolean isWithinFrustrum(float x, float y, float z, float w) {
		if(x<-w && x>w && y<-w && y>w && z<-w && z>w)
			return true;
		return false;
	}
}
