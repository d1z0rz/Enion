import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FxBorderPaneExample2 extends Application
{
    // Create the TextArea for the Output
    private TextArea outputArea = new TextArea();

    public static void main(String[] args)
    {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage)
    {
        // Create the Buttons
        Button centerButton = new Button("Center");
        Button rightButton = new Button("Right");
        Button leftButton = new Button("Left");

        // add an EventHandler to the Left Button
        rightButton.setOnAction((EventHandler) event -> writeOutput("You have pressed the Right Button !!!"));

        // add an EventHandler to the Left Button
        centerButton.setOnAction((EventHandler) event -> writeOutput("You have pressed the Center Button !!!"));

        // add an EventHandler to the Left Button
        leftButton.setOnAction((EventHandler) event -> writeOutput("You have pressed the Left Button !!!"));

        // Set the alignment of the Left Button to Center
        BorderPane.setAlignment(leftButton,Pos.TOP_CENTER);
        // Set the alignment of the Right Button to Center
        BorderPane.setAlignment(rightButton,Pos.CENTER);
        // Set the alignment of the Center Button to Center
        BorderPane.setAlignment(centerButton,Pos.BOTTOM_CENTER);

        // Create the upper BorderPane
        BorderPane borderPane = new BorderPane();
        // Set the Buttons to their Location
        borderPane.setTop(leftButton);
        borderPane.setCenter(rightButton);
        borderPane.setBottom(centerButton);
        borderPane.setStyle("-fx-background-color: #C13131;");

        // Create an empty BorderPane
        BorderPane root = new BorderPane();
        // Add the children to the BorderPane
        root.setCenter(borderPane);
        root.setBottom(outputArea);

        // Set the Size of the VBox
        root.setPrefSize(400, 400);
        // Set the Style-properties of the BorderPane

        // Create the Scene
        Scene scene = new Scene(root);
        scene.setFill(Color.MEDIUMSPRINGGREEN);
        // Add the scene to the Stage
        stage.setScene(scene);
        // Set the title of the Stage
        stage.setTitle("A BorderPane Example with only a Top and Bottom Node");
        // Display the Stage
        stage.show();
    }

    // Method to log the Message to the Output-Area
    private void writeOutput(String msg)
    {
        this.outputArea.appendText("Your Input: " + msg + "\n");
    }

}