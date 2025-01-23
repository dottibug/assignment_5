package org.example.square;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    private final String RED = "#D54063";
    private final String GREEN = "#81B29A";
    private final String ORANGE = "#FFA97D";
    private final double MAX_SIZE = 180;
    private RadioButton redRadio, greenRadio, orangeRadio;
    private Rectangle square = new Rectangle();
    private Slider slider;

    @Override
    public void start(Stage primaryStage) {

        // Color palette
        String FLORAL_WHITE = "#FAF8EF";
        String ONYX = "#3E3E3E";

        // Text blocks
        StackPane instructions = new StackPane();
        StackPane warning = new StackPane();

        // Instructions
        String instructionString = "Change the square color using the radio buttons.\nChange the " +
                "scale of the square from 0-100% with the slider.";

        Text instructionText = createText(instructionString, ONYX);
        instructions.getChildren().add(instructionText);
        instructions.setAlignment(Pos.CENTER);

        // Warning
        String warningString = "Select the radio buttons or the slider only.\nA warning sound " +
                "will play if the mouse is clicked elsewhere.";

        Text warningText = createText(warningString, RED);
        warning.getChildren().add(warningText);
        warning.setAlignment(Pos.CENTER);

        // Radio buttons
        ToggleGroup colors = new ToggleGroup();

        redRadio = createStyledRadioButton("Red", colors, true);
        greenRadio = createStyledRadioButton("Green", colors, false);
        orangeRadio = createStyledRadioButton("Orange", colors, false);

        VBox colorOptions = new VBox(redRadio, greenRadio, orangeRadio);
        colorOptions.setAlignment(Pos.CENTER_LEFT);
        colorOptions.setSpacing(10);

        // Square styling
        square.setWidth(MAX_SIZE);
        square.setHeight(MAX_SIZE);
        square.setArcWidth(10);
        square.setArcHeight(10);
        square.setFill(Color.web(RED));

        // Horizontal layout for radio buttons and square
        HBox colorOptionsSquare = new HBox();
        colorOptionsSquare.getChildren().addAll(colorOptions, square);
        colorOptionsSquare.setAlignment(Pos.CENTER);
        colorOptionsSquare.setSpacing(100);

        // Slider
        slider = new Slider(0, 100, 75);
        slider.setOrientation(Orientation.HORIZONTAL);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(25);
        slider.setShowTickLabels(true);
        slider.setPrefSize(500, 40);
        HBox sliderContainer = new HBox(slider);
        sliderContainer.setAlignment(Pos.CENTER);

        // Bind slider value to square scale
        DoubleProperty sliderValue = slider.valueProperty();
        square.scaleXProperty().bind(sliderValue.divide(100));
        square.scaleYProperty().bind(sliderValue.divide(100));

//        // TEST
//        slider.setOnMouseDragged(event -> {
//            System.out.println(slider.valueProperty());
//        });

        // Vertical root
        VBox root = new VBox();
        root.getChildren().addAll(instructions, colorOptionsSquare, sliderContainer, warning);
        root.setSpacing(50);
        root.setPadding(new Insets(50));

        // Staging
        Scene scene = new Scene(root, Color.web(FLORAL_WHITE));
        primaryStage.setTitle("Changing Square");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }

    private Text createText (String string, String color) {
        Text text = new Text(string);
        text.setFill(Color.web(color));
        text.setFont(Font.font("Roboto", FontWeight.MEDIUM, FontPosture.REGULAR, 18));
        text.setTextAlignment(TextAlignment.CENTER);
        return text;
    }

    private RadioButton createStyledRadioButton(String text, ToggleGroup group, boolean selected) {
        RadioButton radio = new RadioButton(text);
        radio.setStyle("-fx-font-size: 18px; -fx-font-weight: 500;");
        radio.setToggleGroup(group);
        radio.setSelected(selected);
        radio.setOnAction(this::handleColorRadioButtonAction);
        return radio;
    }

    public void handleColorRadioButtonAction(ActionEvent event) {
        if (redRadio.isSelected()) {
            square.setFill(Color.web(RED));
        } else if (greenRadio.isSelected()) {
            square.setFill(Color.web(GREEN));
        } else {
            square.setFill(Color.web(ORANGE));
        }
    }

    public static void main(String[] args) { launch(); }
}