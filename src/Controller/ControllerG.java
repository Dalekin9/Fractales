package Controller;
import Model.*;
import View.ViewFX;

import java.util.ArrayList;
import java.util.LinkedList;

public class ControllerG {
    private ViewFX view;
    private ArrayList<String> fractaleOpt;

    public ControllerG(){}

    public int colorFromField(String str){
        switch (str){
            case "Rouge" -> {
                return 1;
            }
            case "Bleu" -> {
                return 2;
            }
            case "Vert" -> {
                return 3;
            }
            case "Multicolore" -> {
                return 4;
            }
            default -> {
                return 0;
            }
        }
    }

    public static double[] validCst(String constante){
        String[] tab = constante.split(";");
        if (tab.length != 2){
            return null;
        }else{
            try {
                return new double[]{Double.parseDouble(tab[0]), Double.parseDouble(tab[1])};
            } catch (Exception e){
                return null;
            }
        }
    }

    public static double[] validRect(String rect){
        double[] r;
        String[] tab = rect.split(";");
        if (tab.length != 4){
            return null;
        } else {
            try {
                r = new double[]{Double.parseDouble(tab[0]), Double.parseDouble(tab[1]), Double.parseDouble(tab[2]), Double.parseDouble(tab[3])};
            } catch (Exception e){
                return null;
            }
        }

        if (r[0] >= r[1] || r[2] >= r[3]){
            return null;
        }
        return r;
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

    public static LinkedList<double[]> correctFormatFct(String fonction){
        String[] aftPlus = fonction.split("\\+");
        if (! aftPlus[aftPlus.length-1].equals("c")){
            return null;
        }
        LinkedList<double[]> liste = new LinkedList<>();
        for (int i = 0;i<aftPlus.length;i++){
            double[] data = new double[2];
            if (aftPlus[i].equals("c")) {
                if (i != aftPlus.length - 1) {
                    return null;
                } else {
                    return liste;
                }
            } else if (aftPlus[i].charAt(0) == 'x'){
                if (nbX(aftPlus[i]) != 1){
                    return null;
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
                            return null;
                        }
                    }
                }
            } else {
                if (nbX(aftPlus[i]) != 1){
                    return null;
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
                        return null;
                    }
                }
            }
        }
        return null;
    }

    public static double validPas(String pas){
        double p;
        try {
            p = Double.parseDouble(pas);
        } catch (Exception e){
            return 0;
        }

        if (p == 0.0) {
            return 0;
        } else if (p < 0){
            return 0;
        }
        return p;
    }

    public int validOrder(String ordre){
        try {
            int o = (int)Double.parseDouble(ordre);
            if (o >= 0){
                return o;
            } else {
                return -1;
            }
        }catch(NumberFormatException e) {
            return -1;
        }
    }

    public static int validIte(String it){
        try {
            int i = Integer.parseInt(it);
            if (i < 1){
                return -1;
            }else{
                return i;
            }
        } catch (Exception e){
            return -1;
        }
    }

    public String fileName(String nomFic){
        if (nomFic.isEmpty()){
            return "Fractale";
        }
        for (int i = 0; i < nomFic.length(); i++){
            if (nomFic.charAt(i) != ' '){
                return nomFic;
            }
        }
        return "Fractale";
    }

    public ArrayList<String> fractalLaunch(ArrayList<String> opt){
        BuilderFractal fract = new BuilderFractal();
        ArrayList<String> err = new ArrayList<>();
        Fractal fractale;
        switch (opt.get(0)){
            case "Julia","Mandelbrot" -> {
                if(opt.get(0).equals("Julia")) {
                    fract = fract.type("J").fichier(fileName(opt.get(1))).coloration(colorFromField(opt.get(2)));
                }else{
                    fract = fract.type("M").fichier(fileName(opt.get(1))).coloration(colorFromField(opt.get(2)));
                }
                double[] cst = validCst(opt.get(3));
                double[] rect = validRect(opt.get(4));
                double pas = validPas(opt.get(5));

                if (pas == 0){
                    err.add("p");
                }else{
                    fract = fract.pas(pas);
                }

                if (rect == null){
                    err.add("r");
                }else{
                    fract = fract.rect(rect);
                    if (rect[0] + pas >= rect[1] || rect[2] + pas >= rect[3]) {
                        err.add("rp");
                    }
                }

                if (cst == null){
                    err.add("cst");
                    err.add("fo");
                }else{
                    Fonction.BuilderFonction fonction = new Fonction.BuilderFonction(new Complex.Builder(cst[0],cst[1] ).build());
                    LinkedList<double[]> fo = correctFormatFct(opt.get(6));
                    if (fo == null){
                        err.add("fo");
                    }else{
                        fonction.coef(fo);
                        fract = fract.fonction(fonction.build());
                    }
                }

                int ite = validIte(opt.get(7));
                if (ite == -1){
                    err.add("it");
                }else{
                    fract = fract.iter(ite);
                }
                if(err.isEmpty()) {
                    fractaleOpt = opt;
                    fractale = fract.build();
                    view.showFractalJM(fractale);
                }
            }
            case "Sierpinski" -> {
                fract = fract.type("S").fichier(fileName(opt.get(1))).coloration(colorFromField(opt.get(2)));
                int order = validOrder(opt.get(3));
                if (order == -1){
                    err.add("o");
                }else{
                    fract = fract.ordre(order);
                }

                if(err.isEmpty()) {
                    fractaleOpt = opt;
                    fractale = fract.build();
                    view.showFractalS(fractale);
                }
            }
        }

        return err;
    }

    public String rectArrayToString(double[] rect){
        StringBuilder rectangle = new StringBuilder();
        for (int i = 0; i<3; i++){
            rectangle.append(rect[i]).append(";");
        }
        rectangle.append(rect[3]);
        return rectangle.toString();
    }

    public void requestZoomIn(){
        BuilderFractal zoomFract;
        ArrayList<String> opt = fractaleOpt;
        if (opt.get(0).equals("Julia") || opt.get(0).equals("Mandelbrot")){
            zoomFract = sameFract(opt);

            double[] rect = validRect(opt.get(4));
            double diffW10Perc = (rect[1] - rect[0]) * 0.1;
            double diffH10Perc = (rect[3] - rect[2]) * 0.1;
            rect[0] = rect[0] + diffW10Perc;
            rect[1] = rect[1] - diffW10Perc;
            rect[2] = rect[2] + diffH10Perc;
            rect[3] = rect[3] - diffH10Perc;
            String rectangle = rectArrayToString(rect);
            fractaleOpt.set(4,rectangle);
            double pas = 0.8 * validPas(opt.get(5));
            fractaleOpt.set(5, Double.toString(pas));
            zoomFract = zoomFract.rect(rect).pas(pas);
            view.showFractalJM(zoomFract.build());
        }
    }

    public BuilderFractal sameFract(ArrayList<String> opt){
        BuilderFractal fract = new BuilderFractal();
        fract = fract.type(Character.toString(opt.get(0).charAt(0))).fichier(fileName(opt.get(1))).coloration(colorFromField(opt.get(2))).pas(validPas(opt.get(5))).iter(validIte(opt.get(7)));
        double[] cst = validCst(opt.get(3));
        Fonction fonc = new Fonction.BuilderFonction(new Complex.Builder(cst[0], cst[1]).build()).coef(correctFormatFct(opt.get(6))).build();
         fract = fract.fonction(fonc);
         return fract;
    }

    public void requestZoomOut(){
        BuilderFractal zoomFract;
        ArrayList<String> opt = fractaleOpt;
        if (opt.get(0).equals("Julia") || opt.get(0).equals("Mandelbrot")){
            zoomFract = sameFract(opt);
            double[] rect = validRect(opt.get(4));
            double diffW10Perc = (rect[1] - rect[0]) * 0.1;
            double diffH10Perc = (rect[3] - rect[2]) * 0.1;
            rect[0] = rect[0] - diffW10Perc;
            rect[1] = rect[1] + diffW10Perc;
            rect[2] = rect[2] - diffH10Perc;
            rect[3] = rect[3] + diffH10Perc;
            String rectangle = rectArrayToString(rect);
            fractaleOpt.set(4,rectangle);
            double pas = 1.2 * validPas(opt.get(5));
            fractaleOpt.set(5, Double.toString(pas));
            zoomFract = zoomFract.rect(rect).pas(pas);
            view.showFractalJM(zoomFract.build());
        }
    }

    public void requestMove(String direction){
        BuilderFractal zoomFract;
        ArrayList<String> opt = fractaleOpt;
        if (opt.get(0).equals("Julia") || opt.get(0).equals("Mandelbrot")){
            zoomFract = sameFract(opt);
            fractaleOpt.set(5, Double.toString(validPas(opt.get(5))));

            double[] rect = validRect(opt.get(4));
            switch (direction){
                case "UP" -> {
                    double diffH10Perc = (rect[3] - rect[2]) * 0.1;
                    rect[2] = rect[2] - diffH10Perc;
                    rect[3] = rect[3] - diffH10Perc;
                }
                case "DOWN" -> {
                    double diffH10Perc = (rect[3] - rect[2]) * 0.1;
                    rect[2] = rect[2] + diffH10Perc;
                    rect[3] = rect[3] + diffH10Perc;
                }
                case "LEFT" -> {
                    double diffH10Perc = (rect[1] - rect[0]) * 0.1;
                    rect[0] = rect[0] - diffH10Perc;
                    rect[1] = rect[1] - diffH10Perc;
                }
                case "RIGHT" -> {
                    double diffH10Perc = (rect[1] - rect[0]) * 0.1;
                    rect[0] = rect[0] + diffH10Perc;
                    rect[1] = rect[1] + diffH10Perc;
                }

            }
            String rectangle = rectArrayToString(rect);
            fractaleOpt.set(4,rectangle);

            zoomFract = zoomFract.rect(rect).pas(validPas(opt.get(5)));
            view.showFractalJM(zoomFract.build());
        }
    }

    public void setView(ViewFX view) {
        this.view = view;
    }
}
