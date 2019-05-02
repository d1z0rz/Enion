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
    private static final Color värvA = Color.AQUA;
    private static final Color värvAB = Color.AQUAMARINE;
    private static final Color värvW = Color.WHITE;
    private static final Color stseenivärv = Color.BLACK;
    private final Sisu sisuAlgus = Sisu.loo("begin");
    private final Sisu sisuMenüü = Sisu.loo("menu");
    private final Sisu sisuKrüpteermine = Sisu.loo("encrypt");
    private final Sisu sisuDekrüpteerimine = Sisu.loo("decrypt");
    private final Sisu sisuManifest = Sisu.loo("manifest");
    private Scene stseenMenüü;
    private Scene stseenManifest;
    private Scene stseenKrüpteerimine;
    private Scene stseenDekrüpteermine;
    private Stage peaLava;

    private FileChooser failiValija = new FileChooser();
    private File fail;

    public static void main(String[] args) {
        launch(args);
    }

    private Scene looStseen(BorderPane borderPane) {
        Scene stseen = new Scene(borderPane);
        stseen.setFill(stseenivärv);
        return stseen;
    }

    private void handle(KeyEvent sündmus, String stseeniNimi) {
        switch (stseeniNimi) {
            case "begin":
                if (sündmus.getCode() == KeyCode.SPACE) {
                    peaLava.setScene(stseenMenüü);
                }
                break;
            case "menu": {
                KeyCode key = sündmus.getCode();
                if (key == KeyCode.DIGIT0) {
                    peaLava.setScene(stseenKrüpteerimine);
                } else if (key == KeyCode.DIGIT1) {
                    peaLava.setScene(stseenDekrüpteermine);
                } else if (key == KeyCode.DIGIT2) {
                    peaLava.setScene(stseenManifest);
                    sisuManifest.animatsioon.play();
                } else if (key == KeyCode.DIGIT3) {
                    avaBrauser();
                } else if (key == KeyCode.DIGIT4) {
                    peaLava.close();
                }
                break;
            }
            case "encry": {
                KeyCode key = sündmus.getCode();
                if (key == KeyCode.DIGIT0) {
                    peaLava.setScene(stseenMenüü);
                } else if (key == KeyCode.DIGIT1) {
                    sisuKrüpteermine.failiAsukohaSõne.setText("File path");
                    seadistaKrüpteerimiseFailiValija(failiValija);
                    fail = failiValija.showOpenDialog(peaLava);
                    prindiLogi(sisuKrüpteermine.failiAsukohaSõne, fail);
                } else if (key == KeyCode.DIGIT2) {
                    parooliKüsimiseAken("encrypt", fail);
                }
                break;
            }
            case "decry": {
                KeyCode key = sündmus.getCode();
                if (key == KeyCode.DIGIT0) {
                    peaLava.setScene(stseenMenüü);
                } else if (key == KeyCode.DIGIT1) {
                    sisuDekrüpteerimine.failiAsukohaSõne.setText("File path");
                    seadistaDekrüpteerimiseFailiValija(failiValija);
                    fail = failiValija.showOpenDialog(peaLava);
                    prindiLogi(sisuDekrüpteerimine.failiAsukohaSõne, fail);
                } else if (key == KeyCode.DIGIT2) {
                    parooliKüsimiseAken("decrypt", fail);
                }
                break;
            }
            case "manst": {
                KeyCode key = sündmus.getCode();
                if (key == KeyCode.DIGIT0) {
                    peaLava.setScene(stseenMenüü);
                }
                break;
            }
        }
    }


    private void handle(String stseeniNimi) {
        switch (stseeniNimi) {
            case "begin":
                peaLava.setScene(stseenMenüü);
                break;
            case "menu":
                menüüHiireTegevused(sisuMenüü.krüpteerimiseSilt);
                menüüHiireTegevused(sisuMenüü.dekrüpteerimiseSilt);
                menüüHiireTegevused(sisuMenüü.manifestiSilt);
                menüüHiireTegevused(sisuMenüü.gitHubiSilt);
                menüüHiireTegevused(sisuMenüü.väljumiseSilt);
                break;
            case "encry":
                menüüHiireTegevused(sisuKrüpteermine.menüüSilt);
                menüüHiireTegevused(sisuKrüpteermine.valiÜksSilt);
                menüüHiireTegevused(sisuKrüpteermine.failiKrüpteerimiseSilt);
                break;
            case "decry":
                menüüHiireTegevused(sisuDekrüpteerimine.menüüSilt);
                menüüHiireTegevused(sisuDekrüpteerimine.valiÜksSilt);
                menüüHiireTegevused(sisuDekrüpteerimine.failiDekrüpteerimiseSilt);
                break;
            case "manst":
                menüüHiireTegevused(sisuManifest.menüüSilt);
                break;
        }
    }

    private void menüüHiireTegevused(Label menüüSilt) {
        menüüSilt.setOnMouseEntered(event -> menüüSilt.setTextFill(värvAB));
        menüüSilt.setOnMouseExited(event -> menüüSilt.setTextFill(värvA));
        String menüüSildiTekst = menüüSilt.getText();
        switch (menüüSildiTekst) {
            case "[0] Encryption":
                menüüSilt.setOnMouseClicked(event -> peaLava.setScene(stseenKrüpteerimine));
                break;
            case "[1] Decryption":
                menüüSilt.setOnMouseClicked(event -> peaLava.setScene(stseenDekrüpteermine));
                break;
            case "[3] Github page":
                menüüSilt.setOnMouseClicked(event -> avaBrauser());
                break;
            case "[2] Manifest":
                menüüSilt.setOnMouseClicked(event -> {
                    peaLava.setScene(stseenManifest);
                    sisuManifest.animatsioon.play();
                });
                break;
            case "[4] Quit":
                menüüSilt.setOnMouseClicked(event -> Platform.exit());
                break;
            case "[0] To Menu":
                menüüSilt.setOnMouseClicked(event -> peaLava.setScene(stseenMenüü));
                break;
            case "[1] Select one fail to encrypt":
                menüüSilt.setOnMouseClicked(event -> {
                    sisuKrüpteermine.failiAsukohaSõne.setText("File path");
                    seadistaKrüpteerimiseFailiValija(failiValija);
                    fail = failiValija.showOpenDialog(peaLava);
                    prindiLogi(sisuKrüpteermine.failiAsukohaSõne, fail);
                });
                break;
            case "[1] Select one fail to decrypt":
                menüüSilt.setOnMouseClicked(event -> {
                    sisuDekrüpteerimine.failiAsukohaSõne.setText("File path");
                    seadistaDekrüpteerimiseFailiValija(failiValija);
                    fail = failiValija.showOpenDialog(peaLava);
                    prindiLogi(sisuDekrüpteerimine.failiAsukohaSõne, fail);
                });
                break;
            case "[2] Encrypt fail":
                menüüSilt.setOnMouseClicked(event -> parooliKüsimiseAken("encrypt", fail));
                break;
            case "[2] Decrypt fail":
                menüüSilt.setOnMouseClicked(event -> parooliKüsimiseAken("decrypt", fail));
                break;
            default:
                menüüSilt.setOnMouseClicked(event -> System.out.println(menüüSilt.getText()));
                break;
        }
    }

    private void seadistaKrüpteerimiseFailiValija(FileChooser failiValija) {
        failiValija.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("png", "*.png")
        );
    }

    private void seadistaDekrüpteerimiseFailiValija(FileChooser failiValija) {
        failiValija.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Encrypted", "*.secured"));
    }

    private void parooliKüsimiseAken(String tegevus, File valitudFail) {
        Stage lavaParool = new Stage();
        Group grupiParool = new Group();
        Scene stseen = new Scene(grupiParool, 260, 80);
        lavaParool.setScene(stseen);

        lavaParool.setTitle("Enion Password");

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 0, 0, 10));
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        HBox hBox = new HBox();
        hBox.setSpacing(10);

        Label parooliSilt = new Label("Password");

        Label veaSilt = new Label("");

        final PasswordField parool = new PasswordField();
        if (tegevus.equals("encrypt")) {
            parool.setOnAction(e -> {
                if ((parool.getText().getBytes()).length == 16) {
                    veaSilt.setText("Password is ok");
                    veaSilt.setTextFill(Color.web("black"));
                    try {
                        Krüpteerija.tegevusFailiga(valitudFail, "encryption", parool.getText());
                        lavaParool.close();
                    } catch (IOException | InvalidKeyException ex) {
                        ex.printStackTrace();
                    }
                } else if (parool.getText().length() >= 1 && parool.getText().length() < 6) {
                    veaSilt.setText("Password contain 16-bits");
                    veaSilt.setTextFill(Color.web("red"));
                } else {
                    veaSilt.setText("Write your password");
                    veaSilt.setTextFill(Color.web("red"));
                }
            });
        } else if (tegevus.equals("decrypt")) {
            parool.setOnAction(event -> {
                if ((parool.getText().getBytes()).length == 16) {
                    veaSilt.setText("Checking password");
                    veaSilt.setTextFill(Color.web("black"));
                    try {
                        Krüpteerija.tegevusFailiga(valitudFail, "decryption", parool.getText());
                        if (ParooliViga.passwordState == 1) {
                            veaSilt.setText("Wrong password");
                        } else if (ParooliViga.passwordState == 0) {
                            veaSilt.setText("Correct password");
                            lavaParool.close();
                        }
                    } catch (IOException | InvalidKeyException e) {
                        e.printStackTrace();
                    }
                } else if (!(parool.getText().getBytes().length <= 1 || parool.getText().getBytes().length >= 16)) {
                    veaSilt.setText("Password contain 16-bits");
                    veaSilt.setTextFill(Color.web("red"));
                } else {
                    veaSilt.setText("Write your password");
                    veaSilt.setTextFill(Color.web("red"));
                }
            });
        }

        hBox.getChildren().addAll(parooliSilt, parool);
        vBox.getChildren().addAll(hBox, veaSilt);

        stseen.setRoot(vBox);
        lavaParool.show();
    }

    private void prindiLogi(Text tekstiAsukoht, File fail) {
        if (fail == null) {
            return;
        }
        tekstiAsukoht.setText("File path: " + fail.getAbsolutePath() + "\n");
    }

    private void avaBrauser() {
        getHostServices().showDocument("https://github.com/d1z0rz/enion");
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Enion");
        primaryStage.setWidth(640);
        primaryStage.setHeight(360);


        Scene stseenAlgus = looStseen(sisuAlgus.äärisPaneel);
        stseenAlgus.setOnKeyPressed(event -> handle(event, "begin"));
        stseenAlgus.setOnMouseClicked(event -> handle("begin"));
        primaryStage.setScene(stseenAlgus);

        stseenMenüü = looStseen(sisuMenüü.äärisPaneel);
        stseenMenüü.setOnKeyPressed(event -> handle(event, "menu"));
        stseenMenüü.setOnMouseEntered(event -> handle("menu"));

        stseenKrüpteerimine = looStseen(sisuKrüpteermine.äärisPaneel);
        stseenKrüpteerimine.setOnKeyPressed(event -> handle(event, "encry"));
        stseenKrüpteerimine.setOnMouseEntered(event -> handle("encry"));

        stseenDekrüpteermine = looStseen(sisuDekrüpteerimine.äärisPaneel);
        stseenDekrüpteermine.setOnKeyPressed(event -> handle(event, "decry"));
        stseenDekrüpteermine.setOnMouseEntered(event -> handle("decry"));

        stseenManifest = looStseen(sisuManifest.äärisPaneel);
        stseenManifest.setOnKeyPressed(event -> handle(event, "manst"));
        stseenManifest.setOnMouseEntered(event -> handle("manst"));


        (this.peaLava = primaryStage).show();

    }

    private static class Sisu {
        private final BorderPane äärisPaneel = new BorderPane();
        private VBox vBox = new VBox();
        private Label krüpteerimiseSilt, dekrüpteerimiseSilt, manifestiSilt, gitHubiSilt, väljumiseSilt;
        private Label menüüSilt, valiÜksSilt, failiKrüpteerimiseSilt, failiDekrüpteerimiseSilt;
        private String lühiInfo;
        private Text tekst = new Text();
        private Text failiAsukohaSõne = looFailiAsukohaSõne();
        private Animation animatsioon;

        private Text looFailiAsukohaSõne() {
            Text failiAsukoht = new Text("File path:");
            failiAsukoht.setFill(värvW);
            return failiAsukoht;
        }


        private Sisu(String leheNimi) {
            switch (leheNimi) {
                case "begin":
                    vBox.setLayoutY(20);

                    lühiInfo = alguseInfo();
                    tekst.setFill(värvA);
                    tekst.setY(60);
                    tekst.setX(160);
                    animatsioon = new Transition() {
                        {
                            setCycleDuration(Duration.millis(4000));
                        }

                        protected void interpolate(double frac) {
                            int pikkus = lühiInfo.length();
                            int n = Math.round(pikkus * (float) frac);
                            tekst.setText(lühiInfo.substring(0, n));
                        }
                    };
                    animatsioon.play();

                    break;
                case "menu":
                    vBox.setLayoutY(50);

                    krüpteerimiseSilt = looNupp("enc");
                    dekrüpteerimiseSilt = looNupp("dec");
                    manifestiSilt = looNupp("man");
                    gitHubiSilt = looNupp("git");
                    väljumiseSilt = looNupp("qut");
                    vBox.getChildren().addAll(krüpteerimiseSilt, dekrüpteerimiseSilt, manifestiSilt, gitHubiSilt, väljumiseSilt);

                    break;
                case "encrypt":

                    vBox.setLayoutY(30);
                    vBox.setMinWidth(350);

                    menüüSilt = looNupp("menu");
                    valiÜksSilt = looNupp("selOneE");
                    failiKrüpteerimiseSilt = looNupp("fenc");

                    vBox.getChildren().addAll(menüüSilt, failiAsukohaSõne, valiÜksSilt, failiKrüpteerimiseSilt);

                    break;
                case "decrypt":

                    vBox.setLayoutY(30);

                    menüüSilt = looNupp("menu");
                    valiÜksSilt = looNupp("selOneD");
                    failiDekrüpteerimiseSilt = looNupp("fdenc");

                    vBox.getChildren().addAll(menüüSilt, failiAsukohaSõne, valiÜksSilt, failiDekrüpteerimiseSilt);
                    break;
                case "manifest":
                    vBox.setLayoutY(30);

                    menüüSilt = looNupp("menu");
                    lühiInfo = manifestiInfo();
                    tekst.setFill(värvA);
                    tekst.setY(60);
                    tekst.setX(160);
                    animatsioon = new Transition() {
                        {
                            setCycleDuration(Duration.millis(5000));
                        }

                        protected void interpolate(double murd) {
                            int n = Math.round(lühiInfo.length() * (float) murd);
                            tekst.setText(lühiInfo.substring(0, n));
                        }
                    };
                    vBox.getChildren().add(menüüSilt);
                    break;
            }
        }

        private static Sisu loo(String leht) {
            Sisu content = new Sisu(leht);
            BorderPane.setAlignment(content.vBox,Pos.TOP_LEFT);
            BorderPane.setAlignment(content.tekst,Pos.CENTER);

            content.äärisPaneel.setTop(content.vBox);
            content.äärisPaneel.setCenter(content.tekst);
            content.äärisPaneel.setPrefSize(640,340);
            content.äärisPaneel.setStyle("-fx-background-color: #282B5E;" +
                    "-fx-border-color: #282B5E, #00FFFF;" +
                    "-fx-border-width: 5, 5;" +
                    "-fx-border-radius: 0;" +
                    "-fx-border-insets: 0, 5;" +
                    "-fx-border-style: solid inside, dotted outside;"
            );

            return content;
        }

        private Label looNupp(String nupuNimi) {
            Label nupp = new Label();
            nupp.setTextFill(värvA);
            switch (nupuNimi) {
                case "enc":
                    nupp.setText("[0] Encryption");
                    break;
                case "dec":
                    nupp.setText("[1] Decryption");
                    break;
                case "man":
                    nupp.setText("[2] Manifest");
                    break;
                case "git":
                    nupp.setText("[3] Github page");
                    break;
                case "qut":
                    nupp.setText("[4] Quit");
                    break;
                case "menu":
                    nupp.setText("[0] To Menu");
                    break;
                case "selOneE":
                    nupp.setText("[1] Select one fail to encrypt");
                    break;
                case "selOneD":
                    nupp.setText("[1] Select one fail to decrypt");
                    break;
                case "fenc":
                    nupp.setText("[2] Encrypt fail");
                    break;
                case "fdenc":
                    nupp.setText("[2] Decrypt fail");
                    break;
            }
            return nupp;
        }

        private String alguseInfo() {
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

        private String manifestiInfo() {
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