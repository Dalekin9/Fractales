package Model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Fractal {

    /*
     * ***************************************************** *
     *                Fonctions abstraites
     * ***************************************************** *
     */

    public abstract double[][] getTableau();

    public abstract String getFichier();

    public abstract int coloration(int val);

    public abstract double[][] createRect();

    public abstract BufferedImage createImg(double[][] tab_ind);

    public abstract void writeFileTxt() throws IOException;

    /*
     * ***************************************************** *
     *                  Fonctions communes
     * ***************************************************** *
     */

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
        BufferedImage img = createImg(this.getTableau());

        File file = new File(this.getFichier()+".png");

        ImageIO.write(img, "PNG", file);
        writeFileTxt();
    }

}
