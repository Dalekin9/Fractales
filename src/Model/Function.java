package Model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class Function {

    // 5x² = {5,2}
    private LinkedList<double[]> coeff;
    //constante de la fonction
    private Complex c;
    //nb d'iteration max de la fonction
    private int iter;
    // 0 (noir/blanc) 1 (rouge) 2 (bleu) 3 (vert) 4 (multicolor)
    private int color;
    //type de fractale
    private int type;
    private String fic;

    public static class Builder {

        // Required parameters
        private Complex c;
        // Optional parameters - initialized to default values
        private LinkedList<double[]> coef = new LinkedList<>();
        private int iter = 1000;
        private int color = 0;
        private String fic = "Fractale";

        public Builder(Complex c){
            this.c = c;
        }

        public Builder fichier(String f){
            fic = f;
            return this;
        }

        public Builder coloration(int t){
            color = t;
            return this;
        }

        public Builder iter(int i){
            iter = i;
            return this;
        }

        public Builder coeff(LinkedList<double[]> l){
            coef = l;
            return this;
        }

        public Function build(){
            return new Function(this);
        }

    }

    private Function(Builder builder){
        // 5x² = {5,2}
        coeff = builder.coef;
        //constante de la fonction
        c = builder.c;
        //nb d'iteration max de la fonction
        iter = builder.iter;
        // 0 (noir/blanc) 1 (rouge) 2 (bleu) 3 (vert) 4 (multicolor)
        color = builder.color;
        //type de fractale
        //private int type;
        fic = builder.fic;
    }

    /*
    ------GETTERS------
     */

    public Complex getC() {
        return c;
    }

    public int getColor() {
        return color;
    }

    public int getIter() {
        return iter;
    }

    public LinkedList<double[]> getCoeff() {
        return coeff;
    }

    public String getFic() {
        return fic;
    }

    public int getType() {
        return type;
    }

    /*
    --------Fonctions---------
     */

    public Complex img(Complex z){
        Complex res = this.c;
        for (int i = 0; i < coeff.size(); i++){
            //System.out.println(coeff.get(i)[0]+" , "+coeff.get(i)[1]);
            Complex a = z.puissance(z,coeff.get(i)[1]);
            Complex b = new Complex.Builder(coeff.get(i)[0],0).build();
            Complex c = a.mul(b);
            res = res.add(c);
            //System.out.println(res);
        }
        return res;
    }

    public int divergenceIndex (Complex z0){
        int ite = 0;
        Complex zn = z0;
        // sortie de boucle si divergence
        while (ite < this.getIter() - 1 && zn.module() <=this.getIter()){
            zn = this.img(zn);
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

    public void createFractale(double[] rect_s, double pas) throws IOException {
        double[][] rect = {{rect_s[0],rect_s[1]}, {rect_s[2],rect_s[3]}};
        double w = (rect[0][1] - rect[0][0])/pas;
        double h = (rect[1][1] - rect[1][0])/pas;
        int[][] tab_ind = new int[(int)w][(int)h];
        int compi=0;
        double mult = chercheMult(pas);
        System.out.println(mult);
        double rl = rect[0][0];
        while (rl < rect[0][1]){
            int compj = 0;
            double i = rect[1][0];
            while( i < rect[1][1]){
                Complex c = new Complex.Builder(rl,i).build();
                int ind = this.divergenceIndex(c);
                tab_ind[compi][compj] = ind;
                compj++;
                i = Math.round( (i + pas)*mult ) / mult;
            }
            compi++;
            rl = Math.round( (rl + pas)*mult ) / mult;
        }

        var img=new BufferedImage((int)w, (int)h, BufferedImage.TYPE_INT_RGB);
        for (int i = 0;i<tab_ind.length;i++){
            for (int j = 0; j< tab_ind[0].length;j++){
                int r= (255*tab_ind[i][j])/this.getIter();
                int g= (255*tab_ind[i][j])/this.getIter();
                int b= (255*tab_ind[i][j])/this.getIter();
                int col =  (r << 16) | (g << 8) | b;
                img.setRGB(i,j,col);
            }
        }

        if (fic.equals("")){
            fic = "Fractale";
        }
        File file = new File(fic+".png");

        ImageIO.write(img, "PNG", file);
        writeFileTxt(rect,pas);
    }

    public void writeFileTxt(double[][] rect, double pas) throws IOException {
        File file = new File(this.fic+".txt");
        PrintWriter writer = new PrintWriter(file, StandardCharsets.UTF_8);
        writer.println("Descriptif de la Fractale :\n");
        writer.println("Rect : [ ["+rect[0][0]+", "+rect[0][1]+"], ["+rect[1][0]+", "+rect[1][1]+"] ]");
        writer.println("Pas : "+pas);
        writer.println("Fonction : x^2 + ("+ this.getC()+")");
        writer.close();
    }

}
