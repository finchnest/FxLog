
package Provider;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import main.NameGetter;
import org.apache.commons.codec.binary.Base64;


public class ChangePasswController implements Initializable {

    
    NameGetter ngetter;
    @FXML
    private TextField confirmP;
    @FXML
    private TextField newP;
    @FXML
    private TextField errorField;
    private DataOutputStream dout;
    private DataInputStream din;
    
    private static final int iterations = 2*1000;
    private static final int saltLen = 20;
    private static final int desiredKeyLen = 128;
    
    String hashedPass="";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void changeUserPassword(MouseEvent event) throws IOException, Exception {
        
        
        String newPa=newP.getText();
        String confirm=confirmP.getText();
        
        if(newPa.length()>0 
                && confirm.length()>0 && newPa.equals(confirm)){

            String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
            System.out.println(newPa.matches(pattern));
            
            if(newPa.matches(pattern)){
                
                hashedPass=getSaltedHash(newPa);
                System.out.println(hashedPass);
                
                ngetter=new NameGetter();
                String provider=ngetter.getter();

                String concat=hashedPass+" "+provider;

                Socket socket=new Socket("127.0.0.1",11112);

                dout= new DataOutputStream(socket.getOutputStream());
                dout.writeUTF(concat);


                din=new DataInputStream(socket.getInputStream());
                String val=din.readUTF();

                errorField.setText(val);
            }else{
                errorField.setText("Enter a Strong Password");
            }
        }else{
            errorField.setText("Fill All Fields Appropriately");
        }
    }
    
    public  String getSaltedHash(String password) throws Exception {
        byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);
        // store the salt with the password
        System.out.println("salted");
        return Base64.encodeBase64String(salt) + "$" + hash(password, salt);
    }
    
    private  String hash(String password, byte[] salt) throws Exception {
        if (password == null || password.length() == 0)
            throw new IllegalArgumentException("Empty passwords are not supported.");
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey key = f.generateSecret(new PBEKeySpec(
            password.toCharArray(), salt, iterations, desiredKeyLen));
        System.out.println("hashed");
        return Base64.encodeBase64String(key.getEncoded());
    }
    
    public void change(MouseEvent event) throws IOException{
        
    }
}
