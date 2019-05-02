import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;

public class MainTest extends Application {
    private static final Color colorA = Color.AQUA;
    private static final Color colorAB = Color.AQUAMARINE;
    private static final Color colorW = Color.WHITE;
    private static final Color colorScene = Color.BLACK;
    private final Content contentBegin = Content.create("begin");
    private final Content contentMenu = Content.create("menu");
    private final Content contentEncrypt = Content.create("encrypt");
    private final Content contentDecrypt = Content.create("decrypt");
    private final Content contentManifest = Content.create("manifest");
    private Scene sceneMenu;
    private Scene sceneManifest;
    private Scene sceneEncrypt;
    private Scene sceneDecrypt;
    private Stage primaryStage;

    private FileChooser fileChooser = new FileChooser();
    private File file;

    public static void main(String[] args) {
        launch(args);
    }

    private Scene createScene(BorderPane borderPane) {
        Scene scene = new Scene(borderPane);
        scene.setFill(colorScene);
        return scene;
    }

    private void handle(KeyEvent event, String sceneName) {
        switch (sceneName) {
            case "begin":
                if (event.getCode() == KeyCode.SPACE) {
                    primaryStage.setScene(sceneMenu);
                }
                break;
            case "menu": {
                KeyCode key = event.getCode();
                if (key == KeyCode.DIGIT0) {
                    primaryStage.setScene(sceneEncrypt);
                } else if (key == KeyCode.DIGIT1) {
                    primaryStage.setScene(sceneDecrypt);
                } else if (key == KeyCode.DIGIT2) {
                    primaryStage.setScene(sceneManifest);
                    contentManifest.animation.play();
                } else if (key == KeyCode.DIGIT3) {
                    openBrowser();
                } else if (key == KeyCode.DIGIT4) {
                    primaryStage.close();
                }
                break;
            }
            case "encry": {
                KeyCode key = event.getCode();
                if (key == KeyCode.DIGIT0) {
                    primaryStage.setScene(sceneMenu);
                } else if (key == KeyCode.DIGIT1) {
                    contentEncrypt.filePathString.setText("File path");
                    configurationEncryptFileChooser(fileChooser);
                    file = fileChooser.showOpenDialog(primaryStage);
                    printLog(contentEncrypt.filePathString, file);
                } else if (key == KeyCode.DIGIT2) {
                    windowAskPassword("encrypt", file);
                }
                break;
            }
            case "decry": {
                KeyCode key = event.getCode();
                if (key == KeyCode.DIGIT0) {
                    primaryStage.setScene(sceneMenu);
                } else if (key == KeyCode.DIGIT1) {
                    contentDecrypt.filePathString.setText("File path");
                    configutationDecryptFileChooser(fileChooser);
                    file = fileChooser.showOpenDialog(primaryStage);
                    printLog(contentDecrypt.filePathString, file);
                } else if (key == KeyCode.DIGIT2) {
                    windowAskPassword("decrypt", file);
                }
                break;
            }
            case "manst": {
                KeyCode key = event.getCode();
                if (key == KeyCode.DIGIT0) {
                    primaryStage.setScene(sceneMenu);
                }
                break;
            }
        }
    }


    private void handle(String sceneName) {
        switch (sceneName) {
            case "begin":
                primaryStage.setScene(sceneMenu);
                break;
            case "menu":
                menuMouseActions(contentMenu.labelEncrypt);
                menuMouseActions(contentMenu.labelDecrypt);
                menuMouseActions(contentMenu.labelManifest);
                menuMouseActions(contentMenu.labelGitHub);
                menuMouseActions(contentMenu.labelQuit);
                break;
            case "encry":
                menuMouseActions(contentEncrypt.labelMenu);
                menuMouseActions(contentEncrypt.labelSelectOne);
                menuMouseActions(contentEncrypt.labelFileEncrypt);
                break;
            case "decry":
                menuMouseActions(contentDecrypt.labelMenu);
                menuMouseActions(contentDecrypt.labelSelectOne);
                menuMouseActions(contentDecrypt.labelFileDecrypt);
                break;
            case "manst":
                menuMouseActions(contentManifest.labelMenu);
                break;
        }
    }

    private void menuMouseActions(Label menuLabel) {
        menuLabel.setOnMouseEntered(event -> menuLabel.setTextFill(colorAB));
        menuLabel.setOnMouseExited(event -> menuLabel.setTextFill(colorA));
        String menuLabelText = menuLabel.getText();
        switch (menuLabelText) {
            case "[0] Encryption":
                menuLabel.setOnMouseClicked(event -> primaryStage.setScene(sceneEncrypt));
                break;
            case "[1] Decryption":
                menuLabel.setOnMouseClicked(event -> primaryStage.setScene(sceneDecrypt));
                break;
            case "[3] Github page":
                menuLabel.setOnMouseClicked(event -> openBrowser());
                break;
            case "[2] Manifest":
                menuLabel.setOnMouseClicked(event -> {
                    primaryStage.setScene(sceneManifest);
                    contentManifest.animation.play();
                });
                break;
            case "[4] Quit":
                menuLabel.setOnMouseClicked(event -> Platform.exit());
                break;
            case "[0] To Menu":
                menuLabel.setOnMouseClicked(event -> primaryStage.setScene(sceneMenu));
                break;
            case "[1] Select one file to encrypt":
                menuLabel.setOnMouseClicked(event -> {
                    contentEncrypt.filePathString.setText("File path");
                    configurationEncryptFileChooser(fileChooser);
                    file = fileChooser.showOpenDialog(primaryStage);
                    printLog(contentEncrypt.filePathString, file);
                });
                break;
            case "[1] Select one file to decrypt":
                menuLabel.setOnMouseClicked(event -> {
                    contentDecrypt.filePathString.setText("File path");
                    configutationDecryptFileChooser(fileChooser);
                    file = fileChooser.showOpenDialog(primaryStage);
                    printLog(contentDecrypt.filePathString, file);
                });
                break;
            case "[2] Encrypt file":
                menuLabel.setOnMouseClicked(event -> windowAskPassword("encrypt", file));
                break;
            case "[2] Decrypt file":
                menuLabel.setOnMouseClicked(event -> windowAskPassword("decrypt", file));
                break;
            default:
                menuLabel.setOnMouseClicked(event -> System.out.println(menuLabel.getText()));
                break;
        }
    }

    private void configurationEncryptFileChooser(FileChooser fileChooser) {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("png", "*.png")
        );
    }

    private void configutationDecryptFileChooser(FileChooser fileChooser) {
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Encrypted", "*.secured"));
    }

    private void windowAskPassword(String action, File choosenFile) {
        Stage stagePassword = new Stage();
        Group groupPassword = new Group();
        Scene scene = new Scene(groupPassword, 260, 80);
        stagePassword.setScene(scene);

        stagePassword.setTitle("Enion Password");

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 0, 0, 10));
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        HBox hBox = new HBox();
        hBox.setSpacing(10);

        Label labelPassword = new Label("Password");

        Label labelError = new Label("");

        final PasswordField password = new PasswordField();
        if (action.equals("encrypt")) {
            password.setOnAction(e -> {
                if ((password.getText().getBytes()).length == 16) {
                    labelError.setText("Password is ok");
                    labelError.setTextFill(Color.web("black"));
                    try {
                        Crypto.actionWithFile(choosenFile, "encryption", password.getText());
                        stagePassword.close();
                    } catch (IOException | InvalidKeyException ex) {
                        ex.printStackTrace();
                    }
                } else if (password.getText().length() >= 1 && password.getText().length() < 6) {
                    labelError.setText("Password contain 16-bits");
                    labelError.setTextFill(Color.web("red"));
                } else {
                    labelError.setText("Write your password");
                    labelError.setTextFill(Color.web("red"));
                }
            });
        } else if (action.equals("decrypt")) {
            password.setOnAction(event -> {
                if ((password.getText().getBytes()).length == 16) {
                    labelError.setText("Checking password");
                    labelError.setTextFill(Color.web("black"));
                    try {
                        Crypto.actionWithFile(choosenFile, "decryption", password.getText());
                        if (PasswordError.passwordState == 1) {
                            labelError.setText("Wrong password");
                        } else if (PasswordError.passwordState == 0) {
                            labelError.setText("Correct password");
                            stagePassword.close();
                        }
                    } catch (IOException | InvalidKeyException e) {
                        e.printStackTrace();
                    }
                } else if (!(password.getText().getBytes().length <= 1 || password.getText().getBytes().length >= 16)) {
                    labelError.setText("Password contain 16-bits");
                    labelError.setTextFill(Color.web("red"));
                } else {
                    labelError.setText("Write your password");
                    labelError.setTextFill(Color.web("red"));
                }
            });
        }

        hBox.getChildren().addAll(labelPassword, password);
        vBox.getChildren().addAll(hBox, labelError);

        scene.setRoot(vBox);
        stagePassword.show();
    }

    private void printLog(Text textPath, File file) {
        if (file == null) {
            return;
        }
        textPath.setText("File path: " + file.getAbsolutePath() + "\n");
    }

    private void openBrowser() {
        getHostServices().showDocument("https://github.com/d1z0rz/enion");
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Enion");
        primaryStage.setWidth(640);
        primaryStage.setHeight(360);


        Scene sceneBegin = createScene(contentBegin.borderPane);
        sceneBegin.setOnKeyPressed(event -> handle(event, "begin"));
        sceneBegin.setOnMouseClicked(event -> handle("begin"));
        primaryStage.setScene(sceneBegin);

        sceneMenu = createScene(contentMenu.borderPane);
        sceneMenu.setOnKeyPressed(event -> handle(event, "menu"));
        sceneMenu.setOnMouseEntered(event -> handle("menu"));

        sceneEncrypt = createScene(contentEncrypt.borderPane);
        sceneEncrypt.setOnKeyPressed(event -> handle(event, "encry"));
        sceneEncrypt.setOnMouseEntered(event -> handle("encry"));

        sceneDecrypt = createScene(contentDecrypt.borderPane);
        sceneDecrypt.setOnKeyPressed(event -> handle(event, "decry"));
        sceneDecrypt.setOnMouseEntered(event -> handle("decry"));

        sceneManifest = createScene(contentManifest.borderPane);
        sceneManifest.setOnKeyPressed(event -> handle(event, "manst"));
        sceneManifest.setOnMouseEntered(event -> handle("manst"));


        (this.primaryStage = primaryStage).show();

    }

    private static class Content {
        private final BorderPane borderPane = new BorderPane();
        private VBox vBox = new VBox();
        private Label labelEncrypt, labelDecrypt, labelManifest, labelGitHub, labelQuit;
        private Label labelMenu, labelSelectOne, labelFileEncrypt, labelFileDecrypt;
        private String shortInfo;
        private Text text = new Text();
        private Text filePathString = createFilePathString();
        private Animation animation;

        private Text createFilePathString() {
            Text filePath = new Text("File path:");
            filePath.setFill(colorW);
            return filePath;
        }


        private Content(String pageName) {
            switch (pageName) {
                case "begin":
                    vBox.setLayoutY(20);

                    shortInfo = getBeginInfo();
                    text.setFill(colorA);
                    text.setY(60);
                    text.setX(160);
                    animation = new Transition() {
                        {
                            setCycleDuration(Duration.millis(4000));
                        }

                        protected void interpolate(double frac) {
                            int length = shortInfo.length();
                            int n = Math.round(length * (float) frac);
                            text.setText(shortInfo.substring(0, n));
                        }
                    };
                    animation.play();

                    break;
                case "menu":
                    vBox.setLayoutY(50);

                    labelEncrypt = CreateButton("enc");
                    labelDecrypt = CreateButton("dec");
                    labelManifest = CreateButton("man");
                    labelGitHub = CreateButton("git");
                    labelQuit = CreateButton("qut");
                    vBox.getChildren().addAll(labelEncrypt, labelDecrypt, labelManifest, labelGitHub, labelQuit);

                    break;
                case "encrypt":

                    vBox.setLayoutY(30);
                    vBox.setMinWidth(350);

                    labelMenu = CreateButton("menu");
                    labelSelectOne = CreateButton("selOneE");
                    labelFileEncrypt = CreateButton("fenc");

                    vBox.getChildren().addAll(labelMenu, filePathString, labelSelectOne, labelFileEncrypt);

                    break;
                case "decrypt":

                    vBox.setLayoutY(30);

                    labelMenu = CreateButton("menu");
                    labelSelectOne = CreateButton("selOneD");
                    labelFileDecrypt = CreateButton("fdenc");

                    vBox.getChildren().addAll(labelMenu, filePathString, labelSelectOne, labelFileDecrypt);
                    break;
                case "manifest":
                    vBox.setLayoutY(30);

                    labelMenu = CreateButton("menu");
                    shortInfo = getManifestInfo();
                    text.setFill(colorA);
                    text.setY(60);
                    text.setX(160);
                    animation = new Transition() {
                        {
                            setCycleDuration(Duration.millis(5000));
                        }

                        protected void interpolate(double frac) {
                            int n = Math.round(shortInfo.length() * (float) frac);
                            text.setText(shortInfo.substring(0, n));
                        }
                    };
                    vBox.getChildren().add(labelMenu);
                    break;
            }
        }

        private static Content create(String page) {
            Content content = new Content(page);
            BorderPane.setAlignment(content.vBox,Pos.TOP_LEFT);
            BorderPane.setAlignment(content.text,Pos.CENTER);

            content.borderPane.setTop(content.vBox);
            content.borderPane.setCenter(content.text);
            content.borderPane.setPrefSize(640,340);
            content.borderPane.setStyle("-fx-background-color: #282B5E;" +
                    "-fx-border-color: #282B5E, #00FFFF;" +
                    "-fx-border-width: 5, 5;" +
                    "-fx-border-radius: 0;" +
                    "-fx-border-insets: 0, 5;" +
                    "-fx-border-style: solid inside, dotted outside;"
            );

            return content;
        }

        private Label CreateButton(String buttonName) {
            Label button = new Label();
            button.setTextFill(colorA);
            switch (buttonName) {
                case "enc":
                    button.setText("[0] Encryption");
                    break;
                case "dec":
                    button.setText("[1] Decryption");
                    break;
                case "man":
                    button.setText("[2] Manifest");
                    break;
                case "git":
                    button.setText("[3] Github page");
                    break;
                case "qut":
                    button.setText("[4] Quit");
                    break;
                case "menu":
                    button.setText("[0] To Menu");
                    break;
                case "selOneE":
                    button.setText("[1] Select one file to encrypt");
                    break;
                case "selOneD":
                    button.setText("[1] Select one file to decrypt");
                    break;
                case "fenc":
                    button.setText("[2] Encrypt file");
                    break;
                case "fdenc":
                    button.setText("[2] Decrypt file");
                    break;
            }
            return button;
        }

        private String getBeginInfo() {
            String logo = "  _____        __          _    _____        ____            __           _  \n" +
                    " / ___/        /   \\        / )  (_   _)      / ___ \\          /  \\          / ) \n" +
                    "( (__         / /\\  \\      / /      | |       / /     \\ \\        / /\\ \\       / /  \n" +
                    " ) __)       ) )  ) )    ) )       | |      ( ()    () )     ) )  ) )     ) )  \n" +
                    "( (          ( (    ( (   ( (       | |      ( ()    () )    ( (    ( (   ( (   \n" +
                    " \\ \\___    / /      \\ \\ / /       _| |__    \\ \\___/ /     / /      \\ \\ / /   \n" +
                    "  \\____\\ (_/        \\__/      /_____(     \\____/     (_/        \\__/    " + "\n";

            String info =
                    "    |====== Made by D1z0R and sjeppiroe =====|" + "\n" +
                            "    |======               Version 1.0               =====|" + "\n" +
                            "    |======   Awesome Encrypting tool   =====|" + "\n" +
                            "    |======    Have fun and Stay Legal    =====|" + "\n" +
                            "    |======    User Welcome to ENION    =====|" + "\n" +
                            "    |======      Press SPACE to begin      =====|";
            return logo + info;
        }

        private String getManifestInfo() {
            return "Manifest V1.0" + "\n" +
            "Code Base: our imagination and practice" + "\n" +
            "Created by D1z0R && sjeppiroe" + "\n" +
            "Permissions: everything, that you can imagine" + "\n"+
            "Algorithm: AES256" + "\n" +
            "Security level: 1" + "\n" +
            "Your CPU cannot guarantee true random";
        }
    }
}