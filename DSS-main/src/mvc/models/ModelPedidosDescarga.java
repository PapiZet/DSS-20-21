package mvc.models;

import Sistema.CamadaDeDados.Duplo;
import Sistema.LogicaDeNegocios.SSGesDescargasFacade;
import Sistema.LogicaDeNegocios.SSGesGestoresFacade;

import java.util.TreeMap;

public class ModelPedidosDescarga implements Model{

    private static ModelPedidosDescarga singleton = new ModelPedidosDescarga();

    private SSGesDescargasFacade descargasFacade;

    public static ModelPedidosDescarga getInstance(){return ModelPedidosDescarga.singleton;}

    private ModelPedidosDescarga(){
        this.descargasFacade = SSGesDescargasFacade.getInstance();
    }

    public TreeMap<Integer,Duplo<Integer,String>> getDescargasPendentes() {
        return descargasFacade.getPendentes();
    }

    public void aceite(Integer idTemp) {
        this.descargasFacade.addToAceites(idTemp);
    }

    public void rejeitado(Integer idTemp) {
        this.descargasFacade.notAceites(idTemp);
    }

}
