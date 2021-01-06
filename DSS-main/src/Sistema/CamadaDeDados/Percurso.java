package Sistema.CamadaDeDados;

import Sistema.LogicaDeNegocios.SSGesMapaFacade;

import java.util.ArrayList;
import java.util.Collections;

public class Percurso implements Cloneable
{
    private Prateleira finish;
    private Triplo<Integer, Double, ArrayList<Integer>> passos;//Integer é o vertice final, Double é a distancia, ArrayList<Integer> é a lista de vertice por ordem do percurso

    public Percurso()
    {
        this.finish= new Prateleira();
        this.passos = new Triplo<Integer, Double, ArrayList<Integer>>();
    }

    public Percurso(Prateleira prateleira1, Prateleira prateleira2) {
        this.finish = prateleira2.clone();
        this.passos = SSGesMapaFacade.getInstance().getPercursoFromTo(prateleira1,prateleira2);
    }

    public Percurso(Prateleira l,Triplo<Integer, Double, ArrayList<Integer>> g)
    {
        this.finish = l.clone();

        if(g != null) {

            ArrayList<Integer> add = new ArrayList<Integer>();
            ArrayList<Integer> third = g.getThird();
            if(third != null) {
                for (Integer lg : third) {
                    add.add(lg);
                }
            }
            this.passos = new Triplo<Integer, Double, ArrayList<Integer>>(g.getFirst(),g.getSecond(),add);
        }
    }

    public Percurso(Percurso p)
    {
        this.finish = p.getFinish();
        this.passos = p.getPassos();
    }

    public Prateleira getFinish()
    {
        return this.finish.clone();
    }

    public Triplo<Integer, Double, ArrayList<Integer>> getPassos()
    {
        if(this.passos != null) {
            ArrayList<Integer> add = new ArrayList<Integer>();
            if(this.passos.getThird() != null) {
                for (Integer lg : this.passos.getThird()) {
                    add.add(lg);
                }
            }
            return new Triplo<Integer, Double, ArrayList<Integer>>(this.passos.getFirst(),this.passos.getSecond(),add);
        }else {
            return null;
        }
    }

    public void setFinish(Prateleira lp)
    {
        this.finish = lp.clone();
    }

    public Percurso clone() {
        return new Percurso(this);
    }

    public Double getDistanciaPercurso() {
        return this.passos.getSecond();
    }

    public Percurso reverse(Prateleira prateleira) {
        Integer first = this.passos.getFirst();
        Double second = this.passos.getSecond();
        ArrayList<Integer> third = this.passos.getThird();
        Collections.reverse(third);
        first = third.get(third.size()-1);
        return new Percurso(prateleira,new Triplo<>(first,second,third));
    }
}
