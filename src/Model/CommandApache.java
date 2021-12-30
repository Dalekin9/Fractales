package Model;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class CommandApache {

    public CommandApache(){}

    public static Options createOptionDemarrage(){
        Options options = new Options();

        //Graphique
        options.addOption(Option.builder("G")
                .longOpt("graph")
                .hasArg(false)
                .argName("graphique")
                .desc("Mode Graphique")
                .required(false)
                .build());

        //Console
        options.addOption(Option.builder("C")
                .longOpt("cons")
                .hasArg(false)
                .required(false)
                .desc("Mode Console")
                .argName("console")
                .build());

        //rectangle
        options.addOption(Option.builder("r")
                .longOpt("rect")
                .hasArg(true)
                .desc("Borne du rectangle,"+
                        "\nforme x1;x2;y1;y2")
                .argName("rectangle")
                .required(false)
                .build());

        //pas
        options.addOption(Option.builder("p")
                .longOpt("pas")
                .hasArg(true)
                .desc("Pas de discretisation")
                .argName("pas")
                .required(false)
                .build());

        //constante
        options.addOption(Option.builder("cst")
                .longOpt("const")
                .hasArg(true)
                .desc("""
                        Constante de la fonction\s
                        forme -> r;i\s
                        ou r et i = valeurs de la partie reelle/img""")
                .argName("constante")
                .required(false)
                .build());

        //fonction
        options.addOption(Option.builder("fo")
                .longOpt("fonct")
                .hasArg(true)
                .desc("""
                        Fonction pour la fractale,\s
                        forme 3x2+x+c,\s
                        avec c obligatoire""")
                .argName("fonction")
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

        //coloration
        options.addOption(Option.builder("col")
                .longOpt("color")
                .hasArg(true)
                .desc("""
                        Choix de la coloration :\s
                        0(Noir/Blanc),
                        1(Rouge),
                        2(Bleu),
                        3(Vert),
                        4(Multicolor),
                        5(Rose/Orange),
                        6(Orange/Rose),
                        7(Vert/Bleu),
                        8(Vert/Rose),
                        9(Special)""")
                .argName("coloration")
                .required(false)
                .build());

        //fichier
        options.addOption(Option.builder("fi")
                .longOpt("fic")
                .hasArg(true)
                .desc("Nom du fichier de l'image")
                .argName("fichier")
                .required(false)
                .build());

        //type de la fractale
        options.addOption(Option.builder("t")
                .longOpt("type")
                .hasArg(true)
                .desc("Type de Fractale : " +
                        "\nJ, M ou S")
                .argName("type")
                .required(false)
                .build());

        //ordre (Sierpinski)
        options.addOption(Option.builder("o")
                .longOpt("ord")
                .hasArg(true)
                .desc("Ordre du Tapis de Sierpinski")
                .argName("ordre")
                .required(false)
                .build());

        //help
        options.addOption(Option.builder("h")
                .longOpt("help")
                .argName("aide")
                .desc("Aide pour les commandes/arguments")
                .build());

        return options;
    }
}
