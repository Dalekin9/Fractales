package View;

import Controller.Controller;
import Model.BuilderFractal;
import Model.Complex;
import Model.Fonction;
import Model.Fractal;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class ViewFX extends Application {

    private Controller control;
    private Stage mainStage;
    private Fractal fractal;
    Pane previewImage = new Pane();
    ChoiceBox<String> colorChoice;
    TextField fieldOrdre;
    ArrayList<TextField> textFields = new ArrayList<>();
    ArrayList<TextField> julMandOpt = new ArrayList<>();
    ArrayList<TextField> sierpOpt = new ArrayList<>();


    public void lockAll(){
        textFields.forEach(e -> {
            e.setEditable(false);
            e.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY,new CornerRadii(5), new Insets(1))));
        });
        colorChoice.setDisable(true);
    }

    public void unlockJulMand(){
        julMandOpt.forEach(e -> {
            e.setEditable(true);
            e.setBackground(Background.EMPTY);
            e.setStyle("-fx-text-box-border: #000000; -fx-focus-color: #000000;");

        });
    }

    public void unlockSierp(){
        sierpOpt.forEach(e -> {
            e.setEditable(true);
            e.setBackground(Background.EMPTY);
            e.setStyle("-fx-text-box-border: #000000; -fx-focus-color: #000000;");
        });
    }

    public void setFractalMenu(Stage stage){
        GridPane root = new GridPane();

        root.setPadding(new Insets(20));
        root.setHgap(25);
        root.setVgap(20);


        /*
         * Création des éléments du layout
         */

        Label labelTitle = new Label("Let's create a fractal!");

        VBox typeBox = new VBox();
        typeBox.setAlignment(Pos.CENTER);
        typeBox.setSpacing(7);
        Label labelType = new Label("Type de fractale");
        ChoiceBox<String> typechoice = new ChoiceBox<>(FXCollections.observableArrayList(
                "Julia", "Mandelbrot", "Sierpinski"));
        typechoice.setOnAction(e ->{
            File file ;
            Image image;
            ImageView iv;
            switch (typechoice.getValue()) {
                case "Julia" -> {
                    file = new File("Julia.png");
                    lockAll();
                    unlockJulMand();
                }
                case "Mandelbrot" -> {
                    file = new File("Mandelbrot.png");
                    lockAll();
                    unlockJulMand();
                }
                case "Sierpinski" -> {
                    file = new File("Sierpinski.png");
                    lockAll();
                    unlockSierp();
                }
                default -> file = new File("White.png");
            }
            colorChoice.setDisable(false);
            image = new Image(file.toURI().toString());
            iv = new ImageView(image);
            previewImage.getChildren().add(iv);
            stage.show();
        });

        VBox pasBox = new VBox();
        pasBox.setAlignment(Pos.CENTER);
        pasBox.setSpacing(7);
        Label labelPas = new Label("Pas de discrétisation");
        TextField fieldPas = new TextField();
        fieldPas.setPromptText("0.001");
        textFields.add(fieldPas);
        julMandOpt.add(fieldPas);

        VBox iterBox = new VBox();
        iterBox.setAlignment(Pos.CENTER);
        iterBox.setSpacing(7);
        Label labelIter = new Label("Nombre d'itérations");
        TextField fieldIter = new TextField("500");
        fieldIter.setPrefWidth(170);
        textFields.add(fieldIter);
        julMandOpt.add(fieldIter);

        VBox colorBox = new VBox();
        colorBox.setAlignment(Pos.CENTER);
        colorBox.setSpacing(7);
        Label labelColor = new Label("Couleur");
        this.colorChoice = new ChoiceBox<>(FXCollections.observableArrayList(
                "Noir et Blanc","Bleu","Rouge","Vert","Multicolore"));
        this.colorChoice.setOnAction(e ->{

        });
        colorChoice.setValue("Noir et Blanc");

        //Box : définition du nom du fichier
        VBox ficBox = new VBox();
        ficBox.setAlignment(Pos.CENTER);
        ficBox.setSpacing(7);
        Label labelFic = new Label("Nom du fichier");
        TextField fieldFic = new TextField("Fractale");
        textFields.add(fieldFic);
        julMandOpt.add(fieldFic);
        sierpOpt.add(fieldFic);

        VBox rectBox = new VBox();
        rectBox.setAlignment(Pos.CENTER);
        rectBox.setSpacing(7);
        Label labelRect = new Label("Rectangle de visualisation");
        TextField fieldRect = new TextField();
        fieldRect.setPromptText("P1x;P2x;P1y;P2y");
        textFields.add(fieldRect);
        julMandOpt.add(fieldRect);


        VBox constantBox = new VBox();
        constantBox.setAlignment(Pos.CENTER);
        constantBox.setSpacing(7);

        Label labelConstant = new Label("Constante");
        Label labelIm = new Label("+ i");
        TextField fieldConstantR = new TextField();
        fieldConstantR.setPrefWidth(70);
        textFields.add(fieldConstantR);
        julMandOpt.add(fieldConstantR);

        fieldConstantR.setEditable(false);
        TextField fieldConstantI = new TextField();
        fieldConstantI.setPrefWidth(70);
        textFields.add(fieldConstantI);
        julMandOpt.add(fieldConstantI);
        HBox constant = new HBox();

        VBox foncBox = new VBox();
        foncBox.setAlignment(Pos.CENTER);
        foncBox.setSpacing(7);
        Label labelFonc = new Label("Fonction");
        TextField fieldFonc = new TextField();
        textFields.add(fieldFonc);
        julMandOpt.add(fieldFonc);

        VBox orderBox = new VBox();
        orderBox.setSpacing(7);
        orderBox.setAlignment(Pos.CENTER);
        Label labelOrder = new Label("Ordre");
        fieldOrdre = new TextField();
        textFields.add(fieldOrdre);
        sierpOpt.add(fieldOrdre);


        lockAll();

        Button loginButton = new Button("Créer votre fractale");
        loginButton.setOnAction(e -> {
            ArrayList<String> opt = new ArrayList<>();
            opt.add(typechoice.getValue());
            opt.add(fieldFic.getText());
            opt.add(colorChoice.getValue());

            switch (typechoice.getValue()){
                case "Julia","Mandelbrot" -> {
                    opt.add(fieldConstantR.getText() + "+" + fieldConstantI.getText());
                    opt.add(fieldRect.getText());
                    opt.add(fieldPas.getText());
                    opt.add(fieldFonc.getText());
                    opt.add(fieldIter.getText());
                    control.fractalLaunch(opt);
                }
                case "Sierpinski" -> {
                    opt.add(fieldOrdre.getText());
                    control.fractalLaunch(opt);
                }
            }
            showFractal(fractal, stage);
        });




        /*
         *
         * Setup des éléments du layout
         *
         */
        GridPane.setHalignment(labelTitle, HPos.CENTER);
        root.add(labelTitle, 0, 0, 3, 1);

        typeBox.getChildren().addAll(labelType,typechoice);
        root.add(typeBox,1,1,2,1);

        pasBox.getChildren().addAll(labelPas,fieldPas);
        root.add(pasBox,1,2,1,1);
        fieldPas.getParent().requestFocus();

        iterBox.getChildren().addAll(labelIter,fieldIter);
        root.add(iterBox,2,2,1,1);

        GridPane.setHalignment(colorBox, HPos.CENTER);
        colorBox.getChildren().addAll(labelColor,colorChoice);
        root.add(colorBox,1,3,1,1);

        ficBox.getChildren().addAll(labelFic,fieldFic);
        root.add(ficBox,2,3,1,1);


        GridPane.setHalignment(constantBox, HPos.CENTER);
        HBox.setMargin(labelIm, new Insets(0,10,0,10));
        constant.getChildren().addAll(fieldConstantR,labelIm,fieldConstantI);
        constantBox.getChildren().addAll(labelConstant,constant);
        root.add(constantBox,1,4,1,1);

        foncBox.getChildren().addAll(labelFonc,fieldFonc);
        root.add(foncBox,2,4,1,1);

        orderBox.getChildren().addAll(labelOrder,fieldOrdre);
        root.add(orderBox, 2,5,1,1);

        rectBox.getChildren().addAll(labelRect,fieldRect);
        root.add(rectBox,1,5,1,1);
        fieldRect.getParent().requestFocus();

        // Horizontal alignment for Login button.
        GridPane.setHalignment(loginButton, HPos.CENTER);
        root.add(loginButton, 1, 6,2,1);

        previewImage.setMinSize(500,500);
        previewImage.setPrefSize(500,500);
        root.add(previewImage,0,1,1,6);

        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.show();
    }


    public void showFractal(Fractal fractal,Stage stage){
        double[][] rectangle = fractal.getRect();
        double pas = fractal.getPas();
        double width = (rectangle[0][1] - rectangle[0][0])/pas;
        double height = (rectangle[1][1] -rectangle[1][0])/pas;
        int[][] tab_index = fractal.createRect();

        WritableImage image = new WritableImage((int)width, (int)height);
        ImageView view = new ImageView();
        for (int i = 0;i<tab_index.length;i++){
            for (int j = 0; j< tab_index[0].length;j++){
                int c = fractal.coloration(tab_index[i][j]);
                image.getPixelWriter().setArgb(i,j, c);
            }
        }
        view.setImage(image);
        Pane pane = new Pane();
        pane.getChildren().add(view);
        stage.setScene(new Scene(pane, width, height));
        stage.show();
    }


    @Override
    public void start(Stage stage) {
        stage.setTitle("Fractals");
        stage.setAlwaysOnTop(true);
        Fonction func = new Fonction.BuilderFonction(new Complex.Builder(0.285, 0.01).build()).build();
        double[] rect = {-1,1,-1,1};
        this.fractal = new BuilderFractal().type("J").rect(rect).pas(0.004).fonction(func).build();
        System.out.println(Arrays.deepToString(fractal.getRect()));
        setFractalMenu(stage);
    }


    public void showAlertOpt(ArrayList<String> errOpt){
        if (errOpt.isEmpty()){
            return;
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Champs non valides");
        alert.setHeaderText("Les champs suivants sont invalides :");
        alert.setContentText("");
        errOpt.forEach(e -> {
            switch (e) {
                case "cst" -> alert.setContentText(alert.getContentText() + "Constante\n");
                case "r" -> alert.setContentText(alert.getContentText() + "Rectangle de visualisation\n");
                case "p" -> alert.setContentText(alert.getContentText() + "Pas de discrétisation\n");
                case "fo" -> alert.setContentText(alert.getContentText() + "Fonction\n");
                case "it" -> alert.setContentText(alert.getContentText() + "Nombre d'itérations\n");
                case "o" -> alert.setContentText(alert.getContentText() + "Ordre\n");
                case "rp" -> alert.setContentText(alert.getContentText() + "Pas et Rectangle incompatibles\n");
            }
        });

        alert.showAndWait();
    }


    public static void main(String[] args) {
        launch();
    }

    public Stage getMainStage() {
        return mainStage;
    }
}


