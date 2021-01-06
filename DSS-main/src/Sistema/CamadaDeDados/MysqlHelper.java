package Sistema.CamadaDeDados;

import java.sql.*;
import java.util.TreeSet;

public class MysqlHelper {

    static String user = "root";
    static String password = "root";

    public static Connection connect() throws SQLException{
        return DriverManager.getConnection("jdbc:mysql://localhost:3306?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",user,password);
    }

    public static Statement createStatement(Connection con) throws SQLException{
        Statement st = con.createStatement();
        st.executeQuery("USE dss;");
        return st;
    }

    public static void printResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = rs.getString(i);
                System.out.print(columnValue + " " + rsmd.getColumnName(i));
            }
            System.out.println("");
        }
    }

    public static void addAllTables(Statement st) throws SQLException {
        //st.addBatch("DROP SCHEMA IF EXISTS dss;"); // depois para retirar aqui só para fazer testes???
        st.addBatch("CREATE DATABASE IF NOT EXISTS dss");
        st.addBatch("USE dss");
        addDescarga(st);
        addVertice(st);
        addAresta(st);
        addGestor(st);
        addPrateleira(st);
        addPalete(st);
        addRobot(st);
    }

    public static void addDescarga(Statement st) throws  SQLException {
        st.addBatch("CREATE TABLE IF NOT EXISTS dss.descarga(\n" +
                "    idDescarga int AUTO_INCREMENT NOT NULL comment 'primary key',\n" +
                "    def VARCHAR(255) NOT NULL comment 'definition',\n" +
                "    PRIMARY KEY (idDescarga)\n" +
                ");");
    }

    public static void addVertice(Statement st) throws SQLException {
        st.addBatch("CREATE TABLE IF NOT EXISTS dss.vertice(\n" +
                "    idVertice int NOT NULL comment 'primary key',\n" +
                "    x int NOT NULL comment 'x of localizacao',\n" +
                "    y int NOT NULL comment 'y of localizacao',\n" +
                "    PRIMARY KEY (idVertice),\n" +
                "    UNIQUE (x,y)\n" +
                ");");
    }

    public static void addAresta(Statement st) throws SQLException {
        st.addBatch("CREATE TABLE IF NOT EXISTS dss.aresta(\n" +
                "    idAresta int AUTO_INCREMENT NOT NULL comment 'primary key',\n" +
                "    idVertice1 int NOT NULL comment 'vertice1 da aresta',\n" +
                "    idVertice2 int NOT NULL comment 'vertice2 da aresta',\n" +
                "    PRIMARY KEY (idAresta),\n" +
                "    FOREIGN KEY (idVertice1) REFERENCES dss.Vertice(idVertice),\n" +
                "    FOREIGN KEY (idVertice2) REFERENCES dss.Vertice(idVertice),\n" +
                "    UNIQUE (idVertice1,idVertice2)\n" +
                ");");
    }

    public static void addGestor(Statement st) throws SQLException {
        st.addBatch("CREATE TABLE IF NOT EXISTS dss.gestor(\n" +
                "    idGestor int AUTO_INCREMENT NOT NULL comment 'primary key',\n" +
                "    password VARCHAR(255) NOT NULL comment 'password',\n" +
                "    nome VARCHAR(255) NOT NULL comment 'nome',\n" +
                "    PRIMARY KEY (idGestor),\n" +
                "    Index (nome)\n" +
                ");");
    }

    public static void addPrateleira(Statement st) throws SQLException {
        st.addBatch("CREATE TABLE IF NOT EXISTS dss.prateleira(\n" +
                "    idPrateleira int AUTO_INCREMENT NOT NULL comment 'primary key',\n" +
                "    idAresta int NOT NULL comment 'id da dss.Aresta da dss.Prateleira',\n" +
                "    distancia FLOAT(7,4) NOT NULL comment 'distancia a que a prateleira está do vértice 1',\n" +
                "    lado BOOL NOT NULL comment 'lado do corredor direita = true, visto do vertice 1 até ao dois',\n" +
                "    max INTEGER NOT NULL comment 'maximo',\n" +
                "    PRIMARY KEY (idPrateleira),\n" +
                "    FOREIGN KEY (idAresta) REFERENCES dss.Aresta(idAresta)\n" +
                ");");
    }

    public static void addPalete(Statement st) throws SQLException {
        st.addBatch("CREATE TABLE IF NOT EXISTS dss.palete(\n" +
                "    idPalete int AUTO_INCREMENT NOT NULL comment 'primary key',\n" +
                "    idPrateleira int comment 'id da dss.Prateleira onde dss.Palete esta',\n" +
                "    qrCode VARCHAR(255) NOT NULL comment 'id',\n" +
                "    idDescarga int NOT NULL comment 'idDescarga',\n" +
                "    PRIMARY KEY (idPalete),\n" +
                "    FOREIGN KEY (idPrateleira) REFERENCES dss.Prateleira(idPrateleira),\n" +
                "    FOREIGN KEY (idDescarga) REFERENCES dss.Descarga(idDescarga),\n" +
                "    Index (qrCode)\n" +
                ");");
    }

    public static void addRobot(Statement st) throws SQLException {
        st.addBatch("CREATE TABLE IF NOT EXISTS dss.robot(\n" +
                "    idRobot int AUTO_INCREMENT NOT NULL comment 'primary key',\n" +
                "    idPalete int comment 'id da dss.Palete que o dss.Robot vai buscar/leva',\n" +
                "    idPrateleiraOndeEstou int NULL comment 'ida da prateleira onde esta',\n" +
                "    idPrateleiraOndeVou int NULL comment 'ida da prateleira onde vai',\n" +
                "    vouEntregar BOOL NOT NULL DEFAULT 0 comment 'se vai buscar 0, se vai entregar 1',\n" +
                "    PRIMARY KEY (idRobot),\n" +
                "    FOREIGN KEY (idPalete) REFERENCES dss.Palete(idPalete),\n" +
                "    FOREIGN KEY (idPrateleiraOndeVou) REFERENCES prateleira(idPrateleira),\n" +
                "    FOREIGN KEY (idPrateleiraOndeEstou) REFERENCES prateleira(idPrateleira)\n" +
                ");");
    }

    public static TreeSet<Integer> getIds(String sql) {
        TreeSet<Integer> res = new TreeSet<Integer>();
        try {
            Connection con = MysqlHelper.connect();
            Statement st = MysqlHelper.createStatement(con);

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                res.add(rs.getInt(1));
            }
            con.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

//    public static void addEncomenda(Statement st) throws SQLException {
//        st.addBatch("CREATE TABLE IF NOT EXISTS Encomenda(\n" +
//                "    idEncomenda int NOT NULL AUTO_INCREMENT comment 'primary key',\n" +
//                "    id int comment 'id da dss.Palete que o dss.Robot leva',\n" +
//                "    id int NOT NULL comment 'id dss.Robot',\n" +
//                "    idLocalizacao int comment 'id da localizacao onde dss.Robot esta',\n" +
//                "    PRIMARY KEY (idRobot),\n" +
//                "    FOREIGN KEY (idLocalizacao) REFERENCES dss.Localizacao(idLocalizacao),\n" +
//                "    FOREIGN KEY (idPalete) REFERENCES dss.Palete(idPalete),\n" +
//                "    UNIQUE(id),\n" +
//                "    Index (id)\n" +
//                ");"
//
//        );
//    }

}
