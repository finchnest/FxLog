
package Admin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class NewTController implements Initializable {

        
    DataInputStream din;
    DataOutputStream dout;
    

    @FXML
    private AnchorPane regBack;
    @FXML
    private TextField uf;
    @FXML
    private TextField ba;

    @FXML
    private PasswordField pf;
    @FXML
    private TextField reply;
    @FXML
    private Button insP;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }    

    @FXML
    private void insPro(MouseEvent event) throws IOException {        
        if(uf.getText().length()>0 && pf.getText().length()>0 &&
                ba.getText().length()>0){
            
                String passw=pf.getText();
                String bankNo=ba.getText();
                String un=uf.getText();

                String concat=un+"~"+passw+"~"+bankNo;

                Socket socket=new Socket("127.0.0.1",7777);

                dout= new DataOutputStream(socket.getOutputStream());
                dout.writeUTF(concat);

                din=new DataInputStream(socket.getInputStream());
                String val=din.readUTF();

                if(val.length()>0){
                    reply.setText(val);
                }
           
        }else{
            reply.setText("Please Fill All Fields");
        }
    }
    
    
}