package Sistema.LogicaDeNegocios;

import Sistema.CamadaDeDados.Descarga;
import Sistema.CamadaDeDados.Duplo;
import Sistema.CamadaDeDados.QRLeitor;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class SSGesQRLeitoresFacade implements ISSGesQRLeitoresFacade
{
    private TreeMap<Integer,QRLeitor> lista;
    private TreeMap<Integer,Boolean> isBeingUsed;
    private Integer iId = 1;
    private static SSGesQRLeitoresFacade singleton = new SSGesQRLeitoresFacade();

    public static SSGesQRLeitoresFacade getInstance() {return SSGesQRLeitoresFacade.singleton;}

    private SSGesQRLeitoresFacade(){
        this.isBeingUsed = new TreeMap<Integer,Boolean>();
        this.lista = new TreeMap<Integer,QRLeitor>();
    }

//    public SSGesQRLeitoresFacade(QRLeitor[] ls) {
//        this();
//        for (QRLeitor qr: ls) {
//            this.lista.put(qr.getId(),qr.clone());
//            this.isBeingUsed.put(qr.getId(),false);
//        }
//    }
//
//    public SSGesQRLeitoresFacade(TreeMap<Integer,QRLeitor> ls) {
//        this();
//        ls.forEach((k,v)-> {
//            this.lista.put(k,v);
//            this.isBeingUsed.put(k,false);
//        });
//    }
//
//    public SSGesQRLeitoresFacade(SSGesQRLeitoresFacade ss) {
//        this(ss.lista);
//    }

    public Collection<Integer> getNotBeingUsed() {
        TreeSet<Integer> res = new TreeSet<Integer>();
        for(Map.Entry<Integer,Boolean> entry : isBeingUsed.entrySet()) {
            Integer key = entry.getKey();
            Boolean value = entry.getValue();
            if(value == false) res.add(key);
        }
        return res;
    }

    public QRLeitor add() {
        for (Integer x: lista.keySet()) {
            if (!isBeingUsed.get(x)) return lista.get(x);
        }

        QRLeitor res = new QRLeitor(iId);
        this.lista.put(iId,res);
        this.isBeingUsed.put(iId,false);
        iId++;
        return res;
    }

    public Duplo<Boolean,String> refresh(Integer idQRLeitor, Descarga d) {
        Duplo<Boolean,String> res = new Duplo<Boolean,String>();
        QRLeitor qrLeitor = this.lista.get(idQRLeitor);
        if(qrLeitor.getLendo() == null) {
            qrLeitor.setLendo(d);
            res.setFirst(true);
            res.setSecond(d.getDef());
        } else {
            res.setFirst(false);
            res.setSecond(qrLeitor.getLendo().getDef());
        }
        return res;
    }

    public Descarga finish(Integer idQRLeitor) {
        this.isBeingUsed.put(idQRLeitor,false);
        return this.lista.get(idQRLeitor).removeLendo();
    }
}
