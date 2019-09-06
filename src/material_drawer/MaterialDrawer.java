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
    private double transitionTime;

    private TranslateTransition translateTransition;

    private GridPane gridPane;

    private final int numColumns = 2;
    private int numRows = 10;

    public MaterialDrawer() {
        //Size
        if ((this.getParent() instanceof Pane)) {
            ((Pane) this.getParent()).heightProperty().addListener((a, b, c) -> this.setHeight(c.doubleValue()));
        }
        this.setWidth(totalWidth);

        //Animation
        translateTransition = new TranslateTransition(Duration.millis(transitionTime), this);
        translateTransition.setFromX(minimizedWidth - totalWidth);
        translateTransition.setToX(0);
        this.setOnMouseEntered(evt -> {
            translateTransition.setRate(1);
            translateTransition.play();
        });
        this.setOnMouseExited(evt -> {
            translateTransition.setRate(-1);
            translateTransition.play();
        });

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
