package Sistema.LogicaDeNegocios;

import Sistema.CamadaDeDados.*;

import java.util.*;

public class SSGesRobotsFacade implements ISSGesRobots {

    private RobotDAO robots;
    private TreeMap<Integer,Robot> robotsLivres;
    private TreeMap<Integer,Robot> robotsBuscar;
    private TreeMap<Integer,Robot> robotsEntrega;
    private SSGesMapaFacade mapa;

    private static SSGesRobotsFacade singleton = new SSGesRobotsFacade();

    public static SSGesRobotsFacade getInstance(){return SSGesRobotsFacade.singleton;}

    private SSGesRobotsFacade(){
        this.robots = RobotDAO.getInstance();
        this.robotsLivres = this.robots.getRobotsLivres();
        this.robotsBuscar = this.robots.getRobotsBuscar();
        this.robotsEntrega = this.robots.getRobotsEntrega();
        this.mapa = SSGesMapaFacade.getInstance();
    }

    //NAO ESQUECER DE FAZER UPDATE AS LOCALIZACOES DOS ROBOTS


    public boolean robotVaiBuscar(Integer idRobot, Integer idPalete, Integer idPrateleira, Percurso percurso) {
        if(! robotsLivres.containsKey(idRobot)) return false;
        Robot r = robotsLivres.get(idRobot);
        r.setPercurso(percurso);
        r.setIdPalete(idPalete);
        robots.update(idRobot,r.getIdPrateleiraOndeEstou(),idPrateleira,false);
        robotsLivres.remove(idRobot);
        robotsBuscar.put(idRobot,r);
        return true;
    }

    public Integer robotBusca(Integer idPalete, Prateleira prateleira) {
        if(robotsLivres.size() == 0) return null;
        ArrayList<Triplo<Integer,Double, ArrayList<Integer>>> res = mapa.getPercursoFrom(prateleira);
        Comparator<Triplo<Integer,Double,ArrayList<Integer>>> cmp = Comparator.comparing(Triplo::getSecond);
        Collections.sort(res,cmp);

        for (Robot robot : this.robotsLivres.values()) {
            if(robot.getIdPrateleiraOndeEstou() == prateleira.getId()) {
                Integer idRobot = robot.getId();
                Robot newRobot = robot.clone();
                this.robotsLivres.remove(idRobot);
                Percurso reverted= (new Percurso(prateleira,prateleira)).reverse(prateleira);
                newRobot.setPercurso(reverted);
                newRobot.setIdPalete(idPalete);
                this.robotsBuscar.put(idRobot,newRobot);
                this.robots.update(idRobot,idPalete,robot.getIdPrateleiraOndeEstou(),robot.getIdPrateleiraOndeEstou(),false); // porque onde está já é o local
                return idRobot;
            }
        }

        for (Triplo<Integer,Double,ArrayList<Integer>> r: res) {
            Integer p = r.getFirst();
            for (Robot robot : this.robotsLivres.values()) {
                if(robot.getIdPrateleiraOndeEstou() == p) {
                    Integer idRobot = robot.getId();
                    Robot newRobot = robot.clone();
                    this.robotsLivres.remove(idRobot);
                    Percurso reverted= (new Percurso(prateleira,r)).reverse(prateleira);
                    newRobot.setPercurso(reverted);
                    newRobot.setIdPalete(idPalete);
                    this.robotsBuscar.put(idRobot,newRobot);
                    this.robots.update(idRobot,idPalete,robot.getIdPrateleiraOndeEstou(),newRobot.getIdPrateleiraOndeVou(),false);
                    return idRobot;
                }
            }
        }

        return null;
    }

    public boolean robotPegaPalete(Integer idRobot, Integer idPrateleira, Percurso percurso) {
        if(! robotsBuscar.containsKey(idRobot)) return false;
        Robot r = robotsBuscar.get(idRobot);
        r.setPercurso(percurso);
        robots.update(idRobot, idPrateleira,r.getIdPrateleiraOndeVou(),true);
        robotsBuscar.remove(idRobot);
        robotsEntrega.put(idRobot,r);
        return true;
    }

    public Integer robotPousaPalete(Integer idRobot) {
        if(! robotsEntrega.containsKey(idRobot)) return null;
        Robot r = robotsEntrega.get(idRobot);
        Integer prateleiraOndeEstou = r.getIdPrateleiraOndeVou();
        r.setIdPrateleiraOndeEstou(prateleiraOndeEstou);
        r.setIdPalete(null);
        r.setPercurso(null);
        robotsEntrega.remove(idRobot);
        robotsLivres.put(idRobot,r);
        robots.update(idRobot,null, prateleiraOndeEstou,null,true);
        return idRobot;
    }

    public Integer robotLivre(Integer idRobot, Integer idPalete, Prateleira prateleira) {
        if(! robotsEntrega.containsKey(idRobot)) return null;
        Robot r = robotsEntrega.get(idRobot);
        r.setIdPalete(null); r.setPercurso(null);
        robotsEntrega.remove(idRobot);
        robotsLivres.put(idRobot,r);
        robots.update(idRobot,null,null,true);
        return robotBusca(idPalete,prateleira);
    }

    public TreeMap<Integer,Robot> getRobotsLivres() {
        return this.robotsLivres;
    }

    public TreeMap<Integer,Robot> getRobotsBuscar() {
        return this.robotsBuscar;
    }

    public TreeMap<Integer,Robot> getRobotsEntrega() {
        return this.robotsEntrega;
    }

    public Integer escolhaRobotLivre() {
       return null;
    }

    @Override
    public boolean notificacaoDeRecolha(Integer idRobot) {

        return false;
    }

    @Override
    public boolean notificacaoEntrega(Integer idRobot) {
        return false;
    }

    public Robot getRobot(Integer idRobot) {
        if ( robotsLivres.containsKey(idRobot) ) {
            return robotsLivres.get(idRobot);
        } else if (robotsBuscar.containsKey(idRobot)) {
            return robotsBuscar.get(idRobot);
        } else {
            return robotsEntrega.get(idRobot);
        }
    }
}
