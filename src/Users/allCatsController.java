
package Users;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import main.NameGetter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;


public class allCatsController implements Initializable {

    
    FoodRecGetter frg;
    @FXML
    private TableView<FoodTableModel> byCatTable;
//    private TableView<FoodTableModel> byNameTable;
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

    
    
    ObservableList<FoodTableModel> nameSList=FXCollections.observableArrayList();
    
    DataInputStream din;
    DataOutputStream dout;
    
    @FXML
    private TextField status;
//    private Button buyButt;
    private AnchorPane buyingPane;
    
    static String prov;
    public  String food;
    public String cat;
    public  UserPageController upc=new UserPageController();
    public  String customer=upc.userFinder();
    @FXML
    private AnchorPane byTopPane;
    
    @FXML
    private Button byTopButt;
   
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        col_food.setCellValueFactory(new PropertyValueFactory<>("foodName"));
        col_pro.setCellValueFactory(new PropertyValueFactory<>("provider"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        col_available.setCellValueFactory(new PropertyValueFactory<>("available"));
        
        
//        byNameTable.getItems().clear();
         try {
            
            Socket socket = new Socket("127.0.0.1",19111);
            
             
            frg=new FoodRecGetter();
            food=frg.food_name_getter();
            cat=frg.food_cat_getter();
            
            
//            System.out.println(food);
            
            dout=new DataOutputStream(socket.getOutputStream());
            dout.writeUTF("All");
            
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            ArrayList<String> byNameFoods =new ArrayList<>() ;
            
            try {
                byNameFoods=(ArrayList<String>)ois.readObject();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MyHistoryController.class.getName()).log(Level.SEVERE, null, ex);

            }
            
            int ctr=0;
            while(ctr<byNameFoods.size()){
                
                String s=byNameFoods.get(ctr);
                String[] splitted=s.split("~");
                nameSList.add(new FoodTableModel(splitted[0],splitted[1],Integer.parseInt(splitted[2]),splitted[3],Float.parseFloat(splitted[4]),Integer.parseInt(splitted[5])));
                ctr+=1;
                
            }
            
            System.out.println(ctr);
            
        } catch (IOException ex) {
        }
        
        byCatTable.setItems(nameSList);
        byTopButt.disableProperty().bind(Bindings.isEmpty(byCatTable.getSelectionModel().getSelectedItems()));

    }    

    
    @FXML
    private void runBuy(MouseEvent event) {
        try{
            
            FoodTableModel fmodel=byCatTable.getSelectionModel().getSelectedItems().get(0);
            prov=fmodel.provider;

            Socket soc=new Socket("127.0.0.1",12080);
            
            dout = new DataOutputStream(soc.getOutputStream());
            din=new DataInputStream(soc.getInputStream());
            
            String dat=food+"~"+prov+"~"+customer;
            dout.writeUTF(dat);
            
            String nextStepsChecker=din.readUTF();
            if(nextStepsChecker.equalsIgnoreCase("Buying Request Successful")){
                openAfterScreen();
            }else{
                status.setText("Buying Request Unsuccessful");
            }
        }catch(IOException e){
                
        }
        
    }
    
    public void openAfterScreen() throws IOException{
        Parent second = FXMLLoader.load(getClass().getResource("/Users/AfterBuying.fxml"));
        buyingPane.getChildren().removeAll();
        buyingPane.getChildren().setAll(second);
    }


    
}    
    
    
    
    

