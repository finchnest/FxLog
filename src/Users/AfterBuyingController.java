
package Users;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.Rating;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class AfterBuyingController implements Initializable {

    @FXML
    private Pane afterBack;
    @FXML
    private TextField commentField;
    @FXML
    private TextField sharedFriend;
    @FXML
    private AnchorPane afterAnchorPane;

    @FXML
    Rating rating;
    
    int rating_value;
    DataOutputStream dout;
    DataInputStream din;
 
    String proo="";
    String fooo=new FoodRecGetter().food_name_getter();
    
    
    String comment,friend,rate,cust;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        rating.ratingProperty().addListener(new ChangeListener<Number>() {
            
            @Override 
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
               rating_value=t1.intValue();
                System.out.println(rating_value);
                
                proo=ByNameController.prov;
                
            }
        });
    }    

    @FXML
    private void saveAfterData(MouseEvent event) throws IOException {
        
        
        if(commentField.getText().length()>0){
            comment=commentField.getText();
        }else{
            comment="0";
        }
        if(sharedFriend.getText().length()>0){
            friend=sharedFriend.getText();
        }else{
            friend="0";
        }
        if(rating_value>0){
            rate=Integer.toString(rating_value);
        }else{
            rate="0";
        }
        
        
        cust=new ByNameController().customer;
         
        String concat=comment+"~"+friend+"~"+rate+"~"+cust+"~"+proo+"~"+fooo;

        Socket socket=new Socket("127.0.0.1",13000);

        dout= new DataOutputStream(socket.getOutputStream());
        dout.writeUTF(concat);
 
        din=new DataInputStream(socket.getInputStream());
        String val=din.readUTF();
        
        if(val.equals("This User Does Not Exist")){
            sharedFriend.setText("Unregistered User");
        }else if(val.equals("Message Sent")){
            closeAfterScreen(event);
            afterAnchorPane.getChildren().removeAll();
        }else{
            
            closeAfterScreen(event);
            afterAnchorPane.getChildren().removeAll();
        }

        
    }

    @FXML
    private void closeAfterScreen(MouseEvent event) throws IOException {
        afterAnchorPane.getChildren().clear();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successful Order");
        alert.setHeaderText("Your Food is On Its Way");
        alert.setContentText("Your food order transaction was successful..."
                + "and your food is on its way. Enjoy Your Food");

        alert.showAndWait();
    }


    
}
