package mvc.models;

import Sistema.CamadaDeDados.Descarga;
import Sistema.CamadaDeDados.Duplo;
import Sistema.CamadaDeDados.QRLeitor;
import Sistema.CamadaDeDados.Robot;
import Sistema.LogicaDeNegocios.SSGesPaletesPrateleirasFacade;
import Sistema.LogicaDeNegocios.SSGesQRLeitoresFacade;
import Sistema.LogicaDeNegocios.SSGesDescargasFacade;
import java.util.Collection;

public class ModelLeitorQRCode implements Model{

    private SSGesQRLeitoresFacade qrLeitoresFacade;
    private SSGesDescargasFacade descargasFacade;
    private SSGesPaletesPrateleirasFacade paletesPrateleirasFacade;
    private static ModelLeitorQRCode singleton = new ModelLeitorQRCode();

    public static ModelLeitorQRCode getInstance(){return singleton;}

    private ModelLeitorQRCode(){
        this.qrLeitoresFacade = SSGesQRLeitoresFacade.getInstance();
        this.descargasFacade = SSGesDescargasFacade.getInstance();
    }

    public Collection<Integer> getLeitores() {
        return qrLeitoresFacade.getNotBeingUsed();
    }

    public Integer addLeitorQRC (){
        return qrLeitoresFacade.add().getId();
    }

    public String getDetalhes (Integer idLeitor) {
        Descarga descarga = descargasFacade.pollFirst();
        if(descarga == null) return null;

        Duplo<Boolean,String> res = qrLeitoresFacade.refresh(idLeitor,descarga);
        if(res.getFirst()) {
            return res.getSecond();
        } else {
            descargasFacade.returnNotSet(descarga.getId());
            return res.getSecond();
        }
    }

    public boolean leuQRCode(Integer leitorId, String qrCode) {
        Descarga descarga = qrLeitoresFacade.finish(leitorId);
        descargasFacade.removeAceites(descarga.getId());
        paletesPrateleirasFacade.criaPalete(qrCode,descarga.getId());

        return true;
    }
}
