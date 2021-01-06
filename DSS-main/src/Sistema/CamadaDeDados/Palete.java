package Sistema.CamadaDeDados;

public class Palete implements IdentificavelInteger {

    private Integer idPalete;
    private String qrCode;
    private Integer idPrateleira;

    public Palete(){
        this.idPalete = null;
        this.qrCode = null;
        this.idPrateleira = null;
    }

    public Palete(Integer idPalete, String qrCode, Integer idPrateleira){
        this.idPalete = idPalete;
        this.qrCode = qrCode;
        this.idPrateleira = idPrateleira;
    }

    public Palete (String qrCode, Integer idPrateleira) {
        this.idPalete = null;
        this.qrCode = qrCode;
        this.idPrateleira = idPrateleira;
    }

    public Palete(Palete palete) {
        this.idPalete = palete.getId();
        this.qrCode = palete.getQrCode();
        this.idPrateleira = palete.getIdPrateleira();
    }

    public void setQrCode(String qrCode) {this.qrCode = qrCode;}

    public String getQrCode() {return this.qrCode;}

    public void setIdPrateleira(Integer idPrateleira) {this.idPrateleira = idPrateleira;}

    public Integer getIdPrateleira() {return this.idPrateleira;}

    @Override
    public void setId(Integer id) {
        this.idPalete = id;
    }

    @Override
    public Integer getId() {
        return this.idPalete;
    }
}
