
package Users;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import main.NameGetter;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import org.apache.commons.codec.binary.Base64;

public class UserPasswordChangeController implements Initializable {

    @FXML
    private PasswordField newUserP;
    @FXML
    private PasswordField confirmedUserP;
    @FXML
    private TextField checker;

    private DataOutputStream dout;
    private DataInputStream din;
    
    NameGetter ngetter;
    
    private static final int iterations = 2*1000;
    private static final int saltLen = 20;
    private static final int desiredKeyLen = 128;
    
    String hashedPass="";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void performPassChange(MouseEvent event) throws IOException, Exception {
        String newPa=newUserP.getText();
        String confirm=confirmedUserP.getText();
        
        if(newPa.length()>0 
                && confirm.length()>0 && newPa.equals(confirm)){
            
            String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
            System.out.println(newPa.matches(pattern));
            
            if(newPa.matches(pattern)){
                
                hashedPass=getSaltedHash(newPa);
                
                System.out.println(hashedPass);
                ngetter=new NameGetter();
                String userP=ngetter.getter();

                String concat=hashedPass+"    "+userP;

                Socket socket=new Socket("127.0.0.1",12000);

                dout= new DataOutputStream(socket.getOutputStream());
                dout.writeUTF(concat);

                System.out.println("sent");

                din=new DataInputStream(socket.getInputStream());
                String val=din.readUTF();

                System.out.println(val);
                
                checker.setText(val);
            }else{
                checker.setText("Choose A Strong Password");
            }
        }else{
            checker.setText("Fill All Fields Appropriately");
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
    
}
