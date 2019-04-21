import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class MainTest extends Application {
    private Scene sceneBegin, sceneMenu, sceneEncrypt,sceneDecrypt;
    private Stage primaryStage;
    private static final Color colorA = Color.AQUA;
    private static final Color colorAB = Color.AQUAMARINE;
    private static final Color colorW = Color.WHITE;
    private static final Color colorScene = Color.BLACK;

    private final Content contentBegin = Content.create("begin");
    private final Content contentMenu = Content.create("menu");
    private final Content contentEncrypt = Content.create("encrypt");
    private final Content contentDecrypt = Content.create("decrypt");

    private static class Content {
        private final Group group = new Group();
        private Label labelTop, labelGround;
        private VBox vBox = new VBox();
        private Label labelEncrypt, labelDecrypt, labelManifest, labelGitHub, labelQuit;
        private Label labelMenu,labelSelectOne, labelSelectMultiple;
        private String shortInfo;
        private Text text = new Text();
        private TextArea fileArea;
        private Animation animation;

        private static Content create(String page) {
            Content content = new Content(page);
            content.group.getChildren().addAll(content.labelTop, content.labelGround, content.vBox, content.text);
            return content;
        }


        private Content(String pageName) {
            if (pageName.equals("begin")) {
                labelTop = createSeparator(1);
                labelGround = createSeparator(320);
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

            } else if (pageName.equals("menu")) {
                labelTop = createSeparator(1);
                labelGround = createSeparator(320);
                vBox.setLayoutY(50);

                labelEncrypt = CreateButton("enc");
                labelDecrypt = CreateButton("dec");
                labelManifest = CreateButton("man");
                labelGitHub = CreateButton("git");
                labelQuit = CreateButton("qut");
                vBox.getChildren().addAll(labelEncrypt, labelDecrypt, labelManifest, labelGitHub, labelQuit);

            } else if (pageName.equals("encrypt")){
                labelTop = createSeparator(1);
                labelGround = createSeparator(320);

                vBox.setLayoutY(30);
                vBox.setSpacing(30);

                labelMenu = CreateButton("menu");
                labelSelectOne = CreateButton("selOne");
                labelSelectMultiple = CreateButton("selMul");

                fileArea = new TextArea();
                fileArea.minHeight(70);
                vBox.getChildren().addAll(labelMenu,fileArea,labelSelectOne,labelSelectMultiple);


            } else if (pageName.equals("decrypt")){
                labelTop = createSeparator(1);
                labelGround = createSeparator(320);
                vBox.setLayoutY(30);
                vBox.setSpacing(30);
                labelMenu = CreateButton("menu");
                vBox.getChildren().add(labelMenu);
            }
        }

        private Label createSeparator(double Y) {
            Label separator = new Label(
                    "============================================================================\n");
            separator.setTextFill(colorA);
            separator.setLayoutY(Y);
            return separator;
        }

        private Label CreateButton(String buttonName) {
            Label button = new Label();
            button.setTextFill(colorA);
            switch (buttonName) {
                case "enc":
                    button.setText("[0] Encrypt file(s)");
                    break;
                case "dec":
                    button.setText("[1] Decrypt file(s)");
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
                case "selOne":
                    button.setText("[1] Select one file");
                    break;
                case  "selMul":
                    button.setText("[2] Select multiple files");
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
                    "    |====== Made by D1z0R and dfjoppe =====|" + "\n" +
                            "    |======               Version 1.0               =====|" + "\n" +
                            "    |======   Awesome Encrypting tool   =====|" + "\n" +
                            "    |======    Have fun and Stay Legal    =====|" + "\n" +
                            "    |======    User Welcome to ENION    =====|" + "\n" +
                            "    |======      Press SPACE to begin      =====|";
            return logo + info;
        }

    }

    private Scene createScene(Group group) {
        Scene scene = new Scene(group);
        scene.setFill(colorScene);
        return scene;
    }

    private void handle(KeyEvent event, String sceneName) {
        if (sceneName.equals("begin")) {
            if (event.getCode() == KeyCode.SPACE) {
                primaryStage.setScene(sceneMenu);
            }
        } else if (sceneName.equals("menu")) {
            //TODO ADD TO ALL LABELS ALL ACTIONS
            KeyCode key = event.getCode();
            if (key == KeyCode.DIGIT0) {
                primaryStage.setScene(sceneEncrypt);
            } else if (key == KeyCode.DIGIT1) {
                primaryStage.setScene(sceneDecrypt);
            } else if (key == KeyCode.DIGIT2) {
                System.out.println("2");
            } else if (key == KeyCode.DIGIT3) {
                openBrowser();
            } else if (key == KeyCode.DIGIT4) {
                System.out.println("4");
            }
        }
    }


    private void handle(String sceneName) {
        if (sceneName.equals("begin")) {
            primaryStage.setScene(sceneMenu);
        } else if (sceneName.equals("menu")) {
            menuMouseActions(contentMenu.labelEncrypt);
            menuMouseActions(contentMenu.labelDecrypt);
            menuMouseActions(contentMenu.labelManifest);
            menuMouseActions(contentMenu.labelGitHub);
            menuMouseActions(contentMenu.labelQuit);
        }
    }

    private void menuMouseActions(Label menuLabel) {
        menuLabel.setOnMouseEntered(event -> menuLabel.setTextFill(colorAB));
        menuLabel.setOnMouseExited(event -> menuLabel.setTextFill(colorA));
        String menuLabelText = menuLabel.getText();
        if (menuLabelText.equals("[3] Github page")) {
            menuLabel.setOnMouseClicked(event -> openBrowser());
        } else if (menuLabelText.equals("[0] Encrypt file(s)")){
            menuLabel.setOnMouseClicked(event -> primaryStage.setScene(sceneEncrypt));
        } else if (menuLabelText.equals("[1] Decrypt file(s)")) {
            menuLabel.setOnMouseClicked(event -> primaryStage.setScene(sceneDecrypt));
        } else {
            menuLabel.setOnMouseClicked(event -> System.out.println(menuLabel.getText()));
        }
    }

    private void openBrowser() {
        getHostServices().showDocument("https://github.com/d1z0rz/enion");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Enion");
        primaryStage.setWidth(640);
        primaryStage.setHeight(360);

        sceneBegin = createScene(contentBegin.group);
        sceneBegin.setOnKeyPressed(event -> handle(event, "begin"));
        sceneBegin.setOnMouseClicked(event -> handle("begin"));
        primaryStage.setScene(sceneBegin);

        sceneMenu = createScene(contentMenu.group);
        sceneMenu.setOnKeyPressed(event -> handle(event, "menu"));
        sceneMenu.setOnMouseEntered(event -> handle("menu"));

        sceneEncrypt = createScene(contentEncrypt.group);

        sceneEncrypt = createScene(contentDecrypt.group);
        //primaryStage.setScene(sceneMenu);

        (this.primaryStage = primaryStage).show();

    }


    public static void main(String[] args) {
        launch(args);
    }

}
