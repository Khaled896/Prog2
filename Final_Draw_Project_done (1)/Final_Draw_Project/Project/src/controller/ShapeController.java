
package controller;

import model.Shape;
import view.ShapeView;


public class ShapeController {
    
    private Shape shape;
    private ShapeView View;

    public ShapeController(Shape shape, ShapeView View) {
        this.shape = shape;
        this.View = View;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public ShapeView getView() {
        return View;
    }

    public void setView(ShapeView View) {
        this.View = View;
    }
    
    public void resetView(){
        this.View.update(shape);
    }
}
