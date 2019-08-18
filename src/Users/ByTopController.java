
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
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


public class ByTopController implements Initializable {
    
    FoodRecGetter frg;

    @FXML
    private TableView<FoodTableModel> byTopTable;
    @FXML
    private TableColumn<FoodTableModel, String> col_food;
    @FXML
    private TableColumn<FoodTableModel, String> col_pro;
    @FXML
    private TableColumn<FoodTableModel, Integer> col_price;
    @FXML
    private TableColumn<FoodTableModel, String> col_type;
    @FXML
    private TableColumn<FoodTableModel, Float> col_rating;
    @FXML
    private TableColumn<FoodTableModel, Integer> col_available;
    @FXML
    private Button byTopButt;
    @FXML
    private TextField status;

    ObservableList<FoodTableModel> topList=FXCollections.observableArrayList();
    private DataOutputStream dout;
    private DataInputStream din;
    static String food;
    
    public  UserPageController upc=new UserPageController();
    public  String customer=upc.userFinder();
    @FXML
    private AnchorPane byTopPane;
    static String provid;
    
    
    public String cat;
    DataOutputStream doutput;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        col_food.setCellValueFactory(new PropertyValueFactory<>("foodName"));
        col_pro.setCellValueFactory(new PropertyValueFactory<>("provider"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        col_available.setCellValueFactory(new PropertyValueFactory<>("available"));
        
        byTopTable.getItems().clear();
        
         try {
            
            Socket socket = new Socket("127.0.0.1",13600);
            
            frg=new FoodRecGetter();
            cat=frg.food_cat_getter();
            
            doutput = new DataOutputStream(socket.getOutputStream());
            doutput.writeUTF(cat);
            
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            ArrayList<String> byTopFoods =new ArrayList<>() ;
            
            try {
                byTopFoods=(ArrayList<String>)ois.readObject();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MyHistoryController.class.getName()).log(Level.SEVERE, null, ex);

            }
            
            int ctr=0;
            while(ctr<byTopFoods.size()){
                
                String s=byTopFoods.get(ctr);
                String[] splitted=s.split("~");
                topList.add(new FoodTableModel(splitted[0],splitted[1],Integer.parseInt(splitted[2]),splitted[3],Float.parseFloat(splitted[4]),Integer.parseInt(splitted[5])));
                ctr+=1;
                
            }
            
            System.out.println(ctr);
            
        } catch (IOException ex) {
        }
        
        byTopTable.setItems(topList);
        byTopButt.disableProperty().bind(Bindings.isEmpty(byTopTable.getSelectionModel().getSelectedItems()));

    }    

    @FXML
    private void runBuy(MouseEvent event) throws IOException {
            try{
                FoodTableModel fmodel=byTopTable.getSelectionModel().getSelectedItems().get(0);
                provid=fmodel.provider;
                food=fmodel.foodName;

                Socket soc=new Socket("127.0.0.1",12080);

                dout = new DataOutputStream(soc.getOutputStream());
                din=new DataInputStream(soc.getInputStream());

                String dat=food+"~"+provid+"~"+customer;
                dout.writeUTF(dat);

                String nextStepsChecker=din.readUTF();
                if(nextStepsChecker.equalsIgnoreCase("Buying Request Successful")){
                    openAfterScreen();
                }else{
                    status.setText("Buying Request Unsuccessful");
                }
            }catch(Exception e){
            
            }
    }
    public void openAfterScreen() throws IOException{
        Parent second = FXMLLoader.load(getClass().getResource("/Users/AfterForByTop.fxml"));
        byTopPane.getChildren().removeAll();
        byTopPane.getChildren().setAll(second);
    }
}
