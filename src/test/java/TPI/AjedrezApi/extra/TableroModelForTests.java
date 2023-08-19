package TPI.AjedrezApi.extra;

import TPI.AjedrezApi.models.Piezas.Pieza;
import TPI.AjedrezApi.models.Piezas.PiezaVacia;
import TPI.AjedrezApi.models.Posicion;

public class TableroModelForTests {
    private Pieza[][] tablero;

    public TableroModelForTests(){
        tablero = new Pieza[8][8];
        iniciarTablero();
    }
    public void agregarPieza(Pieza pieza, Posicion posicion){
        tablero[posicion.getX()][posicion.getY()] = pieza;
    }
    public Pieza[][] getTablero(){
        return tablero;
    }
    public void iniciarTablero(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tablero[i][j] = new PiezaVacia();
            }
        }
    }
}
