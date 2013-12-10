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
    	Manager m = new Manager();
    	MyMouseListener ml = new MyMouseListener(m);
        GUIFunctions.createCS355Frame(new Controller(m), new View(m), ml, ml);
        GUIFunctions.refresh();
        GUIFunctions.changeSelectedColor(Color.WHITE);
        GUIFunctions.setHScrollBarMin(0);
        GUIFunctions.setVScrollBarMin(0);
        GUIFunctions.setHScrollBarMax(512*4);
        GUIFunctions.setVScrollBarMax(512*4);
        GUIFunctions.setHScrollBarKnob(512);
        GUIFunctions.setVScrollBarKnob(512);
    }
}