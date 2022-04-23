
package model;

import javafx.scene.paint.Color;

/**
 *
 * @author intel
 */
public class Triangle extends Shape{
    
    private double sides[];

    public Triangle(double[] sides, double positionX, double positionY, Color color) {
        super(positionX, positionY, color);
        this.sides = sides;
    }

    public double[] getSides() {
        return sides;
    }

    public void setSides(double[] sides) {
        this.sides = sides;
    }

   

    @Override
    public double calculateArea() {
        // Using formula
        // (x1y2 + x2y3 + x3y1 – x1y3 – x2y1 – x3y2)/2.

        double area = (sides[0]*sides[3])+(sides[2]*sides[5])+(sides[4]*sides[1])-
                      (sides[0]*sides[5])-(sides[2]*sides[1])-(sides[4]*sides[3]);
        
        area = area/2;
        
        
        return area;
        
    }
    
    
}
