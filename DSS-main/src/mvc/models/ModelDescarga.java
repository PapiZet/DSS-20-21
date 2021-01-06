package mvc.models;

import Sistema.LogicaDeNegocios.SSGesDescargasFacade;

public class ModelDescarga implements Model {

    private SSGesDescargasFacade descargasFacade;
    private static ModelDescarga singleton = new ModelDescarga();

    public static ModelDescarga getInstance() {return ModelDescarga.singleton;}

    private ModelDescarga(){
        this.descargasFacade = SSGesDescargasFacade.getInstance();
    }

    public void motoristaAddDescarga(String defenition, Integer quantidade) {
        descargasFacade.add(quantidade,defenition);
    }

}
