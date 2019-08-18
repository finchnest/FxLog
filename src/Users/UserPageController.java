package Users;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import main.NameGetter;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.scene.control.Labeled;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class UserPageController implements Initializable {

    @FXML
    private ImageView compLogo;
    @FXML
    private Button userSignOut;
    @FXML
    private Pane userMainDataview;

    NameGetter ngetter;
    
    DataInputStream din;
    DataOutputStream dout;
    
    @FXML
    private TextField searchByN;
    
    static String food_item_name;
    static String category;
    
    @FXML
    private TextField adPage;
    @FXML
    private TextField enterCat;
    
    public void initialize(URL url, ResourceBundle rb) {
        
        ngetter =new NameGetter();
        String unagi=ngetter.userType();
        if(unagi.equalsIgnoreCase("normal")){
            
            Socket sss;
            try {
                sss = new Socket("127.0.0.1",18600);
                din=new DataInputStream(sss.getInputStream());
                adPage.setText(din.readUTF());
//                adPage.setStyle("-fx-text-fill: #fff;");
                
            } catch (IOException ex) {
                Logger.getLogger(UserPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        Rotator r=new Rotator();
        Thread myR=new Thread(r);
        myR.start();
    }    

    @FXML
    private void myNotifications(MouseEvent event) throws IOException {
        Parent second = FXMLLoader.load(getClass().getResource("/Users/Notification.fxml"));
        userMainDataview.getChildren().removeAll();
        userMainDataview.getChildren().setAll(second);
    }

    @FXML
    private void userPassChange(MouseEvent event) throws IOException {
        Parent second = FXMLLoader.load(getClass().getResource("userPasswordChange.fxml"));
        userMainDataview.getChildren().removeAll();
        userMainDataview.getChildren().setAll(second);
    }

    @FXML
    private void myHistory(MouseEvent event) throws IOException {
        Parent second = FXMLLoader.load(getClass().getResource("/Users/MyHistory.fxml"));
        userMainDataview.getChildren().removeAll();
        userMainDataview.getChildren().setAll(second);
    }

    @FXML
    private void changeAccType(MouseEvent event) throws IOException {
        ngetter =new NameGetter();
        String unagi=ngetter.getter();
        
        Socket socket=new Socket("127.0.0.1",12005);
        
        DataOutputStream output= new DataOutputStream(socket.getOutputStream());
        DataInputStream input= new DataInputStream(socket.getInputStream());
        
        output.writeUTF(unagi);
        
        String updated=input.readUTF();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User Type Change Successful");
        alert.setHeaderText("Change Successful");
        alert.setContentText("User Account Type Successfully Changed to "+updated.toUpperCase());

        alert.showAndWait();
        
    }

    @FXML
    private void userRemoveMyAcc(MouseEvent event) throws IOException {
        
        ngetter =new NameGetter();
        String unagi=ngetter.getter();
        
        Socket socket=new Socket("127.0.0.1",12001);

        dout= new DataOutputStream(socket.getOutputStream());
        dout.writeUTF(unagi);
        
        userLogOut(event);

    }

    @FXML
    private void userLogOut(MouseEvent event) throws IOException {
        Stage stagee = (Stage) userSignOut.getScene().getWindow();
        stagee.close();
        
        Parent fxml = FXMLLoader.load(getClass().getResource("/main/MainUI.fxml"));
        
        Scene scene = new Scene(fxml);
        scene.getStylesheets().add("/main/Formatter.css");
        
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    
    @FXML
    private void searchByName(MouseEvent event) {
        food_item_name=searchByN.getText();
        category=enterCat.getText();
        
        
        if (searchByN.getText().length()>0 && enterCat.getText().length()>0) {
            try {
//                AnchorPane root = (AnchorPane) FXMLLoader.load(UserPageController.class.getResource("/Users/ByName.fxml"));
                Parent second = FXMLLoader.load(getClass().getResource("/Users/ByName.fxml"));
                userMainDataview.getChildren().removeAll();
                userMainDataview.getChildren().setAll(second);
            } catch (IOException ex) {
                Logger.getLogger(UserPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            searchByN.setText("Name Required and Category Required");
        }
        
    }

    public String userFinder(){
        return new NameGetter().getter();
    }
    
    @FXML
    private void searchByTopRated(MouseEvent event) throws IOException {
        category=enterCat.getText();
        if (category.length()>0) {
            Parent second = FXMLLoader.load(getClass().getResource("/Users/ByTop.fxml"));
            userMainDataview.getChildren().removeAll();
            userMainDataview.getChildren().setAll(second);
        }
        else{
            enterCat.setText("Category Required");
        }
    }
    
    
    @FXML
    private void userClose(MouseEvent event) {
        System.exit(0);
    }
    


    @FXML
    private void clear(MouseEvent event) {
        searchByN.setText("");
        
    }

    @FXML
    private void menuNoti(ActionEvent event) throws IOException {
        Parent second = FXMLLoader.load(getClass().getResource("/Users/Notification.fxml"));
        userMainDataview.getChildren().removeAll();
        userMainDataview.getChildren().setAll(second);
        
    }

    @FXML
    private void menuHis(ActionEvent event) throws IOException {
        Parent second = FXMLLoader.load(getClass().getResource("/Users/MyHistory.fxml"));
        userMainDataview.getChildren().removeAll();
        userMainDataview.getChildren().setAll(second);
    }

    @FXML
    private void menuByTop(ActionEvent event) throws IOException {
        Parent second = FXMLLoader.load(getClass().getResource("/Users/ByTop.fxml"));
        userMainDataview.getChildren().removeAll();
        userMainDataview.getChildren().setAll(second);
    }

    @FXML
    private void menuChangePass(ActionEvent event) throws IOException {
        Parent second = FXMLLoader.load(getClass().getResource("userPasswordChange.fxml"));
        userMainDataview.getChildren().removeAll();
        userMainDataview.getChildren().setAll(second);
        
    }

    @FXML
    private void menuChangeAcc(ActionEvent event) throws IOException {
        ngetter =new NameGetter();
        String unagi=ngetter.getter();
        
        Socket socket=new Socket("127.0.0.1",12005);
        
        DataOutputStream output= new DataOutputStream(socket.getOutputStream());
        DataInputStream input= new DataInputStream(socket.getInputStream());
        
        output.writeUTF(unagi);
        
        String updated=input.readUTF();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User Type Change Successful");
        alert.setHeaderText("Change Successful");
        alert.setContentText("User Account Type Successfully Changed to "+updated.toUpperCase());

        alert.showAndWait();
    }


    @FXML
    private void menuLog(ActionEvent event) throws IOException {
        Stage stagee = (Stage) userSignOut.getScene().getWindow();
        stagee.close();
        
        Parent fxml = FXMLLoader.load(getClass().getResource("/main/MainUI.fxml"));
        
        Scene scene = new Scene(fxml);
        scene.getStylesheets().add("/main/Formatter.css");
        
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    @FXML
    private void menuExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void displayAll(ActionEvent event) throws IOException {
        Parent second = FXMLLoader.load(getClass().getResource("/Users/allCats.fxml"));
        userMainDataview.getChildren().removeAll();
        userMainDataview.getChildren().setAll(second);
    }

    class Rotator implements Runnable{

        @Override
        public void run() {
            RotateTransition rt = new RotateTransition(Duration.millis(3000), compLogo);
            rt.setByAngle(360);
            rt.setCycleCount(Animation.INDEFINITE);
            rt.setInterpolator(Interpolator.LINEAR);
            rt.play();
        }

    }
}


