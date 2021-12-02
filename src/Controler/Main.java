package Controler;

import Model.Complex;
import org.apache.commons.cli.*;

import javax.imageio.ImageIO;
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

        //suppose que on a tjr gauche < droite

        Options options = new Options();
        //rectangle
        options.addOption(Option.builder("r")
                .longOpt("rect")
                .hasArg(true)
                .desc("Borne du rectangle")
                .argName("rectangle")
                .required(true)
                .build());
        //constante
        options.addOption(Option.builder("c")
                .longOpt("const")
                .hasArg(true)
                .desc("Constante de la fonction")
                .argName("constante")
                .required(true)
                .build());
        //pas
        options.addOption(Option.builder("p")
                .longOpt("pas")
                .hasArg(true)
                .desc("Pas de discrétisation")
                .argName("pas")
                .required(true)
                .build());
        //help
        options.addOption(Option.builder("h")
                .longOpt("help")
                .build());

        //parser la ligne de commande
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);

            String rect_s = cmd.getOptionValue("r");
            System.out.println("Rect : " + rect_s);

            String constante = cmd.getOptionValue("c");
            System.out.println("Const : " + constante);

            String p = cmd.getOptionValue("p");
            System.out.println("Pas : " + p);

            // pour le rectangle
            int compt = 0;
            String rec = "";
            while(compt != rect_s.length()){
                if (rect_s.charAt(compt) == ','){
                    rec+="/";
                } else if (rect_s.charAt(compt) != ' ' && rect_s.charAt(compt) != '{' && rect_s.charAt(compt) != '}') {
                    rec+=rect_s.charAt(compt);
                }
                compt++;
            }
            System.out.println(rec);
            String[] res = rec.split("/");
            System.out.println("1 : "+res[0]+", 2 : "+res[1]+", 3 : "+res[2]+", 4 : "+res[3]);

            // pour la constante
            compt = 0;
            String co = "";
            while(compt != constante.length()){
                if (constante.charAt(compt) == '+'){
                    co+="/";
                } else if (constante.charAt(compt) != ' ' && constante.charAt(compt) != 'i') {
                    co+=constante.charAt(compt);
                }
                compt++;
            }
            System.out.println(co);
            String[] con = co.split("/");
            System.out.println("1 : "+con[0]+", 2 : "+con[1]);

            double[][] rect = {
                    {Double.parseDouble(res[0]),Double.parseDouble(res[1])},
                    {Double.parseDouble(res[2]),Double.parseDouble(res[3])}
            };
            double pas = Double.parseDouble(p);
            double w = (rect[0][1] - rect[0][0])/pas;
            double h = (rect[1][1] - rect[1][0])/pas;
            int[][] tab_ind = new int[(int)w][(int)h];
            int compi=0;

            for (double r = rect[0][0]; r < rect[0][1];r+=pas){
                int compj=0;
                for (double i = rect[1][0]; i < rect[1][1];i+=pas){
                    //System.out.println("i : "+i);
                    //System.out.println("nouveau i : "+i);
                    Complex c = new Complex.Builder(r,i).build();
                    int ind = Program.divergenceIndex(c);
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
                    //System.out.println("indice : "+tab_ind[i][j]);
                    //System.out.println(" r : "+r+", g : "+g+", b : "+b);
                    int col =  (r << 16) | (g << 8) | b;
                    img.setRGB(i,j,col);
                }
            }
            File f = new File("FileTest.png");
            ImageIO.write(img, "PNG", f);







            if (cmd.hasOption("h")){
                final HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("FractaleCLI", options, true);
                System.exit(0);
            }
        } catch (ParseException pe) {
            System.out.println("Error parsing command-line arguments!");
            System.out.println("Please, follow the instructions below:");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "FractaleCLI", options, true );
            System.exit(0);
        }

    }

}
