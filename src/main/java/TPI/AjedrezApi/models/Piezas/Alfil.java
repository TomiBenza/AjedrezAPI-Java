package TPI.AjedrezApi.models.Piezas;
import TPI.AjedrezApi.models.*;


public class Alfil extends Pieza{
    public Alfil(Posicion posicion, Color color, Tipo tipo) {
        super(posicion, color, tipo);
    }

    @Override
    public String getSymbol() {
        if(getColor() == Color.BLANCAS){
            return "♗";
        }
        else return "♝";
    }

    @Override
    public boolean movimientoValido(Movimiento mov, Tablero tablero)
    {
        Posicion from = mov.getFrom();
        Posicion to = mov.getTo();

        int rowDiff = Math.abs(to.getX() - from.getX());
        int colDiff = Math.abs(to.getY() - from.getY());

        if (rowDiff == colDiff && rowDiff != 0) {
            int rowStep = (to.getX() > from.getX()) ? 1 : -1;
            int colStep = (to.getY() > from.getY()) ? 1 : -1;

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
            if (piezaAtacada != null && piezaAtacada.getColor() == this.getColor()) {
                // Hay una pieza del mismo color en la posición final, el movimiento no es válido
                return false;
            }

            // No hay piezas en el camino y la posición final no contiene una pieza del mismo color
            return true;
        }

        return false;
    }

}
