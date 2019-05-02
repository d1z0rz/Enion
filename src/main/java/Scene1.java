import javafx.animation.*;
import javafx.animation.PathTransition.OrientationType;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Scene1 extends Application {

    private static final double SIZE = 300;
    private final Content content = Content.create(SIZE);

    public void play() {
        content.animation.play();
    }

    private static final class Content {

        private static final Duration DURATION = Duration.seconds(2);
        private static final Color COLOR = Color.AQUA;
        private static final double WIDTH = 3;
        private final Group group = new Group();
        private final Rotate rx = new Rotate(0, Rotate.X_AXIS);
        private final Rotate ry = new Rotate(0, Rotate.Y_AXIS);
        private final Rotate rz = new Rotate(0, Rotate.Z_AXIS);
        private final Shape circle, arrow, lock;
        private final Label label;
        private final Animation animation;

        private static Content create(double size) {
            Content c = new Content(size);
            c.group.getChildren().addAll(c.arrow, c.circle,c.lock,c.label);
            c.group.getTransforms().addAll(c.rz, c.ry, c.rx);
            return c;
        }

        private Content(double size) {
            circle = createCircle(size);
            arrow = createShape();
            lock = createLock();
            label = createLabel();
            animation = new ParallelTransition(
                    createTransition(circle, arrow));
        }

        private Circle createCircle(double size) {
            Circle c = new Circle(size / 4);
            c.setFill(Color.TRANSPARENT);
            return c;
        }

        private Shape createShape() {
            Shape s = new Polygon(10,17.66,18.5,5,
                    25,5,30,0,35,0,40,5,60,5,
                    65,0,60,-5,55,-5,50,0,45,0,40,-5,
                    18.5,-5,10,-17.66,-10,-17.66,-20,0,-10,17.66);
            s.setStrokeWidth(WIDTH);
            s.setStroke(COLOR);
            s.setEffect(new Bloom());
            return s;
        }

        private Shape createLock(){
            Shape lock = new Polygon(35,5,35,-35,-35,-35,-35,5,-20,5,
                    -20,22.5,-7.5,35,7.5,35,20,22.5,
                    20,5);
            lock.setStrokeWidth(WIDTH);
            lock.setStroke(COLOR);
            lock.setEffect(new Bloom());
            return  lock;
        }

        private Label createLabel(){
            Label label = new Label("E");
            label.setFont(Font.font("Arial",35));
            label.setLayoutX(-12.5);
            label.setLayoutY(-35);
            label.setTextFill(COLOR);
            return label;
        }

        private Transition createTransition(Shape path, Shape node) {
            PathTransition t = new PathTransition(DURATION, path, node);
            t.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
            t.setCycleCount(Timeline.INDEFINITE);
            t.setInterpolator(Interpolator.LINEAR);
            return t;
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("JavaFX 3D");
        Scene scene = new Scene(content.group, SIZE, SIZE , true);
        primaryStage.setScene(scene);
        scene.setFill(Color.BLACK);
        scene.setOnMouseMoved((final MouseEvent e) -> {
            content.rx.setAngle(e.getSceneY() * 360 / scene.getHeight());
            content.ry.setAngle(e.getSceneX() * 360 / scene.getWidth());
        });
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setFarClip(SIZE * 6);
        camera.setTranslateZ(-2 * SIZE);
        scene.setCamera(camera);
        primaryStage.show();
        play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}