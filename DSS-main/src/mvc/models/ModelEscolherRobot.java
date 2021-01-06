package mvc.models;

import Sistema.CamadaDeDados.Palete;
import Sistema.CamadaDeDados.Percurso;
import Sistema.CamadaDeDados.Prateleira;
import Sistema.CamadaDeDados.Robot;
import Sistema.LogicaDeNegocios.SSGesMapaFacade;
import Sistema.LogicaDeNegocios.SSGesPaletesPrateleirasFacade;
import Sistema.LogicaDeNegocios.SSGesRobotsFacade;

import java.util.Collection;

public class ModelEscolherRobot implements Model{

    private SSGesRobotsFacade robotsFacade;
    private SSGesMapaFacade mapaFacade;
    private SSGesPaletesPrateleirasFacade paletesPrateleirasFacade;
    private static ModelEscolherRobot singleton = new ModelEscolherRobot();

    public static ModelEscolherRobot getInstance(){return singleton;}

    private ModelEscolherRobot(){
        this.mapaFacade = SSGesMapaFacade.getInstance();
        this.paletesPrateleirasFacade = SSGesPaletesPrateleirasFacade.getInstance();
        this.robotsFacade = SSGesRobotsFacade.getInstance();
    }

    public Collection<Integer> getRobotsLivresID() {
        return robotsFacade.getRobotsLivres().keySet();
    }

    public Collection<Integer> getRobotsBuscarID() {
        return robotsFacade.getRobotsBuscar().keySet();
    }

    public Collection<Integer> getRobotsEntregaID() { return robotsFacade.getRobotsEntrega().keySet();}

    public boolean pegouPalete(Integer robotId) {
        Integer prateleiraRevervadaId = paletesPrateleirasFacade.reserva();
        if(prateleiraRevervadaId == null) return false;

        Robot robot = robotsFacade.getRobot(robotId);
        Integer idPrateleiraOndeAgoraEstou = robot.getIdPrateleiraOndeVou();
        Prateleira prateleira = paletesPrateleirasFacade.getPrateleira(idPrateleiraOndeAgoraEstou);
        Prateleira prateleiraReservada = paletesPrateleirasFacade.getPrateleira(prateleiraRevervadaId);
        paletesPrateleirasFacade.robotPegaPalete(robot.getIdPalete(), robotId);
        Percurso pass = new Percurso(prateleiraReservada, mapaFacade.getPercursoFromTo(prateleira,prateleiraReservada));
        robotsFacade.robotPegaPalete(robotId,idPrateleiraOndeAgoraEstou,pass);
        return true;
    }

    public boolean deixouPalete(Integer robotId) {
        Robot robot = robotsFacade.getRobot(robotId);
        paletesPrateleirasFacade.robotPousaPalete(robot.getIdPalete(),robot.getIdPrateleiraOndeVou());
        robotsFacade.robotPousaPalete(robotId);
        Integer newPalete = paletesPrateleirasFacade.getNewPalete(robotId);
        if(newPalete != null) {
            robotsFacade.robotBusca(newPalete,paletesPrateleirasFacade.getPrateleira(SSGesPaletesPrateleirasFacade.ENTRADA));
        }
        return true;
    }
}
