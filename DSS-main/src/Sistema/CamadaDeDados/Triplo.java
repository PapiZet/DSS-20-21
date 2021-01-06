package Sistema.CamadaDeDados;

public class Triplo<Q,E,K> {
    private Q first;
    private E second;
    private K third;

    public Triplo(){
        this.first = null;
        this.second = null;
        this.third = null;
    }

    public Triplo(Q first, E second, K third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public Triplo(Triplo<Q,E,K> d) {
        this(d.first,d.second,d.third);
    }

    public Q getFirst() {
        return this.first;
    }

    public E getSecond() {
        return this.second;
    }

    public K getThird() {return this.third;}

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        Triplo teste = (Triplo) obj;
        return first.equals(teste.first) && second.equals(teste.second) && third.equals(teste.third);
    }
}
