package Sistema.CamadaDeDados;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeSet;

public class DescargaDAO{

    private static DescargaDAO singleton = new DescargaDAO();

    private DescargaDAO() {
    }

    public Integer insert(String def) {
        Integer res = 0;
        try {
            Connection con = MysqlHelper.connect();
            Statement st = MysqlHelper.createStatement(con);

            res = st.executeUpdate("INSERT INTO dss.Descarga (def)\n" +
                    "VALUES ( '" + def + "' );\n", Statement.RETURN_GENERATED_KEYS
            );
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            res = rs.getInt(1);

            con.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }



    public Integer insert(Descarga descarga) {
        return this.insert(descarga.getDef());
    }

    public Descarga get(Integer idDescarga) {
        Descarga res = null;
        try {
            Connection con = MysqlHelper.connect();
            Statement st = MysqlHelper.createStatement(con);

            ResultSet rs = st.executeQuery("SELECT idDescarga,def FROM dss.descarga\n" +
                    "   WHERE idDescarga = " + idDescarga );

            if(rs.next() != false && rs.isLast() && rs.isFirst()) {
                res = new Descarga(rs.getInt(1), rs.getString(2));
            }
            con.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    public boolean remove(Integer idDescarga) {
        try {
            Connection con = MysqlHelper.connect();
            Statement st = MysqlHelper.createStatement(con);

            int res = st.executeUpdate("DELETE FROM dss.descarga\n" +
                    "   WHERE idDescarga = " + idDescarga );

            con.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public TreeSet<Integer> getIds() {
        return MysqlHelper.getIds("SELECT idDescarga FROM dss.descarga");
    }

    public TreeSet<Integer> getIdsAlreadyUsed() {
        TreeSet<Integer> res = new TreeSet<Integer>();
        try {
            Connection con = MysqlHelper.connect();
            Statement st = MysqlHelper.createStatement(con);

            ResultSet rs = st.executeQuery("SELECT p.idDescarga FROM dss.descarga as p INNER JOIN dss.palete as pp ON p.idDescarga = pp.idDescarga\n" +
                    "WHERE pp.idPalete IS NOT NULL");



            if(rs.next() != false) {
                res.add(rs.getInt(1));
            }
            con.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    public static DescargaDAO getInstance() {
        return DescargaDAO.singleton;
    }
}
