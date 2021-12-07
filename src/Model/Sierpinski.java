package Model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class Sierpinski {

    int ordre;
    int taille;
    int[][] tab;

    public Sierpinski(int t, int o){
        this.ordre = o;
        this.taille = t;
        tab = new int[(int) Math.pow(3, this.ordre)][(int) Math.pow(3, this.ordre)];
        init();
    }

    public void init(){
        for (int i = 0; i < this.tab.length;i++){
            for (int j = 0; j < this.tab[0].length;j++){
                this.tab[i][j] = 0;
            }
        }
    }

    public void build() throws IOException {
        construct(0, 0, this.tab.length, 0 , this.tab.length);

        var img=new BufferedImage((int)this.tab.length, (int)this.tab.length, BufferedImage.TYPE_INT_RGB);
        for (int i = 0;i<this.tab.length;i++){
            for (int j = 0; j< this.tab[0].length;j++){
                if (this.tab[i][j] == 0){
                    img.setRGB(i,j, Color.black.getRGB());
                } else {
                    img.setRGB(i, j, Color.YELLOW.getRGB());
                }
            }
        }

        File file = new File("Sierp.png");

        ImageIO.write(img, "PNG", file);

    }

    public void construct(int compt, int i1, int i2, int j1, int j2){
        System.out.println(compt);
        if (compt < this.ordre){
            int li1 = i1 + (i2 - i1)/3;
            int li2 = i1 + 2*(i2 - i1)/3;
            if (i2 - i1 == 3){
                li1 = i1 + 1;
                li2 = i1 + 2;
            }

            int lj1 = j1 + (j2 - j1)/3;
            int lj2 = j1 +2*(j2 - j1)/3;
            if (j2 - j1 == 3){
                lj1 = j1 + 1;
                lj2 = j1 + 2;
            }
            System.out.println("li1 :"+li1+", li2 :"+li2+", lj1 :"+lj1+", lj2 :"+lj2);
            for (int i = li1; i < li2; i++) {
                for (int j = lj1; j < lj2; j++) {
                    this.tab[i][j] = 1;
                }
            }
            int nv = compt + 1;
            construct(nv, i1 , li1, j1, lj1);
            construct(nv, i1 , li1, lj1, lj2);
            construct(nv, i1 , li1, lj2, j2);
            construct(nv, li1 , li2, j1, lj1);
            construct(nv, li1 , li2, lj2, j2);
            construct(nv, li2 , i2, j1, lj1);
            construct(nv, li2 , i2, lj1, lj2);
            construct(nv, li2 , i2, lj2, j2);
        }
    }

}
