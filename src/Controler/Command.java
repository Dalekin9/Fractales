package Controler;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class Command {

    public Command(){}

    public static Options createOptionConsole(){
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

        //fichier
        options.addOption(Option.builder("f")
                .longOpt("fic")
                .hasArg(true)
                .desc("Nom du fichier de l'image ")
                .argName("fichier")
                .required(false)
                .build());

        return options;
    }

    public static Options createOptionDemarrage(){
        Options options = new Options();

        //graphique
        options.addOption(Option.builder("G")
                .longOpt("graph")
                .hasArg(false)
                .desc("Mode Graphique")
                .build());

        //console
        options.addOption(Option.builder("C")
                .longOpt("cons")
                .hasArg(false)
                .desc("Mode Console")
                .build());

        //rectangle
        options.addOption(Option.builder("r")
                .longOpt("rect")
                .hasArg(true)
                .desc("Borne du rectangle")
                .argName("rectangle")
                .required(false)
                .build());
        //constante
        options.addOption(Option.builder("cst")
                .longOpt("const")
                .hasArg(true)
                .desc("Constante de la fonction")
                .argName("constante")
                .required(false)
                .build());
        //pas
        options.addOption(Option.builder("p")
                .longOpt("pas")
                .hasArg(true)
                .desc("Pas de discrétisation")
                .argName("pas")
                .required(false)
                .build());
        //help
        options.addOption(Option.builder("h")
                .longOpt("help")
                .build());

        //fichier
        options.addOption(Option.builder("f")
                .longOpt("fic")
                .hasArg(true)
                .desc("Nom du fichier de l'image ")
                .argName("fichier")
                .required(false)
                .build());

        return options;
    }
}
