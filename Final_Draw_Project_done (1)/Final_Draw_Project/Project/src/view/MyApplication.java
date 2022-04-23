
package view;         //#####

import controller.ShapeController;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane; 
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author intel
 */
public class MyApplication extends Application{

   
    @Override
    public void start(Stage stage) throws Exception {
        
        BorderPane border =  new BorderPane();
        
        GridPane top =  new GridPane();
        
         Text t1=  new Text("Drawing Application");
         t1.setStyle("-fx-font-size:40px;-fx-font-weight:bold");
         final ToggleGroup group = new ToggleGroup();
         ToggleButton  circleButton = new ToggleButton ("C");
         circleButton.setMaxSize(70, 70);
         circleButton.setMinSize(70, 70);
         circleButton.setShape(new Circle(5));
         circleButton.setToggleGroup(group);
         
         
         ToggleButton  triangleButton = new ToggleButton ("T");
         triangleButton.setMaxSize(70, 70);
         triangleButton.setMinSize(70, 70);
         triangleButton.setStyle("-fx-shape: 'M 0 0 200 0 100 200 z'");
         triangleButton.setToggleGroup(group);
         
         
         ToggleButton  rectangleButton = new ToggleButton ("R");
          rectangleButton.setMaxSize(90, 55);
         rectangleButton.setMinSize(90, 55);
         rectangleButton.setShape(new Rectangle(10,8));
         
         rectangleButton.setToggleGroup(group);
         ToggleButton  lineButton = new ToggleButton ("______");
         lineButton.setMaxSize(70, 55);
         lineButton.setMinSize(70, 55);
         
         lineButton.setToggleGroup(group);
         
         ToggleButton  squareButton = new ToggleButton ("S");
         squareButton.setMaxSize(60, 60);
         squareButton.setMinSize(60, 60);
         squareButton.setShape(new Rectangle(10,8));
         squareButton.setToggleGroup(group);
         
         
         HBox buttonBox =  new HBox();
         buttonBox.setSpacing(100);
         buttonBox.getChildren().addAll(circleButton,
                                        triangleButton,
                                        rectangleButton,
                                        lineButton,
                                        squareButton);
 Pane center = new Pane();

         circleButton.setOnAction(e->{
              border.setRight(getLeftGrid(group,center));  
         });
         triangleButton.setOnAction(e->{
              border.setRight(getLeftGrid(group,center));  
         });
         lineButton.setOnAction(e->{
              border.setRight(getLeftGrid(group,center));  
         });
         rectangleButton.setOnAction(e->{
              border.setRight(getLeftGrid(group,center));  
         });
         squareButton.setOnAction(e->{
              border.setRight(getLeftGrid(group,center));  
         });
      
     
         top.setHgap(5);
         top.setVgap(5);
         top.setMinSize(500, 100);         
         top.add(t1, 80, 2);
         top.add(buttonBox, 20, 5,500,3);
         
         center.setStyle("-fx-background-color:white");
         ContextMenu menu = new ContextMenu();
          MenuItem paste = new MenuItem("Paste");
          menu.getItems().addAll(paste);
         
            center.setOnMouseClicked(e->{
            if (e.isPopupTrigger()) {
            menu.show((Node)e.getSource(), Side.TOP, 500, 100);
            e.consume();
        }
        });
            
           center.setManaged(true);
            paste.setOnAction(e->{
                if(ShapeView.copied!=null){
                    ShapeController c = new ShapeController(ShapeView.copied,new ShapeView(center));
                    c.resetView();
                }
            });
         border.setTop(top);
         border.setCenter(center);
        
        
        Scene scene = new Scene(border,1100,650);
        stage.setScene(scene);
        stage.show();
        
    }
    public static void main(String[] args) {
       launch(args);
    }
    public GridPane getLeftGrid(ToggleGroup group,Pane center){
        
        String h2="-fx-font-size:25px;-fx-font-weight:bold;";
        String h3="-fx-font-size:15px;-fx-font-weight:bold;";
        GridPane right = new GridPane();
        
        Text heading =  new Text("Settings");
        heading.setFill(Color.LIGHTGRAY);
        heading.setStyle("-fx-font-size:30px;-fx-font-weight:bold;-fx-font-family:fantasy;");
         
        Text x =  new Text("X");
        x.setFill(Color.LIGHTGRAY);
        x.setStyle(h2);
        Text y =  new Text("Y");
        y.setFill(Color.LIGHTGRAY);
        y.setStyle(h2);
        
        TextField xf =  new TextField();
        xf.setMaxSize(100, 30);
        xf.setStyle("-fx-padding:3px;-fx-border-radius:10px");
        
        TextField yf =  new TextField();
        yf.setMaxSize(100, 30);
        yf.setStyle("-fx-padding:3px;-fx-border-radius:10px");
        
        Text chooseColor =  new Text("Choose Color:");
        chooseColor.setStyle(h3);
        chooseColor.setFill(Color.LIGHTGRAY);
        
        ColorPicker colorPicker =  new ColorPicker();
        colorPicker.setMaxSize(200, 30);
        
        Button draw  = new Button();
        draw.setText("Draw ");
       
        Button undo =  new Button("<Undo");
        Button redo  = new Button("Redo>");
        
        
        redo.setOnAction(re->{
            
              if(!ShapeView.redo.isEmpty()){
                
                State state = ShapeView.redo.pop();
                if(state.choice.equals(Choices.add)){
                    center.getChildren().add(state.getShape());
                    ShapeView.stateList.add(state);
                    
                }else if(state.choice.equals(Choices.color)){
                    State prevState = ShapeView.redo.pop();
                     if(prevState.choice.equals(Choices.color)){
                        center.getChildren().remove(state.getShape());
                        state.getShape().setFill(prevState.getShape().getFill());
                        center.getChildren().add(state.getShape());                        
                    }else{
                        ShapeView.redo.add(prevState);
                    }
                     
                }else if(state.choice.equals(Choices.delete)){
                    center.getChildren().remove(state.getShape());
                   ShapeView.stateList.add(state);
                    
                }else if(state.choice.equals(Choices.resize)){
                    State prevState = ShapeView.redo.pop();
                    if(prevState.choice.equals(Choices.resize)){
                        
                            if(state.getShape() instanceof Circle){
                                Circle circle  =(Circle)state.getShape();
                                Circle pre  =  (Circle) prevState.getShape();
                                center.getChildren().remove(circle);
                                circle.setRadius(pre.getRadius());
                                center.getChildren().add(circle);
                                ShapeView.stateList.add(state);
                            }else if(state.getShape() instanceof Polygon){
                                    Polygon polygon  =(Polygon)state.getShape();
                                Polygon pre  =  (Polygon) prevState.getShape();
                                center.getChildren().remove(polygon);
                                polygon.getPoints().clear();
                                polygon.getPoints().addAll(pre.getPoints());
                                boolean isRemoved=center.getChildren().add(polygon);
                                if(isRemoved){
                                   ShapeView.stateList.add(state);
                                }
                            }else if(state.getShape() instanceof Rectangle){
                                Rectangle newRectangle  =(Rectangle)state.getShape();
                                Rectangle pre  =  (Rectangle) prevState.getShape();
                                center.getChildren().remove(newRectangle);
                                newRectangle.setHeight(pre.getHeight());
                                newRectangle.setWidth(pre.getWidth());
                                boolean isRemoved=center.getChildren().add(newRectangle);
                                if(isRemoved){
                                 ShapeView.stateList.add(state);
                                }

                            }else if(state.getShape() instanceof Line){
                                Line newLine  =(Line)state.getShape();
                                Line pre  =  (Line) prevState.getShape();
                                center.getChildren().remove(newLine);
                                newLine.setStartX(pre.getStartX());
                                newLine.setStartY(pre.getStartY());
                                boolean isRemoved=center.getChildren().add(newLine);
                                if(isRemoved){
                                    ShapeView.stateList.add(state);
                                }
                           }else{

                           }
                    }
                }
            }
            
        });
        undo.setOnAction(un->{
            if(!ShapeView.stateList.isEmpty()){
                
                State state = ShapeView.stateList.pop();
                if(state.choice.equals(Choices.add)){
                    boolean isRemoved=center.getChildren().remove(state.getShape());
                    if(isRemoved){
                        ShapeView.redo.add(state);
                    }
                }else if(state.choice.equals(Choices.color)){
                    State prevState = ShapeView.stateList.pop();
                     if(prevState.choice.equals(Choices.color)){
                         
                        center.getChildren().remove(state.getShape());
                        state.getShape().setFill(prevState.getShape().getFill());
                       boolean isAdd =  center.getChildren().add(state.getShape());  
                        if(isAdd){
                            
                            ShapeView.redo.add(state);
                            ShapeView.redo.add(prevState);
                    }
                    }else{
                        ShapeView.stateList.add(prevState);
                    }
                     
                }else if(state.choice.equals(Choices.delete)){
                    boolean isRemoved=center.getChildren().add(state.getShape());
                    if(isRemoved){
                        ShapeView.redo.add(state);
                    }
                    
                }else if(state.choice.equals(Choices.resize)){
                    State prevState = ShapeView.stateList.pop();
                    if(prevState.choice.equals(Choices.resize)){
                        
                            if(state.getShape() instanceof Circle){
                                Circle circle  =(Circle)state.getShape();
                                Circle pre  =  (Circle) prevState.getShape();
                                center.getChildren().remove(circle);
                                circle.setRadius(pre.getRadius());
                                boolean isRemoved=center.getChildren().add(circle);
                                if(isRemoved){
                                    ShapeView.redo.add(state);
                                }
                            }else if(state.getShape() instanceof Polygon){
                                    Polygon polygon  =(Polygon)state.getShape();
                                Polygon pre  =  (Polygon) prevState.getShape();
                                center.getChildren().remove(polygon);
                                polygon.getPoints().clear();
                                polygon.getPoints().addAll(pre.getPoints());
                                boolean isRemoved=center.getChildren().add(polygon);
                                if(isRemoved){
                                    ShapeView.redo.add(state);
                                }
                            }else if(state.getShape() instanceof Rectangle){
                                Rectangle newRectangle  =(Rectangle)state.getShape();
                                Rectangle pre  =  (Rectangle) prevState.getShape();
                                center.getChildren().remove(newRectangle);
                                newRectangle.setHeight(pre.getHeight());
                                newRectangle.setWidth(pre.getWidth());
                                boolean isRemoved=center.getChildren().add(newRectangle);
                                if(isRemoved){
                                    ShapeView.redo.add(state);
                                }

                            }else if(state.getShape() instanceof Line){
                                Line newLine  =(Line)state.getShape();
                                Line pre  =  (Line) prevState.getShape();
                                center.getChildren().remove(newLine);
                                newLine.setStartX(pre.getStartX());
                                newLine.setStartY(pre.getStartY());
                                boolean isRemoved=center.getChildren().add(newLine);
                                if(isRemoved){
                                    ShapeView.redo.add(state);
                                }
                           }else{

                           }
                    }
                }
            }
        });
      
        right.setVgap(2);
        right.setHgap(2);
        right.setMinSize(200, 500);
        right.setStyle("-fx-margin:50px;-fx-background-color:#333333;");
        
        
        
        right.add(heading, 6, 2,10,2);
        right.add(x, 2, 5);
        right.add(y, 11, 5);
        
        right.add(xf, 2, 6,10,2);
        right.add(yf, 11, 6);
        
        right.add(chooseColor, 2, 8,50,1);
        right.add(colorPicker, 2, 9,50,1);
        
        
        
        right.add(draw, 10, 30);
        right.add(undo, 5, 60);
        right.add(redo, 10, 60);
        
    //circle
        if(group.getToggles().get(0).isSelected()){
            
            
           Text t1= new Text("Enter Radius :");
           t1.setStyle(h3);
           t1.setFill(Color.LIGHTGRAY);
           TextField tf =  new TextField();
           right.add(t1, 2, 11);
           right.add(tf, 2, 12,8,2);
           
             draw.setOnAction(s->{
            
                 if(xf.getText().equals("")){
                     xf.requestFocus();
                     xf.setStyle("-fx-border-color:red; -fx-border-style:solid");
                 }else if(yf.getText().equals("")){
                     yf.requestFocus();
                     yf.setStyle("-fx-border-color:red; -fx-border-style:solid");
                 }else if(tf.getText().equals("")){
                      tf.requestFocus();
                     tf.setStyle("-fx-border-color:red; -fx-border-style:solid");
                 }else{
                     
                     int x_axis =Integer.parseInt(xf.getText());
                     int y_axis = Integer.parseInt(yf.getText());
                     Color color = colorPicker.getValue();
                     double radius = Double.parseDouble(tf.getText());
                     
                     
                     
                     model.Shape circle = new model.Circle(radius, x_axis, y_axis, color);
                     ShapeView view = new ShapeView(center);
                     ShapeController controller = new ShapeController(circle,view);
                     controller.resetView();
                     
                     Text areaText = new Text("Area: "+circle.calculateArea());
                     areaText.setStyle(h3);
                     areaText.setFill(Color.LIGHTGRAY);
                     right.add(areaText, 2, 40,30,2);
                     
                 }
                
        });
        
           
        }else  if(group.getToggles().get(1).isSelected()){
           Text t1= new Text("Enter Verteces :");
           t1.setStyle(h3);
           t1.setFill(Color.LIGHTGRAY);
           
           Text x0 = new Text("X0");
           x0.setFill(Color.LIGHTGRAY);
           Text y0 = new Text("Y0");
           y0.setFill(Color.LIGHTGRAY);
           Text x1 = new Text("X1");
           x1.setFill(Color.LIGHTGRAY);
           Text y1 = new Text("Y1");
           y1.setFill(Color.LIGHTGRAY);
           Text x2 = new Text("X2");
           x2.setFill(Color.LIGHTGRAY);
           Text y2 = new Text("Y2");
           y2.setFill(Color.LIGHTGRAY);
           
           
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
           
           right.add(t1, 2, 11,10,1);
          
           right.add(x0, 2, 13);right.add(y0, 10, 13);
           right.add(x0f, 2, 14);right.add(y0f, 10, 14);
           
           right.add(x1, 2, 16);right.add(y1, 10, 16);
           right.add(x1f, 2, 17);right.add(y1f, 10, 17);
           
           right.add(x2, 2, 20);right.add(y2, 10, 20);
           right.add(x2f, 2, 21);right.add(y2f, 10, 21);
           
           right.getChildren().removeAll(x,xf,y,yf);
           
             draw.setOnAction(s->{
            
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
                     
       
                     Color color = colorPicker.getValue();
                     double array[]={
                                     Double.parseDouble(x0f.getText()),Double.parseDouble(y0f.getText()),
                                     Double.parseDouble(x1f.getText()),Double.parseDouble(y1f.getText()),
                                     Double.parseDouble(x2f.getText()),Double.parseDouble(y2f.getText())
                     };
                     
                    
                     
                    model.Shape triangle = new model.Triangle(array,  Integer.parseInt(x0f.getText()),Integer.parseInt(y0f.getText()), color);
                     ShapeView view = new ShapeView(center);
                     ShapeController controller = new ShapeController(triangle,view);
                     controller.resetView();
                     
                     Text areaText = new Text("Area: "+triangle.calculateArea());
                     areaText.setStyle(h3);
                     areaText.setFill(Color.LIGHTGRAY);
                     right.add(areaText, 2, 40,30,2);
                     
                 }
                
        });
        
        }else  if(group.getToggles().get(2).isSelected()){
  
          Text t1= new Text("Enter Length :");
          Text t2= new Text("Enter Width :");
           t1.setStyle(h3);
           t1.setFill(Color.LIGHTGRAY);
           t2.setStyle(h3);
           t2.setFill(Color.LIGHTGRAY);
           
           TextField tf =  new TextField();
           TextField tf1 =  new TextField();
           right.add(t1, 2, 11);
           right.add(tf, 2, 12,8,2);
           
           right.add(t2, 2, 14);
           right.add(tf1, 2, 15,8,2);
           
           draw.setOnAction(s->{
            
                 if(xf.getText().equals("")){
                     xf.requestFocus();
                     xf.setStyle("-fx-border-color:red; -fx-border-style:solid");
                 }else if(yf.getText().equals("")){
                     yf.requestFocus();
                     yf.setStyle("-fx-border-color:red; -fx-border-style:solid");
                 }else if(tf.getText().equals("")){
                      tf.requestFocus();
                     tf.setStyle("-fx-border-color:red; -fx-border-style:solid");
                 }else if(tf1.getText().equals("")){
                      tf1.requestFocus();
                     tf1.setStyle("-fx-border-color:red; -fx-border-style:solid");
                 }
                 else{
                     
                     int x_axis =Integer.parseInt(xf.getText());
                     int y_axis = Integer.parseInt(yf.getText());
                     Color color = colorPicker.getValue();
                     double length = Double.parseDouble(tf.getText());
                     double width = Double.parseDouble(tf1.getText());
                     
                     
                     
                     model.Shape rectangle = new model.Rectangle(length,width, x_axis, y_axis, color);
                     ShapeView view = new ShapeView(center);
                     ShapeController controller = new ShapeController(rectangle,view);
                     controller.resetView();
                     
                     Text areaText = new Text("Area: "+rectangle.calculateArea());
                     areaText.setStyle(h3);
                     areaText.setFill(Color.LIGHTGRAY);
                     right.add(areaText, 2, 40,30,2);
                     
                 }
                
        });
        
            
        
        }else  if(group.getToggles().get(3).isSelected()){
            Text t1= new Text("Start Point :");
          Text t2= new Text("End Point :");
           t1.setStyle(h3);
           t1.setFill(Color.LIGHTGRAY);
           t2.setStyle(h3);
           t2.setFill(Color.LIGHTGRAY);
           
           TextField tf =  new TextField();
           TextField tf1 =  new TextField();
           right.add(t1, 2, 11);
           right.add(tf, 2, 12,8,2);
           right.add(t2, 2, 14);
           right.add(tf1, 2, 15,8,2);
           
           draw.setOnAction(s->{
            
                 if(xf.getText().equals("")){
                     xf.requestFocus();
                     xf.setStyle("-fx-border-color:red; -fx-border-style:solid");
                 }else if(yf.getText().equals("")){
                     yf.requestFocus();
                     yf.setStyle("-fx-border-color:red; -fx-border-style:solid");
                 }else if(tf.getText().equals("")){
                      tf.requestFocus();
                     tf.setStyle("-fx-border-color:red; -fx-border-style:solid");
                 }else if(tf1.getText().equals("")){
                      tf1.requestFocus();
                     tf1.setStyle("-fx-border-color:red; -fx-border-style:solid");
                 }
                 else{
                     
                     int x_axis =Integer.parseInt(xf.getText());
                     int y_axis = Integer.parseInt(yf.getText());
                     Color color = colorPicker.getValue();
                     double length = Double.parseDouble(tf.getText());
                     double width = Double.parseDouble(tf1.getText());
                     
                     
                     
                     model.Shape line = new model.LineSegment(length,width, x_axis, y_axis, color);
                     ShapeView view = new ShapeView(center);
                     ShapeController controller = new ShapeController(line,view);
                     controller.resetView();
                     
                     Text areaText = new Text("Area: "+line.calculateArea());
                     areaText.setStyle(h3);
                     areaText.setFill(Color.LIGHTGRAY);
                     right.add(areaText, 2, 40,30,2);
                     
                 }
                
        });
        
          
        }else  if(group.getToggles().get(4).isSelected()){
           Text t1= new Text("Enter Length:");
           t1.setStyle(h3);
           t1.setFill(Color.LIGHTGRAY);
           TextField tf =  new TextField();
           right.add(t1, 2, 11);
           right.add(tf, 2, 12,8,2);
           draw.setOnAction(s->{
            
                 if(xf.getText().equals("")){
                     xf.requestFocus();
                     xf.setStyle("-fx-border-color:red; -fx-border-style:solid");
                 }else if(yf.getText().equals("")){
                     yf.requestFocus();
                     yf.setStyle("-fx-border-color:red; -fx-border-style:solid");
                 }else if(tf.getText().equals("")){
                      tf.requestFocus();
                     tf.setStyle("-fx-border-color:red; -fx-border-style:solid");
                 }
                 else{
                     
                     int x_axis =Integer.parseInt(xf.getText());
                     int y_axis = Integer.parseInt(yf.getText());
                     Color color = colorPicker.getValue();
                     double length = Double.parseDouble(tf.getText());
                    
                     
                     
                     
                     model.Shape square = new model.Square(length, x_axis, y_axis, color);
                     ShapeView view = new ShapeView(center);
                     ShapeController controller = new ShapeController(square,view);
                     controller.resetView();
                     
                     Text areaText = new Text("Area: "+square.calculateArea());
                     areaText.setStyle(h3);
                     areaText.setFill(Color.LIGHTGRAY);
                     right.add(areaText, 2, 40,30,2);
                     
                 }
                
        });
        
           
           
        }else{
            right.setDisable(true);
            heading.setText("Please Select One");
        }
        
        
         return right;
    }   
    
    
  
}