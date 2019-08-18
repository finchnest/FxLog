
package Provider;


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
import main.NameGetter;


public class RemoveFoodController implements Initializable {

    NameGetter ngetter;
    
    @FXML
    private TableColumn<FoodModel, String> foodItemName;
    @FXML
    private TableColumn<FoodModel, Integer> foodItemPrice;
    @FXML
    private TableColumn<FoodModel, String> foodItemType;
    @FXML
    private TableColumn<FoodModel, Float> foodItemRating;
    @FXML
    private TableColumn<FoodModel, Integer> foodItemCount;
    @FXML
    private TableView<FoodModel> foodTable;
    @FXML
    private TableColumn<FoodModel, Integer> foodItemRatersCount;


    ObservableList<FoodModel> foodList=FXCollections.observableArrayList();

    DataInputStream din;
    DataOutputStream dout;
    @FXML
    private Button finalRemove;
    public String provider;
    @FXML
    public TextField foodDelInfo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        foodItemName.setCellValueFactory(new PropertyValueFactory<>("foodN"));
        foodItemPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        foodItemType.setCellValueFactory(new PropertyValueFactory<>("foodT"));
        foodItemRatersCount.setCellValueFactory(new PropertyValueFactory<>("ratersCount"));
        foodItemCount.setCellValueFactory(new PropertyValueFactory<>("foodCount"));
        foodItemRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        
        
        try {
            
            Socket socket = new Socket("127.0.0.1",11113);

            ngetter=new NameGetter();
            provider=ngetter.getter();
            
            dout= new DataOutputStream(socket.getOutputStream());
            dout.writeUTF(provider);
            
            
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            ArrayList<String> foods =new ArrayList<>() ;
            
            try {
                foods=(ArrayList<String>)ois.readObject();
            } catch (ClassNotFoundException ex) {
            }
            
            int ctr=0;
            while(ctr<foods.size()){
                
                String s=foods.get(ctr);
                String[] splitted=s.split("~");
                foodList.add(new FoodModel(splitted[0],Integer.parseInt(splitted[1]),splitted[2],Float.parseFloat(splitted[3]),Integer.parseInt(splitted[4]),Integer.parseInt(splitted[5])));
                ctr+=1;
                
            }
            
            System.out.println(ctr);
            
        } catch (IOException ex) {
        }
        
        foodTable.setItems(foodList);
        
        finalRemove.disableProperty().bind(Bindings.isEmpty(foodTable.getSelectionModel().getSelectedItems()));
        
    }    

    public void remove(String provider, String food) throws IOException{
            
    }

    @FXML
    private void delCom(MouseEvent event) throws IOException {
        try{
            String f=foodTable.getSelectionModel().getSelectedItem().foodN;
            
            foodTable.getItems().removeAll(foodTable.getSelectionModel().getSelectedItems());
            
            
            
            Socket socket=new Socket("127.0.0.1",11115);
            dout = new DataOutputStream(socket.getOutputStream());
            din=new DataInputStream((socket.getInputStream()));
            
            String del=provider+"~"+f;
            dout.writeUTF(del);
            
            String me=din.readUTF();
            foodDelInfo.setText(me);
        }catch(Exception e){

        }
    }
    
}
