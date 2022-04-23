/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ShapeController;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;

import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import jfxtras.labs.util.event.MouseControlUtil;
import model.Shape;

/**
 *
 * @author intel
 */
public class ShapeView {
    
       Pane pane;
      
       public static Stack<State> stateList = new Stack<State>();
       public static Stack<State> redo  = new Stack<State>();
       public static Shape copied;
    
  public  ShapeView(Pane pane){
       this.pane=pane;
   }
  
  
  

   public void update(Shape shape){
       
       ContextMenu menu = new ContextMenu();
          MenuItem delete = new MenuItem("Delete");
          MenuItem copy = new MenuItem("Copy");
          MenuItem resize = new MenuItem("Resize");
          MenuItem color = new MenuItem("Color");
          menu.getItems().addAll(delete,copy,resize,color);
          
        
          
          
          //circle
     if(shape instanceof model.Circle){   
         
         //casting
        model.Circle c  = (model.Circle) shape;
        //making javafxCircle
        Circle circle =  new Circle(c.getPositionX(),c.getPositionY(),c.getRadius()); 
        //making it dragable
        MouseControlUtil.makeDraggable(circle);
        
        
        //action
        circle.setOnMouseClicked(e->{
            if (e.isPopupTrigger()) {
            menu.show((Node)e.getSource(), Side.RIGHT, 50, 50);
            e.consume();
        }
        });
         circle.setFill(c.getColor());
        circle.setCursor(Cursor.HAND);
        boolean isDone = pane.getChildren().add(circle);
        if(isDone){
            State s =  new State(circle);
            s.choice=Choices.add;
            stateList.add(s);
        }
        
        
        
        
        //********************************************All Menu Funtions**********************************************
        //Delete Funtion
        delete.setOnAction(val ->{
            
            boolean isRemoved = pane.getChildren().remove(circle);
            if(isRemoved){
                State s = new State(circle);
                s.choice=Choices.delete;
                stateList.add(s);
            }
            
        });
        resize.setOnAction(e->{
            Text t =  new Text("Enter Size");
            TextField f = new TextField();
           
            f.setMaxSize(50, 5);
            pane.getChildren().addAll(t,f);
            
            f.setOnKeyPressed(event->{
                if(event.getCode().equals(KeyCode.ENTER)){
                 double rad=Double.parseDouble(f.getText());
           
                 boolean isRemoved =  pane.getChildren().remove(circle);
                 if(isRemoved){
                     Circle newCircle  =  new Circle();
                      newCircle.setRadius(circle.getRadius());
                        State s = new State(newCircle);
                        s.choice=Choices.resize;
                        stateList.add(s);
                    }
                
                   circle.setRadius(rad);
                    boolean isResized = pane.getChildren().add(circle);
                    if(isResized){
                            State s = new State(circle);
                            s.choice=Choices.resize;
                            stateList.add(s);
                         }
                 pane.getChildren().remove(f);
                 pane.getChildren().remove(t);
                 }
            });
           
        });
        color.setOnAction(ev->{
            VBox box = new VBox();
            ColorPicker colorPicker =  new ColorPicker();
            Button b =  new Button("Change");
            box.getChildren().addAll(colorPicker,b);
            pane.getChildren().add(box);
            b.setOnAction(e->{
                
                
                
                 
                   boolean isRemoved=  pane.getChildren().remove(circle);
                   if(isRemoved){
                       Circle newCircle = new Circle();
                       newCircle.setFill(circle.getFill());
                        State s = new State(newCircle);
                        s.choice=Choices.color;
                        stateList.add(s);
                    }
                    pane.getChildren().remove(box);
                
                       circle.setFill(colorPicker.getValue());
                    boolean isChanged = pane.getChildren().add(circle);
                    if(isChanged){
                            State s = new State(circle);
                            s.choice=Choices.color;
                            stateList.add(s);
                         }
                
            });
        });
        
        copy.setOnAction(e->{
            model.Circle cmd =  new model.Circle( circle.getRadius(),circle.getCenterX(), circle.getCenterY(), (Color) circle.getFill());
           copied=cmd;
        });
       
     }
     
     
     
     
     // Triangle
     else if(shape instanceof model.Triangle){   
        model.Triangle t  = (model.Triangle) shape;
        Polygon triangle =  new Polygon(t.getSides());
        triangle.setOnDragEntered(v->{
            System.out.println("X: "+v.getSceneX());
            System.out.println("Y: "+v.getSceneY());
        });
        MouseControlUtil.makeDraggable(triangle);
        triangle.setOnMouseClicked(e->{
            if (e.isPopupTrigger()) {
            menu.show((Node)e.getSource(), Side.RIGHT, 50, 50);
            e.consume();
        }
        });
        
        
         triangle.setFill(t.getColor());
        triangle.setCursor(Cursor.HAND);
         boolean isDone = pane.getChildren().add(triangle);
        if(isDone){
            State s =  new State(triangle);
            s.choice=Choices.add;
            stateList.add(s);
        }
        
        delete.setOnAction(val ->{
            boolean isRemoved = pane.getChildren().remove(triangle);
            if(isRemoved){
                State s = new State(triangle);
                s.choice=Choices.delete;
                stateList.add(s);
            }
        });
        
        resize.setOnAction(e->{
            Text txt =  new Text("Enter Points");
            VBox vbox1 = new VBox();
             VBox vbox2 = new VBox();
              
           TextField x0f = new TextField();
           x0f.setMaxSize(70, 20);
           TextField y0f = new TextField();
           y0f.setMaxSize(70, 20);
           TextField x1f = new TextField();
           x1f.setMaxSize(70, 20);
           TextField y1f = new TextField();
           y1f.setMaxSize(70, 20);
           TextField x2f = new TextField();
           x2f.setMaxSize(70, 20);
           TextField y2f = new TextField();
           y2f.setMaxSize(70, 20);
           
               Button change = new Button("change");
           vbox1.getChildren().addAll(x0f,x1f,x2f,change);
           vbox2.getChildren().addAll(y0f,y1f,y2f);
           
       
           
            HBox hbox  =  new HBox();
            
            hbox.getChildren().addAll(vbox1,vbox2);
           
            pane.getChildren().addAll(txt,hbox);
   
            
            change.setOnAction(event->{
                  if(x0f.getText().equals("")){
                      x0f.requestFocus();
                     x0f.setStyle("-fx-border-color:red; -fx-border-style:solid");
                 }else if(y0f.getText().equals("")){
                      y0f.requestFocus();
                     y0f.setStyle("-fx-border-color:red; -fx-border-style:solid");
                 }else if(x1f.getText().equals("")){
                      x1f.requestFocus();
                     x1f.setStyle("-fx-border-color:red; -fx-border-style:solid");
                 }else if(y1f.getText().equals("")){
                      y1f.requestFocus();
                     y1f.setStyle("-fx-border-color:red; -fx-border-style:solid");
                 }else if(x2f.getText().equals("")){
                      x2f.requestFocus();
                     x2f.setStyle("-fx-border-color:red; -fx-border-style:solid");
                 }else if(y2f.getText().equals("")){
                      y2f.requestFocus();
                     y2f.setStyle("-fx-border-color:red; -fx-border-style:solid");
                 }
                 else{
                    
                     Double array[]={
                                     Double.parseDouble(x0f.getText()),Double.parseDouble(y0f.getText()),
                                     Double.parseDouble(x1f.getText()),Double.parseDouble(y1f.getText()),
                                     Double.parseDouble(x2f.getText()),Double.parseDouble(y2f.getText())
                     };
                     
                 boolean isRemoved =  pane.getChildren().remove(triangle);
                 if(isRemoved){
                     Polygon newTriangle  =  new Polygon(t.getSides());
                        State s = new State(newTriangle);
                        s.choice=Choices.resize;
                        stateList.add(s);
                    }
                
                    triangle.getPoints().clear();
                   triangle.getPoints().addAll(array);
                    boolean isResized = pane.getChildren().add(triangle);
                    if(isResized){
                            State s = new State(triangle);
                            s.choice=Choices.resize;
                            stateList.add(s);
                         }
                    
                    
                    
                     pane.getChildren().remove(txt);
                     pane.getChildren().remove(hbox);
                     pane.getChildren().remove(change);
                     
                }
            });
           
        });
        
        color.setOnAction(ev->{
            VBox box = new VBox();
            ColorPicker colorPicker =  new ColorPicker();
            Button b =  new Button("Change");
            box.getChildren().addAll(colorPicker,b);
            pane.getChildren().add(box);
            b.setOnAction(e->{
                
                  boolean isRemoved=  pane.getChildren().remove(triangle);
                   if(isRemoved){
                       Polygon newtriangle = new Polygon();
                       newtriangle.setFill(triangle.getFill());
                        State s = new State(newtriangle);
                        s.choice=Choices.color;
                        stateList.add(s);
                    }
                 
                       triangle.setFill(colorPicker.getValue());
                    boolean isChanged = pane.getChildren().add(triangle);
                    if(isChanged){
                            State s = new State(triangle);
                            s.choice=Choices.color;
                            stateList.add(s);
                         }
                    pane.getChildren().remove(box);
                
            });
        });
        double ts[]= new double[triangle.getPoints().size()];
        for(int i=0;i<triangle.getPoints().size();i++){
            ts[i]=triangle.getPoints().get(i);
        }
        copy.setOnAction((ActionEvent e) -> {
            model.Triangle cmd =  new model.Triangle(ts,triangle.getPoints().get(0),triangle.getPoints().get(1), (Color)  triangle.getFill());
            copied=cmd;
        });
       
     }
     
     
     //Rectangle
     else if(shape instanceof model.Rectangle){   
        model.Rectangle r  = (model.Rectangle) shape;
        Rectangle rectangle =  new Rectangle(r.getPositionX(),r.getPositionY(),r.getLength(),r.getWidth());
        rectangle.setOnDragEntered(v->{
            System.out.println("X: "+v.getSceneX());
            System.out.println("Y: "+v.getSceneY());
        });
        MouseControlUtil.makeDraggable(rectangle);
        rectangle.setOnMouseClicked(e->{
            if (e.isPopupTrigger()) {
            menu.show((Node)e.getSource(), Side.RIGHT, 50, 50);
            e.consume();
        }
        });
        
        
        rectangle.setFill(r.getColor());
        rectangle.setCursor(Cursor.HAND);
        boolean isDone = pane.getChildren().add(rectangle);
        if(isDone){
            State s =  new State(rectangle);
            s.choice=Choices.add;
            stateList.add(s);
        }
        
        delete.setOnAction(val ->{
            boolean isRemoved = pane.getChildren().remove(rectangle);
            if(isRemoved){
                State s = new State(rectangle);
                s.choice=Choices.delete;
                stateList.add(s);
            }
            
        });
        
          resize.setOnAction(e->{
            Text t =  new Text("Enter length");
            Text t1 =  new Text("Enter width");
            
            TextField f = new TextField();
            TextField f1 = new TextField();
           
            VBox box =  new VBox();
            
            Button b = new Button("Change");
            box.getChildren().addAll(t,f,t1,f1,b);
            
            
             pane.getChildren().add(box);
            
            b.setOnAction(event->{
               
                if(f.getText().equals("")){
                    f.requestFocus();
                    f.setStyle("-fx-border-color:red; -fx-border-style:solid");
                }else if(f1.getText().equals("")){
                    f1.requestFocus();
                    f1.setStyle("-fx-border-color:red; -fx-border-style:solid");
                }else{
                    
                    
                    
                    
                    
                 double len=Double.parseDouble(f.getText());
                 double wid=Double.parseDouble(f1.getText());
                 
                 
                  boolean isRemoved =  pane.getChildren().remove(rectangle);
                 if(isRemoved){
                     Rectangle newRectangle =  new Rectangle();
                      newRectangle.setHeight(rectangle.getHeight());
                      newRectangle.setWidth(rectangle.getWidth());
                        State s = new State(newRectangle);
                        s.choice=Choices.resize;
                        stateList.add(s);
                    }
                
                   rectangle.setHeight(len);
                    rectangle.setWidth(wid);
                    boolean isResized = pane.getChildren().add(rectangle);
                    if(isResized){
                            State s = new State(rectangle);
                            s.choice=Choices.resize;
                            stateList.add(s);
                         }
                 pane.getChildren().remove(box);
                
                }
            });
           
        });
        
        color.setOnAction(ev->{
            VBox box = new VBox();
            ColorPicker colorPicker =  new ColorPicker();
            Button b =  new Button("Change");
            box.getChildren().addAll(colorPicker,b);
            pane.getChildren().add(box);
            b.setOnAction(e->{
                
                
                     boolean isRemoved=  pane.getChildren().remove(rectangle);
                   if(isRemoved){
                       Rectangle newRectangle = new Rectangle();
                       newRectangle.setFill(rectangle.getFill());
                        State s = new State(newRectangle);
                        s.choice=Choices.color;
                        stateList.add(s);
                    }
                 
                       rectangle.setFill(colorPicker.getValue());
                    boolean isChanged = pane.getChildren().add(rectangle);
                    if(isChanged){
                            State s = new State(rectangle);
                            s.choice=Choices.color;
                            stateList.add(s);
                         }
                    pane.getChildren().remove(box);
                
            });
        });
        
          copy.setOnAction((ActionEvent e) -> {
            model.Rectangle cmd =  new model.Rectangle(rectangle.getWidth(),rectangle.getHeight(),rectangle.getX(), rectangle.getY(), (Color)  rectangle.getFill());
            copied=cmd;
        });
        
     }
     
     
     //Line segment
      else if(shape instanceof model.LineSegment){   
        model.LineSegment l  = (model.LineSegment) shape;
        Line line =  new Line(l.getPositionX(),l.getPositionY(),l.getStartPoint(),l.getEndPoint());
        line.setOnDragEntered(v->{
            System.out.println("X: "+v.getSceneX());
            System.out.println("Y: "+v.getSceneY());
        });
        MouseControlUtil.makeDraggable(line);
        line.setOnMouseClicked(e->{
            if (e.isPopupTrigger()) {
            menu.show((Node)e.getSource(), Side.RIGHT, 50, 50);
            e.consume();
        }
        });
        
         line.setFill(l.getColor());
        line.setCursor(Cursor.HAND);
         boolean isDone = pane.getChildren().add(line);
        if(isDone){
            State s =  new State(line);
            s.choice=Choices.add;
            stateList.add(s);
        }
        
        
        
        delete.setOnAction(val ->{
            boolean isRemoved = pane.getChildren().remove(line);
            if(isRemoved){
                State s = new State(line);
                s.choice=Choices.delete;
                stateList.add(s);
            }
        });
         resize.setOnAction(e->{
            Text t =  new Text("Start Point");
            Text t1 =  new Text("End Point");
            
            TextField f = new TextField();
            TextField f1 = new TextField();
           
            VBox box =  new VBox();
            
            Button b = new Button("Change");
            box.getChildren().addAll(t,f,t1,f1,b);
            
            
             pane.getChildren().add(box);
            
            b.setOnAction(event->{
               
                if(f.getText().equals("")){
                    f.requestFocus();
                    f.setStyle("-fx-border-color:red; -fx-border-style:solid");
                }else if(f1.getText().equals("")){
                    f1.requestFocus();
                    f1.setStyle("-fx-border-color:red; -fx-border-style:solid");
                }else{
                 double len=Double.parseDouble(f.getText());
                 double wid=Double.parseDouble(f1.getText());
                 
                 
                  boolean isRemoved =  pane.getChildren().remove(line);
                 if(isRemoved){
                     Line newLine =  new Line();
                      newLine.setStartX(line.getStartX());
                      newLine.setStartY(line.getStartY());
                        State s = new State(newLine);
                        s.choice=Choices.resize;
                        stateList.add(s);
                    }
                
                      line.setStartX(len);
                      line.setStartY(wid);
                    boolean isResized = pane.getChildren().add(line);
                    if(isResized){
                            State s = new State(line);
                            s.choice=Choices.resize;
                            stateList.add(s);
                         }
              
                 pane.getChildren().remove(box);
                
                }
            });
           
        });
        
        color.setOnAction(ev->{
            VBox box = new VBox();
            ColorPicker colorPicker =  new ColorPicker();
            Button b =  new Button("Change");
            box.getChildren().addAll(colorPicker,b);
            pane.getChildren().add(box);
            b.setOnAction(e->{
                
           boolean isRemoved=  pane.getChildren().remove(line);
                   if(isRemoved){
                       Polygon newLine = new Polygon();
                       newLine.setFill(line.getFill());
                        State s = new State(newLine);
                        s.choice=Choices.color;
                        stateList.add(s);
                    }
                 
                       line.setFill(colorPicker.getValue());
                    boolean isChanged = pane.getChildren().add(line);
                    if(isChanged){
                            State s = new State(line);
                            s.choice=Choices.color;
                            stateList.add(s);
                         }                
                    pane.getChildren().remove(box);
                
            });
        });
         copy.setOnAction((ActionEvent e) -> {
            model.LineSegment cmd =  new model.LineSegment(line.getStartX(),line.getStartY(),line.getEndX(), line.getEndY(), (Color)  line.getFill());
            copied=cmd;
        });
        
        
       
        
        
        //Square
     } else if(shape instanceof model.Square){   
        model.Square s  = (model.Square) shape;
        Rectangle square =  new Rectangle(s.getPositionX(),s.getPositionY(),s.getSide(),s.getSide());
        square.setOnDragEntered(v->{
            System.out.println("X: "+v.getSceneX());
            System.out.println("Y: "+v.getSceneY());
        });
        MouseControlUtil.makeDraggable(square);
        square.setOnMouseClicked(e->{
            if (e.isPopupTrigger()) {
            menu.show((Node)e.getSource(), Side.RIGHT, 50, 50);
            e.consume();
        }
        });
        
        
         square.setFill(s.getColor());
        square.setCursor(Cursor.HAND);
        
         boolean isDone = pane.getChildren().add(square);
        if(isDone){
            State st =  new State(square);
            st.choice=Choices.add;
            stateList.add(st);
        }
        delete.setOnAction(val ->{
            boolean isRemoved = pane.getChildren().remove(square);
            if(isRemoved){
                State sm = new State(square);
                sm.choice=Choices.delete;
                stateList.add(sm);
            }
        });
        
         resize.setOnAction(e->{
            Text t =  new Text("Enter length");
            
            
            TextField f = new TextField();
        
           
            VBox box =  new VBox();
            
            Button b = new Button("Change");
            box.getChildren().addAll(t,f,b);
            
            
             pane.getChildren().add(box);
            
            b.setOnAction(event->{
               
                if(f.getText().equals("")){
                    f.requestFocus();
                    f.setStyle("-fx-border-color:red; -fx-border-style:solid");
                }else{
                 double len=Double.parseDouble(f.getText());
                 
                 
                  boolean isRemoved =  pane.getChildren().remove(square);
                 if(isRemoved){
                     Rectangle newRectangle =  new Rectangle();
                      newRectangle.setHeight(square.getHeight());
                      newRectangle.setWidth(square.getWidth());
                        State st = new State(newRectangle);
                        st.choice=Choices.resize;
                        stateList.add(st);
                    }
                
                   square.setHeight(len);
                    square.setWidth(len);
                    boolean isResized = pane.getChildren().add(square);
                    if(isResized){
                            State st = new State(square);
                            st.choice=Choices.resize;
                            stateList.add(st);
                         }
                 pane.getChildren().remove(box);
                
                }
            });
           
        });
        
        color.setOnAction(ev->{
            VBox box = new VBox();
            ColorPicker colorPicker =  new ColorPicker();
            Button b =  new Button("Change");
            box.getChildren().addAll(colorPicker,b);
            pane.getChildren().add(box);
            b.setOnAction(e->{
                
                
                  boolean isRemoved=  pane.getChildren().remove(square);
                   if(isRemoved){
                       Rectangle newsquare = new Rectangle();
                       newsquare.setFill(square.getFill());
                        State st = new State(newsquare);
                        st.choice=Choices.color;
                        stateList.add(st);
                    }
                 
                       square.setFill(colorPicker.getValue());
                    boolean isChanged = pane.getChildren().add(square);
                    if(isChanged){
                            State st = new State(square);
                            st.choice=Choices.color;
                            stateList.add(st);
                         }                
                    pane.getChildren().remove(box);
                
            });
        });
        
         copy.setOnAction((ActionEvent e) -> {
            model.Square cmd =  new model.Square(square.getHeight(),square.getX(), square.getY(), (Color)  square.getFill());
            copied=cmd;
        });
       
     }
   }    
   
     public void remove(Shape sh){
         pane.getChildren().remove(sh);
     }
}
