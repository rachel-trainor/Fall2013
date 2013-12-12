package lab1;

public class MatrixOps {

	public double[][] transform(double[][] transformMatrix, double x, double y) {
		double[][] xy = new double[][]{{x},{y},{1}};
		return dot(transformMatrix, xy);
	}
	
	public double[][] rTheta(double rotation) {
		return new double[][] {
				{Math.cos(rotation), -Math.sin(rotation), 0},
				{Math.sin(rotation), Math.cos(rotation), 0},
				{0, 0, 1}};
	}
	
	public double[][] rotation3d(double rotation) {
		return new double[][] {
				{Math.cos(rotation * Math.PI / 180), 0, -Math.sin(rotation * Math.PI / 180), 0},
				{0, 1, 0, 0},
				{-Math.sin(rotation * Math.PI / 180), 0, -Math.cos(rotation * Math.PI / 180), 0},
				{0, 0, 0, 1}};
	}
	
	public double[][] rThetaInv(double rotation) {
		return new double[][] {
				{Math.cos(rotation), Math.sin(rotation), 0},
				{-Math.sin(rotation), Math.cos(rotation), 0},
				{0, 0, 1}};
	}
	
	public double[][] tC(double x, double y) {
		return new double[][] {
				{1, 0, x},
				{0, 1, y},
				{0, 0, 1}};
	}
	
	public double[][] translation3d(double x, double y, double z) {
		return new double[][] {
				{1, 0, 0, x},
				{0, 1, 0, y},
				{0, 0, 1, z},
				{0, 0, 0, 1}};
	}
	
	public double[][] tCInv(double x, double y) {
		return new double[][] {
				{1, 0, -x},
				{0, 1, -y},
				{0, 0, 1}};
	}
	
	public double[][] sF(double f) {
		return new double[][] {
				{f, 0, 0},
				{0, f, 0},
				{0, 0, 1}};
	}
	
	public double[][] sFInv(double f) {
		return new double[][] {
				{1/f, 0, 0},
				{0, 1/f, 0},
				{0, 0, 1}};
	}
	
	public double[][] dot(double[][] a, double[][] b) {
		// assume all matrices have 3 rows
		// assume matrix a has 3 columns
		// assume matrix b has either 3 columns or 1 column
		
		if(b[0].length == 3) { // b has 3 columns
			return new double[][] {
					{a[0][0]*b[0][0] + a[0][1]*b[1][0] + a[0][2]*b[2][0],
					 a[0][0]*b[0][1] + a[0][1]*b[1][1] + a[0][2]*b[2][1],
					 a[0][0]*b[0][2] + a[0][1]*b[1][2] + a[0][2]*b[2][2]},
					{a[1][0]*b[0][0] + a[1][1]*b[1][0] + a[1][2]*b[2][0],
					 a[1][0]*b[0][1] + a[1][1]*b[1][1] + a[1][2]*b[2][1],
					 a[1][0]*b[0][2] + a[1][1]*b[1][2] + a[1][2]*b[2][2]},
					{a[2][0]*b[0][0] + a[2][1]*b[1][0] + a[1][2]*b[2][0],
					 a[2][0]*b[0][1] + a[2][1]*b[1][1] + a[1][2]*b[2][1],
					 a[2][0]*b[0][2] + a[2][1]*b[1][2] + a[1][2]*b[2][2]}};
		} else { // b has 1 column
			return new double[][] {
					{a[0][0]*b[0][0] + a[0][1]*b[1][0] + a[0][2]*b[2][0]},
					{a[1][0]*b[0][0] + a[1][1]*b[1][0] + a[1][2]*b[2][0]},
					{a[2][0]*b[0][0] + a[2][1]*b[1][0] + a[1][2]*b[2][0]}};
		}
	}
	
	public double[][] multiply(double[][] a, double[][] b) {
        double[][] result = new double[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                for (int k = 0; k < b.length; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
	}
	
}
