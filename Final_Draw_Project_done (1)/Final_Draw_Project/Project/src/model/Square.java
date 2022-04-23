
package model;

import javafx.scene.paint.Color;

public class Square extends Shape{
    
    private double side;

    public Square(double side, double positionX, double positionY, Color color) {
        super(positionX, positionY, color);
        this.side = side;
    }

    public double getSide() {
        return side;
    }

    public void setSide(double side) {
        this.side = side;
    }

    @Override
    public double calculateArea() {
        
        double area =  side*side;
        return area;
    }
    
    
}
