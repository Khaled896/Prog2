
package model;

import javafx.scene.paint.Color;

public abstract class Shape {
    private double positionX;
    private double positionY; 
    private Color color;

    public Shape(double positionX, double positionY, Color color) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.color = color;
    }

  
    public void setColor(Color color) {
        this.color = color;
    }
  
    public void setPositionX(double positionX) {
        if(positionX<0)
            positionX=0;
        this.positionX = positionX;
    }
    public void setPositionY(double positionY) {
         if(positionY<0)
            positionY=0;
        this.positionY = positionY;
    }
    
     public double getPositionX() {
        return positionX;
    }

      public Color getColor() {
        return color;
    }
          public double getPositionY() {
        return positionY;
    }
   
    public abstract double calculateArea();
}
