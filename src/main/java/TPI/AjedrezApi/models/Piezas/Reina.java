package TPI.AjedrezApi.models.Piezas;
import TPI.AjedrezApi.models.*;

public class Reina extends Pieza {
    public Reina(Posicion posicion, Color color, Tipo tipo) {
        super(posicion, color, tipo);
    }

    @Override
    public String getSymbol() {
        if(getColor() == Color.BLANCAS){
            return "♕";
        }
        else return "♛";
    }

    @Override
    public boolean movimientoValido(Movimiento mov, Tablero tablero) {
        Posicion from = mov.getFrom();
        Posicion to = mov.getTo();

        int rowDiff = Math.abs(to.getX() - from.getX());
        int colDiff = Math.abs(to.getY() - from.getY());

        // Verificar si es horizontal, vertical o diagonal
        if ((rowDiff > 0 && colDiff == 0) || (rowDiff == 0 && colDiff > 0) || (rowDiff == colDiff)) {
            // Verificar si hay piezas en el camino
            int rowStep = 0;
            int colStep = 0;

            // Determinar el paso en filas y columnas
            if (rowDiff > 0) {
                rowStep = (to.getX() - from.getX()) / rowDiff;
            }
            if (colDiff > 0) {
                colStep = (to.getY() - from.getY()) / colDiff;
            }

            int currentRow = from.getX() + rowStep;
            int currentCol = from.getY() + colStep;

            while (currentRow != to.getX() || currentCol != to.getY()) {
                if (tablero.getPosPieza(new Posicion(currentRow, currentCol)).getTipo() != null) {
                    // Hay una pieza en el camino, el movimiento no es válido
                    return false;
                }
                currentRow += rowStep;
                currentCol += colStep;
            }

            // Verificar si hay una pieza en la posición final
            Pieza piezaAtacada = tablero.getPosPieza(to);
            // Hay una pieza del mismo color en la posición final, el movimiento no es válido
            return piezaAtacada == null || piezaAtacada.getColor() != this.getColor();

            // No hay piezas en el camino y la posición final no contiene una pieza del mismo color
        }

        return false;
    }

}
