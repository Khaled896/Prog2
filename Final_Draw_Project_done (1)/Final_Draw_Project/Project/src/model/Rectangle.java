
package model;
import javafx.scene.paint.Color;
public class Rectangle extends Shape {
    
    private double length;
    private double width;
    public Rectangle(double length, double width, double positionX, double positionY, Color color) {
        super(positionX, positionY, color);
        this.setLength(length); 
        this.setWidth(width);
    }
    public double getLength() {
        return length;
    }
    public void setLength(double length) {
        this.length = length;
    }
    public double getWidth() {
        return width;
    }
    public void setWidth(double width) {
        this.width = width;
    }
    @Override
    public double calculateArea() {
        double area =  this.length*this.width;
        return area;
    }    
}
