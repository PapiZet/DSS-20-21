package Sistema.CamadaDeDados;

public interface LocalizacaoFisica extends Cloneable{

    public void setIdAresta(Integer idAresta);

    public Integer getIdAresta();

    public void setDistancia(Double distancia);

    public Double getDistancia();

    public LocalizacaoFisica clone();

}
