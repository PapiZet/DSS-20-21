package Sistema.LogicaDeNegocios;

public interface ISSGesRobots {


    Integer escolhaRobotLivre();
    //boolean pedidoRecolha(String idRobot, String idPalete, Sistema.CamadaDeDados.Percurso percurso);
    boolean notificacaoDeRecolha(Integer idRobot);
    //void setStateRobot(String idRobot, String idPalete, Sistema.CamadaDeDados.Percurso percurso, boolean estragado);
    //localizacaoGrafo localObstruido(String idRobot);
    boolean notificacaoEntrega(Integer idRobot);

}
