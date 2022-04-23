/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.scene.shape.Shape;


public class State {
   
    private Shape shape;

    public Choices choice;
    public State(Shape shape) {
        this.shape = shape;
    }

    public Shape getShape() {
        return shape;
    }
    
    
}
