package Controler;

import Model.Complex;
import Model.Function;
import org.apache.commons.cli.*;

import java.io.IOException;

public class Launcher {

    static Options opt = Command.createOptionDemarrage();

    /*
     * ***************************************************** *
     * Verifie le format des arguments de la ligne de commande
     * ***************************************************** *
     */

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

    public static double correctFormatPas(String pas){
        try {
            return Double.parseDouble(pas);
        } catch (Exception e){
            errorParsing();
        }
        return 0;
    }

    public static boolean isNumber(char a){
        return switch (a) {
            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> true;
            default -> false;
        };
    }

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
            if (cmd.hasOption("G")){
                if (cmd.hasOption("C") || cmd.hasOption("r") || cmd.hasOption("cst") || cmd.hasOption("p") || cmd.hasOption("h") || cmd.hasOption("f") ){
                    errorParsing();
                } else {
                    //lancer le mode graphique
                    System.out.println("Mode graphique");
                }
            } else if (cmd.hasOption( "C")){
                if (cmd.hasOption("G") || cmd.hasOption("h")){
                    errorParsing();
                } else if (cmd.hasOption("cst") && cmd.hasOption("r") && cmd.hasOption("p")){
                    double[] cst = correctFormatCst(cmd.getOptionValue("cst"));
                    double[] r = correctFormatRect(cmd.getOptionValue("r"));
                    double p = correctFormatPas(cmd.getOptionValue("p"));

                    Program program = new Program(new Function(new Complex.Builder(cst[0],cst[1]).build()));
                    if (cmd.hasOption("f")) {
                        program.createFractale(r, p, cmd.getOptionValue("f"));
                    } else {
                        program.createFractale(r, p, "");
                    }
                } else {
                    errorParsing();
                }

                //lancer le mode console
            } else if (cmd.hasOption("h")){
                final HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("FractaleCLI", opt, true);
                System.exit(0);
            }
        } catch (ParseException e) {
            errorParsing();
        }

    }

}
