/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package material_drawer;

import javafx.scene.Node;
import javafx.scene.control.Button;

/**
 *
 * @author Oliver
 */
public class MenuEntry implements IMenuAddable {

    private Button labelButton;
    private Button iconButton;
    private Runnable onClickRunnable;
    private String labelString;
    private Node iconGraphicsNode;

    public MenuEntry(Runnable onClickRunnable, String labelString, Node iconGraphicsNode) {
        this.onClickRunnable = onClickRunnable;
        this.labelString = labelString;
        this.iconGraphicsNode = iconGraphicsNode;

        this.labelButton = new Button(labelString);
        this.iconButton = new Button("", iconGraphicsNode);
        labelButton.setPrefSize(20, 20);
        labelButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        iconButton.setPrefSize(20, 20);
        iconButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        this.labelButton.setOnAction((e) -> onClickRunnable.run());
        this.iconButton.setOnAction((e) -> onClickRunnable.run());

        //Default size (@TODO DO CSS WIZARDRY HERE FRHOU18)
        labelButton.setStyle("-fx-font-size: 25; -fx-font-color: white");
    }

    @Override
    public Node getLabelNode() {
        return this.labelButton;
    }

    @Override
    public Node getIconNode() {
        return this.iconButton;
    }

    public Runnable getOnClickRunnable() {
        return this.onClickRunnable;
    }

    public void setOnClickRunnable(Runnable onClickRunnable) {
        this.onClickRunnable = onClickRunnable;
        this.labelButton.setOnAction((e) -> onClickRunnable.run());
        this.iconButton.setOnAction((e) -> onClickRunnable.run());
    }

    public String getLabelString() {
        return this.labelString;
    }

    public void setLabelString(String labelString) {
        this.labelString = labelString;
        this.labelButton.setText(labelString);
    }

    public Node getIconGraphicsNode() {
        return this.iconGraphicsNode;
    }

    public void setIconGraphicsNode(Node iconGraphicsNode) {
        this.iconGraphicsNode = iconGraphicsNode;
        this.iconButton.setGraphic(iconGraphicsNode);
    }

}
