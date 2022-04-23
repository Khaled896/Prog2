
package model;

import javafx.scene.paint.Color;


public class Circle extends Shape{
    
    private double radius;

    public Circle(double radius, double positionX, double positionY, Color color) {
        super(positionX, positionY, color);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    
    public boolean setRadius(double radius) {
        if(radius<=0){
            return false;
        }else{
        this.radius = radius;
        return true;
        }
    }

    @Override
    public double calculateArea() {
        double area = Math.PI*Math.sqrt(radius);
        return area;
    }
    
    
    
}
