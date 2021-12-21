package Model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class Mandelbrot extends Fractal{

    private final double[][] rect;
    private final double[][] data;
    private final double pas; //pas pr la fonction
    private final int iter; //nb d'iteration max de la fonction
    private final int color; // 0 (noir/blanc) 1 (rouge) 2 (bleu) 3 (vert) 4 (multicolor)
    private final String fic; //nom pour le fichier
    private final Fonction function;

    Mandelbrot(BuilderFractal builderFractal){
        this.rect = builderFractal.rect;
        this.pas = builderFractal.pas;
        this.iter = builderFractal.iter;
        this.color = builderFractal.color;
        this.fic = builderFractal.fic;
        this.function = builderFractal.function;
        this.data = createRect();
    }


    /*
     * ***************************************************** *
     *                     Fonctions
     * ***************************************************** *
     */


    public int divergenceIndex (Complex z0){
        int ite = 0;
        Complex zn = z0;
        // sortie de boucle si divergence
        while (ite < this.iter - 1 && zn.module() <=2){
            Fonction f = new Fonction.BuilderFonction(z0).coef(this.function.getCoeff()).build();
            zn = f.apply(zn);
            ite++;
        }
        return ite;
    }

    @Override
    public double[][] getTableau() {
        return this.data;
    }

    @Override
    public String getFichier() {
        return fic;
    }

    @Override
    public double[][] createRect(){
        double w = (this.rect[0][1] - this.rect[0][0])/this.pas;
        double h = (this.rect[1][1] - this.rect[1][0])/this.pas;
        double[][] tab_ind = new double[(int)w][(int)h];
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
        return tab_ind;
    }

    @Override
    public BufferedImage createImg(double[][] tab_ind){
        double w = (this.rect[0][1] - this.rect[0][0])/this.pas;
        double h = (this.rect[1][1] - this.rect[1][0])/this.pas;
        BufferedImage img = new BufferedImage((int)w, (int)h, BufferedImage.TYPE_INT_RGB);
        for (int i = 0;i<tab_ind.length;i++){
            for (int j = 0; j< tab_ind[0].length;j++){
                int c = this.coloration((int) tab_ind[i][j]);
                img.setRGB(j,i,c);
            }
        }
        return img;
    }

    @Override
    public int coloration(int val) {
        float s = (float) (30 + (val * 360) / this.iter) / 360;
        float bb = (float) (30 + (val * 360) / this.iter) / 360;
        if (val == this.iter) {
            return new Color(0, 0, 0).getRGB();
        }
        switch (this.color) {
            case 0 -> {
                int r = (255 * val) / this.iter;
                int g = (255 * val) / this.iter;
                int b = (255 * val) / this.iter;
                return new Color(r, g, b).getRGB();
            }
            case 1 -> {
                int res = -42 + (val * 84 / this.iter);
                if (res < 0) {
                    return Color.HSBtoRGB((float) (360 - res) / 360, s, bb);
                } else {
                    return Color.HSBtoRGB((float) (res) / 360, s, bb);
                }
            }
            case 2 -> {
                int res = 216 + (val * 120 / this.iter);
                return Color.HSBtoRGB((float) (res) / 360, s, bb);
            }
            case 3 -> {
                int res = 96 + (val * 120 / this.iter);
                return Color.HSBtoRGB((float) (res) / 360, s, bb);
            }
            case 4 -> {
                int res = (val * 360 / this.iter);
                return Color.HSBtoRGB((float) (res) / 360, 0.8F, 0.8F);
            }
            default -> {
                return Color.BLACK.getRGB();
            }
        }
    }

    @Override
    public void writeFileTxt() throws IOException {
        File file = new File(this.fic+".txt");
        PrintWriter writer = new PrintWriter(file, StandardCharsets.UTF_8);
        writer.println("Descriptif de la Fractale :\n");
        writer.println("Type : Mandelbrot\n");
        writer.println("Rect : [ ["+this.rect[0][0]+", "+this.rect[0][1]+"], ["+this.rect[1][0]+", "+this.rect[1][1]+"] ]");
        writer.println("Pas : "+this.pas);
        writer.println("Fonction : "+ this.function);
        writer.println("Iterations : "+ this.iter);
        if (this.color == 0) {
            writer.println("Coloration : Noire et Blanche");
        } else if (this.color == 1) {
            writer.println("Coloration : Rouge");
        } else if (this.color == 2) {
            writer.println("Coloration : Bleue");
        } else if (this.color == 3) {
            writer.println("Coloration : Verte");
        }
        writer.close();
    }
}
