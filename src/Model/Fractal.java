package Model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public abstract class Fractal {

    protected double[][] rect;
    protected double pas; //pas pr la fonction
    protected int iter; //nb d'iteration max de la fonction
    protected int color; // 0 (noir/blanc) 1 (rouge) 2 (bleu) 3 (vert) 4 (multicolor)
    protected String fic; //nom pour le fichier
    protected Fonction function;

    /*********************
     Fonctions propre a chaque Fractale
     *********************/

    public int coloration(int val) {
        return Color.black.getRGB();
    }

    public int[][] createRect(){
        return new int[0][0];
    }

    public BufferedImage createImg(int[][] tab){return null;}

    public void writeFileTxt() throws IOException {}

    /*********************
    Fonctions communes aux Fractales
     **********************/

    public double chercheMult(double pas){
        String val = String.valueOf(pas);
        System.out.println(val.length());
        System.out.println(val.length()+"+"+val+"+"+val.length());
        if (val.length() < 3){
            return 1;
        } else {
            boolean virgule = false;
            double compt = 0.0;
            int i=0;
            while (i != val.length()){
                if (val.charAt(i) == '.'){
                    if (val.length() == i + 2){
                        if (val.charAt(i+1) == '0'){
                            return 1;
                        }
                    }
                    virgule = true;
                }
                else if (virgule){
                    compt++;
                }
                i++;
            }
            if (compt == 0){
                return 1;
            } else {
                return Math.pow(10.0,compt);
            }
        }
    }

    public void launchFractale() throws IOException {
        int[][] tab = createRect();

        BufferedImage img = createImg(tab);

        File file = new File(this.fic+".png");

        ImageIO.write(img, "PNG", file);
        writeFileTxt();
    }

}
