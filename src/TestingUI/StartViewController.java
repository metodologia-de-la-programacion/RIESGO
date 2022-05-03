package TestingUI;

import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

public class StartViewController {

    @FXML
    private void exitGame() {
        System.exit(0);
    }
    @FXML
    private void changeColorEnter2(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        button.setStyle("-fx-background-color: #E19C1B");
    }
    @FXML
    private void changeColorOut2(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        button.setStyle("-fx-background-color: #FFFFFF");
    }
    @FXML
    private void play() {
        onlinePane.setVisible(true);
    }

    @FXML
    private void localButtonAction(MouseEvent event){
        onlinePane.setVisible(false);
        try {
            Parent root;
            ResourceBundle bundle =  ResourceBundle.getBundle("riesgoBundle", MainUI.locale);
            root = FXMLLoader.load(getClass().getResource("customize-view.fxml"), bundle);
            Scene scene = new Scene(root);
            Stage appStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.toFront();
            appStage.show();
        } catch (Exception e) {
        }
    }
    @FXML
    private void onlineButtonAction(){
        onlinePane.setVisible(false);
        rolPane.setVisible(true);
    }
    @FXML
    private void hostButtonAction(){
        rolPane.setVisible(false);
        //¿QUÉ MÁS?
    }
    @FXML
    private void guestButtonAction(){
        rolPane.setVisible(false);
        ipPane.setVisible(true);
    }
    @FXML
    private void sendButtonAction(){
        ip = ipField.getCharacters().toString();
        System.out.println(ip);
    }
    @FXML
    private void returnGame() {
        onlinePane.setVisible(false);
        rolPane.setVisible(false);
        ipPane.setVisible(false);
    }
    @FXML
    private void imageOut(MouseEvent mouseEvent) {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        if (imageView.getId().equals("settings")) {
            File file = new File("Resources/TestingUI/Images/settingsLogoOn.jpg");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } else if (imageView.getId().equals("exit")) {
            File file = new File("Resources/TestingUI/Images/exit.jpg");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } else if (imageView.getId().equals("play")) {
            File file = new File("Resources/TestingUI/Images/play2.jpg");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } else if (imageView.getId().equals("atras")) {
            File file = new File("Resources/TestingUI/Images/Flecha_atras2.png");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        }
    }
    @FXML
    private void imageIn(MouseEvent mouseEvent) throws FileNotFoundException {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        if (imageView.getId().equals("settings")) {
            File file = new File("Resources/TestingUI/Images/settingsLogo.jpg");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } else if (imageView.getId().equals("exit")) {
            File file = new File("Resources/TestingUI/Images/exit2.jpg");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } else if (imageView.getId().equals("play")) {
            File file = new File("Resources/TestingUI/Images/play.jpg");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } else if (imageView.getId().equals("atras")) {
            File file = new File("Resources/TestingUI/Images/Flecha_atras1.png");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        }
    }
    @FXML
    private void settings(MouseEvent event) {
        try {
            Parent root;
            Scene scene;
            root = FXMLLoader.load(getClass().getResource("settings-view.fxml"));
            scene = new Scene(root, 1280, 720);
            Stage appStage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.toFront();
            appStage.show();
        } catch (Exception e) {
        }
    }
    @FXML
    private void credits(MouseEvent event) {
        try {
            Parent root;
            Scene scene;
            Button button = (Button) event.getSource();
            root = FXMLLoader.load(getClass().getResource("credits-view.fxml"));
            scene = new Scene(root, 1280, 720);
            Stage appStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.toFront();
            appStage.show();
        } catch (Exception e) {
        }
    }
    @FXML
    private void howToPlay() {
        try {
            Desktop.getDesktop().browse(new URI("https://www.hasbro.com/common/documents/dad2886d1c4311ddbd0b0800200c9a66/ADE84A6E50569047F504839559C5FEBF.pdf"));
        } catch (URISyntaxException | IOException ex) {
            System.out.println(ex);
        }
    }
    @FXML
    private void back(MouseEvent event) {
        try {
            Parent root;
            ImageView imageView = (ImageView) event.getSource();
            ResourceBundle bundle =  ResourceBundle.getBundle("riesgoBundle", MainUI.locale);
            root = FXMLLoader.load(getClass().getResource("start-view.fxml"),bundle);
            Scene scene = new Scene(root);
            Stage appStage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.toFront();
            appStage.show();

        } catch (Exception e) {
        }
    }
    @FXML
    private void setEnglish() {
        MainUI.locale = new Locale("en","UK");
    }
    @FXML
    private void setSpanish() {
        MainUI.locale = new Locale("es","ES");
    }
}