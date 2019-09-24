/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package material_drawer;

import java.util.*;
import javafx.animation.TranslateTransition;
import javafx.css.*;
import javafx.scene.layout.*;
import javafx.util.Duration;

/**
 *
 * @author Oliver & Frederik
 */
@SuppressWarnings({"DanglingJavadoc", "WeakerAccess"})
public class MaterialDrawer extends VBox {

    /**
     * The entries for the drawer.
     * <p>
     * Their index represents their order from top to bottom in the drawer
     * entries.
     */
    @SuppressWarnings("unchecked")
    private Map<Integer, IMenuAddable> entries = new HashMap(10);

    private TranslateTransition translateTransition;

    private GridPane gridPane = new GridPane();

    /**
     * Initialize the drawer with default parameters totalWidth: 200
     * minimizedWidth: 60 transitionTime: 500
     *
     * @param entries
     */
    public MaterialDrawer(IMenuAddable... entries) {
        this(60, 200, 500, entries);
    }

    /**
     * Initialize the drawer with custom parameters
     *
     * @param minimizedWidth is the width when the drawer is minimized
     * @param totalWidth is the width when the drawer is fully extended
     * @param transitionTime is the transition time of the animations of the
     * drawer
     * @param entries
     */
    public MaterialDrawer(int minimizedWidth, int totalWidth, double transitionTime, IMenuAddable... entries) {
        super();

        this.minimizedWidth.set(minimizedWidth);
        this.totalWidth.set(totalWidth);
        this.transitionTime.set(transitionTime);
        this.numRows.set(10);

        initialize();
        populateDrawerMenu(entries);
    }

    private void initialize() {
        //Size
        this.setPrefWidth(this.totalWidth.intValue());

        //Location
        this.setLayoutY(0);
        this.setLayoutX(this.minimizedWidth.intValue() - this.totalWidth.intValue());

        //Height
        this.parentProperty().addListener((a1, b2, c3) -> {
            this.prefHeightProperty().bind(((Pane) c3).heightProperty());
        });

        this.heightProperty().addListener((a, b, c) -> {
            gridPane.setPrefHeight(getHeight());
            gridPane.resize(getWidth(), getHeight());
        });

        //Animation
        translateTransition = new TranslateTransition(Duration.millis(this.transitionTime.doubleValue()), this);

        translateTransition.setFromX(0);
        translateTransition.setToX(this.totalWidth.intValue() - this.minimizedWidth.intValue());

        this.setOnMouseEntered(evt -> {
            translateTransition.setRate(1);
            translateTransition.play();
        });

        this.setOnMouseExited(evt -> {
            translateTransition.setRate(-1);
            translateTransition.play();
        });

        //Default styling
        this.setStyle("-fx-background-color: slateblue; -fx-effect: dropshadow(three-pass-box, black, 10, 0, 0, 0);");

    }

    private void populateDrawerMenu(IMenuAddable... entries) {
        gridPane = new GridPane();
        gridPane.setPrefWidth(totalWidth.intValue());
        gridPane.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        gridPane.getColumnConstraints().add(new ColumnConstraints(totalWidth.intValue() - minimizedWidth.intValue()));
        gridPane.getColumnConstraints().add(new ColumnConstraints(minimizedWidth.intValue()));

        for (IMenuAddable entry : entries) {
            RowConstraints rowContraints = new RowConstraints();
            rowContraints.setPercentHeight(100.0 / numRows.intValue());
            gridPane.getRowConstraints().add(rowContraints);
            int row = Arrays.asList(entries).indexOf(entry);

            gridPane.add(entry.getLabelNode(), 0, row);
            GridPane.setFillWidth(entry.getLabelNode(), true);
            GridPane.setFillHeight(entry.getLabelNode(), true);
            gridPane.add(entry.getIconNode(), 1, row);
            GridPane.setFillWidth(entry.getIconNode(), true);
            GridPane.setFillHeight(entry.getIconNode(), true);
        }
        this.getChildren().add(gridPane);
    }

    /**
     * *************************************************************************
     *                                                                         *
     * Styleable Properties * *
     * ************************************************************************
     */
    /**
     * Width when the drawer is extended
     */
    private final StyleableIntegerProperty totalWidth = new SimpleStyleableIntegerProperty(StyleableProperties.TOTAL_WIDTH);

    /**
     * This is the width when the drawer is fully extended Basically a getter
     *
     * @return a stylable property dictating the width of the drawer
     */
    public final StyleableIntegerProperty totalWidthProperty() {
        return totalWidth;
    }

    /**
     * This is the width when the drawer is fully extended
     */
    public final void setTotalWidth(int width) {
        this.totalWidth.set(width);
        /**
         * Update animation
         */
        this.setLayoutX(this.minimizedWidth.intValue() - this.totalWidth.intValue());
        translateTransition.setToX(this.totalWidth.intValue() - this.minimizedWidth.intValue());
        //TODO Add support for updating the gridpane
    }

    /**
     * Width when the drawer is retracted
     */
    private final StyleableIntegerProperty minimizedWidth = new SimpleStyleableIntegerProperty(StyleableProperties.MINIMIZED_WIDTH);

    /**
     * This is the width when the drawer is minimized Basically a getter
     *
     * @return an integer property dictating the width of the drawer when
     * retracted
     */
    public final StyleableIntegerProperty minimizedWidthProperty() {
        return minimizedWidth;
    }

    public final void setMinimizedWidth(int width) {
        this.minimizedWidth.set(width);
    }

    /**
     * Animation time in milliseconds
     */
    private final StyleableDoubleProperty transitionTime = new SimpleStyleableDoubleProperty(StyleableProperties.TRANSITION_TIME);

    public final StyleableDoubleProperty transitionTimeProperty() {
        return transitionTime;
    }

    public final void setTransitionTime(int time) {
        this.transitionTime.set(time);
    }

    /**
     * The number of rows in the drawer
     */
    private final StyleableIntegerProperty numRows = new SimpleStyleableIntegerProperty(StyleableProperties.NUM_ROWS);

    public final StyleableIntegerProperty numRowsProperty() {
        return numRows;
    }

    public final void setNumRows(int rows) {
        this.numRows.set(rows);

    }

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
