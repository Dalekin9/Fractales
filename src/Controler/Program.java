package Controler;

import Model.Complex;
import Model.Function;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Program {

    static int MAX_ITER = 1000;
    static double RADIUS = 2.;
    static Function f;

    public Program(Function f){
        Program.f = f;
    }

    public int divergenceIndex (Complex z0){
        int ite = 0;
        Complex zn = z0;
        // sortie de boucle si divergence
        while (ite < MAX_ITER - 1 && zn.module() <=RADIUS){
            zn = f.img(zn.mul(zn));
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


    public void createFractale(double[] rect_s, double pas, String fic) throws IOException {
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
                int r= (255*tab_ind[i][j])/1000;
                int g= (400*tab_ind[i][j])/1000;
                int b= (300*tab_ind[i][j])/1000;
                int col =  (r << 16) | (g << 8) | b;
                img.setRGB(i,j,col);
            }
        }

        if (fic.equals("")){
            fic = "Fractale";
        }
        File file = new File(fic+".png");

        ImageIO.write(img, "PNG", file);
        writeFileTxt(rect,pas,f,fic);
    }

    public void writeFileTxt(double[][] rect, double pas, Function f,String name) throws IOException {
        File file = new File(name+".txt");
        PrintWriter writer = new PrintWriter(file, StandardCharsets.UTF_8);
        writer.println("Descriptif de la Fractale :\n");
        writer.println("Rect : [ ["+rect[0][0]+", "+rect[0][1]+"], ["+rect[1][0]+", "+rect[1][1]+"] ]");
        writer.println("Pas : "+pas);
        writer.println("Fonction : x^2 + ("+f.getC()+")");
        writer.close();
    }

}
