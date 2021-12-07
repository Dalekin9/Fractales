package Model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public abstract class Fractal {

    protected double[][] rect;
    protected double pas; //pas pr la fonction
    protected int iter; //nb d'iteration max de la fonction
    protected int color; // 0 (noir/blanc) 1 (rouge) 2 (bleu) 3 (vert) 4 (multicolor)
    protected String fic; //nom pour le fichier
    protected Fonction function;
    protected int type;

    /*
     Fonctions propre a chaque Fractale
     */

    public int coloration(int val) {
        return 0;
    }

    /*
    Fonctions communes aux Fractales
     */

    public int divergenceIndex (Complex z0){
        int ite = 0;
        Complex zn = z0;
        // sortie de boucle si divergence
        while (ite < this.iter - 1 && zn.module() <=2){
            zn = this.function.apply(zn);
            ite++;
        }
        return ite;
    }

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
        double w = (this.rect[0][1] - this.rect[0][0])/this.pas;
        double h = (this.rect[1][1] - this.rect[1][0])/this.pas;
        int[][] tab_ind = new int[(int)w][(int)h];
        int compi=0;
        double mult = chercheMult(this.pas);
        System.out.println(mult);
        double rl = this.rect[0][0];
        while (rl < this.rect[0][1]){
            int compj = 0;
            double i = this.rect[1][0];
            while( i < this.rect[1][1]){
                Complex c = new Complex.Builder(rl,i).build();
                int ind = this.divergenceIndex(c);
                tab_ind[compi][compj] = ind;
                compj++;
                i = Math.round( (i + this.pas)*mult ) / mult;
            }
            compi++;
            rl = Math.round( (rl + this.pas)*mult ) / mult;
        }

        var img=new BufferedImage((int)w, (int)h, BufferedImage.TYPE_INT_RGB);
        for (int i = 0;i<tab_ind.length;i++){
            for (int j = 0; j< tab_ind[0].length;j++){
                img.setRGB(i,j,this.coloration(tab_ind[i][j]));
            }
        }

        File file = new File(this.fic+".png");

        ImageIO.write(img, "PNG", file);
        writeFileTxt();
    }

    public void writeFileTxt() throws IOException {
        File file = new File(this.fic+".txt");
        PrintWriter writer = new PrintWriter(file, StandardCharsets.UTF_8);
        writer.println("Descriptif de la Fractale :\n");
        writer.println("Rect : [ ["+this.rect[0][0]+", "+this.rect[0][1]+"], ["+this.rect[1][0]+", "+this.rect[1][1]+"] ]");
        writer.println("Pas : "+this.pas);
        writer.println("Fonction : "+ this.function);
        writer.close();
    }

}
