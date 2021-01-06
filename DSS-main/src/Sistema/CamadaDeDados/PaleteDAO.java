package Sistema.CamadaDeDados;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeMap;
import java.util.TreeSet;

public class PaleteDAO {

    private static PaleteDAO singleton = new PaleteDAO();

    private PaleteDAO(){}

    public Integer insert(Integer idPrateleira, String qrCode, Integer idDescarga) {
        Integer res = null;
        try {
        Connection con = MysqlHelper.connect();
        Statement st = MysqlHelper.createStatement(con);

        st.executeUpdate("INSERT INTO dss.Palete (idPrateleira, qrCode, idDescarga)\n" +
                "VALUES ( " + idPrateleira + " , '" + qrCode + "' , " + idDescarga + " ); ", Statement.RETURN_GENERATED_KEYS);

        ResultSet rs = st.getGeneratedKeys();
        rs.next();
        res = rs.getInt(1);

        con.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    public Integer insert(Palete palete, Integer idDescarga) {
        return this.insert(palete.getIdPrateleira(), palete.getQrCode(), idDescarga);
    }

    public Palete get(Integer idPalete) {
        Palete res = null;
        try {
            Connection con = MysqlHelper.connect();
            Statement st = MysqlHelper.createStatement(con);

            ResultSet rs = st.executeQuery("SELECT idPalete,idPrateleira,qrCode FROM dss.palete\n" +
                    "   WHERE idPalete = " + idPalete );

            if(rs.next() != false && rs.isLast() && rs.isFirst()) {
                res = new Palete(rs.getInt(1), rs.getString(3), rs.getInt(2));
            }
            con.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    public Integer getIdPrateleira(Integer idPalete) {
        Integer res = null;
        try {
            Connection con = MysqlHelper.connect();
            Statement st = MysqlHelper.createStatement(con);

            //FUTURO FAZER UM JOIN???
            ResultSet rs = st.executeQuery("SELECT idPrateleira FROM dss.palete\n" +
                    "   WHERE idPalete = " + idPalete );

            if(rs.next() != false && rs.isLast() && rs.isFirst()) {
                res = rs.getInt(1);
            }
            con.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    public static PaleteDAO getInstance() {
        return PaleteDAO.singleton;
    }

    public TreeSet<Integer> getIds() {
        return MysqlHelper.getIds("SELECT idPalete FROM dss.palete");
    }

    public TreeSet<Integer> getIdsWithPrateleira() {
        return MysqlHelper.getIds("SELECT idPalete FROM dss.palete WHERE idPrateleira NOT IN (NULL,1,2)");
    }

    public TreeMap<Integer,Integer> getIdsRobots() {
        TreeMap<Integer,Integer> res = new TreeMap<Integer,Integer>();
        try {
            Connection con = MysqlHelper.connect();
            Statement st = MysqlHelper.createStatement(con);

            ResultSet rs = st.executeQuery("SELECT idRobot,idPalete FROM dss.robot WHERE idPalete IS NOT NULL");

            while (rs.next()) {
                res.put(rs.getInt(2),rs.getInt(1));
            }
            con.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    public TreeSet<Integer> getIdsDescarga() {
        return MysqlHelper.getIds("SELECT idPalete FROM dss.palete WHERE idPrateleira IS 1");
    }

    public TreeSet<Integer> getIdsEntrega() {
        return MysqlHelper.getIds("SELECT idPalete FROM dss.palete WHERE idPrateleira IS 2");
    }

    public Integer removeIDPrateleira(Integer idPalete) {
        Integer res = null;
        try {
            Connection con = MysqlHelper.connect();
            Statement st = MysqlHelper.createStatement(con);

            ResultSet rs = st.executeQuery("SELECT idPrateleira FROM dss.palete WHERE idPalete = " + idPalete);

            rs.next();
            res = rs.getInt(1);

            st.executeUpdate("UPDATE dss.palete SET idPrateleira = null WHERE idPalete = " + idPalete);

            con.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    public Prateleira getPrateleira(Integer idPalete) {
        Prateleira res = null;
        try {
            Connection con = MysqlHelper.connect();
            Statement st = MysqlHelper.createStatement(con);

            ResultSet rs = st.executeQuery("SELECT p.idPrateleira,p.max,p.distancia,p.lado,p.idAresta FROM dss.prateleira as p INNER JOIN dss.palete as pp ON p.idPrateleira = pp.idPrateleira\n" +
                            "WHERE pp.idPalete = " + idPalete);



            if(rs.next() != false && rs.isLast() && rs.isFirst()) {
                res = new Prateleira(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getBoolean(4), rs.getInt(5));
            }
            con.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    public void addIDPrateleira(Integer idPalete, Integer idPrateleira) {
        try {
            Connection con = MysqlHelper.connect();
            Statement st = MysqlHelper.createStatement(con);

            st.executeUpdate("UPDATE dss.palete SET idPrateleira = " + idPrateleira + " WHERE idPalete = " + idPalete);

            con.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
