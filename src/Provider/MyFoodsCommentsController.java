
package Provider;

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
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.NameGetter;

public class MyFoodsCommentsController implements Initializable {

    @FXML
    private TableView<CommentModel> commentTable;
    @FXML
    private TableColumn<CommentModel, String> col_food;
    @FXML
    private TableColumn<CommentModel, String> col_cust;
    @FXML
    private TableColumn<CommentModel, String> col_pro;
    @FXML
    private TableColumn<CommentModel, String> col_comment;
    private NameGetter ngetter;
    private DataOutputStream dout;

    ObservableList<CommentModel> commList=FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        col_food.setCellValueFactory(new PropertyValueFactory<>("foodItem"));
        col_cust.setCellValueFactory(new PropertyValueFactory<>("commentor"));
        col_pro.setCellValueFactory(new PropertyValueFactory<>("producer"));
        col_comment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        
        
        try {
            
            Socket socket = new Socket("127.0.0.1",15000);

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            
            ngetter=new NameGetter();
            String prov=ngetter.getter();
            
            dout=new DataOutputStream(socket.getOutputStream());
            dout.writeUTF(prov);
            
            ArrayList<String> comm =new ArrayList<>() ;
            try {
                comm=(ArrayList<String>)ois.readObject();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MyCustomersController.class.getName()).log(Level.SEVERE, null, ex);

            }
            
            int ctr=0;
            while(ctr<comm.size()){
                
                String s=comm.get(ctr);
                String[] splitted=s.split("~");
                commList.add(new CommentModel(splitted[0],splitted[1],splitted[2],splitted[3]));
                ctr+=1;
                
            }
            
            System.out.println(ctr);
            
        } catch (IOException ex) {
        }
        
        commentTable.setItems(commList);
    }    
    
}
