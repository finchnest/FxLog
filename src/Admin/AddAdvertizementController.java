
package Admin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class AddAdvertizementController implements Initializable {

    @FXML
    private TextField advertizer;
    @FXML
    private TextField advertizement;
    private DataOutputStream dout;
    private DataInputStream din;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void insertAd(MouseEvent event) throws IOException {
        if(advertizer.getText().length()>0 && advertizement.getText().length()>0){
        
            String adver=advertizer.getText().toUpperCase();
            String ad=advertizement.getText().toUpperCase();
            

            String concat=adver+"~"+ad;

            Socket socket=new Socket("127.0.0.1",17777);

            dout= new DataOutputStream(socket.getOutputStream());
            dout.writeUTF(concat);

            din=new DataInputStream(socket.getInputStream());
            advertizer.setText(din.readUTF()); 
        
        }else{
            advertizer.setText("Fill Fields Successfully");
        }
    }
    
}
