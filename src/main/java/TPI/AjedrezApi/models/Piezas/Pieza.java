package TPI.AjedrezApi.models.Piezas;

import TPI.AjedrezApi.models.*;

public abstract class Pieza {
    private Color color;
    private Posicion posicion;
    private Estado estado;
    private Tipo tipo;



    @Override
    public String toString() {
        return this.tipo+" de "+this.color;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Pieza(Posicion posicion, Color color,Tipo tipo) {
        this.posicion = posicion;
        this.estado = Estado.Activo;
        this.color=color;
        this.tipo=tipo;
    }

    /**
     * @return Se obtiene el simbolo de tablero de la ficha en cuestión
     */
    public abstract String getSymbol();

    /**
     * Este metodo se encargará de verificar si el movimiento de una ficha es valido
     * @param mov Movimiento que hará la ficha
     * @return true en caso de ser válido
     *
     */
    public abstract boolean movimientoValido(Movimiento mov, Tablero tablero);


    /**
     * Este metodo sirve para cambiar el estado de una pieza, se utilizará para establecer como muerta a una pieza
     * @param estado .
     *
     */
    public void cambiarEstado(Estado estado){
        this.estado=estado;
    }

    //crea una copia de la pieza en una nueva posicion
    public Pieza(Pieza otraPieza, Posicion nuevaPosicion) {
        this.posicion = nuevaPosicion;
        this.estado = otraPieza.estado;
        this.color = otraPieza.color;
        this.tipo = otraPieza.tipo;
    }

}
