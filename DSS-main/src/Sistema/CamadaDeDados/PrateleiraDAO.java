package Sistema.CamadaDeDados;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

public class PrateleiraDAO {

    private static PrateleiraDAO singleton = new PrateleiraDAO();

    private PrateleiraDAO(){}

    public static PrateleiraDAO getInstance(){return PrateleiraDAO.singleton;}

    public Integer insert(Integer idAresta, Double distancia, Boolean lado, Integer max){
        Integer res = null;
        try {
            Connection con = MysqlHelper.connect();
            Statement st = MysqlHelper.createStatement(con);

            st.executeUpdate("INSERT INTO dss.Prateleira (idAresta, lado, distancia, max)\n" +
                    "VALUES ( " + idAresta + " , " + (lado ? "TRUE" : "FALSE") + " , " + distancia + " , " + max + " );", Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            res = rs.getInt(1);

            con.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    public Integer insert(Prateleira prateleira) {
        return this.insert(prateleira.getIdAresta(), prateleira.getDistancia(), prateleira.getLado(), prateleira.getMax());
    }

    public TreeMap<Integer,Duplo<ArrayList<Integer>,Integer>> getOccupancy() {
        TreeMap<Integer,Duplo<ArrayList<Integer>,Integer>> res = new TreeMap<Integer,Duplo<ArrayList<Integer>,Integer>>();
        try {
            Connection con = MysqlHelper.connect();
            Statement st = MysqlHelper.createStatement(con);

            ResultSet rs = st.executeQuery("SELECT p.idPrateleira, pp.idPrateleira, p.max, pp.idPalete FROM dss.prateleira as p LEFT JOIN dss.palete as pp ON p.idPrateleira = pp.idPrateleira");
                    //"WHERE pp.idPrateleira IS NOT NULL");



            while(rs.next() != false) {
                Integer idPrateleira = rs.getInt(1);
                Integer idPrateleiraInPalete = rs.getInt(2);
                if(res.containsKey(idPrateleira)) {
                    if(idPrateleiraInPalete!=0) {
                        Duplo<ArrayList<Integer>,Integer> d = res.get(idPrateleira);
                        d.getFirst().add(rs.getInt(4));
                    }
                }else {
                    if(idPrateleiraInPalete==0) {
                        res.put(idPrateleira,new Duplo<ArrayList<Integer>,Integer>(new ArrayList<Integer>(),rs.getInt(3)));
                    } else{
                        ArrayList<Integer> ar = new ArrayList<Integer>();
                        ar.add(rs.getInt(4));
                        res.put(idPrateleira,new Duplo<ArrayList<Integer>,Integer>(ar,rs.getInt(3)));
                    }
                }
            }
            con.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;

    }

    public Prateleira get(Integer idPrateleira) {
        Prateleira res = null;
        try {
            Connection con = MysqlHelper.connect();
            Statement st = MysqlHelper.createStatement(con);

            ResultSet rs = st.executeQuery("SELECT idPrateleira,max,distancia,lado,idAresta FROM dss.prateleira\n" +
                    "   WHERE idPrateleira = " + idPrateleira );

            if(rs.next() != false && rs.isLast() && rs.isFirst()) {
                res = new Prateleira(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getBoolean(4), rs.getInt(5));
            }
            con.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    public TreeSet<Integer> getIds() {
        return MysqlHelper.getIds("SELECT idPrateleira FROM dss.prateleira");
    }
}
