/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package material_drawer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 *
 * @author Oliver
 */
public class MaterialDrawer extends VBox {

    /**
     * The entries for the drawer.
     * <p>
     * Their index represents their order from top to bottom in the drawer
     * entries.
     */
    private Map<Integer, IMenuAddable> entries = new HashMap(10);

    /**
     * The total width of the drawer.
     */
    private double totalWidth = 200;

    /**
     * The visible width of the drawer when unfocused.
     */
    private double minimizedWidth = 60;

    /**
     * The time it takes to do a transition in milliseconds.
     * <p>
     * See
     * {@link javafx.animation.TranslateTransition#TranslateTransition(Duration, Node)}
     */
    private double transitionTime = 500;

    private TranslateTransition translateTransition;

    private GridPane gridPane;

    private int numRows = 10;

    public MaterialDrawer() {
        super();
        this.setPrefWidth(totalWidth);
        this.setLayoutX(minimizedWidth - totalWidth);
        this.setLayoutY(0);

        //Size
        this.parentProperty().addListener((a1, b2, c3) -> {
            this.prefHeightProperty().bind(((Pane) c3).heightProperty());
        });

        //Animation
        translateTransition = new TranslateTransition(Duration.millis(transitionTime), this);
        translateTransition.setFromX(0);
        translateTransition.setToX(totalWidth - minimizedWidth);
        this.setOnMouseEntered(evt -> {
            translateTransition.setRate(1);
            translateTransition.play();
        });
        this.setOnMouseExited(evt -> {
            translateTransition.setRate(-1);
            translateTransition.play();
        });

        //Default styling
        this.setStyle("-fx-background-color: slateblue");

        populateDrawerMenu();
    }

    private void populateDrawerMenu(IMenuAddable... entries) {
        gridPane = new GridPane();

        gridPane.getColumnConstraints().add(new ColumnConstraints(totalWidth - minimizedWidth));
        gridPane.getColumnConstraints().add(new ColumnConstraints(minimizedWidth));

        for (int i = 0; i < numRows; i++) {
            RowConstraints rowContraints = new RowConstraints();
            rowContraints.setPercentHeight(100.0 / numRows);
            gridPane.getRowConstraints().add(rowContraints);
        }

        for (IMenuAddable entry : entries) {
            int row = Arrays.asList(entries).indexOf(entry);
            gridPane.add(entry.getLabelNode(), 0, row);
            gridPane.add(entry.getIconNode(), 1, row);
        }
    }
}
