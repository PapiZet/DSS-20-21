package Sistema.CamadaDeDados;

public class QRLeitor implements IdentificavelInteger, Comparable<QRLeitor>, Cloneable {

    private Integer id;
    private Descarga lendo;

    public QRLeitor()
    {
        this.id = null;
        this.lendo = null;
    }

    public QRLeitor(Integer id)
    {
        this.id = id;
        this.lendo = null;
    }

    public QRLeitor(Integer id, Descarga lendo) {
        this.id = id;
        this.lendo = lendo.clone();
    }

    public QRLeitor(QRLeitor g)
    {
        this.id = g.getId();
        this.lendo = g.lendo.clone();
    }

    public Descarga getLendo() {
        if(this.lendo == null) return null;
        return this.lendo.clone();
    }

    public void setLendo(Descarga d) {
        if(d == null) this.lendo = null;
        else this.lendo = d.clone();
    }

    public Descarga removeLendo() {
        Descarga buff = this.getLendo();
        this.setLendo(null);
        return buff;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public QRLeitor clone() {
        return new QRLeitor(this);
    }

    @Override
    public int compareTo(QRLeitor o) {
        return this.getId()-o.getId();
    }
}
