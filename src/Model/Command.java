package Model;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class Command {

    public Command(){}

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
        //fichier
        options.addOption(Option.builder("fi")
                .longOpt("fic")
                .hasArg(true)
                .desc("Nom du fichier de l'image ")
                .argName("fichier")
                .required(false)
                .build());
        //fonction
        options.addOption(Option.builder("fo")
                .longOpt("fonct")
                .hasArg(true)
                .desc("Fonction pour la fractale")
                .argName("fonction")
                .required(false)
                .build());
        //coloration
        options.addOption(Option.builder("col")
                .longOpt("color")
                .hasArg(true)
                .desc("Choix de la coloration")
                .argName("coloration")
                .required(false)
                .build());
        //nombre d'iteration
        options.addOption(Option.builder("it")
                .longOpt("iter")
                .hasArg(true)
                .desc("Nombre d'iterations de la fonction")
                .argName("iterations")
                .required(false)
                .build());
        //help
        options.addOption(Option.builder("h")
                .longOpt("help")
                .build());

        return options;
    }
}
