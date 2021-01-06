package Sistema.LogicaDeNegocios;

import Sistema.CamadaDeDados.Duplo;
import Sistema.CamadaDeDados.Gestor;
import Sistema.CamadaDeDados.GestorDAO;
import Sistema.CamadaDeDados.Palete;

import java.util.ArrayList;

import java.util.*;

public class SSGesGestoresFacade implements ISSGestGestores {

    private GestorDAO gestores;
    private TreeSet<Integer> gestoresID;

    private static SSGesGestoresFacade singleton = new SSGesGestoresFacade();

    public static SSGesGestoresFacade getInstance(){return SSGesGestoresFacade.singleton;}

    private   SSGesGestoresFacade(){
        this.gestores = GestorDAO.getInstance();
        this.gestoresID = this.gestores.getIds();
    }

    public Gestor logIn(String password, Integer id) {
        if(!gestoresID.contains(id)) return null;
        Gestor res = gestores.get(id);
        if(res.checkPassword(password)) return res;
        return null;
    }

    public Gestor get(Integer id){
        if(!this.gestoresID.contains(id))return null;
        else return this.gestores.get(id);
    }

    public void add(String nome, String password) {
        Integer id = this.gestores.insert(nome,password);
        this.gestoresID.add(id);
    }

    public void add(Gestor g) {
        this.add(g.getNome(),g.getPassword());
    }


}
