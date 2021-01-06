package Sistema.CamadaDeDados;

public class Descarga implements Cloneable ,IdentificavelInteger  {
    private String def;
    private Integer id;

    public Descarga(){
        def = null;
        id = null;
    }

    public Descarga(String def) {
        this.id = null;
        this.def = def;
    }

    public Descarga(Integer id, String def) {
        this.def = def;
        this.id = id;
    }

    public Descarga(Descarga d) {
        this(d.id, d.def);
    }

    public void setDef(String def) {
        this.def = def;
    }

    public String getDef() {
        return this.def;
    }

    @Override
    public Descarga clone() {
        return new Descarga(this);
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
    public String toString() {
        return "Sistema.CamadaDeDados.Descarga\n" +
                "id: " + this.getId() + "\n" +
                "def: " + this.getDef();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        Descarga teste = (Descarga) obj;
        return def == teste.def && id == teste.id;
    }
}
