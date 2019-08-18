
package Provider;

import Users.HistoryTModel;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import main.NameGetter;

public class MyCustomersController implements Initializable {

    @FXML
    private TableView<HistoryTModel> proHistoryTable;
    @FXML
    private TableColumn<HistoryTModel, String> col_pro;
    @FXML
    private TableColumn<HistoryTModel, String> col_cust;
    @FXML
    private TableColumn<HistoryTModel, String> col_food;
   
    ObservableList<HistoryTModel> custList=FXCollections.observableArrayList();
    
    private NameGetter ngetter;
    private DataOutputStream dout;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        col_pro.setCellValueFactory(new PropertyValueFactory<>("provider"));
        col_cust.setCellValueFactory(new PropertyValueFactory<>("buyer"));
        col_food.setCellValueFactory(new PropertyValueFactory<>("foodName"));
        
        
            
         try {
            
            Socket socket = new Socket("127.0.0.1",14000);

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            
            ngetter=new NameGetter();
            String prov=ngetter.getter();
            
            dout=new DataOutputStream(socket.getOutputStream());
            dout.writeUTF(prov);
            
            ArrayList<String> custs =new ArrayList<>() ;
            try {
                custs=(ArrayList<String>)ois.readObject();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MyCustomersController.class.getName()).log(Level.SEVERE, null, ex);

            }
            
            int ctr=0;
            while(ctr<custs.size()){
                
                String s=custs.get(ctr);
                String[] splitted=s.split("~");
                custList.add(new HistoryTModel(splitted[0],splitted[1],splitted[2]));
                ctr+=1;
                
            }
            
            System.out.println(ctr);
            
        } catch (IOException ex) {
        }
        
        proHistoryTable.setItems(custList);
    } 
}    
    

