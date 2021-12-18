package Controller;

import Model.*;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.LinkedList;

public class Launcher {

    static Options opt = Command.createOptionDemarrage();

    /*
     * ***************************************************** *
     * Verifie le format des arguments de la ligne de commande
     * ***************************************************** *
     */

    public static boolean isNumber(char a){
        return switch (a) {
            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> true;
            default -> false;
        };
    }

    //rectangle
    public static double[] correctFormatRect(String rect){
        String[] tab = rect.split(";");
        if (tab.length != 4){
            errorParsing();
        } else {
            try {
                return new double[]{Double.parseDouble(tab[0]), Double.parseDouble(tab[1]), Double.parseDouble(tab[2]), Double.parseDouble(tab[3])};
            } catch (Exception e){
                errorParsing();
            }
        }
        return null;
    }

    //pas
    public static double correctFormatPas(String pas){
        try {
            return Double.parseDouble(pas);
        } catch (Exception e){
            errorParsing();
        }
        return 0;
    }

    public static int correctFormatIte(String it){
        try {
            return Integer.parseInt(it);
        } catch (Exception e){
            errorParsing();
        }
        return -1;
    }

    //constante de fonction
    public static double[] correctFormatCst(String constante){

        String[] tab = constante.split(";");
        if (tab.length != 2){
            errorParsing();
        }else{
            try {
                return new double[]{Double.parseDouble(tab[0]), Double.parseDouble(tab[1])};
            } catch (Exception e){
                errorParsing();
            }
        }
        return null;

    }

    public static int nbX(String s){
        int c = 0;
        for (int i = 0; i<s.length();i++){
            if (s.charAt(i) == 'x'){
                c++;
            }
        }
        return c;
    }

    //fonction
    public static LinkedList<double[]> correctFormatFct(String fonction){
        String[] aftPlus = fonction.split("\\+");
        if (! aftPlus[aftPlus.length-1].equals("c")){
            errorParsing();
        }
        LinkedList<double[]> liste = new LinkedList<>();
        for (int i = 0;i<aftPlus.length;i++){
            double[] data = new double[2];
            if (aftPlus[i].equals("c")) {
                if (i != aftPlus.length - 1) {
                    errorParsing();
                } else {
                    return liste;
                }
            } else if (aftPlus[i].charAt(0) == 'x'){
                if (nbX(aftPlus[i]) != 1){
                    errorParsing();
                } else {
                    data[0] = 1;
                    if (aftPlus[i].length() == 1) {
                        data[1] = 1;
                        liste.add(i, data);
                    } else {
                        String[] res = aftPlus[i].split("x");
                        try {
                            data[1] = Double.parseDouble(res[1]);
                            liste.add(i, data);
                        } catch (Exception e) {
                            errorParsing();
                        }
                    }
                }
            } else {
                if (nbX(aftPlus[i]) != 1){
                    errorParsing();
                } else {
                    String[] res = aftPlus[i].split("x");
                    try {
                        data[0] = Double.parseDouble(res[0]);
                        if (res.length == 1) {
                            data[1] = 1;
                        } else {
                            data[1] = Double.parseDouble(res[1]);
                        }
                        liste.add(i, data);
                    } catch (Exception e) {
                        errorParsing();
                    }
                }
            }
        }
        errorParsing();
        return null;
    }

    //coloration
    public static int correctFormatColor(String c){
        if (c.equals("0") || c.equals("1") || c.equals("2") || c.equals("3") || c.equals("4") ){
            return Integer.parseInt(c);
        } else {
            errorParsing();
        }
        return -1;
    }

    //coloration
    public static String correctFormatType(String c){
        if (c.equals("J") || c.equals("M") || c.equals("S")){
            return c;
        } else {
            errorParsing();
        }
        return "";
    }

    //coloration
    public static int correctFormatOrdre(String c){
        try {
            int o = (int)Double.parseDouble(c);
            if (o < 0){
                errorParsing();
            } else {
                return o;
            }
        }catch(NumberFormatException e) {
            System.out.println("It is not numerical string");
            errorParsing();
        }
        return -1;
    }


    /*
     * ****************************** *
     * Partie du lancement du Programme
     * ****************************** *
     */

    public static void errorParsing(){
        System.out.println("Error parsing command-line arguments!");
        System.out.println("Please, follow the instructions below:");
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("FractaleCLI", opt, true);
        System.exit(0);
    }

    public static void main(String[] args) throws IOException {

        //parser la ligne de commande
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(opt, args);
            //graphique
            if (cmd.hasOption("G")){
                if (cmd.hasOption("C") || cmd.hasOption("r") || cmd.hasOption("cst") || cmd.hasOption("p") || cmd.hasOption("h") || cmd.hasOption("f") ){
                    errorParsing();
                } else {
                    //lancer le mode graphique
                    System.out.println("Mode graphique");
                }
            }
            //console
            else if (cmd.hasOption( "C")){
                if (cmd.hasOption("G") || cmd.hasOption("h")){
                    errorParsing();
                }
                else if (cmd.hasOption("t")){
                    String type = correctFormatType(cmd.getOptionValue("t"));
                    //Julia ou Mandelbrot
                    if (type.equals("J") || type.equals("M")){
                        if (cmd.hasOption("o")) {
                            errorParsing();
                        }
                        else if (cmd.hasOption("cst") && cmd.hasOption("r") && cmd.hasOption("p")){
                            double[] cst = correctFormatCst(cmd.getOptionValue("cst"));
                            double[] r = correctFormatRect(cmd.getOptionValue("r"));
                            double p = correctFormatPas(cmd.getOptionValue("p"));

                            if (r[0] >= r[1] || r[2] >= r[3]){
                                errorParsing();
                            } else if (r[0] + p >= r[1] || r[2] + p >= r[3]){
                                errorParsing();
                            } else if (p == 0.0) {
                                errorParsing();
                            } else if (p < 0){
                                errorParsing();
                            }

                            BuilderFractal fractale = new BuilderFractal();
                            fractale = fractale.rect(r).type(type).pas(p);

                            if (cmd.hasOption("fi")){
                                String fic = cmd.getOptionValue("fi");
                                fractale = fractale.fichier(fic);
                            }

                            Fonction.BuilderFonction fonction = new Fonction.BuilderFonction(new Complex.Builder(cst[0],cst[1] ).build());
                            if (cmd.hasOption("fo")) {
                                LinkedList<double[]> fo = correctFormatFct(cmd.getOptionValue("fo"));
                                fonction = fonction.coef(fo);
                            }
                            fractale = fractale.fonction(fonction.build());

                            if (cmd.hasOption("col")){
                                int color = correctFormatColor(cmd.getOptionValue("col"));
                                fractale = fractale.coloration(color);
                            }

                            if (cmd.hasOption("it")){
                                int ite = correctFormatIte(cmd.getOptionValue("it"));
                                fractale = fractale.iter(ite);
                            }

                            Fractal fractal = fractale.build();
                            fractal.launchFractale();

                        }
                        else {
                            errorParsing();
                        }
                    }
                    //Sierpinski
                    else if (type.equals("S")){
                        if (cmd.hasOption("o") && cmd.getArgs().length < 1){
                            int ordre = correctFormatOrdre(cmd.getOptionValue("o"));
                            BuilderFractal fractale = new BuilderFractal();
                            fractale = fractale.type(type).ordre(ordre);

                            if (cmd.hasOption("fi")){
                                String fic = cmd.getOptionValue("fi");
                                fractale = fractale.fichier(fic);
                            }

                            if (cmd.hasOption("col")){
                                int color = correctFormatColor(cmd.getOptionValue("col"));
                                fractale = fractale.coloration(color);
                            }

                            Fractal fractal = fractale.build();
                            fractal.launchFractale();

                        }
                        else {
                            errorParsing();
                        }
                    }
                    else {
                        errorParsing();
                    }
                } else {
                    errorParsing();
                }
            }
            //help
            else if (cmd.hasOption("h") && cmd.getArgs().length < 1){
                final HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("FractaleCLI", opt, true);
                System.exit(0);
            } else {
                errorParsing();
            }
        } catch (ParseException e) {
            errorParsing();
        }
    }

}
