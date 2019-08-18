package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;



public class MainUIController implements Initializable {
    
    @FXML
    private Pane content_area;
    @FXML
    private Button logInBtn;
    @FXML
    private Pane homePic;
    @FXML
    private Pane mainLogIn;
   
    double x=0;
    double y=0;
    
    @FXML
    private ImageView logoFlip;
    @FXML
    private TextField userField;
    @FXML
    private Pane footer;
    @FXML
    private Button contact;
    @FXML
    private Button privacy;
    @FXML
    private PasswordField passField;
    
    
    static String validUsername;

    static String buyer_type;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            rotator();
    }    


    @FXML
    private void openLoggedIn(MouseEvent event) throws IOException {
        
        String name=userField.getText();
        String pass=passField.getText();
         
        if(name.length()>0 && pass.length()>0){
            String inse=name+" "+pass;

            Socket socket=new Socket("127.0.0.1",9090);

            
            DataOutputStream dout= new DataOutputStream(socket.getOutputStream());
            dout.writeUTF(inse);

            DataInputStream din=new DataInputStream(socket.getInputStream());
            String val=din.readUTF();
            
            String[] splitted = val.split("~");

            System.out.println(splitted[0]);

            if (splitted[0].equals("yes") && !"fghi".equals(pass)) {

                validUsername=name;

                if(splitted[1].equals("provider")){
                    Parent root;
                    try {
                        root = FXMLLoader.load(getClass().getResource("/Provider/ProviderPage.fxml"));
                        Stage stage = new Stage();
                        Scene scene=new Scene(root);
                        stage.setScene(scene);
                        scene.getStylesheets().add("/Provider/ProStyling.css");
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.show();
                        ((Node)(event.getSource())).getScene().getWindow().hide();
                    }
                    catch (IOException e) {
                        e.getMessage();
                    }
                }
                else if(splitted[1].equals("admin")){
                    Parent root;
                    try {
                        root = FXMLLoader.load(getClass().getResource("/Admin/adminPage.fxml"));
                        Stage stage = new Stage();
                        Scene scene=new Scene(root);
                        stage.setScene(scene);
                        scene.getStylesheets().add("/Admin/adminStyling.css");
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.show();
                        ((Node)(event.getSource())).getScene().getWindow().hide();
                    }
                    catch (IOException e) {
                        e.getMessage();
                    }
                }
                else if(splitted[1].equals("buyer")){

                    buyer_type=splitted[2];

                    Parent root;
                    try {
                        root = FXMLLoader.load(getClass().getResource("/Users/userPage.fxml"));
                        Stage stage = new Stage();
                        Scene scene=new Scene(root);
                        stage.setScene(scene);
                        scene.getStylesheets().add("/Users/userStyle.css");
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.show();
                        ((Node)(event.getSource())).getScene().getWindow().hide();
                    }
                    catch (IOException e) {
                        e.getMessage();
                    }
                }


            } else {
                userField.setText("Incorrect Username or Password");
                passField.setText("");

            }
        }else{
            userField.setText("Fill All Fields Appropriately");
        }
    }
    
    @FXML
    private void dragged(MouseEvent event) {
        Node node=(Node)event.getSource();
        Stage stage=(Stage)node.getScene().getWindow();
        stage.setX(event.getScreenX()-x);
        stage.setY(event.getScreenY()-y);
    }

    @FXML
    private void pressed(MouseEvent event) {
        x=event.getSceneX();
        y=event.getSceneY();
    }

    @FXML
    private void close(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void minimize(MouseEvent event) {
        Node node=(Node)event.getSource();
        ((Stage) (node).getScene().getWindow()).setIconified(true);
        
    }
    
    private void rotator(){
        RotateTransition rt = new RotateTransition(Duration.millis(3000), logoFlip);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
    }

    @FXML
    private void clear(MouseEvent event) {
        userField.setText("");
       
    }
    
}
