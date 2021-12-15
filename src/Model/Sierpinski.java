package Model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class Sierpinski extends Fractal{

    int ordre;
    int[][] tab;

    public Sierpinski(BuilderFractal builderFractal){
        super();
        this.color = builderFractal.color;
        this.fic = builderFractal.fic;
        this.ordre = builderFractal.ordre;
        this.tab = new int[(int) Math.pow(3, this.ordre)][(int) Math.pow(3, this.ordre)];
        init();
    }

    public void init(){
        for (int i = 0; i < this.tab.length;i++){
            for (int j = 0; j < this.tab[0].length;j++){
                this.tab[i][j] = 0;
            }
        }
    }


    public int[][] construct(int compt, int i1, int i2, int j1, int j2){
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
        return this.tab;
    }


    @Override
    public int[][] createRect(){
        return construct(0, 0, this.tab.length, 0 , this.tab.length);
    }

    @Override
    public BufferedImage createImg(int[][] tab_ind){
        BufferedImage img = new BufferedImage(this.tab.length, this.tab.length, BufferedImage.TYPE_INT_RGB);
        for (int i = 0;i<this.tab.length;i++){
            for (int j = 0; j< this.tab[0].length;j++){
                int c = this.coloration(tab_ind[i][j]);
                if (this.tab[i][j] == 0){
                    img.setRGB(i,j,c);
                } else {
                    img.setRGB(i,j,Color.BLACK.getRGB());
                }
            }
        }
        return img;
    }

    @Override
    public int coloration(int val) {
        switch (this.color) {
            case 0 -> {
                return Color.WHITE.getRGB();
            }
            case 1 -> {
                return Color.RED.getRGB();
            }
            case 2 -> {
                return Color.BLUE.getRGB();
            }
            case 3 -> {
                return Color.GREEN.getRGB();
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
        writer.println("Type : Sierpinski\n");
        writer.println("Ordre : "+this.ordre);
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
