package Controler;

import Model.Complex;
import Model.Function;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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


    public void createFractale(double[] rect_s, double pas, String fic) throws IOException {
        double[][] rect = {{rect_s[0],rect_s[1]}, {rect_s[2],rect_s[3]}};
        double w = (rect[0][1] - rect[0][0])/pas;
        double h = (rect[1][1] - rect[1][0])/pas;
        int[][] tab_ind = new int[(int)w][(int)h];
        int compi=0;

        for (double r = rect[0][0]; r < rect[0][1];r+=pas){
            int compj=0;
            for (double i = rect[1][0]; i < rect[1][1];i+=pas){
                Complex c = new Complex.Builder(r,i).build();
                int ind = this.divergenceIndex(c);
                tab_ind[compi][compj] = ind;
                compj++;
            }
            compi++;
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

        File f;
        if (fic.equals("")){
            f = new File("Fractale.png");
        } else {
            f = new File(fic);
        }
        ImageIO.write(img, "PNG", f);
    }

}
