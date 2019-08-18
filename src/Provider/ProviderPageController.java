
package Provider;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


public class ProviderPageController implements Initializable {

    @FXML
    private ImageView compLogo;
    @FXML
    private Button logOut;
    @FXML
    private Pane userDataView;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rotator();
    }    

    @FXML
    private void changeUserPass(MouseEvent event) throws IOException {
        Parent second = FXMLLoader.load(getClass().getResource("ChangePassw.fxml"));
        userDataView.getChildren().removeAll();
        userDataView.getChildren().setAll(second);
    }

    @FXML
    private void addFoodItem(MouseEvent event) throws IOException {
        Parent second = FXMLLoader.load(getClass().getResource("/Provider/addItem.fxml"));
        userDataView.getChildren().removeAll();
        userDataView.getChildren().setAll(second);
    }

    @FXML
    private void removeFoodItem(MouseEvent event) throws IOException {
        Parent second = FXMLLoader.load(getClass().getResource("/Provider/removeFood.fxml"));
        userDataView.getChildren().removeAll();
        userDataView.getChildren().setAll(second);
        
    }

    @FXML
    private void userLogOut(MouseEvent event) throws IOException {
        Stage stagee = (Stage) logOut.getScene().getWindow();
        stagee.close();
        
        Parent fxml = FXMLLoader.load(getClass().getResource("/main/MainUI.fxml"));
        
        Scene scene = new Scene(fxml);
        scene.getStylesheets().add("/main/Formatter.css");
        
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    @FXML
    private void closeUserWindow(MouseEvent event) {
        System.exit(0);
    }
    public void rotator(){
        RotateTransition rt = new RotateTransition(Duration.millis(3000), compLogo);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
    } 

    @FXML
    private void myCustomers(MouseEvent event) throws IOException {
        Parent second = FXMLLoader.load(getClass().getResource("/Provider/MyCustomers.fxml"));
        userDataView.getChildren().removeAll();
        userDataView.getChildren().setAll(second);
    }

    @FXML
    private void myComments(MouseEvent event) throws IOException {
        Parent second = FXMLLoader.load(getClass().getResource("/Provider/MyFoodsComments.fxml"));
        userDataView.getChildren().removeAll();
        userDataView.getChildren().setAll(second);
    }

    @FXML
    private void menuFoodAdd(ActionEvent event) throws IOException {
        Parent second = FXMLLoader.load(getClass().getResource("/Provider/addItem.fxml"));
        userDataView.getChildren().removeAll();
        userDataView.getChildren().setAll(second);
    }

    @FXML
    private void menuMyComments(ActionEvent event) throws IOException {
        Parent second = FXMLLoader.load(getClass().getResource("/Provider/MyFoodsComments.fxml"));
        userDataView.getChildren().removeAll();
        userDataView.getChildren().setAll(second);
    }

    @FXML
    private void menuMyCust(ActionEvent event) throws IOException {
        Parent second = FXMLLoader.load(getClass().getResource("/Provider/MyCustomers.fxml"));
        userDataView.getChildren().removeAll();
        userDataView.getChildren().setAll(second);
    }

    @FXML
    private void menuRemoveFood(ActionEvent event) throws IOException {
        Parent second = FXMLLoader.load(getClass().getResource("/Provider/removeFood.fxml"));
        userDataView.getChildren().removeAll();
        userDataView.getChildren().setAll(second);
    }

    @FXML
    private void aboutUs(ActionEvent event) throws IOException {
        Parent second = FXMLLoader.load(getClass().getResource("/Provider/AboutUs.fxml"));
        userDataView.getChildren().removeAll();
        userDataView.getChildren().setAll(second);
    }

    @FXML
    private void policy(ActionEvent event) throws IOException {
        Parent second = FXMLLoader.load(getClass().getResource("/Provider/PrivacyPolicy.fxml"));
        userDataView.getChildren().removeAll();
        userDataView.getChildren().setAll(second);
    }

}
