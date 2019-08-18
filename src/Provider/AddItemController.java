
package Provider;

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
import main.NameGetter;

public class AddItemController implements Initializable {

    DataInputStream din;
    DataOutputStream dout;
    
    NameGetter ngetter;
    
    @FXML
    private TextField foodName;
    @FXML
    private TextField foodPrice;
    @FXML
    private TextField foodType;
    @FXML
    private TextField foodCount;
    @FXML
    private TextField errorMes;
    @FXML
    private TextField foodCategory;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void uploadFoodData(MouseEvent event) {
        
        try {
            Double.parseDouble(foodPrice.getText());
            Integer.parseInt(foodCount.getText());
            
            if(foodName.getText().length()>0 && foodPrice.getText().length()>0 
                 && foodCount.getText().length()>0 
                    && foodCategory.getText().length()>0){
            
                reg(event);
                foodName.setText("");
                foodType.setText("");
                foodPrice.setText("");
                foodCount.setText("");
                foodCategory.setText("");
            
            }else{
                errorMes.setText("Please Enter Data Appropriately");
            }
            
        } catch (Exception e) {
            errorMes.setText("Please Enter Data Appropriately");
        }
        
        
    }

    private void reg(MouseEvent event) throws IOException {
        
        String foodN=foodName.getText();
        String foodP=foodPrice.getText();
        String foodTy=foodType.getText();
        String foodC=foodCount.getText();
        String foodCat=foodCategory.getText();
        
        ngetter=new NameGetter();
        String provider=ngetter.getter();
        
        String concat=foodN+"~"+foodP+"~"+foodTy+"~"+foodC+"~"+provider+"~"+foodCat;

        Socket socket=new Socket("127.0.0.1",11111);

        dout= new DataOutputStream(socket.getOutputStream());
        dout.writeUTF(concat);
        
        
        din=new DataInputStream(socket.getInputStream());
        String val=din.readUTF();
        
        errorMes.setText(val);
    }
    
}
