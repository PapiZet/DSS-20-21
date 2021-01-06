package Sistema.LogicaDeNegocios;

import Sistema.CamadaDeDados.*;

import java.util.ArrayList;
import java.util.Iterator;

public class SSGesMapaFacade {

    private ArestaDAO arestas;
    private VerticeDAO vertices;
    private final double[][] matrix;

    private static SSGesMapaFacade singleton = new SSGesMapaFacade();

    private SSGesMapaFacade() {
        this.arestas = ArestaDAO.getInstance();
        this.vertices = VerticeDAO.getInstance();

        Integer n = this.vertices.getSize();

        matrix = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = 0.0;
            }
        }

        for (int i = 1; i <= n; i++) {
            for (Iterator<Integer> it = this.arestas.getLigacoes(i).iterator(); it.hasNext(); ) {
                Integer b = it.next();
                matrix[i-1][b-1] = matrix[b-1][i-1] = Aresta.distance(vertices.get(i),vertices.get(b)).intValue();
            }
        }
    }

    public static SSGesMapaFacade getInstance(){return SSGesMapaFacade.singleton;}

    public ArrayList<Triplo<Integer,Double, ArrayList<Integer>>> dijkstra(Integer idVerticeStart) {
        return DijkstrasAlgorithm.dijkstra(matrix,idVerticeStart);
    }

    public Triplo<Integer, Double, ArrayList<Integer>> getPercursoFromTo(Integer idAresta1, Double distancia1, Integer idAresta2, Double distancia2) {
        Aresta a1 = arestas.get(idAresta1);
        Double tamanho1 = a1.getTamanho();

        Aresta a2 = arestas.get(idAresta2);
        Double tamanho2 = a2.getTamanho();

        boolean check11 = distancia1.equals(0.0);
        boolean check12 = distancia1.equals(tamanho1);

        boolean check21 = distancia2.equals(0.0);
        boolean check22 = distancia2.equals(tamanho2);

        boolean check1 = !(check11 || check12);
        boolean check2 = !(check21 || check22);

        if(idAresta1.equals(idAresta2) && distancia1.equals(distancia2)) {
            ArrayList<Integer> r = new ArrayList<Integer>();
            r.add(0);
            return new Triplo<Integer,Double,ArrayList<Integer>>(0,0.0,r);
        }

        if( check1 || check2 ) {
            int n = matrix.length;
            double matrix1[][];

            if( check1 && check2 ) {
                matrix1 = new double[n+2][n+2];
            } else {
                matrix1 = new double[n+1][n+1];
            }

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    matrix1[i][j] = matrix[i][j];
                }
            }

            int nn = n;

            if(check1) {
                int v11 = a1.getIdVertice1()-1;
                int v12 = a1.getIdVertice2()-1;

                matrix1[v11][n] = matrix1[n][v11] = distancia1;
                matrix1[v12][n] = matrix1[n][v12] = tamanho1 - distancia1;
                nn++;
            } else {
                n = (check11 ? a1.getIdVertice1()-1 : a1.getIdVertice2()-1);
            }

            if( check2 ) {
                int v21 = a2.getIdVertice1()-1;
                int v22 = a2.getIdVertice2()-1;

                matrix1[v21][nn] = matrix1[nn][v21] = distancia2;
                matrix1[v22][nn] = matrix1[nn][v22] = tamanho2 - distancia2;
            }else {
                nn = (check21 ? a2.getIdVertice1()-1 : a2.getIdVertice2()-1);
            }

            Triplo<Integer, Double, ArrayList<Integer>> res = null;
            for (Triplo<Integer, Double, ArrayList<Integer>> t :DijkstrasAlgorithm.dijkstra(matrix1,n+1)) {
                if(t.getFirst().equals(nn+1)) {
                    res = t;
                    break;
                }
            }
            return res;
        } else {
            int v1;
            if (check11) {
                v1 = a1.getIdVertice1();
            } else {
                v1 = a1.getIdVertice2();
            }

            int v2;
            if (check21) {
                v2 = a2.getIdVertice1();
            } else {
                v2 = a2.getIdVertice2();
            }

            Triplo<Integer, Double, ArrayList<Integer>> res = null;
            for (Triplo<Integer, Double, ArrayList<Integer>> t : DijkstrasAlgorithm.dijkstra(matrix,v1)) {
                if(t.getFirst().equals(v2)) {res = t; break;}
            }
            return res;
        }

    }

    public ArrayList<Triplo<Integer, Double, ArrayList<Integer>>> getPercursoFrom(Integer idAresta1, Double distancia1) {
        Aresta a1 = arestas.get(idAresta1);
        Double tamanho1 = a1.getTamanho();
        boolean check1;
        if(!((check1 = (distancia1.equals(0.0))) || (distancia1.equals(tamanho1)))) {
            int n = matrix.length;
            double matrix1[][] = new double[n+1][n+1];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    matrix1[i][j] = matrix[i][j];
                }
            }
            int v1 = a1.getIdVertice1()-1;
            int v2 = a1.getIdVertice2()-1;

            matrix1[v1][n] = matrix1[n][v1] = distancia1;
            matrix1[v2][n] = matrix1[n][v2] = tamanho1 - distancia1;

            return DijkstrasAlgorithm.dijkstra(matrix1,n+1);
        } else if (check1) {
            return DijkstrasAlgorithm.dijkstra(matrix,a1.getIdVertice1());
        } else {
            return DijkstrasAlgorithm.dijkstra(matrix,a1.getIdVertice2());
        }
    }

    public ArrayList<Triplo<Integer, Double, ArrayList<Integer>>> getPercursoFrom(Prateleira p) {
        return getPercursoFrom(p.getIdAresta(),p.getDistancia());
    }

    public Triplo<Integer, Double, ArrayList<Integer>> getPercursoFromTo(Prateleira p1, Prateleira p2) {
        return getPercursoFromTo(p1.getIdAresta(),p1.getDistancia(),p2.getIdAresta(),p2.getDistancia());
    }
}
