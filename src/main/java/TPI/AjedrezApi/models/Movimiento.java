package TPI.AjedrezApi.models;

public class Movimiento {
    private Posicion from;
    private Posicion to;

    public Posicion getFrom() {
        return from;
    }

    public void setFrom(Posicion from) {
        this.from = from;
    }

    public Posicion getTo() {
        return to;
    }

    public void setTo(Posicion to) {
        this.to = to;
    }

    public Movimiento(Posicion from, Posicion to){
        this.from=from;
        this.to=to;
    }
}
