package Sistema.LogicaDeNegocios;

import Sistema.CamadaDeDados.Gestor;

public interface ISSGestGestores {

     Gestor logIn(String password, Integer id);
     //ArrayList<Duplo<Integer,Integer>> consultarListagemDeLocalizaçoes();

}
