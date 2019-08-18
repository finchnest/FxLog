package Admin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.AnchorPane;

public class SeeUController implements Initializable {
    @FXML
    private TableView<ModelTable> table;
    @FXML
    private TableColumn<ModelTable, String> col_fname;
    @FXML
    private TableColumn<ModelTable, String> col_lname;
    @FXML
    private TableColumn<ModelTable, String> col_username;
    
    @FXML
    private TableColumn<ModelTable, String> col_usertype;
    @FXML
    private TableColumn<ModelTable, Integer> col_bank;
    
    ObservableList<ModelTable> oblist=FXCollections.observableArrayList();

    DataInputStream din;
    DataOutputStream dout;

    @FXML
    private AnchorPane userTableView;


    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        col_fname.setCellValueFactory(new PropertyValueFactory<>("fname"));
        col_lname.setCellValueFactory(new PropertyValueFactory<>("lname"));
        col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_usertype.setCellValueFactory(new PropertyValueFactory<>("usertype"));
        col_bank.setCellValueFactory(new PropertyValueFactory<>("account"));
        
        try {
            
            Socket socket = new Socket("127.0.0.1",9494);
//            dout= new DataOutputStream(socket.getOutputStream());
//            din=new DataInputStream(socket.getInputStream());
//            
//            String[] row=din.readUTF().split("\\s");
            

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            ArrayList<String> user =new ArrayList<>() ;
            try {
                user=(ArrayList<String>)ois.readObject();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SeeUController.class.getName()).log(Level.SEVERE, null, ex);
            }
            int ctr=0;
            while(ctr<user.size()){
                String s=user.get(ctr);
                String[] splitted=s.split("~");
                ModelTable mt=new ModelTable(splitted[0],splitted[1],splitted[2],splitted[3],Integer.parseInt(splitted[4]));
                oblist.add(mt);
                ctr+=1;
                
            }
            
            System.out.println(ctr);
            
        } catch (IOException ex) {
            Logger.getLogger(SeeUController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        table.setItems(oblist);
    }    

}
