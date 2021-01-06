package Sistema.CamadaDeDados;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeSet;

public class ArestaDAO {

    private static ArestaDAO singleton = new ArestaDAO();

    private ArestaDAO(){}

    public static ArestaDAO getInstance() {
        return ArestaDAO.singleton;
    }

    public Integer insert(Integer idAresta, Integer idVertice1, Integer idVertice2) {
        try {
            Connection con = MysqlHelper.connect();
            Statement st = MysqlHelper.createStatement(con);

            st.executeUpdate("INSERT INTO dss.Aresta (idAresta,idVertice1,idVertice2)\n" +
                    "VALUES ( " + idAresta + " , " + idVertice1 + " , " + idVertice2 + " ); ", Statement.RETURN_GENERATED_KEYS);


            con.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return idAresta;
    }

    public Integer insert(Aresta aresta) {
        return this.insert(aresta.getId(), aresta.getIdVertice1(),aresta.getIdVertice2());
    }

    public Aresta get(Integer idAresta) {
        Aresta res = null;
        try {
            Connection con = MysqlHelper.connect();
            Statement st = MysqlHelper.createStatement(con);

            ResultSet rs = st.executeQuery("SELECT r.idAresta, r.idVertice1, r.idVertice2, p.x, p.y FROM dss.aresta as r INNER JOIN dss.vertice as p ON r.idVertice1 = p.idVertice or r.idVertice2 = p.idVertice\n" +
                    "   WHERE r.idAresta = " + idAresta );

            if(rs.next() != false && rs.isFirst()) {
                res = new Aresta(rs.getInt(1), rs.getInt(2), rs.getInt(3), null);
                Integer x1 = rs.getInt(4);
                Integer y1 = rs.getInt(5);
                if(rs.next() != false && rs.isLast()) {
                    Integer x2 = rs.getInt(4);
                    Integer y2 = rs.getInt(5);
                    res.setTamanho(Aresta.distance(x1,y1,x2,y2));
                } else res = null;
            }
            con.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    public TreeSet<Integer> getLigacoes(Integer idVertice1) {
        TreeSet<Integer> res = new TreeSet<Integer>();
        try {
            Connection con = MysqlHelper.connect();
            Statement st = MysqlHelper.createStatement(con);

            ResultSet rs = st.executeQuery("SELECT idVertice2 FROM dss.aresta\n" +
                    "   WHERE idVertice1 = " + idVertice1 );

            while(rs.next() != false) {
                res.add(rs.getInt(1));
            }

            con.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    public TreeSet<Integer> getIds() {
        return MysqlHelper.getIds("SELECT idAresta FROM dss.aresta");
    }

}
