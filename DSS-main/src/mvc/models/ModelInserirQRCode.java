package mvc.models;

import Sistema.CamadaDeDados.Descarga;
import Sistema.CamadaDeDados.Duplo;
import Sistema.LogicaDeNegocios.SSGesDescargasFacade;
import Sistema.LogicaDeNegocios.SSGesPaletesPrateleirasFacade;
import Sistema.LogicaDeNegocios.SSGesQRLeitoresFacade;
import Sistema.LogicaDeNegocios.SSGesRobotsFacade;

import java.util.Collection;

public class ModelInserirQRCode implements Model{

    private SSGesQRLeitoresFacade qrLeitoresFacade;
    private SSGesDescargasFacade descargasFacade;
    private SSGesPaletesPrateleirasFacade paletesPrateleirasFacade;
    private SSGesRobotsFacade robotsFacade;
    private static ModelInserirQRCode singleton = new ModelInserirQRCode();

    public static ModelInserirQRCode getInstance(){return singleton;}

    private ModelInserirQRCode(){
        this.qrLeitoresFacade = SSGesQRLeitoresFacade.getInstance();
        this.descargasFacade = SSGesDescargasFacade.getInstance();
        this.paletesPrateleirasFacade = SSGesPaletesPrateleirasFacade.getInstance();
        this.robotsFacade = SSGesRobotsFacade.getInstance();
    }

    public void notFinished(Integer leitorId) {
        Descarga d = qrLeitoresFacade.finish(leitorId);
        descargasFacade.returnNotSet(d.getId());
    }

    public void finished(Integer leitorId, String qrCode) {
        Descarga d = qrLeitoresFacade.finish(leitorId);
        Integer newPalete = paletesPrateleirasFacade.criaPalete(qrCode,d.getId());

        if(newPalete != null) {
            Integer isRobotAvailable = robotsFacade.robotBusca(newPalete,paletesPrateleirasFacade.getPrateleira(SSGesPaletesPrateleirasFacade.ENTRADA));
            if(isRobotAvailable != null) {
                paletesPrateleirasFacade.getNewPalete(isRobotAvailable);
            }
        }
    }

}
