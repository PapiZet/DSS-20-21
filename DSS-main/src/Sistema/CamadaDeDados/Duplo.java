package Sistema.CamadaDeDados;

public class Duplo<Q,E> {
    private Q first;
    private E second;

    public Duplo(Q first, E second) {
        this.first = first;
        this.second = second;
    }

    public Duplo(Duplo<Q,E> d) {
        this(d.first,d.second);
    }

    public Duplo() {
    }

    public Q getFirst() {
        return this.first;
    }

    public E getSecond() {
        return this.second;
    }

    public void setFirst(Q first) {this.first = first;}

    public void setSecond(E second) {this.second = second;}

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        Duplo teste = (Duplo) obj;
        return first.equals(teste.first) && second.equals(teste.second);
    }
}
