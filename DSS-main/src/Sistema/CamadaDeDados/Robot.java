package Sistema.CamadaDeDados;

public class Robot implements IdentificavelInteger, Comparable<Robot> {

    private Integer idRobot;
    private Integer idPalete;
    private Integer idPrateleiraOndeEstou;
    private Percurso percurso;


    public Robot() {
        this.idRobot = null;
        this.idPalete = null;
        this.idPrateleiraOndeEstou = null;
        this.percurso = new Percurso();
    }

    public Robot(Integer idRobot, Integer idPalete, Integer idPrateleiraOndeEstou) {
        this.idRobot = idRobot;
        this.idPalete = idPalete;
        this.idPrateleiraOndeEstou = idPrateleiraOndeEstou;
        this.percurso = new Percurso();;
    }

    public Robot(Integer idRobot, Integer idPalete, Integer idPrateleiraOndeEstou, Percurso percurso) {
        this.idRobot = idRobot;
        this.idPalete = idPalete;
        this.idPrateleiraOndeEstou = idPrateleiraOndeEstou;
        this.percurso = (percurso != null ? percurso.clone() : null);
    }

    public Robot(Robot r, Percurso percurso) {
        this(r.getId(),r.getIdPalete(),r.getIdPrateleiraOndeVou(),(percurso == null ? null : percurso.clone()));
    }

    public Robot(Robot r) {
        this(r.getId(),r.getIdPalete(),r.getIdPrateleiraOndeEstou(),r.getPercurso());
    }

    public Integer getIdPalete() {
        return this.idPalete;
    }

    public void setIdPalete(Integer idPalete) {
        this.idPalete = idPalete;
    }

    public void setId(Integer id) {
        this.idRobot = id;
    }

    public Integer getId() {
        return this.idRobot;
    }

    public void setIdPrateleiraOndeEstou(Integer idPrateleiraOndeEstou) {
        this.idPrateleiraOndeEstou = idPrateleiraOndeEstou;
    }

    public Integer getIdPrateleiraOndeEstou() {
        return this.idPrateleiraOndeEstou;
    }


    public Percurso getPercurso() {return (this.percurso == null ? null : this.percurso.clone());}

    public void setPercurso(Percurso percurso) {
        if(percurso == null) this.percurso = null;
        else this.percurso = new Percurso(percurso);
    }

    public Integer getIdPrateleiraOndeVou() {
        Integer res = null;
        Percurso p = this.getPercurso();
        if(p == null) return null;
        else {return p.getFinish().getId();}

    }

    @Override
    public Robot clone() {
        return new Robot(this);
    }


    @Override
    public int compareTo(Robot o) {
        return this.getId().compareTo(o.getId());
    }
}
