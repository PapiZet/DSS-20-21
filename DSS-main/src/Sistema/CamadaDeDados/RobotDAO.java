package Sistema.CamadaDeDados;

import Sistema.LogicaDeNegocios.SSGesMapaFacade;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeMap;
import java.util.TreeSet;

public class RobotDAO {

    private static RobotDAO singleton = new RobotDAO();

    private RobotDAO(){}

    public static RobotDAO getInstance(){return RobotDAO.singleton;}

    public Integer insert(Integer idPalete, Integer idPrateleiraOndeEstou, Integer idPrateleiraOndeVou){
        Integer res = null;
        try {
            Connection con = MysqlHelper.connect();
            Statement st = MysqlHelper.createStatement(con);

            st.executeUpdate("INSERT INTO dss.Robot (idPalete, idPrateleiraOndeEstou,idPrateleiraOndeVou)\n" +
                    "VALUES ( " + idPalete + " , " + idPrateleiraOndeEstou + " , " + idPrateleiraOndeVou + " ); ", Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            res = rs.getInt(1);

            con.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    public Integer insert(Robot robot){
        return this.insert(robot.getIdPalete(),robot.getIdPrateleiraOndeEstou(), robot.getIdPrateleiraOndeVou());
    }

    public Robot get(Integer idRobot) {
        Robot res = null;
        try {
            Connection con = MysqlHelper.connect();
            Statement st = MysqlHelper.createStatement(con);

            ResultSet rs = st.executeQuery("SELECT r.idRobot, r.idPalete, r.idPrateleiraOndeEstou, r.idPrateleiraOndeVou, p.max, p.distancia, p.lado, p.idAresta, pp.max as maxOndeEstou, pp.distancia as distanciaOndeEstou, pp.lado as ladoOndeEstou, pp.idAresta as idArestaOndeEstou " +
                    "FROM dss.robot as r " +
                    "INNER JOIN dss.prateleira as p ON r.idPrateleiraOndeVou = p.idPrateleira " +
                    "INNER JOIN dss.prateleira as pp ON r.idPrateleiraOndeEstou = pp.idPrateleira " +
                    "   WHERE idRobot = " + idRobot );

            if(rs.next() != false && rs.isLast() && rs.isFirst()) {
                Prateleira ondeVou = new Prateleira(rs.getInt(4),rs.getInt(5),rs.getDouble(6),rs.getBoolean(7),rs.getInt(8));
                Prateleira ondeEstou = new Prateleira(rs.getInt(3),rs.getInt(9),rs.getDouble(10),rs.getBoolean(11),rs.getInt(12));
                Robot r = new Robot(rs.getInt(1), rs.getInt(2), rs.getInt(3),new Percurso(ondeVou, SSGesMapaFacade.getInstance().getPercursoFromTo(ondeEstou,ondeVou)));
                res = r;
            }
            con.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    public TreeSet<Integer> getIds() {
        return MysqlHelper.getIds("SELECT idRobot FROM dss.robot");
    }

    public TreeSet<Integer> getIdsLivres() {
        return MysqlHelper.getIds("SELECT idRobot FROM dss.robot WHERE idPalete IS NULL");
    }

    public TreeSet<Integer> getIdsBuscar() {
        return MysqlHelper.getIds("SELECT idRobot FROM dss.robot WHERE idPalete ISNOT NULL AND vouEntregar = 0");
    }

    public TreeSet<Integer> getIdsEntrega() {
        return MysqlHelper.getIds("SELECT idRobot FROM dss.robot WHERE idPalete IS NOT NULL AND vouEntregar = 1");
    }

    public void update(Integer idRobot, Integer idPalete, Integer idPrateleiraOndeEstou, Integer idPrateleiraOndeVou, Boolean vouEntregar) {
        try {
            Connection con = MysqlHelper.connect();
            Statement st = MysqlHelper.createStatement(con);

            st.executeUpdate("UPDATE dss.robot SET idPrateleiraOndeEstou = " + idPrateleiraOndeEstou + " , idPalete = " + idPalete + ", idPrateleiraOndeVou = " + idPrateleiraOndeVou + ", vouEntregar = " + vouEntregar + " WHERE idRobot = " + idRobot );

            con.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update(Integer idRobot, Integer idPrateleiraOndeEstou, Integer idPrateleiraOndeVou,  Boolean vouEntregar) {
        try {
            Connection con = MysqlHelper.connect();
            Statement st = MysqlHelper.createStatement(con);

            st.executeUpdate("UPDATE dss.robot SET idPrateleiraOndeEstou = " + idPrateleiraOndeEstou + ", idPrateleiraOndeVou = " + idPrateleiraOndeVou + ", vouEntregar = " + vouEntregar + " WHERE idRobot = " + idRobot );

            con.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public TreeMap<Integer,Robot> getRobotsLivres() {
        TreeMap<Integer,Robot> res = new TreeMap<Integer,Robot>();
        try {
            Connection con = MysqlHelper.connect();
            Statement st = MysqlHelper.createStatement(con);

            ResultSet rs = st.executeQuery("SELECT idRobot, idPalete, idPrateleiraOndeEstou, idPrateleiraOndeVou FROM dss.robot \n" +
                    "WHERE idPalete IS NULL");

            while(rs.next() != false) {
                Robot r = new Robot(rs.getInt(1), rs.getInt(2), rs.getInt(3));
                res.put(r.getId(),r);
            }
            con.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    public TreeMap<Integer,Robot> getRobotsBuscar() {
        TreeMap<Integer,Robot> res = new TreeMap<Integer,Robot>();
        try {
            Connection con = MysqlHelper.connect();
            Statement st = MysqlHelper.createStatement(con);

            ResultSet rs = st.executeQuery("SELECT r.idRobot, r.idPalete, r.idPrateleiraOndeEstou, r.idPrateleiraOndeVou, p.max, p.distancia, p.lado, p.idAresta, pp.max as maxOndeEstou, pp.distancia as distanciaOndeEstou, pp.lado as ladoOndeEstou, pp.idAresta as idArestaOndeEstou " +
                    "FROM dss.robot as r " +
                    "INNER JOIN dss.prateleira as p ON r.idPrateleiraOndeVou = p.idPrateleira " +
                    "INNER JOIN dss.prateleira as pp ON r.idPrateleiraOndeEstou = pp.idPrateleira " +
                    "WHERE idPalete IS NOT NULL AND vouEntregar = 0");

            while(rs.next() != false) {
                Prateleira ondeVou = new Prateleira(rs.getInt(4),rs.getInt(5),rs.getDouble(6),rs.getBoolean(7),rs.getInt(8));
                Prateleira ondeEstou = new Prateleira(rs.getInt(3),rs.getInt(9),rs.getDouble(10),rs.getBoolean(11),rs.getInt(12));
                Robot r = new Robot(rs.getInt(1), rs.getInt(2), rs.getInt(3),new Percurso(ondeVou, SSGesMapaFacade.getInstance().getPercursoFromTo(ondeEstou,ondeVou)));
                res.put(r.getId(),r);
            }
            con.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    public TreeMap<Integer,Robot> getRobotsEntrega() {
        TreeMap<Integer,Robot> res = new TreeMap<Integer,Robot>();
        try {
            Connection con = MysqlHelper.connect();
            Statement st = MysqlHelper.createStatement(con);

            ResultSet rs = st.executeQuery("SELECT r.idRobot, r.idPalete, r.idPrateleiraOndeEstou, r.idPrateleiraOndeVou, p.max, p.distancia, p.lado, p.idAresta, pp.max as maxOndeEstou, pp.distancia as distanciaOndeEstou, pp.lado as ladoOndeEstou, pp.idAresta as idArestaOndeEstou " +
                            "FROM dss.robot as r " +
                            "INNER JOIN dss.prateleira as p ON r.idPrateleiraOndeVou = p.idPrateleira " +
                            "INNER JOIN dss.prateleira as pp ON r.idPrateleiraOndeEstou = pp.idPrateleira " +
                    "WHERE idPalete IS NOT NULL AND vouEntregar = 1");

            while(rs.next() != false) {
                Prateleira ondeVou = new Prateleira(rs.getInt(4),rs.getInt(5),rs.getDouble(6),rs.getBoolean(7),rs.getInt(8));
                Prateleira ondeEstou = new Prateleira(rs.getInt(3),rs.getInt(9),rs.getDouble(10),rs.getBoolean(11),rs.getInt(12));
                Robot r = new Robot(rs.getInt(1), rs.getInt(2), rs.getInt(3),new Percurso(ondeVou, SSGesMapaFacade.getInstance().getPercursoFromTo(ondeEstou,ondeVou)));
                res.put(r.getId(),r);
            }
            con.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    public void updateRobotBuscar(Integer idRobot, Integer idPalete, Integer idPrateleiraOndeVou) {
        try {
            Connection con = MysqlHelper.connect();
            Statement st = MysqlHelper.createStatement(con);

            st.executeUpdate("UPDATE dss.robot SET idPalete = " + idPalete + ", idPrateleiraOndeVou = " + idPrateleiraOndeVou + " WHERE idRobot = " + idRobot);

            con.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
