package cerberus.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BaseController implements Initializable {
    // drawer
    @FXML JFXHamburger menuBurger;
    @FXML AnchorPane drawerAnchor;
    @FXML JFXDrawersStack drawerStack;
    private JFXDrawer drawer;

    @FXML JFXTabPane menuTab;
    @FXML Tab newTab;
    @FXML Tab calenderTab;
    @FXML Tab chartTab;
    @FXML Tab settingsTab;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initDrawer();
    }

    private void initDrawer() {
        drawer = new JFXDrawer();
        drawer.setDefaultDrawerSize(220);
        drawerStack.toggle(drawer, false);

        // Load and set the side bar content
        try {
            AnchorPane box = FXMLLoader.load(getClass().getResource("/fxml/drawer.fxml"));
            drawer.setSidePane(box);

            for (Node node: ((VBox) box.getChildren().get(0)).getChildren()) {
                Node button = ((AnchorPane) node).getChildren().get(0);
                if (button.getAccessibleText() != null) {
                    button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        switch (button.getAccessibleText()) {
                            case "book": {
                                menuTab.getSelectionModel().select(newTab);
                                break;
                            }
                            case "calender": {
                                menuTab.getSelectionModel().select(calenderTab);
                                break;
                            }
                            case "chart": {
                                menuTab.getSelectionModel().select(chartTab);
                                break;
                            }
                            case "settings": {
                                menuTab.getSelectionModel().select(settingsTab);
                                break;
                            }
                            default:
                                break;
                        }

                        drawerStack.toggle(drawer, false);

                    });
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        HamburgerBackArrowBasicTransition burgerTask = new HamburgerBackArrowBasicTransition(menuBurger);
        burgerTask.setRate(-1);
        menuBurger.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            drawerStack.toggle(drawer);
        });

        drawer.setOnDrawerClosing(event -> {
            burgerTask.setRate(burgerTask.getRate() * -1);
            drawerAnchor.setMouseTransparent(true);
            drawerStack.setMouseTransparent(true);
            burgerTask.play();
        });

        drawer.setOnDrawerOpening(event -> {
            burgerTask.setRate(burgerTask.getRate() * -1);
            drawerAnchor.setMouseTransparent(false);
            drawerStack.setMouseTransparent(false);
            burgerTask.play();
        });
    }
}
