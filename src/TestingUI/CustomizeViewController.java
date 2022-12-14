package TestingUI;

import controller.controllers.net.ClientController;
import controller.editor.ReadingFiles;
import controller.game.ServerController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

/**
 * This class is used for controlling the customize-view.fxml
 */
public class CustomizeViewController extends GameController implements Initializable {
    @FXML
    private ComboBox<String> comboMaps, one, two, three, four, five, six;
    @FXML
    private VBox oneBox, twoBox, threeBox, fourBox, fiveBox, sixBox;
    @FXML
    private TextField name1, name2, name3, name4, name5, name6;
    @FXML
    private Label errorLabel;
    @FXML
    private Pane errorPane;
    private ResourceBundle resources;

    private int numberOfPlayers;

    private String[] maps = new String[2];
    private String[] strategies = new String[6];

    /**
     * Initialize comboBox from the view.fxml
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The ResourceBundle for the translator
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        maps[0] = resources.getString("world");
        maps[1] = resources.getString("europe");

        strategies[0] = resources.getString("noneTag");
        strategies[1] = resources.getString("Human");
        strategies[2] = resources.getString("Benevolent");
        strategies[3] = resources.getString("Aggressive");
        strategies[4] = resources.getString("Random");
        strategies[5] = resources.getString("Cheater");

        comboMaps.getItems().addAll(maps);

        one.getItems().addAll(strategies);
        two.getItems().addAll(strategies);
        three.getItems().addAll(strategies);
        four.getItems().addAll(strategies);
        five.getItems().addAll(strategies);
        six.getItems().addAll(strategies);

        //twoBox.setDisable(true);
        threeBox.setDisable(true);
        fourBox.setDisable(true);
        fiveBox.setDisable(true);
        sixBox.setDisable(true);
    }

    /**
     * Enables or disables player selection comboBoxes and name labels.
     */
    @FXML
    private void playerSelected() {
        twoBox.setDisable(Objects.equals(one.getValue(), resources.getString("noneTag")));
        threeBox.setDisable(Objects.equals(two.getValue(), resources.getString("noneTag")) || twoBox.isDisable());
        fourBox.setDisable(Objects.equals(three.getValue(), resources.getString("noneTag")) || threeBox.isDisable());
        fiveBox.setDisable(Objects.equals(four.getValue(), resources.getString("noneTag")) || fourBox.isDisable());
        sixBox.setDisable(Objects.equals(five.getValue(), resources.getString("noneTag")) || fiveBox.isDisable());
    }

    /**
     * Sets the number of players and their strategies.
     */
    private void setPlayers() {
        ReadingFiles.playerNames = new ArrayList<>();
        numberOfPlayers = 0;
        if (!Objects.equals(one.getValue(), resources.getString("noneTag"))) {
            numberOfPlayers++;
            ReadingFiles.strategy_selected.add(one.getValue());
            if(name1.getText()!=null) {
                ReadingFiles.playerNames.add(name1.getText());
            }else{
                ReadingFiles.playerNames.add(resources.getString("player1Tag"));
            }
        }else{return;}
        if (!Objects.equals(two.getValue(), resources.getString("noneTag"))) {
            numberOfPlayers++;
            ReadingFiles.strategy_selected.add(two.getValue());
            if(name2.getText()!=null) {
                ReadingFiles.playerNames.add(name2.getText());
            }else{
                ReadingFiles.playerNames.add(resources.getString("player2Tag"));
            }
        }else{return;}
        if (!Objects.equals(three.getValue(), resources.getString("noneTag"))) {
            numberOfPlayers++;
            ReadingFiles.strategy_selected.add(three.getValue());
            if(name3.getText()!=null) {
                ReadingFiles.playerNames.add(name3.getText());
            }else{
                ReadingFiles.playerNames.add(resources.getString("player3Tag"));
            }
        }else{return;}
        if (!Objects.equals(four.getValue(), resources.getString("noneTag"))) {
            numberOfPlayers++;
            ReadingFiles.strategy_selected.add(four.getValue());
            if(name4.getText()!=null) {
                ReadingFiles.playerNames.add(name4.getText());
            }else{
                ReadingFiles.playerNames.add(resources.getString("player4Tag"));
            }
        }else{return;}
        if (!Objects.equals(five.getValue(), resources.getString("noneTag"))) {
            numberOfPlayers++;
            ReadingFiles.strategy_selected.add(five.getValue());
            if(name5.getText()!=null) {
                ReadingFiles.playerNames.add(name5.getText());
            }else{
                ReadingFiles.playerNames.add(resources.getString("player5Tag"));
            }
        }else{return;}
        if (!Objects.equals(six.getValue(), resources.getString("noneTag"))) {
            numberOfPlayers++;
            ReadingFiles.strategy_selected.add(six.getValue());
            if(name6.getText()!=null) {
                ReadingFiles.playerNames.add(name6.getText());
            }else{
                ReadingFiles.playerNames.add(resources.getString("player6Tag"));
            }
        }else{return;}
    }

    /**
     * Starts Game.
     * Control some errors and load the corresponding map and view.
     */
    @FXML
    private void game() {
        setPlayers();
        if (numberOfPlayers < 2) {
            errorLabel.setText(resources.getString("invalidNumOfPlayersTag"));
            errorPane.setVisible(true);
        } else if (comboMaps.getValue() == null) {
            errorLabel.setText(resources.getString("invalidMapTag"));
            errorPane.setVisible(true);
        } else {
            getContainer().setClientController(new ClientController());
            getContainer().setServerController(new ServerController());

            ReadingFiles.MapType = 4;
            ReadingFiles.mapName = "Resources/TestingUI/Maps/" + comboMaps.getValue() + ".map";
            ReadingFiles.mapCode = comboMaps.getValue();
            ReadingFiles.NumberOfPlayers = numberOfPlayers;

            try {
                getContainer().getServerController().Function();
            } catch (Exception e) {
                e.printStackTrace();
            }
            getContainer().getClientController().getPlayerConfiguration().setName(name1.getText());
            getContainer().getClientController().getClient().connect(getContainer().getIp());
            getContainer().getClientController().getClient().start();

            System.out.println("Esperando respuesta del server...");

            while (getContainer().getClientController().getServerBoard() == null) {}

            System.out.println("Respuesta recibida!");

            getContainer().getClientController().updatePlayer();

            loadView("map-view.fxml");
        }
    }

    /**
     * Method for changing the image while mouse is on it (view effect)
     * @param mouseEvent
     * @throws FileNotFoundException
     */
    @FXML
    private void imageIn(MouseEvent mouseEvent) throws FileNotFoundException {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        if (imageView.getId().equals("atras")) {
            File file = new File("Resources/TestingUI/Images/backPressed.png");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } else if (imageView.getId().equals("adelante")) {
            File file = new File("Resources/TestingUI/Images/nextPressed.png");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        }
    }

    /**
     * Method for changing the image when the mouse is out of it (view effect)
     * @param mouseEvent
     * @throws FileNotFoundException
     */
    @FXML
    private void imageOut(MouseEvent mouseEvent) throws FileNotFoundException {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        if (imageView.getId().equals("atras")) {
            File file = new File("Resources/TestingUI/Images/back.png");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } else if (imageView.getId().equals("adelante")) {
            File file = new File("Resources/TestingUI/Images/next.png");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        }
    }

    /**
     * Method for going back to the start-view.
     * @param event
     */
    @FXML
    private void back(MouseEvent event) {
        try{
            Parent root;
            ImageView image = (ImageView) event.getSource();
            loadView("start-view.fxml");
        } catch (Exception e) {
        }
    }

    /**
     * Method for closing Panes when clicking outside while being in a view.
     */
    @FXML
    private void returnGame() {
        errorPane.setVisible(false);
    }
}


