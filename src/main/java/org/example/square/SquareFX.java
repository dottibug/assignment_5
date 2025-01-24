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
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

// -------------------------------------------------------------------------------------------
// Author: Tanya Woodside
// Date: Jan 24, 2025
// COMP 1231 Assignment 5

// A JavaFX application that displays a square. The color and size of the square can be changed
// through radio button options (for color) and a slider (for size). Clicking anywhere except the
// radio and slider controls will play a warning "bloop" sound.
// -------------------------------------------------------------------------------------------

public class SquareFX extends Application {
    // Color Palette
    private static final String BACKGROUND = "#FFFCEF";
    private static final String ONYX = "#3E3E3E";
    private static final String RED = "#D54063";
    private static final String GREEN = "#81B29A";
    private static final String ORANGE = "#FFA97D";

    // Constants
    private static final double MAX_SIZE = 180;
    private static final double INITIAL_SCALE = 75;

    // UI Components
    private final Rectangle square = new Rectangle(MAX_SIZE, MAX_SIZE);
    private RadioButton redRadio, greenRadio, orangeRadio;
    private Slider slider;
    private AudioClip bloopSound;


    // TODO move the methods after start() after refactoring
    // Methods
    // Resource: https://openjfx.io/javadoc/23/javafx.media/javafx/scene/media/AudioClip.html
    private void setupSound() {
        try {
            String soundUrl = SquareFX.class.getResource("/bloop.mp3").toExternalForm();
            bloopSound = new AudioClip(soundUrl);
        }  catch (NullPointerException e) {
            // TODO show warning dialog that the sound file was not found and the program will
            //  continue without that feature
            System.out.println(e.getMessage());
        }
    }

    private VBox setupUI() {
        VBox root = new VBox(50);
        root.setPadding(new Insets(50));
        root.setStyle("-fx-background-color: " + BACKGROUND + ";");

        root.getChildren().addAll(
                setupInstructions(),
                setupColorOptionsAndSquare(),
                setupSlider(),
                setupWarning()
        );

        return root;
    }

    private HBox setupSlider() {
        slider = new Slider(0, 100, INITIAL_SCALE);
        slider.setOrientation(Orientation.HORIZONTAL);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(25);
        slider.setShowTickLabels(true);
        slider.setPrefSize(500, 40);

        // Bind slider value to square scale
        DoubleProperty sliderValue = slider.valueProperty();
        square.scaleXProperty().bind(sliderValue.divide(100));
        square.scaleYProperty().bind(sliderValue.divide(100));

        HBox container = new HBox(slider);
        container.setAlignment(Pos.CENTER);
        return container;
    }

    private HBox setupColorOptionsAndSquare() {
        VBox colorOptions = createColorRadioButtons();
        setupSquare();

        HBox container = new HBox(100);
        container.getChildren().addAll(colorOptions, square);
        container.setAlignment(Pos.CENTER);
        return container;
    }

    private VBox createColorRadioButtons() {
        ToggleGroup colorOptions = new ToggleGroup();
        redRadio = createStyledRadioButton("Red", colorOptions, true);
        greenRadio = createStyledRadioButton("Green", colorOptions, false);
        orangeRadio = createStyledRadioButton("Orange", colorOptions, false);

        VBox container = new VBox(10);
        container.getChildren().addAll(redRadio, greenRadio, orangeRadio);
        container.setAlignment(Pos.CENTER_LEFT);
        return container;
    }

    private RadioButton createStyledRadioButton(String text, ToggleGroup group, boolean selected) {
        RadioButton radio = new RadioButton(text);
        radio.setStyle("-fx-font-size: 18px; -fx-font-weight: 500;");
        radio.setToggleGroup(group);
        radio.setSelected(selected);
        radio.setOnAction(this::handleColorRadioButtonAction);
        return radio;
    }

    private void setupSquare() {
        square.setArcWidth(10);
        square.setArcHeight(10);
        square.setFill(Color.web(RED));
    }

    private StackPane setupInstructions() {
        String instructions = "Change the square color using the radio buttons.\nChange the " +
                "scale of the square from 0-100% with the slider.";
        return createStackPaneTextContainer(instructions, ONYX);
    }

    private StackPane setupWarning() {
        String warning = "Select the radio buttons or the slider only.\nA warning sound " +
                "will play if the mouse is clicked elsewhere.";
        return createStackPaneTextContainer(warning, RED);
    }

    private StackPane createStackPaneTextContainer(String textString, String textColor) {
        Text text = new Text(textString);
        StackPane container = new StackPane();

        text.setFill(Color.web(textColor));
        text.setFont(Font.font("Roboto", FontWeight.MEDIUM, FontPosture.REGULAR, 18));
        text.setTextAlignment(TextAlignment.CENTER);

        container.getChildren().add(text);
        container.setAlignment(Pos.CENTER);
        return container;
    }


    //////

    @Override
    public void start(Stage primaryStage) {
        setupSound();
        VBox root = setupUI();

        // Text blocks
//        StackPane instructions = new StackPane();
//        StackPane warning = new StackPane();

        // Instructions
//        String instructionString = "Change the square color using the radio buttons.\nChange the " +
//                "scale of the square from 0-100% with the slider.";
//
//        Text instructionText = createText(instructionString, ONYX);
//        instructions.getChildren().add(instructionText);
//        instructions.setAlignment(Pos.CENTER);
//
//        // Warning
//        String warningString = "Select the radio buttons or the slider only.\nA warning sound " +
//                "will play if the mouse is clicked elsewhere.";
//
//        Text warningText = createText(warningString, RED);
//        warning.getChildren().add(warningText);
//        warning.setAlignment(Pos.CENTER);

        // Radio buttons
//        ToggleGroup colors = new ToggleGroup();
//
//        redRadio = createStyledRadioButton("Red", colors, true);
//        greenRadio = createStyledRadioButton("Green", colors, false);
//        orangeRadio = createStyledRadioButton("Orange", colors, false);
//
//        VBox colorOptions = new VBox(redRadio, greenRadio, orangeRadio);
//        colorOptions.setAlignment(Pos.CENTER_LEFT);
//        colorOptions.setSpacing(10);
//
//        // Square styling
//        square.setWidth(MAX_SIZE);
//        square.setHeight(MAX_SIZE);
//        square.setArcWidth(10);
//        square.setArcHeight(10);
//        square.setFill(Color.web(RED));
//
//        // Horizontal layout for radio buttons and square
//        HBox colorOptionsSquare = new HBox();
//        colorOptionsSquare.getChildren().addAll(colorOptions, square);
//        colorOptionsSquare.setAlignment(Pos.CENTER);
//        colorOptionsSquare.setSpacing(100);

        // Slider
//        slider = new Slider(0, 100, 75);
//        slider.setOrientation(Orientation.HORIZONTAL);
//        slider.setShowTickMarks(true);
//        slider.setMajorTickUnit(25);
//        slider.setShowTickLabels(true);
//        slider.setPrefSize(500, 40);
//        HBox sliderContainer = new HBox(slider);
//        sliderContainer.setAlignment(Pos.CENTER);

        // Bind slider value to square scale
//        DoubleProperty sliderValue = slider.valueProperty();
//        square.scaleXProperty().bind(sliderValue.divide(100));
//        square.scaleYProperty().bind(sliderValue.divide(100));

        // Vertical root
//        VBox root = new VBox();
//        root.getChildren().addAll(instructions, colorOptionsSquare, sliderContainer, warning);
//        root.setStyle("-fx-background-color: " + BACKGROUND + ";");
//        root.setSpacing(50);
//        root.setPadding(new Insets(50));

        // Staging
        Scene scene = new Scene(root);
        scene.setOnMouseClicked(this::handleMouseClick);

        primaryStage.setTitle("Changing Square");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }

    // Handles mouse click events on the scene (clicks on radio buttons and the slider are
    // consumed by their own handlers and will not bubble up to scene; thus, we do not have
    // to do any event delegation/filtering to play the sound when anything besides the
    // radio buttons or slider are clicked)
    private void handleMouseClick(MouseEvent event) {
        if (bloopSound != null) {
            bloopSound.play();
        }
    }



    private void handleColorRadioButtonAction(ActionEvent event) {
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