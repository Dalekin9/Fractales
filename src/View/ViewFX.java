package View;

import Controller.ControllerG;
import Model.Fractal;
import Model.Sierpinski;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.embed.swing.SwingFXUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class ViewFX {

    private final ControllerG control;
    private Stage mainStage;
    Pane previewImage = new Pane();
    ChoiceBox<String> colorChoice;
    TextField fieldOrdre;
    ArrayList<TextField> textFields = new ArrayList<>();
    ArrayList<TextField> julMandOpt = new ArrayList<>();
    ArrayList<TextField> sierpOpt = new ArrayList<>();


    public ViewFX(ControllerG control){
        this.control = control;
    }

    public void lockAll(){
        textFields.forEach(e -> {
            e.setFocusTraversable(false);
            e.setDisable(true);
        });
        colorChoice.setDisable(true);
    }


    public void unlockJulMand(){
        julMandOpt.forEach(e -> {
            e.setBackground(Background.EMPTY);
            e.setStyle("-fx-text-box-border: #000000; -fx-focus-color: #000000;");
            e.setDisable(false);
        });
    }

    public void unlockSierp(){
        sierpOpt.forEach(e -> {
            e.setBackground(Background.EMPTY);
            e.setStyle("-fx-text-box-border: #000000; -fx-focus-color: #000000;");
            e.setDisable(false);
        });
    }

    public void showSetMenu(){

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
            iv.resize(500,500);
            previewImage.resize(500,500);
            previewImage.getChildren().clear();
            previewImage.getChildren().add(iv);

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
            if(typechoice.getValue() == null) {
                showAlertType();
                return;
            }
            opt.add(typechoice.getValue());
            opt.add(fieldFic.getText());
            opt.add(colorChoice.getValue());

            switch (typechoice.getValue()){
                case "Julia","Mandelbrot" -> {
                    opt.add(fieldConstantR.getText() + ";" + fieldConstantI.getText());
                    opt.add(fieldRect.getText());
                    opt.add(fieldPas.getText());
                    opt.add(fieldFonc.getText());
                    opt.add(fieldIter.getText());
                }
                case "Sierpinski" -> {
                    opt.add(fieldOrdre.getText());
                }
            }
            showAlertOpt(control.fractalLaunch(opt));
            previewImage.getChildren().clear();
            mainStage.setAlwaysOnTop(true);
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
        Scene scene = new Scene(root,1000,600);
        mainStage.setScene(scene);
        mainStage.setAlwaysOnTop(false);
        mainStage.show();
    }


    public GridPane createControlPanel(){
        GridPane main = new GridPane();
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            showSetMenu();
        });

        Button saveButton = new Button("Save this fractal");

        Button zoomIn,zoomOut;
        zoomIn = new Button("+");
        zoomIn.setOnAction(e -> control.requestZoomIn());
        zoomOut = new Button("-");
        zoomOut.setMinSize(zoomIn.getWidth(), zoomIn.getHeight());
        zoomOut.setOnAction(e -> control.requestZoomOut());

        HBox zoomButt = new HBox();
        zoomButt.setSpacing(3);
        zoomButt.getChildren().addAll(zoomIn,zoomOut);

        GridPane.setHalignment(zoomButt,HPos.CENTER);
        GridPane.setHalignment(saveButton,HPos.CENTER);
        GridPane.setHalignment(backButton,HPos.CENTER);
        main.add(zoomButt,0,0,2,1);
        main.add(saveButton,0,1,2,1);
        main.add(backButton,0,2,2,1);
        return main;
    }

    public WritableImage resizeIfNecessary(int width, int height, WritableImage image){
        WritableImage newImg = image;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight() - 50;
        double ratioW = screenWidth / width;
        double ratioH = screenHeight / height;
        if (ratioW < 1 || ratioH < 1){
            if(ratioW < ratioH){
                BufferedImage buffImage = SwingFXUtils.fromFXImage(newImg,null);
                java.awt.Image resultingImage = buffImage.getScaledInstance((int)screenWidth, (int)(ratioW * height), java.awt.Image.SCALE_SMOOTH);
                BufferedImage outputImage = new BufferedImage((int)screenWidth, (int)(ratioW * height), BufferedImage.TYPE_INT_RGB);
                outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
                newImg = SwingFXUtils.toFXImage(outputImage,null);
            }else{
                BufferedImage buffImage = SwingFXUtils.fromFXImage(newImg,null);
                java.awt.Image resultingImage = buffImage.getScaledInstance((int)(ratioH * width), (int)screenHeight, java.awt.Image.SCALE_SMOOTH);
                BufferedImage outputImage = new BufferedImage((int)(ratioH * width), (int)screenHeight, BufferedImage.TYPE_INT_RGB);
                outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
                newImg = SwingFXUtils.toFXImage(outputImage,null);
            }
        }
        return newImg;
    }

    public void showFractalS(Fractal fractal){
        double[][] tab_ind = fractal.getTableau();
        int tabLength = ((Sierpinski)fractal).getTab().length;

        WritableImage image = new WritableImage(tabLength,tabLength);
        ImageView view = new ImageView();
        for (int i = 0;i<tabLength;i++){
            for (int j = 0; j< tabLength;j++){
                int c = fractal.coloration((int)tab_ind[i][j]);
                if (((Sierpinski)fractal).getTab()[i][j] == 0){
                    image.getPixelWriter().setArgb(i,j,c);
                } else {
                    image.getPixelWriter().setArgb(i,j, java.awt.Color.BLACK.getRGB());
                }
            }
        }

        image = resizeIfNecessary(tabLength,tabLength, image);
        view.setImage(image);
        HBox pane = new HBox();
        pane.getChildren().addAll(view,createControlPanel());
        Scene scene = new Scene(pane, Math.min(tab_ind.length, image.getWidth()) + 100, Math.min(tab_ind[0].length, image.getHeight()));
        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()){
                case Z -> {
                    control.requestMove("UP");
                    System.out.println("WE HERE");
                }
                case S -> {
                    control.requestMove("DOWN");
                    System.out.println("WE HERE");
                }
                case Q -> {
                    control.requestMove("LEFT");
                    System.out.println("WE HERE");
                }
                case D -> {
                    control.requestMove("RIGHT");
                    System.out.println("WE HERE");
                }
            }
        });
        mainStage.setScene(scene);
        mainStage.show();
    }


    public void showFractalJM(Fractal fractal){
        double[][] tab_index = fractal.getTableau();
        WritableImage image = new WritableImage(tab_index[0].length, tab_index.length);
        ImageView view = new ImageView();

        for (int i = 0;i< tab_index.length;i++) {
            for (int j = 0; j < tab_index[0].length; j++) {
                int c = fractal.coloration((int) tab_index[i][j]);
                image.getPixelWriter().setArgb(j, i, c);
            }
        }
        image = resizeIfNecessary(tab_index[0].length, tab_index.length, image);
        view.setImage(image);
        HBox pane = new HBox();
        pane.getChildren().addAll(view,createControlPanel());
        Scene scene = new Scene(pane, Math.min(tab_index.length, image.getWidth()) + 100, Math.min(tab_index[0].length, image.getHeight()));
        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()){
                case Z -> control.requestMove("UP");

                case S -> control.requestMove("DOWN");

                case Q -> control.requestMove("LEFT");

                case D -> control.requestMove("RIGHT");
            }
        });
        mainStage.setScene(scene);
        mainStage.show();
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

    public void showAlertType(){
        Alert typeAlert = new Alert(Alert.AlertType.ERROR);
        typeAlert.setTitle("Fractal type is null");
        typeAlert.setHeaderText("Choose a fractal type!");
        typeAlert.showAndWait();
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

}


