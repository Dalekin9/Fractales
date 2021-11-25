package Controler;

import Model.Complex;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        /*
        prendre un rectangle du plan complexe (ex [−1, 1]×[−1, 1])
        prendre des points du rectangles avec un pas h (ex h= 0.01)
        obtention d'une grille de points (ex 201*201 points)
        pour chaque points z :
            calcule de ind=divergenceIndex(z)
            colorie le pixel de z selon la valeur de int (definir code couleur)
        obtention de l'image de julia
         */

        int[][] rect = { {-1,0}, {-1,0}};
        double pas = 0.1;
        int w = 150;
        int h = 150;
        int[][] tab_ind = new int[w][h];
        int compi=0;
        int compj=0;
        for (double r = rect[0][0]; r < rect[0][1];r+=pas){
            for (double i = rect[1][0]; i < rect[1][1];i+=pas){
                Complex c = new Complex.Builder(r,i).build();
                int ind = Program.divergenceIndex(c);
                tab_ind[compi][compj] = ind;
                compj++;
            }
            compi++;
        }

        var img=new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        for (int i = 0;i<tab_ind.length;i++){
            for (int j = 0; j< tab_ind[0].length;j++){
                int r= (int) (Math.random()*255); int g=(int) (Math.random()*255); int b=(int) (Math.random()*255);
                int col =  (r << 16) | (g << 8) | b;
                img.setRGB(i,j,col);
            }
        }
        File f = new File("FileTest.png");
        ImageIO.write(img, "PNG", f);


    }

}
