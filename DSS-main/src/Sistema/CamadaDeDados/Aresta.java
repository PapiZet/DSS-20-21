package Sistema.CamadaDeDados;

public class Aresta implements LocalizacaoGrafo{

    private Integer idVertice1;
    private Integer idVertice2;
    private Integer idAresta;
    private Double tamanho;


    public Aresta(){
        this.idVertice1 = null;
        this.idVertice2 = null;
        this.tamanho = null;
    }

    public Aresta(Integer idAresta, Integer idVertice1, Integer idVertice2, Double tamanho){
        this.idAresta = idAresta;
        this.idVertice1 = idVertice1;
        this.idVertice2 = idVertice2;
        this.tamanho = tamanho;
    }

    public Aresta(Integer idAresta, Vertice vertice1, Vertice vertice2) {
        this.idAresta = idAresta;
        this.idVertice1 = vertice1.getId();
        this.idVertice2 = vertice2.getId();
        this.tamanho = Aresta.distance(vertice1,vertice2);
    }

    public Aresta(Aresta a){
        this(a.getId(), a.getIdVertice1(), a.getIdVertice2(), a.getTamanho());

    }

    public Integer getIdVertice1() {return this.idVertice1;}

    public void setIdVertice1(Integer idVertice1) {this.idVertice1 = idVertice1;}

    public Integer getIdVertice2() {return this.idVertice2;}

    public void setIdVertice2(Integer idVertice2) {this.idVertice2 = idVertice2;}

    public Double getTamanho() {return this.tamanho;}

    public void setTamanho(Double tamanho) {this.tamanho = tamanho;}


    @Override
    public LocalizacaoGrafo clone() {
        return new Aresta(this.getId(),this.getIdVertice1(), this.getIdVertice2(), this.getTamanho());
    }

    @Override
    public void setId(Integer idAresta) {
        this.idAresta = idAresta;
    }

    @Override
    public Integer getId() {
        return this.idAresta;
    }

    public static Double distance(Vertice v1, Vertice v2) {return Aresta.distance(v1.getX(),v1.getY(),v2.getX(),v2.getY());}

    public static Double distance(int x1, int y1, int x2, int y2)
    {
        // Calculating distance
        return Math.sqrt(Math.pow(x2 - x1, 2) +
                Math.pow(y2 - y1, 2) * 1.0);
    }

    public String toString(){
        return "Aresta (" + this.idAresta + ", " + this.idVertice1 + ", " + this.idVertice2 + ")";
    }

}
