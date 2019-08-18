
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

public class SeeProController implements Initializable {

    @FXML
    private TableView<ModelTable> table;
    
    @FXML
    private TableColumn<ModelTable, String> col_username;
    
   
    @FXML
    private TableColumn<ModelTable, Integer> col_bank;
    
    ObservableList<ModelTable> oblist=FXCollections.observableArrayList();

    DataInputStream din;
    DataOutputStream dout;

    @FXML
    private AnchorPane proTableView;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_bank.setCellValueFactory(new PropertyValueFactory<>("account"));
        
        
        try {
            
            Socket socket = new Socket("127.0.0.1",4949);
//            dout= new DataOutputStream(socket.getOutputStream());
//            din=new DataInputStream(socket.getInputStream());
//            
//            String[] row=din.readUTF().split("\\s");
            

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            ArrayList<String> pros =new ArrayList<>() ;
            try {
                pros=(ArrayList<String>)ois.readObject();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SeeUController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            int ctr=0;
            while(ctr<pros.size()){
                String s=pros.get(ctr);
                String[] splitted=s.split("~");
                
//                int finder=0;
                
//                while(finder<oblist.size()){
                
//                    if(oblist.get(finder).username.equals(splitted[0])){
//                        break;
//                    }
//                    else if(finder==oblist.size()-1){
                        ModelTable mt=new ModelTable(splitted[0],Integer.parseInt(splitted[1]));
                        oblist.add(mt);
//                        
//                    }
//                    else{
//                        continue;
//                    }
//                    finder+=1;

                    
//                }
                ctr+=1; 
     
            }
            
            System.out.println(ctr);
            
        } catch (IOException ex) {
            Logger.getLogger(SeeUController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        table.setItems(oblist);
    }    



}
