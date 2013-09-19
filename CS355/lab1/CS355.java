/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lab1;

import resources.GUIFunctions;
import java.awt.Color;

/**
 *
 * @author Dallin Christensen
 */
public class CS355 
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
    	Shapes s = new Shapes();
    	MyMouseListener ml = new MyMouseListener(s);
        GUIFunctions.createCS355Frame(new Controller(s), new View(s), ml, ml);
        GUIFunctions.refresh();
        GUIFunctions.changeSelectedColor(Color.WHITE);
        GUIFunctions.setHScrollBarMin(0);
        GUIFunctions.setVScrollBarMin(0);
        GUIFunctions.setHScrollBarMax(512);
        GUIFunctions.setVScrollBarMax(512);
        GUIFunctions.setHScrollBarKnob(256);
        GUIFunctions.setVScrollBarKnob(256);
    }
}