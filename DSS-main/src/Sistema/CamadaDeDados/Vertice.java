package Sistema.CamadaDeDados;

public class Vertice implements LocalizacaoGrafo{

    private Integer idVertice;
    private int x;
    private int y;


    public Vertice(){
        this.idVertice = null;
        this.x = 0;
        this.y = 0;
    }

    public Vertice(Integer id, int x, int y){
        this.idVertice = id;
        this.x = x;
        this.y = y;
    }

    public Vertice(Vertice v){
        this.idVertice = v.getId();
        this.x = v.getX();
        this.y = v.getY();
    }

    public int getX(){
        return this.x;
    }

    public void setX(int x){
        this.x = x;
    }

    public int getY(){
        return this.y;
    }

    public void setY(int y){
        this.y = y;
    }

    @Override
    public LocalizacaoGrafo clone() {
        return new Vertice(this);
    }

    @Override
    public void setId(Integer id) {
        this.idVertice = id;
    }

    @Override
    public Integer getId() {
        return this.idVertice;
    }

    public String toString() {
        return "Vertice(" + this.idVertice + ", " + this.x + ", " + this.y + ")";
    }

}
