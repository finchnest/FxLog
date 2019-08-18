
package Users;
import java.io.DataInputStream;
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
import javafx.scene.layout.AnchorPane;
import main.NameGetter;


public class MyHistoryController implements Initializable {
    
    @FXML
    private TableView<HistoryTModel> historyTable;
  
    @FXML
    private TableColumn<HistoryTModel, String> col_cust;
    
    @FXML
    private TableColumn<HistoryTModel, String> col_pro;
    @FXML
    
    private TableColumn<HistoryTModel, String> col_food;
    @FXML
    private AnchorPane historyTableView;  
    
    DataInputStream din;
    DataOutputStream dout;
    
    NameGetter ngetter;
    
    ObservableList<HistoryTModel> hisList=FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        col_cust.setCellValueFactory(new PropertyValueFactory<>("buyer"));
        col_pro.setCellValueFactory(new PropertyValueFactory<>("provider"));
        col_food.setCellValueFactory(new PropertyValueFactory<>("foodName"));
        
        
         try {
            
            Socket socket = new Socket("127.0.0.1",12006);

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            
            ngetter=new NameGetter();
            String usernami=ngetter.getter();
            
            dout=new DataOutputStream(socket.getOutputStream());
            dout.writeUTF(usernami);
            
            ArrayList<String> his =new ArrayList<>() ;
            try {
                his=(ArrayList<String>)ois.readObject();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MyHistoryController.class.getName()).log(Level.SEVERE, null, ex);

            }
            
            int ctr=0;
            while(ctr<his.size()){
                
                String s=his.get(ctr);
                String[] splitted=s.split("~");
                hisList.add(new HistoryTModel(splitted[0],splitted[1],splitted[2]));
                ctr+=1;
                
            }
            
            System.out.println(ctr);
            
        } catch (IOException ex) {
        }
        
        historyTable.setItems(hisList);
    }    

    }    
    

