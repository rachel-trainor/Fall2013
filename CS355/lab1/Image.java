package lab1;

import java.util.ArrayList;
import java.util.Collections;

public class Image {
	int height, width;
	int[][] pixels, buf, buf2;
	
	public Image(int h, int w, int[][] p) {
		height = h;
		width = w;
		pixels = p;
		buf = new int[h][w];
	}
	
	public int getWidth() {return width;}
	
	public void setWidth(int w) {width = w;}
	
	public int getHeight() {return height;}
	
	public void setHeight(int h) {height = h;}
	
	public int[][] getPixels() {return pixels;}
	
	public void setPixels(int[][] p) {pixels = p;}

    public void changeBrightness(int deltaBrightness) {
        for(int i = 0; i < pixels.length; i++) {
            for(int j = 0; j < pixels[i].length; j++)
                pixels[i][j] = putInRange(pixels[i][j] + deltaBrightness);
        }
    }
    
    public void changeContrast(int deltaContrast) {
        for(int i = 0; i < pixels.length; i++) {
            for(int j = 0; j < pixels[i].length; j++) {
            	double dif = (deltaContrast + 100.0) / 100.0;
                pixels[i][j] = putInRange((int) Math.round(Math.pow(dif, 4) * (pixels[i][j] - 128) + 128));
            }
        }
    }

    public void uniformBlur() {
        kernel(new int[][] {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}}, 9, true);
    }

    public void medianBlur() {
        for(int i = 0; i < pixels.length; i++) {
            for(int j = 0; j < pixels[i].length; j++) {
                ArrayList<Integer> values = new ArrayList<>();
                for(int k = -1; k <= 1; k++) {
                    for(int l = -1; l <= 1; l++) {
                        if(i + k < 0 || i + k >= pixels.length)
                            values.add(pixels[i][j]);
                        else if(j + l < 0 || j + l >= pixels[i].length)
                            values.add(pixels[i][j]);
                        else
                            values.add(pixels[i + k][j + l]);
                    }
                }
                Collections.sort(values);
                buf[i][j] = values.get(values.size() / 2);
            }
        }
        for(int i = 0; i < pixels.length; i++) {
            for(int j = 0; j < pixels[i].length; j++)
                pixels[i][j] = buf[i][j];
        }
    }

    public void sharpen() {
    	kernel(new int[][] {{0, -1, 0}, {-1, 6, -1}, {0, -1, 0}}, 2, true);
    }

    public void detectEdges() {
    	kernel(new int[][] {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}}, 8, false);
    	int[][] temp = buf;
    	kernel(new int[][] {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}}, 8, false);
    	for(int i = 0; i < pixels.length; i++) {
    		for(int j = 0; j < pixels[i].length; j++) {
    			double result = Math.sqrt(Math.pow(temp[i][j], 2) + Math.pow(buf[i][j], 2));
    			pixels[i][j] = (int) Math.round(result);
    		}
    	}
    }

    public void kernel(int[][] kernel, double div, boolean persist) {
        for(int i = 0; i < pixels.length; i++) {
            for(int j = 0; j < pixels[i].length; j++) {
                int average = 0;
                for(int k = -kernel.length / 2; k <= kernel.length / 2; k++) {
                    for(int l = -kernel[0].length / 2; l <= kernel[0].length / 2; l++) {
                    	if(i + k < 0 || i + k >= pixels.length)
                    		continue;
                    	if(j + l < 0 || j + l >= pixels[i].length)
                    		continue;
                    	average += pixels[i + k][j + l] * kernel[k + kernel.length / 2][l + kernel[0].length / 2];
                    }
                }
                buf[i][j] = putInRange((int) Math.round(average / div));
            }
        }
        if(persist) {
            for(int i = 0; i < pixels.length; i++) {
            	for(int j = 0; j < pixels[i].length; j++)
                    pixels[i][j] = buf[i][j];
            }
        }
    }
    
    public int putInRange(int val) {
    	if(val < 0)
    		return 0;
    	else if(val > 255)
    		return 255;
    	else return val;
    }
}
