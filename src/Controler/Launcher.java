package Controler;

import Model.Command;
import Model.Complex;
import Model.Function;
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
                    System.out.println("1");
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
                            System.out.println("2");
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
                        System.out.println("3");
                        errorParsing();
                    }
                }
            }
        }
        System.out.println("4");
        errorParsing();
        return null;
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
                } else if (cmd.hasOption("cst") && cmd.hasOption("r") && cmd.hasOption("p")){
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

                    String fic = "";
                    if (cmd.hasOption("fi")){
                        fic = cmd.getOptionValue("fi");
                    }

                    LinkedList<double[]> fo = new LinkedList<>();
                    if (cmd.hasOption("fo")){
                        fo = correctFormatFct(cmd.getOptionValue("fo"));
                        System.out.println(fo.get(0)[0]+""+fo.get(0)[1]);
                    }

                    Program program = new Program(new Function(new Complex.Builder(cst[0],cst[1]).build(), fo));
                    program.createFractale(r, p, fic);

                } else {
                    errorParsing();
                }
            }
            //help
            else if (cmd.hasOption("h")){
                final HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("FractaleCLI", opt, true);
                System.exit(0);
            }
        } catch (ParseException e) {
            errorParsing();
        }
    }

}
