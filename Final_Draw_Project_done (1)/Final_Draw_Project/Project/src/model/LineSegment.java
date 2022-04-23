/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.scene.paint.Color;

/**
 *
 * @author intel
 */
public class LineSegment extends Shape {
    
    private double startPoint;
    private double endPoint;

    public LineSegment(double startPoint, double endPoint, double positionX, double positionY, Color color) {
        super(positionX, positionY, color);
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public double getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(double startPoint) {
        this.startPoint = startPoint;
    }

    public double getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(double endPoint) {
        this.endPoint = endPoint;
    }

    
    
    
    @Override
    public double calculateArea() {
        return 1;
    }
    
    
}
