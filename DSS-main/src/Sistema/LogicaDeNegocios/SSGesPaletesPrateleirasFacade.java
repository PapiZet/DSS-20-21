package Sistema.LogicaDeNegocios;

import Sistema.CamadaDeDados.*;

import java.util.*;

public class SSGesPaletesPrateleirasFacade {

    public final static Integer ENTRADA = 1;
    public final static Integer SAIDA = 2;


    private PaleteDAO paletes;
    private PrateleiraDAO prateleiras;

    private static SSGesPaletesPrateleirasFacade singleton = new SSGesPaletesPrateleirasFacade();

    public static SSGesPaletesPrateleirasFacade getInstance(){return SSGesPaletesPrateleirasFacade.singleton;}

    private TreeMap<Integer,Integer> paletesIDRobots; // first Integer = Palete second Robot

    private TreeMap<Integer, Duplo<ArrayList<Integer>,Integer>> prateleirasOccupancy; // id da prateleira, duplo<lista de ids das paletes dentro da prateleira, tamanho max da prateleira>

    private SSGesPaletesPrateleirasFacade(){
        this.paletes = PaleteDAO.getInstance();
        this.prateleiras = PrateleiraDAO.getInstance();
        this.paletesIDRobots = this.paletes.getIdsRobots();
        this.prateleirasOccupancy = this.prateleiras.getOccupancy();

        SSGesRobotsFacade rs = SSGesRobotsFacade.getInstance();
        TreeMap<Integer, Robot> robots = rs.getRobotsEntrega();
        for (Robot r: robots.values()) {
            this.prateleirasOccupancy.get(r.getIdPrateleiraOndeEstou()).getFirst().add(0);
        }
    }

    //-------------------------------------------------------------------

    //id 1 e 2 reservados para local de descarga e local de entrega
    // o cria coloca no local de entrega que é quando entram no sistema
    public Integer criaPalete(String qrcode, Integer idDescarga){
        Integer id = this.paletes.insert(ENTRADA,qrcode,idDescarga);
        this.prateleirasOccupancy.get(1).getFirst().add(id);
        return id;
    }

    public Integer getNewPalete(Integer idRobot) {
        ArrayList<Integer> res = this.prateleirasOccupancy.get(ENTRADA).getFirst();
        Integer ret = null;
        for (Integer i: res) {
            if(!this.paletesIDRobots.containsKey(i)) {
                this.paletesIDRobots.put(i,idRobot);
                ret = i;
                break;
            }
        }
        return ret;
    }

    public void robotPegaPalete(Integer idPalete, Integer idRobot) {
        Integer idPrateleira = this.paletes.removeIDPrateleira(idPalete);

        this.prateleirasOccupancy.get(idPrateleira).getFirst().remove(idPalete);

        this.paletesIDRobots.put(idPalete,idRobot);
    }

    public void robotPousaPalete(Integer idPalete, Integer idPrateleira) {
        this.paletes.addIDPrateleira(idPalete,idPrateleira);

        Collections.sort(this.prateleirasOccupancy.get(idPrateleira).getFirst());
        ArrayList<Integer> t = this.prateleirasOccupancy.get(idPrateleira).getFirst();
        if(t.size() != 0 && t.get(0) == 0) {
            t.remove(0); // ocupa a reserva
        }


        this.prateleirasOccupancy.get(idPrateleira).getFirst().add(idPalete);

        this.paletesIDRobots.remove(idPalete);
    }

    public Integer reserva() {
        Integer idPrateleira = null;
        for(Map.Entry<Integer, Duplo<ArrayList<Integer>,Integer>> entry : this.prateleirasOccupancy.entrySet()) {
            Integer key = entry.getKey();
            Duplo<ArrayList<Integer>,Integer> value = entry.getValue();
            if(value.getFirst().size() < value.getSecond() && key != ENTRADA && key != SAIDA) {
                idPrateleira = key;
                this.prateleirasOccupancy.get(idPrateleira).getFirst().add(0);// reserva o lugar
                break;
            }
        }
        return idPrateleira;
    }

    public Prateleira getPrateleira(Integer prateleira) {
        return prateleiras.get(prateleira);
    }

    public TreeMap<Integer, Duplo<ArrayList<Integer>,Integer>> getPrateleiras(){

        TreeMap<Integer, Duplo<ArrayList<Integer>,Integer>> map;
        map = new TreeMap<Integer, Duplo<ArrayList<Integer>,Integer>>();
        for(Map.Entry<Integer, Duplo<ArrayList<Integer>,Integer>> a : this.prateleirasOccupancy.entrySet()) {
            map.put(a.getKey(), a.getValue());
        }

        return map;
    }

    public TreeMap<Integer,Integer> consultarListagemDeLocalizaçoes(){

        TreeMap<Integer,Integer> list = new TreeMap<Integer,Integer>();

        TreeMap<Integer, Duplo<ArrayList<Integer>,Integer>> map = this.prateleirasOccupancy;

        for(Integer c : map.keySet()){
            ArrayList<Integer> l = map.get(c).getFirst();
            for(Integer i : l){
                if(i != 0) list.put(i,c);
            }
        }

        return list;
    }

    public TreeMap<Integer,Integer> getPaletesIDRobots() {
        TreeMap<Integer,Integer> res = new TreeMap<Integer,Integer>();

        for (Map.Entry<Integer,Integer> r: this.paletesIDRobots.entrySet()) {
            Integer key = r.getKey();
            Integer value = r.getValue();
            if(!this.prateleirasOccupancy.get(ENTRADA).getFirst().contains(key)) {
                res.put(key,value);
            }
        }
        return res;
    }

    public static <K,E> TreeMap<K, ArrayList<E>> revert(TreeMap<E,K> t) {
        TreeMap<K,ArrayList<E>> res = new TreeMap<K,ArrayList<E>>();
        for (Map.Entry<E,K> e: t.entrySet() ) {
            E key = e.getKey();
            K value = e.getValue();

            if(res.containsKey(value)) {
                res.get(value).add(key);
            } else {
                ArrayList<E> r = new ArrayList<>();
                r.add(key);
                res.put(value,r);
            }
        }
        return res;
    }

}

