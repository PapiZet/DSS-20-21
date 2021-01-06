package mvc.models;

import Sistema.CamadaDeDados.Gestor;
import Sistema.LogicaDeNegocios.SSGesGestoresFacade;

public class ModelLogin implements  Model{

    private static ModelLogin singleton = new ModelLogin();

    private SSGesGestoresFacade gestoresFacade;

    public static ModelLogin getInstance(){return ModelLogin.singleton;}

    private ModelLogin(){
        this.gestoresFacade = SSGesGestoresFacade.getInstance();
    }

    public String login(Integer gestorId, String password) {
        Gestor gestor = gestoresFacade.logIn(password,gestorId);
        if(gestor == null) return null;
        return gestor.getNome();
    }
}
