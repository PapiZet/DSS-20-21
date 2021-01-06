package mvc.models;

import Sistema.CamadaDeDados.Duplo;
import Sistema.LogicaDeNegocios.SSGesGestoresFacade;
import Sistema.LogicaDeNegocios.SSGesPaletesPrateleirasFacade;
//import Sistema.LogicaDeNegocios.SSGesPaletesPrateleirasFacade;

import java.util.ArrayList;
import java.util.TreeMap;

public class ModelListagemLocalizacoes implements Model{

    private static ModelListagemLocalizacoes singleton = new ModelListagemLocalizacoes();

    private SSGesGestoresFacade gestoresFacade;
    private SSGesPaletesPrateleirasFacade paletesPrateleirasFacade;

    public static ModelListagemLocalizacoes getInstance(){return ModelListagemLocalizacoes.singleton;}

    private ModelListagemLocalizacoes(){
        this.paletesPrateleirasFacade = SSGesPaletesPrateleirasFacade.getInstance();
        this.gestoresFacade = SSGesGestoresFacade.getInstance();
    }

    public Duplo<TreeMap<Integer,Integer>,TreeMap<Integer,Integer>> getListagens(){

        TreeMap<Integer,Integer> list = this.paletesPrateleirasFacade.consultarListagemDeLocalizaçoes();
        TreeMap<Integer,Integer> list1 = this.paletesPrateleirasFacade.getPaletesIDRobots();

        return new Duplo<TreeMap<Integer,Integer>,TreeMap<Integer,Integer>>(list,list1);
    }

    public Duplo<TreeMap<Integer,ArrayList<Integer>>,TreeMap<Integer,ArrayList<Integer>>> getListagensNewLayout(){
        Duplo<TreeMap<Integer,Integer>,TreeMap<Integer,Integer>> res = this.getListagens();
        TreeMap<Integer,Integer> one = res.getFirst();
        TreeMap<Integer,Integer> two = res.getSecond();

        return new Duplo<TreeMap<Integer,ArrayList<Integer>>,TreeMap<Integer,ArrayList<Integer>>>(SSGesPaletesPrateleirasFacade.revert(one),SSGesPaletesPrateleirasFacade.revert(two));

    }
}
