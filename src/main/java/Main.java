import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Reflection;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLOutput;
import java.util.concurrent.TransferQueue;

public class Main extends Application {

    private Scene sceneBegin, sceneMenu, sceneDemo;
    private Stage primaryStage;
    private static final Color COLORA = Color.AQUA;
    private static final Color COLORW = Color.WHITE;
    private static final Color COLORSc = Color.BLACK;
    private static final Color COLORAB = Color.AQUAMARINE;


    private static final double SIZE = 320;

    public static void main(String[] args) {
        launch(args);
    }


    public void start(Stage primaryStage) {

        primaryStage.setTitle("Enion");
        primaryStage.setWidth(640);
        primaryStage.setHeight(360);

        //Scene BEGIN, START SCREEN

        Group groupSc1 = new Group();

        VBox vBoxSc1 = new VBox();
        vBoxSc1.setLayoutX(20);
        vBoxSc1.setLayoutY(20);
        vBoxSc1.setSpacing(10);

        String rowSpacingAllSc ="========================================================================="+"\n";

        String row2Sc1 = "  _____        __          _    _____        ____            __           _  \n" +
                " / ___/        /   \\        / )  (_   _)      / ___ \\          /  \\          / ) \n" +
                "( (__         / /\\  \\      / /      | |       / /     \\ \\        / /\\ \\       / /  \n" +
                " ) __)       ) )  ) )    ) )       | |      ( ()    () )     ) )  ) )     ) )  \n" +
                "( (          ( (    ( (   ( (       | |      ( ()    () )    ( (    ( (   ( (   \n" +
                " \\ \\___    / /      \\ \\ / /       _| |__    \\ \\___/ /     / /      \\ \\ / /   \n" +
                "  \\____\\ (_/        \\__/      /_____(     \\____/     (_/        \\__/    "+"\n";

        String shortInfoScBegin =
                "|====== Made by D1z0R and dfjoppe =====|"+"\n"+
                "|======               Version 1.0               =====|"+"\n"+
                "|======   Awesome Encrypting tool   =====|"+"\n"+
                "|======    Have fun and Stay Legal    =====|"+"\n"+
                "|======    User Welcome to ENION    =====|"+"\n"+
                "|======      Press SPACE to begin      =====|";

        String rowSc1 = rowSpacingAllSc+row2Sc1+shortInfoScBegin;

        Text textSc1 = new Text(10, 20, "");
        textSc1.setFill(COLORA);

        Animation animation1Sc1 = new Transition() {
            {
                setCycleDuration(Duration.millis(4000));
            }

            @Override
            protected void interpolate(double frac) {
                int length = rowSc1.length();
                int n = Math.round(length * (float) frac);
                textSc1.setText(rowSc1.substring(0, n));
            }
        };
        animation1Sc1.play();
        groupSc1.getChildren().add(textSc1);
        groupSc1.getChildren().add(vBoxSc1);
        sceneBegin = new Scene(groupSc1);
        sceneBegin.setFill(COLORSc);
        //***************************************************


        Label labelOptionsSc2, label1Sc2, label2Sc2, label3Sc2, label4Sc2;
        Label labelSc2U1;

        Group groupSc2 = new Group();

        VBox vBox0Sc2 = new VBox();
        vBox0Sc2.setLayoutX(0);
        vBox0Sc2.setLayoutY(0);
        vBox0Sc2.setSpacing(5);

        VBox vBox1Sc2 = new VBox();
        vBox1Sc2.setLayoutX(0);
        vBox1Sc2.setLayoutY(100);
        vBox1Sc2.setSpacing(0);


        labelSc2U1 = new Label("Last login: Fri 8 23:59:59 on your_safety" + '\n' +
                "your_safety@localhost: ~$ whereis enion" + '\n' +
                "/usr/bin/secret/enion" + '\n' +
                "your_safety@localhost: ~$ java enion");
        labelSc2U1.setFont(Font.font("Arial"));
        labelSc2U1.setTextFill(COLORW);

        labelOptionsSc2 = new Label("Choose one option: ");
        labelOptionsSc2.setTextFill(COLORA);

        label1Sc2 = new Label("[0] Encrypt file");
        label1Sc2.setTextFill(COLORA);
        //label1Sc2.setFont(Font.font("Arial", 20));

        label2Sc2 = new Label("[1] Decrypt file");
        label2Sc2.setTextFill(COLORA);
        //label2Sc2.setFont(Font.font("Arial", 20));

        label3Sc2 = new Label("[2] Manifest");
        label3Sc2.setTextFill(COLORA);
        //label3Sc2.setFont(Font.font("Arial", 20));

        label4Sc2 = new Label("[3] Quit");
        label4Sc2.setTextFill(COLORA);
        //label4Sc2.setFont(Font.font("Arial", 20));

        vBox0Sc2.getChildren().addAll(labelSc2U1);
        vBox1Sc2.getChildren().addAll(labelOptionsSc2, label1Sc2, label2Sc2, label3Sc2, label4Sc2);

        groupSc2.getChildren().addAll(vBox0Sc2, vBox1Sc2);
        sceneMenu = new Scene(groupSc2);
        sceneMenu.setFill(Color.BLACK);


        primaryStage.setScene(sceneBegin);
        System.out.println(Font.getFamilies());
        (this.primaryStage = primaryStage).show();

        sceneBegin.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE) {
                primaryStage.setScene(sceneMenu);
            }
        });

        label1Sc2.setOnMouseClicked(event -> {
            System.out.println("Encrypt file");
        });

        label2Sc2.setOnMouseClicked(event -> {
            System.out.println("Decrypt file");
        });

        label3Sc2.setOnMouseClicked(event -> {
            System.out.println("Manifest");
        });

        label4Sc2.setOnMouseClicked(event -> {
            System.out.println("Quit");
        });

        //SCENE MENU LABELS MOUSE EVENTS

        label1Sc2.setOnMouseEntered(event -> {
            label1Sc2.setTextFill(COLORAB);
        });
        label1Sc2.setOnMouseExited(event -> {
            label1Sc2.setTextFill(COLORA);
        });

        label2Sc2.setOnMouseEntered(event -> {
            label2Sc2.setTextFill(COLORAB);
        });
        label2Sc2.setOnMouseExited(event -> {
            label2Sc2.setTextFill(COLORA);
        });

        label3Sc2.setOnMouseEntered(event -> {
            label3Sc2.setTextFill(COLORAB);
        });
        label3Sc2.setOnMouseExited(event -> {
            label3Sc2.setTextFill(COLORA);
        });

        label4Sc2.setOnMouseEntered(event -> {
            label4Sc2.setTextFill(COLORAB);
        });
        label4Sc2.setOnMouseExited(event -> {
            label4Sc2.setTextFill(Color.AQUA);
        });

    }
}
