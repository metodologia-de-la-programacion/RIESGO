package TestingUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class GameController {

    GameContainer container;

    public void setContainer(GameContainer container) {
        this.container = container;
    }
    public GameContainer getContainer() {
        return container;
    }

    /**
     * Method for loading the view specified in the parameters.
     * @param view
     */
    protected void loadView(String view){
        try {
            getContainer().setBundle(ResourceBundle.getBundle("riesgoBundle", getContainer().getLocale()));
            FXMLLoader loader = new FXMLLoader(getClass().getResource(view), getContainer().getBundle());
            Parent root = loader.load();
            Scene scene = new Scene(root);
            getContainer().setFxmlLoader(loader);
            if(getContainer().getFxmlLoader().getController() instanceof GameController){
                ((GameController)getContainer().getFxmlLoader().getController()).setContainer(getContainer());
            }
            Stage appStage = getContainer().getStage();
            appStage.setScene(scene);
            appStage.toFront();
            appStage.show();
            if(getContainer().getFxmlLoader().getController() instanceof GameController){
                ((GameController)getContainer().getFxmlLoader().getController()).onLoad();
            }

        } catch (Exception e) {
            System.out.println("Error de carga de " + view);
        }
    }

    public void onLoad(){

    }

}
