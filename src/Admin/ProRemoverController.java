
package Admin;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import javafx.scene.layout.AnchorPane;


public class ProRemoverController implements Initializable {

    @FXML
    private FontAwesomeIconView garbage;
    @FXML
    private AnchorPane delBack;
    @FXML
    private TextField txt;

    DataInputStream din;
    DataOutputStream dout;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void passDeleteCommand(MouseEvent event) throws IOException {
        
        if(txt.getText().length()>0){
            String pro=txt.getText();

            Socket socket=new Socket("127.0.0.1",5858);

            dout= new DataOutputStream(socket.getOutputStream());
            dout.writeUTF(pro);

            din=new DataInputStream(socket.getInputStream());
            String val=din.readUTF();

            if(val.length()>0){
                txt.setText(val);
            }
        
        }else{
            txt.setText("Please Enter a Provider Name");
        }
    }

    @FXML
    private void clearText(MouseEvent event) {
        txt.setText("");
    }
    
}
