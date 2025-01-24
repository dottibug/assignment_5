package org.example.square;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    private static double INITIAL_SCALE = 75;

    // UI Components
    private final Rectangle square = new Rectangle(MAX_SIZE, MAX_SIZE);
    private RadioButton redRadio, greenRadio, orangeRadio;
    private Slider slider;
    private AudioClip bloopSound;

    @Override
    public void start(Stage primaryStage) {
        setupSound();
        VBox root = setupRoot();
        setupStage(root, primaryStage);
    }

    public static void main(String[] args) { launch(); }

    // -------------------------------------------------------------------------------------------
    // SETUP METHODS
    // -------------------------------------------------------------------------------------------
    // Initializes the bloop sound used when a user clicks any part of the scene that is not a
    // radio button option or the slider.
    // Handles exceptions by showing an error dialog box and continuing with the application
    // without the bloop sound effect.
    // Resource: https://openjfx.io/javadoc/23/javafx.media/javafx/scene/media/AudioClip.html
    private void setupSound() {
            String soundFile = "/bloop.mp3";
        try {
            String soundUrl = SquareFX.class.getResource(soundFile).toExternalForm();
            bloopSound = new AudioClip(soundUrl);
        }  catch (NullPointerException e) {
            String errorMsg = "Sound file \"" + soundFile + "\" was not found in resources. \nThe" +
                    "app will run without sound effects.";
            showErrorDialog("Audio Error", errorMsg);
        } catch (Exception e) {
            String errorMsg = "Error: " + e.getMessage() + "\nThe app will run without sound effects.";
            showErrorDialog("Audio Error", errorMsg);
        }
    }

    // Sets up the root node and its children.
    private VBox setupRoot() {
        VBox root = new VBox(50);

        String instructionsText = "Change the square color using the radio buttons.\nChange the " +
                "scale of the square from 0-100% with the slider.";
        StackPane instructions = createStackPaneTextContainer(instructionsText, ONYX);

        HBox colorOptionsAndSquare = setupColorOptionsAndSquare();
        HBox slider = setupSlider();

        String warningText = "Select the radio buttons or the slider only.\nA warning sound " +
                "will play if the mouse is clicked elsewhere.";
        StackPane warning = createStackPaneTextContainer(warningText, RED);

        root.setPadding(new Insets(50));
        root.setStyle("-fx-background-color: " + BACKGROUND + ";");
        root.getChildren().addAll(instructions, colorOptionsAndSquare, slider, warning);
        return root;
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

    // Lays out the radio buttons to the left of the colored square
    private HBox setupColorOptionsAndSquare() {
        VBox colorOptions = createColorRadioButtons();
        createSquare();
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

    // Creates and styles radio buttons, and sets the same event handler to handle radio selection
    private RadioButton createStyledRadioButton(String text, ToggleGroup group, boolean selected) {
        RadioButton radio = new RadioButton(text);
        radio.setStyle("-fx-font-size: 18px; -fx-font-weight: 500;");
        radio.setToggleGroup(group);
        radio.setSelected(selected);
        radio.setOnAction(this::handleColorRadioButtonAction);
        return radio;
    }

    // Creates and styles the square (rounded corners, initially red)
    private void createSquare() {
        square.setArcWidth(10);
        square.setArcHeight(10);
        square.setFill(Color.web(RED));
    }

    // Sets up the slider.
    // Throws an exception if the INITIAL_SCALE value is not between 0 and 100; the user will see
    // an error dialog box and the app will set the default scale to 75 so the program can still
    // run.
    // The slider is placed in an HBox, so it can be centered in the scene.
    private HBox setupSlider() {
        try {
            if (INITIAL_SCALE < 0 || INITIAL_SCALE > 100) {
                throw new IllegalArgumentException("Initial scale must be between 0 and 100. The " +
                        "initial scale will be set to 75 so the program can run.");
            }
            slider = new Slider(0, 100, INITIAL_SCALE);
        } catch (Exception e){
            showErrorDialog("Slider error", e.getMessage());
            slider = new Slider(0, 100, 75);
        }

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

    private void setupStage(VBox root, Stage primaryStage) {
        Scene scene = new Scene(root);
        scene.setOnMouseClicked(this::handleMouseClick);

        primaryStage.setTitle("SquareFX App");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }

    // -------------------------------------------------------------------------------------------
    // EVENT HANDLERS
    // -------------------------------------------------------------------------------------------
    private void handleColorRadioButtonAction(ActionEvent event) {
        if (redRadio.isSelected()) {
            square.setFill(Color.web(RED));
        } else if (greenRadio.isSelected()) {
            square.setFill(Color.web(GREEN));
        } else {
            square.setFill(Color.web(ORANGE));
        }
    }

    // Handles mouse click events on the scene. The null check ensures the app can still run
    // without sound effects if there was an error with the audio file.
    // NOTE: Radio button and slider clicks are consumed by their own handlers, which means they
    //  will not bubble up to the scene node. Thus, we do not have to do any event delegation to
    //  play the sound whenever the click target is not a radio button or the slider.
    private void handleMouseClick(MouseEvent event) {
        if (bloopSound != null) {
            bloopSound.play();
        }
    }

    // -------------------------------------------------------------------------------------------
    // DIALOGS
    // -------------------------------------------------------------------------------------------
    private void showErrorDialog(String header, String errorMsg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(header);
        alert.setContentText(errorMsg);
        alert.showAndWait();
    }
}