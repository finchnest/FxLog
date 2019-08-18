
package Admin;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


public class AdminPageController implements Initializable {

    @FXML
    private AnchorPane adminBack;
    @FXML
    private Pane adminMenuPane;
    @FXML
    private Button addCust;
    
    @FXML
    private Button viewUsers;
    @FXML
    private Button viewPros;
    @FXML
    private Button delPro;
    @FXML
    private Button addAd;
    @FXML
    private Button logOut;
    @FXML
    private Pane dataViewer;
    @FXML
    private FontAwesomeIconView end;
    @FXML
    private Button userPdf;
    @FXML
    private ImageView adminLogo;

    static String identifier;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rotator();
    }    

    @FXML
    private void registerationForm(MouseEvent event) throws IOException {
        Parent second = FXMLLoader.load(getClass().getResource("regUser.fxml"));
        dataViewer.getChildren().removeAll();
        dataViewer.getChildren().setAll(second);
    }


    @FXML
    private void end(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void availableUsers(MouseEvent event) throws IOException, ClassNotFoundException {
        Parent second = FXMLLoader.load(getClass().getResource("seeUser.fxml"));
        dataViewer.getChildren().removeAll();
        
        dataViewer.getChildren().setAll(second);
    }



    @FXML
    private void availableProviders(MouseEvent event) throws IOException {
        Parent second = FXMLLoader.load(getClass().getResource("seePro.fxml"));
        dataViewer.getChildren().removeAll();
        dataViewer.getChildren().setAll(second);
    }

    @FXML
    private void deletePro(MouseEvent event) throws IOException {
        Parent second = FXMLLoader.load(getClass().getResource("proRemover.fxml"));
        dataViewer.getChildren().removeAll();
        dataViewer.getChildren().setAll(second);
    }

    @FXML
    private void generatePdf(MouseEvent event) throws IOException {
        
        identifier="current";
        Parent second = FXMLLoader.load(getClass().getResource("/Admin/PDF.fxml"));
        dataViewer.getChildren().removeAll();
        dataViewer.getChildren().setAll(second);
    }
    
    public void rotator(){
        RotateTransition rt = new RotateTransition(Duration.millis(3000), adminLogo);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
    } 


    @FXML
    private void addPToData(MouseEvent event) throws IOException {
        Parent second = FXMLLoader.load(getClass().getResource("newT.fxml"));
        dataViewer.getChildren().removeAll();
        
        dataViewer.getChildren().setAll(second);
    }

    @FXML
    private void logout(MouseEvent event) throws IOException {

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
    private void allTimeUserReport(MouseEvent event) throws IOException {
        
        identifier="alltime";
        Parent second = FXMLLoader.load(getClass().getResource("/Admin/PDF.fxml"));
        dataViewer.getChildren().removeAll();
        dataViewer.getChildren().setAll(second);
    }

    @FXML
    private void addAdvert(MouseEvent event) throws IOException {
        Parent second = FXMLLoader.load(getClass().getResource("/Admin/addAdvertizement.fxml"));
        dataViewer.getChildren().removeAll();
        
        dataViewer.getChildren().setAll(second);
    }
    
    
}
