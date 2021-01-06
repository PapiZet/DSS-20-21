package mvc;

import javax.swing.SwingUtilities;

import Sistema.CamadaDeDados.*;
import Sistema.LogicaDeNegocios.*;
import Sistema.CamadaDeDados.MysqlHelper;
import Sistema.Parser;
import mvc.views.*;
import mvc.controllers.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main
{
    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            System.out.println("Error finding com.mysql.jdbc.Driver Class");
        }

        Connection con;
        try {
            con = MysqlHelper.connect();
            Statement st = con.createStatement();
            System.out.print("Escrever \'Y\' se quer apagar a base de dados e refazer parser dos ficheiros: ");
            Scanner in = new Scanner(System.in);
            String s = in.nextLine();

            Integer robots = 0;

            Boolean parse = false;
            if(s.equals("Y")) {
                st.addBatch("DROP SCHEMA IF EXISTS dss;");
                parse = true;

                while(robots == 0) {
                    System.out.print("Quantos Robots: ");
                    in = new Scanner(System.in);
                    s = in.nextLine();

                    try {
                        Integer test = Integer.parseInt(s);
                        if(test <= 0) throw new NumberFormatException();
                        robots = test;
                    } catch (NumberFormatException e) {
                        System.out.println("escreva um número maior que 0");
                    }

                }
            }





            MysqlHelper.addAllTables(st);
            int[] res = st.executeBatch();
            ResultSet rs = st.executeQuery("SELECT DATABASE();");

            if(parse) {
                try {
                    Triplo<TreeMap<Integer, Vertice>, TreeMap<Integer, Aresta>, ArrayList<Prateleira>> verAr = Parser.ParserVerticeAresta("vertices.txt");

                    VerticeDAO v = VerticeDAO.getInstance();

                    for (Map.Entry<Integer, Vertice> e : verAr.getFirst().entrySet()) {
                        v.insert(e.getValue());
                    }

                    ArestaDAO a = ArestaDAO.getInstance();

                    for (Map.Entry<Integer, Aresta> e : verAr.getSecond().entrySet()) {
                        a.insert(e.getValue());
                    }

                    PrateleiraDAO pdao = PrateleiraDAO.getInstance();

                    for(Prateleira p : verAr.getThird()) {
                        pdao.insert(p);
                    }

                    ArrayList<Gestor> ges = Parser.readGestores("gestores.txt");

                    SSGesGestoresFacade gestoresFacade = SSGesGestoresFacade.getInstance();

                    for (Gestor g : ges) {
                        gestoresFacade.add(g.getNome(),g.getPassword());
                    }

                    RobotDAO robotdao = RobotDAO.getInstance();
                    for (int i = 0; i < robots; i++) {
                        robotdao.insert(null,1,null);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


//            Vertice v1 = new Vertice(1,0,0);
//            Vertice v2 = new Vertice(2, 1,0);
//            Vertice v3 = new Vertice(3,5,0);
//            Vertice v4 = new Vertice(4,5,5);
//            Aresta a = new Aresta(3,1,2,Aresta.distance(v1,v2));
//            Aresta b = new Aresta(1,3,4,Aresta.distance(v3,v4));
//            Aresta c = new Aresta(2,2,3, Aresta.distance(v2,v3));
//            VerticeDAO vd = VerticeDAO.getInstance();
//            vd.insert(v1);
//            vd.insert(v2);
//            vd.insert(v3);
//            vd.insert(v4);
//            ArestaDAO ad = ArestaDAO.getInstance();
//            ad.insert(a);
//            ad.insert(b);
//            ad.insert(c);
//
//            Aresta d = ad.get(3);
//            Prateleira p = new Prateleira(1,100,5.0,false, b.getId());
//            Prateleira p1 = new Prateleira(2,100,0.5,true,a.getId());
//            Prateleira p3 = new Prateleira(3,100,0.5, false,c.getId());
//            PrateleiraDAO pdao = PrateleiraDAO.getInstance();
//            Integer pid = pdao.insert(p);
//            pdao.insert(p1);
//            pdao.insert(p3);
//            Palete pp = new Palete(1, "abc", 1);
//            Palete pp1 = new Palete(2, "abc", 1);
//            PaleteDAO.getInstance().insert(pp);
//            PaleteDAO.getInstance().insert(pp1);
//            Palete pp1s = PaleteDAO.getInstance().get(1);
//            Descarga d1 = new Descarga(1,"aello");
//            Descarga d2 = new Descarga(3,"bello");
//            DescargaDAO dd = DescargaDAO.getInstance();
//            dd.insert(d1);
//            dd.insert(d2);
//
//            SSGesDescargasFacade gesDD = SSGesDescargasFacade.getInstance();
//            Integer what = gesDD.add(3,"cello");
//            gesDD.addToAceites(what);
//            System.out.println(gesDD.pollFirst().toString());
//            System.out.println(gesDD.pollFirst().toString());
//            gesDD.returnNotSet(1);
//            gesDD.removeAceites(2);
//
//            SSGesGestoresFacade gestoresFacade = SSGesGestoresFacade.getInstance();
//            gestoresFacade.add("Admin","admin");
//            System.out.println(gesDD.pollFirst().toString());
//
//            SSGesMapaFacade mapa = SSGesMapaFacade.getInstance();
//            Robot rr = new Robot(3,null,1,null);
//            RobotDAO.getInstance().updateRobotBuscar(RobotDAO.getInstance().insert(rr),1,pid);
//
//            Robot r1 = RobotDAO.getInstance().get(1);
//
//            Triplo<Integer, Double, ArrayList<Integer>> test = mapa.getPercursoFromTo(p,p);
//            SSGesRobotsFacade rf = SSGesRobotsFacade.getInstance();

            con.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                View view = new View();
                Controller controller = new Controller(null,view);
                controller.contol();
            }
        });
    }
}