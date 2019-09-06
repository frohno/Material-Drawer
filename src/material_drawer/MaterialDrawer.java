/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package material_drawer;

import javafx.animation.TranslateTransition;
import javafx.css.*;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.util.*;

/**
 *
 * @author Oliver
 */
@SuppressWarnings({"DanglingJavadoc", "WeakerAccess"})
public class MaterialDrawer extends VBox {

    /**
     * The entries for the drawer.
     * <p>
     * Their index represents their order from top to bottom in the drawer
     * entries.
     */
    @SuppressWarnings("unchecked" )
    private Map<Integer, IMenuAddable> entries = new HashMap(10);

    private TranslateTransition translateTransition;

    private GridPane gridPane;

    public MaterialDrawer() {
        super();
        this.setPrefWidth(totalWidth.intValue());
        this.setLayoutX(minimizedWidth.intValue() - totalWidth.intValue());
        this.setLayoutY(0);

        //Size
        this.parentProperty().addListener((a1, b2, c3) -> this.prefHeightProperty().bind(((Pane) c3).heightProperty()));

        //Animation
        translateTransition = new TranslateTransition(Duration.millis(transitionTime.doubleValue()), this);
        translateTransition.setFromX(0);
        translateTransition.setToX(totalWidth.intValue() - minimizedWidth.intValue());
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

        gridPane.getColumnConstraints().add(new ColumnConstraints(totalWidth.intValue() - minimizedWidth.intValue()));
        gridPane.getColumnConstraints().add(new ColumnConstraints(minimizedWidth.intValue()));

        for (int i = 0; i < numRows.intValue(); i++) {
            RowConstraints rowContraints = new RowConstraints();
            rowContraints.setPercentHeight(100.0 / numRows.intValue());
            gridPane.getRowConstraints().add(rowContraints);
        }

        for (IMenuAddable entry : entries) {
            int row = Arrays.asList(entries).indexOf(entry);
            gridPane.add(entry.getLabelNode(), 0, row);
            gridPane.add(entry.getIconNode(), 1, row);
        }
    }

    /***************************************************************************
     *                                                                         *
     * styleable Properties                                                    *
     *                                                                         *
     **************************************************************************/
    /**
     * Width when the drawer is extended
     */
    private final StyleableIntegerProperty totalWidth = new SimpleStyleableIntegerProperty(StyleableProperties.TOTAL_WIDTH);

    public final StyleableIntegerProperty totalWidthProperty() {
        return totalWidth;
    }

    public final void setTotalWidth(int width) {
        this.totalWidth.set(width);
    }


    /**
     * Width when the drawer is retracted
     */
    private final StyleableIntegerProperty minimizedWidth = new SimpleStyleableIntegerProperty(StyleableProperties.MINIMIZED_WIDTH);

    public final StyleableIntegerProperty minimizedWidthProperty() {
        return minimizedWidth;
    }

    public final void setminimizedWidth(int width) {
        this.minimizedWidth.set(width);
    }

    /**
     * Animation time in milliseconds
     */
    private final StyleableDoubleProperty transitionTime = new SimpleStyleableDoubleProperty(StyleableProperties.TRANSITION_TIME);

    public final StyleableDoubleProperty transitionTimeProperty() {
        return transitionTime;
    }

    public final void settransitionTime(int time) {
        this.transitionTime.set(time);
    }

    /**
     * The number of rows in the drawer
     */
    private final StyleableIntegerProperty numRows = new SimpleStyleableIntegerProperty(StyleableProperties.NUM_ROWS);

    /**
     * The CssMetaData for the stylable properties
     */
    private static class StyleableProperties {
        private static final CssMetaData<MaterialDrawer, Number> TOTAL_WIDTH = new CssMetaData<MaterialDrawer, Number>("-ffx-total-width", StyleConverter.getSizeConverter(), 200) {
            @Override
            public boolean isSettable(MaterialDrawer styleable) {
                return styleable.totalWidth == null || !styleable.totalWidth.isBound();
            }

            @Override
            public StyleableIntegerProperty getStyleableProperty(MaterialDrawer styleable) {
                return styleable.totalWidthProperty();
            }
        };
        private static final CssMetaData<MaterialDrawer, Number> MINIMIZED_WIDTH = new CssMetaData<MaterialDrawer, Number>("-ffx-minimized-width", StyleConverter.getSizeConverter(), 60) {
            @Override
            public boolean isSettable(MaterialDrawer styleable) {
                return styleable.minimizedWidth == null || !styleable.minimizedWidth.isBound();
            }

            @Override
            public StyleableProperty<Number> getStyleableProperty(MaterialDrawer styleable) {
                return styleable.minimizedWidth;
            }
        };
        private static final CssMetaData<MaterialDrawer, Number> TRANSITION_TIME = new CssMetaData<MaterialDrawer, Number>("-ffx-transition-time", StyleConverter.getSizeConverter(), 500d) {

            @Override
            public boolean isSettable(MaterialDrawer styleable) {
                return styleable.transitionTime == null || !styleable.transitionTime.isBound();
            }

            @Override
            public StyleableDoubleProperty getStyleableProperty(MaterialDrawer styleable) {
                return styleable.transitionTime;
            }
        };
        private static final CssMetaData<MaterialDrawer, Number> NUM_ROWS = new CssMetaData<MaterialDrawer, Number>("-ffx-num-width", StyleConverter.getSizeConverter(), 10) {
            @Override
            public boolean isSettable(MaterialDrawer styleable) {
                return styleable.numRows == null || !styleable.numRows.isBound();
            }

            @Override
            public StyleableProperty<Number> getStyleableProperty(MaterialDrawer styleable) {
                return styleable.numRows;
            }
        };

        private static final List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;

        static {
            final List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(VBox.getClassCssMetaData());
            Collections.addAll(styleables, TOTAL_WIDTH, MINIMIZED_WIDTH, TRANSITION_TIME, NUM_ROWS);
            CHILD_STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return StyleableProperties.CHILD_STYLEABLES;
    }
}


