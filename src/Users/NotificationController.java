
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
import main.NameGetter;

public class NotificationController implements Initializable {

    
    @FXML
    private TableView<UserMessageModel> notificationTable;
    @FXML
    private TableColumn<UserMessageModel, String> col_rec;
    @FXML
    private TableColumn<UserMessageModel, String> col_sen;
    @FXML
    private TableColumn<UserMessageModel, String> col_mes;

    DataInputStream din;
    DataOutputStream dout;

    NameGetter ngetter;

    ObservableList<UserMessageModel> mesList=FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        col_rec.setCellValueFactory(new PropertyValueFactory<>("reciever"));
        col_sen.setCellValueFactory(new PropertyValueFactory<>("sender"));
        col_mes.setCellValueFactory(new PropertyValueFactory<>("message"));
        
          try {
            
            Socket socket = new Socket("127.0.0.1",12050);

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            
            ngetter=new NameGetter();
            String usernami=ngetter.getter();
            
            dout=new DataOutputStream(socket.getOutputStream());
            dout.writeUTF(usernami);
            
            ArrayList<String> mess =new ArrayList<>() ;
            try {
                mess=(ArrayList<String>)ois.readObject();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(NotificationController.class.getName()).log(Level.SEVERE, null, ex);
            }
            int ctr=0;
            while(ctr<mess.size()){
                
                String s=mess.get(ctr);
                String[] splitted=s.split("~");
                mesList.add(new UserMessageModel(splitted[0],splitted[1],splitted[2]));
                ctr+=1;
                
            }
            
            System.out.println(ctr);
            
        } catch (IOException ex) {
        }
        
        notificationTable.setItems(mesList);
    }  
}    

