/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package Assignment;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

/**
 *
 * @author DELL
 */
public class FXMLDocumentController implements Initializable {
   
    
   private String path;
   private MediaPlayer player;
   
   @FXML
   private Button chooseFile;

    @FXML
   private Button fastForward;

    @FXML
   private Button pause;

    @FXML
   private Button play;
       
    @FXML
   private Button stop;
   
   @FXML
   private MediaView view;
   
   @FXML
   private Slider progressBar;
   
   @FXML
   private Slider volume;
   
   public void chooseFile(ActionEvent event){
       FileChooser chose = new FileChooser();
       File fl = chose.showOpenDialog(null);
       path = fl.toURI().toString();
       
       if(path != null){
           Media md = new Media(path);
           player = new MediaPlayer(md);
           view.setMediaPlayer(player);
           
           DoubleProperty widthProp = view.fitWidthProperty();
           DoubleProperty heightProp = view.fitHeightProperty();
           
           widthProp.bind(Bindings.selectDouble(view.sceneProperty(), "width"));
           heightProp.bind(Bindings.selectDouble(view.sceneProperty(), "height"));
           
           player.currentTimeProperty().addListener(new ChangeListener<Duration>(){
               @Override
               public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue){
                   progressBar.setValue(newValue.toSeconds());
               }
           
           });
           
           progressBar.setOnMousePressed(new EventHandler<MouseEvent>(){
           
           @Override
           public void handle(MouseEvent event){
               player.seek(Duration.seconds(progressBar.getValue()));
           }
           
           
           });
           
           progressBar.setOnMouseDragged(new EventHandler<MouseEvent>(){
           
           @Override
           public void handle(MouseEvent event){
               player.seek(Duration.seconds(progressBar.getValue()));
           }
            
           });
           
           player.setOnReady(new Runnable() {
               @Override
               public void run() {
                   Duration tt = player.getTotalDuration();
                   progressBar.setMax(tt.toSeconds());
               }
           });
           
           volume.setValue(player.getVolume() * 100);
           volume.valueProperty().addListener(new InvalidationListener() {
               @Override
               public void invalidated(Observable arg0) {
                   player.setVolume(volume.getValue()/100);
               }
           });
           
           player.play();
       }
   }
   
   //Maximum time slider duration
    
   
    public void play(ActionEvent event){
       player.play();
       
       //if you have increased or decreased the video time
       //once you click on play it will play in a normal rate
       player.setRate(1);
   }
    
   @FXML
    public void pause(ActionEvent event){
       player.pause();
   }
    
    @FXML
    public void stop(ActionEvent event){
       player.stop();
   }
    
     @FXML
     public void slowRate(ActionEvent event){
       player.setRate(0.5);
   }
    
    @FXML
    public void fastForward(ActionEvent event){
       player.setRate(2);
   }
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
