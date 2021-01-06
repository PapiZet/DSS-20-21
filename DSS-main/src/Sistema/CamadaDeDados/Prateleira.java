package Sistema.CamadaDeDados;

public class Prateleira implements IdentificavelInteger, LocalizacaoFisica{

    private Integer idPrateleira;
    private Integer idAresta;
    private Integer max;
    private Double distancia;
    private Boolean lado;

    public Prateleira(){
        this.idPrateleira = null;
        this.max = null;
        this.distancia = null;
        this.lado = null;
        this.idAresta = null;
    }

    public Prateleira(Integer idPrateleira, Integer max, Double distancia, Boolean lado, Integer idAresta) {
        this.idPrateleira = idPrateleira;
        this.max = max;
        this.distancia = distancia;
        this.lado = lado;
        this.idAresta = idAresta;
    }

    public Prateleira(Prateleira prat) {
        this(prat.getId(),prat.getMax(), prat.getDistancia(), prat.getLado(),prat.getIdAresta());
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getMax() {
        return this.max;
    }

    public void setLado(Boolean lado) {
        this.lado = lado;
    }

    public Boolean getLado() {
        return this.lado;
    }

    @Override
    public void setDistancia(Double distancia) {
        this.distancia = distancia;
    }

    @Override
    public Double getDistancia() {
        return this.distancia;
    }

    @Override
    public Prateleira clone() {
        return new Prateleira(this);
    }

    @Override
    public void setIdAresta(Integer idAresta) {this.idAresta = idAresta;}

    @Override
    public Integer getIdAresta() {return this.idAresta;}

    @Override
    public void setId(Integer idPrateleira) {
        this.idPrateleira = idPrateleira;
    }

    @Override
    public Integer getId() {
        return this.idPrateleira;
    }
}
