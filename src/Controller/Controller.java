package Controller;
import Model.*;
import View.ViewFX;

import java.util.ArrayList;
import java.util.LinkedList;

public class Controller {
    private ViewFX view;
    private Fractal fractale;

    public int colorFromField(String str){
        switch (str){
            case "Noir et Blanc" -> {
                return 0;
            }
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
            default -> {return 0;}
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
            return Integer.parseInt(it);
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

                if (cst == null){
                    err.add("cst");
                }
                if (rect == null){
                    err.add("r");
                }
                if (pas == 0){
                    err.add("p");
                }

                if (rect[0] + pas >= rect[1] || rect[2] + pas >= rect[3]) {
                    err.add("rp");
                }

                fract = fract.rect(rect).pas(pas);

                Fonction.BuilderFonction fonction = new Fonction.BuilderFonction(new Complex.Builder(cst[0],cst[1] ).build());
                LinkedList<double[]> fo = correctFormatFct(opt.get(6));
                if (fo == null){
                    err.add("fo");
                }
                fonction.coef(fo);

                fract = fract.fonction(fonction.build());

                int ite = validIte(opt.get(7));
                if (ite == -1){
                    err.add("it");
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
            }
        }

        if(err.isEmpty()) {
            this.fractale = fract.build();
            view.showFractal(this.fractale, view.getMainStage());
        }
        return err;
    }
}
