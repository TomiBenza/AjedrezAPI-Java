package TPI.AjedrezApi.models.Piezas;

import TPI.AjedrezApi.models.*;

public class Caballo extends Pieza {
    public Caballo(Posicion posicion, Color color, Tipo tipo) {
        super(posicion, color, tipo);
    }

    @Override
    public String getSymbol() {
        if(getColor() == Color.BLANCAS){
            return "♘";
        }
        else return "♞";
    }

    @Override
    public boolean movimientoValido(Movimiento mov, Tablero tablero) {
        Posicion from = mov.getFrom();
        Posicion to = mov.getTo();

        int rowDiff = Math.abs(to.getX() - from.getX());
        int colDiff = Math.abs(to.getY() - from.getY());

        if ((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2)) {
            // Verificar si hay una pieza en la posición final
            Pieza piezaAtacada = tablero.getPosPieza(to);
            if (piezaAtacada != null && piezaAtacada.getColor() == this.getColor()) {
                // Hay una pieza del mismo color en la posición final, el movimiento no es válido
                return false;
            }

            // El movimiento del caballo es válido
            return true;
        }
        return false;
    }

}
