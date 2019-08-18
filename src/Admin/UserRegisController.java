package Admin;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;


public class UserRegisController implements Initializable {


    DataInputStream din;
    DataOutputStream dout;


    @FXML
    private TextField fname;
    @FXML
    private TextField lname;
    @FXML
    private TextField username;
    @FXML
    private TextField account;
    @FXML
    private Button registerBtn;
    @FXML
    private RadioButton normal;
    @FXML
    private ToggleGroup userType;
    @FXML
    private RadioButton premium;
    @FXML
    private PasswordField password;
    @FXML
    private TextField errorMes;
    @FXML
    private ImageView proview;
    @FXML
    private AnchorPane regS;
    @FXML
    private Button profilePic;
    @FXML
    private Label fileSelected;

    private String imageFile;

    File selectedFile;

    Image image;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }


    @FXML
    private void regUser(MouseEvent event) throws IOException {
        if (fname.getText().length() > 0 && lname.getText().length() > 0
                && account.getText().length() > 0 && username.getText().length() > 0 &&
                password.getText().length() > 0/* && selectedFile != null*/) {
            if (normal.isSelected() || premium.isSelected()) {
                reg(event);
                fname.setText("");
                username.setText("");
                account.setText("");
                lname.setText("");
                password.setText("");
            } else {
                errorMes.setText("Please Fill All Fields");
            }
        } else {
            errorMes.setText("Please Fill All Fields");
        }
    }

    public void reg(MouseEvent event) throws IOException {
        String fn = fname.getText();
        String passw = password.getText();
        String ln = lname.getText();
        String bankNo = account.getText();
        String un = username.getText();
        String usertype = "";
        if (normal.isSelected() == true) {
            usertype = "normal";
        } else {
            usertype = "premium";
        }

//        //image loader 
//    /*
        BufferedImage imagee = SwingFXUtils.fromFXImage(image, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(imagee, "JPG", baos);
        byte imgBytes[] = baos.toByteArray();
   */


        String concat = fn + "~" + ln + "~" + un + "~" + passw + "~" + usertype + "~" + bankNo;

        Socket socket = new Socket("127.0.0.1", 8888);

        dout = new DataOutputStream(socket.getOutputStream());
        dout.writeUTF(concat);
        
      
        /*
        dout.writeInt(imgBytes.length); // write length of the message
        dout.write(imgBytes);           // write the message
        */

        din = new DataInputStream(socket.getInputStream());
        String val = din.readUTF();

        if (val.length() > 0) {
            errorMes.setText(val);
        }


    }


    @FXML
    private void openDir(MouseEvent event) throws MalformedURLException {
        fileSelected.setText("");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.bmp", "*.png", "*.jpg", "*.gif")); // limit fileChooser options to image files
        selectedFile = fileChooser.showOpenDialog(proview.getScene().getWindow());

        if (selectedFile != null) {

            imageFile = selectedFile.toURI().toURL().toString();

            image = new Image(imageFile);
            proview.setImage(image);
        } else {
            fileSelected.setText("Image file selection cancelled.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Please Select a File");
            alert.setContentText("You didn't select a file!");

            alert.showAndWait();

        }


    }


}
