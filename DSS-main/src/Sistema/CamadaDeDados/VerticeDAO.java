package Sistema.CamadaDeDados;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VerticeDAO {

    private static VerticeDAO singleton = new VerticeDAO();

    private VerticeDAO(){}

    public static VerticeDAO getInstance() {
        return VerticeDAO.singleton;
    }

    public void insert(Integer idVertice, Integer x, Integer y) {
        try{
            Connection con = MysqlHelper.connect();
            Statement st = MysqlHelper.createStatement(con);

            int res = st.executeUpdate("INSERT INTO dss.Vertice (idVertice,x,y)\n" +
                    "VALUES ( " + idVertice + " , " + x + " , " + y + " ); ");

            con.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void insert(Vertice vertice) {
        this.insert(vertice.getId(),vertice.getX(),vertice.getY());
    }

    public Vertice get(Integer idVertice) {
        Vertice res = null;
        try {
            Connection con = MysqlHelper.connect();
            Statement st = MysqlHelper.createStatement(con);

            ResultSet rs = st.executeQuery("SELECT idVertice,x,y FROM dss.vertice \n" +
                    "   WHERE idVertice = " + idVertice );

            if(rs.next() != false && rs.isLast() && rs.isFirst()) {
                res = new Vertice(rs.getInt(1), rs.getInt(2), rs.getInt(3));
            }
            con.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    public Integer getSize() {
        Integer res = null;
        try {
            Connection con = MysqlHelper.connect();
            Statement st = MysqlHelper.createStatement(con);

            ResultSet rs = st.executeQuery("SELECT idVertice FROM dss.vertice");

            res =0;
            while(rs.next() != false) {
                res++;
            }

            con.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

}
