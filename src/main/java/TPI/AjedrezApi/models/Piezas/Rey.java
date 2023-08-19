package TPI.AjedrezApi.models.Piezas;
import TPI.AjedrezApi.models.*;

public class Rey extends Pieza{
    public Rey(Posicion posicion, Color color, Tipo tipo) {
        super(posicion, color, tipo);
    }

    @Override
    public String getSymbol() {
        if(getColor() == Color.BLANCAS){
            return "♔";
        }
        else return "♚";
    }

    @Override
    public boolean movimientoValido(Movimiento mov, Tablero tablero) {
        Pieza piezaAtacada = tablero.getPosPieza(mov.getTo());

        Posicion from = mov.getFrom();
        Posicion to = mov.getTo();

        int rowDiff = Math.abs(to.getX() - from.getX());
        int colDiff = Math.abs(to.getY() - from.getY());

        if(rowDiff == 1 && colDiff == 1){
            return piezaAtacada == null || piezaAtacada.getColor() != this.getColor();

        }
        else if(rowDiff== 0 && colDiff == 1){
            return piezaAtacada == null || piezaAtacada.getColor() != this.getColor();
        }
        else if(rowDiff == 1 && colDiff == 0){
            return piezaAtacada == null || piezaAtacada.getColor() != this.getColor();
        }
        else return false;
    }

}
