package Sistema.LogicaDeNegocios;

import Sistema.CamadaDeDados.Descarga;
import Sistema.CamadaDeDados.DescargaDAO;
import Sistema.CamadaDeDados.Duplo;

import java.util.*;

public class SSGesDescargasFacade {
    private TreeMap<Integer,Duplo<Integer,String>> pendentes;
    private DescargaDAO aceites;
    private TreeSet<Integer> aceitesID;
    private TreeSet<Integer> aceitesIDWaiting;
    private Integer idTemp;

    private static SSGesDescargasFacade singleton = new SSGesDescargasFacade();

    public static SSGesDescargasFacade getInstance() {return SSGesDescargasFacade.singleton;}

    private SSGesDescargasFacade() {
        this.pendentes = new TreeMap<Integer,Duplo<Integer,String>>();
        this.aceites = DescargaDAO.getInstance();
        this.aceitesID = this.aceites.getIds();
        for (Integer x: this.aceites.getIdsAlreadyUsed()) {
            if (this.aceitesID.contains(x)) {
                this.aceitesID.remove(x);
            }
        }
        this.aceitesIDWaiting = new TreeSet<Integer>();
        this.idTemp = 1;
    }

    private SSGesDescargasFacade(TreeMap<Integer,Duplo<Integer,String>> pendentes) {
        this();
        for (Map.Entry<Integer,Duplo<Integer,String>> ds: pendentes.entrySet()) {
            Integer key = ds.getKey();
            Duplo<Integer,String> value = ds.getValue();

            this.pendentes.put(idTemp,(new Duplo<Integer,String>(value.getFirst(),value.getSecond())));
            idTemp += 1;
        }
    }

    public SSGesDescargasFacade(SSGesDescargasFacade ss) {
        this(ss.pendentes);
    }

    //usar quando camionista adiciona descarga com a mesma defenição
    public Integer add(Integer quantidade, String def) {
        this.pendentes.put(idTemp,new Duplo<>(quantidade,def));
        idTemp += 1;
        return idTemp-1;
    }

    public TreeMap<Integer,Duplo<Integer,String>> getPendentes() {
        TreeMap<Integer,Duplo<Integer,String>> res = new TreeMap<Integer,Duplo<Integer,String>>();
        for (Map.Entry<Integer,Duplo<Integer,String>> e: this.pendentes.entrySet()) {
            Integer key = e.getKey();
            Duplo<Integer,String> value = e.getValue();
            res.put(key,new Duplo<>(value.getFirst(), value.getSecond()));
        }
        return res;
    }

    //usar quando gestor aprova Sistema.CamadaDeDados.Descarga
    public void addToAceites( Integer temp) {
        Duplo<Integer,String> ds = this.pendentes.remove(temp);
        if( ds != null ) {
            for (int i = 0; i < ds.getFirst(); i++) {
                this.aceitesID.add(this.aceites.insert(ds.getSecond()));
            }
        }
    }

    public void notAceites(Integer temp) {
        this.pendentes.remove(temp);
    }

    //usar quando qrleitor quer ler
    public Descarga pollFirst() {
        Descarga res;
        try {
            Integer selected = this.aceitesID.pollFirst();
            this.aceitesIDWaiting.add(selected);
            res = this.aceites.get(selected);
        } catch (NoSuchElementException | NullPointerException e) {
            res = null;
        }
        return res;
    }

    //usar quando qrleitor volta a trás
    public void returnNotSet(Integer idDescarga) {
        this.aceitesIDWaiting.remove(idDescarga);
        this.aceitesID.add(idDescarga);
    }

    //usar quando qrleitor lê
    public Descarga removeAceites(Integer idDescarga) {
        Descarga res;
        try{
            res = this.aceites.get(idDescarga);
            this.aceites.remove(idDescarga);
            this.aceitesIDWaiting.remove(idDescarga);
        } catch (NoSuchElementException e) {
            res = null;
        }

        return res;
    }



}
