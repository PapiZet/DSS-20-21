package Sistema;

import Sistema.CamadaDeDados.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private static Parser singleton = new Parser();

    private TreeMap<Integer,String> parser;

    public static Parser getInstance(){return Parser.singleton;}

    private Parser() {}



    public static ArrayList<Gestor> readGestores(String path) throws Exception {
        ArrayList<Gestor> ges = new ArrayList<Gestor>();

        String pattern = "\\s*\"(\\w+)\"\\s+\"(\\w+)\"\\s*";
        Pattern r = Pattern.compile(pattern);

        File file = new File(path);

        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        String s = new String();
        String nome = "";

        while ((st = br.readLine()) != null) {

            Matcher m = r.matcher(st);

            if(m.matches()){

                nome = m.group(1);
                s = m.group(2);
            }

            ges.add(new Gestor(nome,s));

        }

        return ges;
    }

    public static Triplo<TreeMap<Integer, Vertice>, TreeMap<Integer, Aresta>,ArrayList<Prateleira>> ParserVerticeAresta(String path) throws Exception {

        File file = new File(path);
        String pattern = "\\s*(\\d+)\\s+(\\d+)\\s+(\\d+)\\s*";
        Pattern r = Pattern.compile(pattern);

        String pattern1 = "\\s*(\\d+)\\s+(\\d+\\.\\d+)\\s+(\\d+)\\s+([0|1])";
        Pattern r1 = Pattern.compile(pattern1);

        String st;
        Integer idp;
        Integer ida;
        Integer max;
        Double distancia;
        boolean lado;

        ArrayList<Prateleira> prateleiras = new ArrayList<Prateleira>();


        BufferedReader br = new BufferedReader(new FileReader(file));
        int x1 = 0;
        int x2 = 0;
        int x3 = 0;
        int f = 1;
        TreeMap vs = new TreeMap<Integer, Vertice>();
        TreeMap as = new TreeMap<Integer,Aresta>();

        while ((st = br.readLine()) != null) {

            if(st.equals("vertices")) f = 1;
            if(st.equals("arestas")) f = 0;

            Matcher m = r.matcher(st);
            Matcher m1 = r1.matcher(st);

            if (m.matches() && f == 1) {

                x1 = Integer.parseInt(m.group(1));
                x2 = Integer.parseInt(m.group(2));
                x3 = Integer.parseInt(m.group(3));
                Vertice x = new Vertice(x1, x2, x3);
                vs.put(x1, x);

            }
            if (m.matches() && f == 0) {

                Aresta y = new Aresta();
                x1 = Integer.parseInt(m.group(1));
                x2 = Integer.parseInt(m.group(2));
                x3 = Integer.parseInt(m.group(3));
                y.setId(x1);
                y.setIdVertice1(x2);
                y.setIdVertice2(x3);
                as.put(x1,y);

            }
            if (m1.matches()) {

                ida = Integer.parseInt(m1.group(1));
                max = Integer.parseInt(m1.group(3));
                distancia = Double.parseDouble(m1.group(2));
                lado = false;

                if(m1.group(4).equals("1")){lado = true;}

                if(m1.group(4).equals("0")){lado = false;}

                Prateleira p = new Prateleira(null,max,distancia,lado,ida);

                prateleiras.add(p);

            }


        }
        Triplo<TreeMap<Integer, Vertice>, TreeMap<Integer, Aresta>, ArrayList<Prateleira>> parser = new Triplo<TreeMap<Integer, Vertice>, TreeMap<Integer, Aresta>, ArrayList<Prateleira>>(vs, as,prateleiras);
        return parser;
    }
}
