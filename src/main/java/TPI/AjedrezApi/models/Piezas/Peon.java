package TPI.AjedrezApi.models.Piezas;
import TPI.AjedrezApi.models.*;

public class Peon extends Pieza {


    public Peon(Posicion posicion, Color color, Tipo tipo) {
        super(posicion, color, tipo);
    }

    @Override
    public String getSymbol() {

        if(getColor() == Color.BLANCAS){return "♙";
        }
        else return "♟";

    }

    public boolean esPrimerMovimiento(Movimiento movimiento){
        if(getColor() == Color.BLANCAS){
            return movimiento.getFrom().getX() == 1;
        }
        else return movimiento.getFrom().getX() == 6;
    }
    @Override
    public boolean movimientoValido(Movimiento mov, Tablero tablero) {
        Posicion from = mov.getFrom();
        Posicion to = mov.getTo();

        int rowDiff = Math.abs(to.getX() - from.getX());
        int colDiff = Math.abs(to.getY() - from.getY());

        // Verificar el movimiento hacia adelante
        if (getColor() == Color.BLANCAS) {
            if (to.getX() < from.getX()) {
                return false;
            }
        } else {
            if (to.getX() > from.getX()) {
                return false;
            }
        }

        // Movimiento en diagonal para captura
        if (colDiff == 1 && rowDiff == 1) {

            // Verificar si hay una pieza enemiga en la casilla de destino
            Pieza piezaAtacada = tablero.getPosPieza(to);
            if (piezaAtacada.getTipo() != null && piezaAtacada.getColor() != getColor()) {
                return true;
            }
        }

        // Movimiento hacia adelante
        if (colDiff == 0 && rowDiff == 1) {
            // Verificar si la casilla de destino está vacía
            Pieza targetPiece = tablero.getPosPieza(to);
            if (targetPiece.getTipo() == null) {
                return true;
            }

        }

        // Movimiento inicial de dos casillas hacia adelante
        if (esPrimerMovimiento(mov) && colDiff == 0 && rowDiff == 2) {
            // Verificar si las casillas intermedias están vacías
            int middleRow = (from.getX() + to.getX()) / 2;
            Pieza middlePiece = tablero.getPosPieza(new Posicion(middleRow, from.getY()));
            return middlePiece.getTipo() == null;
        }
        return false;

    }

}
