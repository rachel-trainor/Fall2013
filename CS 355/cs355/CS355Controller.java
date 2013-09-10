/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs355;

import java.awt.Color;

/**
 *
 * @author Talonos
 */
public interface CS355Controller 
{

    void colorButtonHit(Color c);
    void triangleButtonHit();
    void squareButtonHit();
    void rectangleButtonHit();
    void circleButtonHit();
    void ellipseButtonHit();

    public void lineButtonHit();

    public void selectButtonHit();

    public void zoomInButtonHit();

    public void zoomOutButtonHit();

    public void hScrollbarChanged(int value);

    public void vScrollbarChanged(int value);
}
