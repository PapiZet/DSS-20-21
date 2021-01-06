package Sistema.CamadaDeDados;

import java.sql.*;
import java.util.TreeSet;

public class GestorDAO {

    private static GestorDAO singleton = new GestorDAO();

    private GestorDAO(){}

    public Integer insert(String nome, String password){
        Integer res = null;

        try {
        Connection con = MysqlHelper.connect();
        Statement st = MysqlHelper.createStatement(con);

        st.executeUpdate("INSERT INTO dss.Gestor (password ,nome)\n" +
                "VALUES ( '" + password + "' , '" + nome + "' ); ", Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = st.getGeneratedKeys();
        rs.next();
        res = rs.getInt(1);

        con.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return res;
    }

    public Integer insert(Gestor gestor) {
        return this.insert(gestor.getPassword(), gestor.getNome());
    }

    public static GestorDAO getInstance() {
        return GestorDAO.singleton;
    }


    public Gestor get(Integer idGestor) {
        Gestor res = null;
        try {
            Connection con = MysqlHelper.connect();
            Statement st = MysqlHelper.createStatement(con);

            ResultSet rs = st.executeQuery("SELECT idGestor,nome,password FROM dss.gestor\n" +
                    "   WHERE idGestor = " + idGestor );

            if(rs.next() != false && rs.isLast() && rs.isFirst()) {
                res = new Gestor(rs.getInt(1), rs.getString(2), rs.getString(3));
            }
            con.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    public TreeSet<Integer> getIds() {
        return MysqlHelper.getIds("SELECT idGestor FROM dss.gestor");
    }

}
