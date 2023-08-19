package TPI.AjedrezApi.models.Piezas;
import TPI.AjedrezApi.models.Movimiento;
import TPI.AjedrezApi.models.Posicion;
import TPI.AjedrezApi.models.Tablero;


public class PiezaVacia extends Pieza{
    public PiezaVacia() {
        super(null,null,null);
    }
    public PiezaVacia(Posicion pos) {
        super(pos,null,null);
    }

    @Override
    public String getSymbol() {
        return "＿";
    }

    @Override
    public boolean movimientoValido(Movimiento mov, Tablero tablero) {
        return false;
    }

}
