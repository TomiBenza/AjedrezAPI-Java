package TPI.AjedrezApi.models;

import TPI.AjedrezApi.models.Piezas.*;
import TPI.AjedrezApi.models.Piezas.Pieza;
import jakarta.annotation.Nullable;
import jakarta.persistence.GeneratedValue;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class Tablero {
    private TPI.AjedrezApi.models.Piezas.Pieza[][] tablero;
    private List<String> historialMovimientos;
    private EstadoJuego estadoJuego;
    private Color colorGanador;
    private Color colorEnJaque;

    public Tablero() {
        tablero = new TPI.AjedrezApi.models.Piezas.Pieza[8][8];
        inicializarTablero();
        historialMovimientos = new ArrayList<>();
        estadoJuego=EstadoJuego.EN_CURSO;
    }
    public Tablero(Pieza[][] tableroCustom){
        tablero = tableroCustom;
        historialMovimientos = new ArrayList<>();
        estadoJuego=EstadoJuego.EN_CURSO;
    }

    public Pieza[][] getTablero() {
        return tablero;
    }

    public List<String> getHistorialMovimientos() {
        return historialMovimientos;
    }

    /**
     * Inicializador del tablero al comienzo de una nueva partida
     */
    private void inicializarTablero() {
        // Inicializar las piezas blancas
        tablero[0][0] = new Torre(new Posicion(0, 0), Color.BLANCAS, Tipo.TORRE);
        tablero[0][1] = new Caballo(new Posicion(0, 1), Color.BLANCAS, Tipo.CABALLO);
        tablero[0][2] = new Alfil(new Posicion(0, 2), Color.BLANCAS, Tipo.ALFIL);
        tablero[0][3] = new Reina(new Posicion(0, 3), Color.BLANCAS, Tipo.REINA);
        tablero[0][4] = new Rey(new Posicion(0, 4), Color.BLANCAS, Tipo.REY);
        tablero[0][5] = new Alfil(new Posicion(0, 5), Color.BLANCAS, Tipo.ALFIL);
        tablero[0][6] = new Caballo(new Posicion(0, 6), Color.BLANCAS, Tipo.CABALLO);
        tablero[0][7] = new Torre(new Posicion(0, 7), Color.BLANCAS, Tipo.TORRE);
        for (int i = 0; i < 8; i++) {
            tablero[1][i] = new Peon(new Posicion(1, i), Color.BLANCAS, Tipo.PEON);
        }

        // Inicializar las piezas negras
        tablero[7][0] = new Torre(new Posicion(7, 0), Color.NEGRAS, Tipo.TORRE);
        tablero[7][1] = new Caballo(new Posicion(7, 1), Color.NEGRAS, Tipo.CABALLO);
        tablero[7][2] = new Alfil(new Posicion(7, 2), Color.NEGRAS, Tipo.ALFIL);
        tablero[7][3] = new Rey(new Posicion(7, 3), Color.NEGRAS, Tipo.REY);
        tablero[7][4] = new Reina(new Posicion(7, 4), Color.NEGRAS, Tipo.REINA);
        tablero[7][5] = new Alfil(new Posicion(7, 5), Color.NEGRAS, Tipo.ALFIL);
        tablero[7][6] = new Caballo(new Posicion(7, 6), Color.NEGRAS, Tipo.CABALLO);
        tablero[7][7] = new Torre(new Posicion(7, 7), Color.NEGRAS, Tipo.TORRE);
        for (int i = 0; i < 8; i++) {
            tablero[6][i] = new Peon(new Posicion(6, i), Color.NEGRAS, Tipo.PEON);
        }
        // Rellenar el resto del tablero con espacios vacíos
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                tablero[i][j] = new PiezaVacia();
            }
        }
    }

    /**
    Cargar tablero con posiciones de las fichas de la BD
    */
    /*
    public Tablero cargarTablero(List<Pieza> piezas){
        int x,y;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tablero[i][j]=new PiezaVacia();
            }
        }
        for (TPI.AjedrezApi.models.Piezas.Pieza p:piezas) {
            //Validacion si esta viva...
            x=p.getPosicion().getX();
            y=p.getPosicion().getY();
            tablero[x][y]=p;
        }
        return this;
    }*/

    /**
     * Metodo encargado de mostrar el tablero
     * @return string del tablero
     *
     */
    public String verTablero() {
        StringBuilder r = new StringBuilder();
        r.append("＃ ");
        for (int j = 0; j < 8; j++) {
            r.append((char) ('１' + j)).append(" ");
        }
        r.append("\n<br>");

        for (int i = 0; i < 8; i++) {
            r.append((char) ('Ａ' + i)).append(" ");

            for (int j = 0; j < 8; j++) {
                r.append(tablero[i][j].getSymbol()).append(" ");
            }
            r.append("\n<br>");
        }
        return r.toString();
    }

    /**
     * Metodo encargado de obtener la pieza (y si existe alguna) en la posición especificada
     * @param posicion posicion de la que se quiere obtener la información
     * @return la ficha en cuestión, o null, en caso que no exista ninguna en la posición
     *
     */
    public Pieza getPosPieza(Posicion posicion) {
        return tablero[posicion.getX()][posicion.getY()];
    }

    /**
     * Este metodo se encarga de gestionar el movimiento de una pieza, asi tambien como agregarlo al historial
     * o detectar si hubo una promoción (peón)
     * @param pieza      Pieza en cuestion a mover
     * @param movimiento Objeto que tiene las coords de origen de la pieza, junto con las coords a donde se moverá
     * @return true si el movimiento es valido para la pieza
     *
     *
     */

    public boolean moverPieza(Pieza pieza, Movimiento movimiento){

        // Verifica si es un enroque
        if(movimientoEnroque(pieza,movimiento)) {
            pieza.setPosicion(movimiento.getTo());
            setEstadoJuego(pieza);
            addMovimientoHistorial(movimiento,pieza,tablero[movimiento.getTo().getX()][movimiento.getTo().getY()],false);

            return true;
        }
        // Checkea si la pieza está intentando moverse a un lugar erróneo
        if(!pieza.movimientoValido(movimiento,this)) return false;



        // Si no es enroque, mueve la pieza normalmente:
        // 1. Modifica la posicion inicial de la pieza a vacía
        tablero[movimiento.getFrom().getX()][movimiento.getFrom().getY()] = new PiezaVacia(movimiento.getFrom());

        // 2. Se guarda en variable la pieza que se encuentra en la posicion en que se moverá la otra pieza
        Pieza piezaAtacada = tablero[movimiento.getTo().getX()][movimiento.getTo().getY()];

        // 3. Se reemplaza la pieza atacada (o vacia) por la pieza movida y se setea la nueva posicion de la pieza movida
        tablero[movimiento.getTo().getX()][movimiento.getTo().getY()] = pieza;
        pieza.setPosicion(movimiento.getTo());

        // 3.1 Verificacion de una posible promoción del Peón
        boolean esPromocion = false;
        if(verificarPromocion(pieza,movimiento)){
            tablero[movimiento.getTo().getX()][movimiento.getTo().getY()]
                        =new Reina(pieza.getPosicion(),pieza.getColor(),Tipo.REINA);
            esPromocion=true;
        }

        // 4. Establece el estado del juego, si es jaque, jaque mate o está en curso
        setEstadoJuego(pieza);
        // 5. Añade el movimiento al historial
        addMovimientoHistorial(movimiento,pieza,piezaAtacada,esPromocion);

        return true;
    }

    /**
     * Se encarga de comprobar y gestionar un enroque
     * @param pieza a mover
     * @param movimiento
     * @return true si se realizó
     *
     */
    public boolean movimientoEnroque(Pieza pieza, Movimiento movimiento){
        // Se verifica que el movimiento de la pieza sea de enroque
        if(!verificarEnroque(pieza,movimiento)) return false;

        // Devuelve si pudo realizar el enroque
        return realizarEnroque(pieza, movimiento);
    }

    /**
     * Metodo encargado de mover el rey y torre para realizar el enroque
     * @param rey
     * @param movimiento
     * @return si efectivamente se pudieron mover
     *
     */
    public boolean realizarEnroque(Pieza rey, Movimiento movimiento){
        if(rey.getColor()==Color.BLANCAS)
        {
            if(movimiento.getTo().getY()==0)
            {
                tablero[movimiento.getFrom().getX()][movimiento.getFrom().getY()] = new PiezaVacia();
                tablero[0][2] = new Rey(new Posicion(0, 2), rey.getColor(), Tipo.REY);

                tablero[movimiento.getTo().getX()][movimiento.getTo().getY()] = new PiezaVacia();
                tablero[0][3] = new Torre(new Posicion(0,3),rey.getColor(), Tipo.TORRE);
                return true;
            }
            else if (movimiento.getTo().getY()==7)
            {
                tablero[movimiento.getFrom().getX()][movimiento.getFrom().getY()] = new PiezaVacia();
                tablero[0][6] = new Rey(new Posicion(0, 6), rey.getColor(), Tipo.REY);
                tablero[movimiento.getTo().getX()][movimiento.getTo().getY()] = new PiezaVacia();
                tablero[0][5] = new Torre(new Posicion(0,5),rey.getColor(), Tipo.TORRE);
                return true;
            }
        }
        else if (rey.getColor()==Color.NEGRAS)
        {
            if(movimiento.getTo().getY()==0)
            {
                tablero[movimiento.getFrom().getX()][movimiento.getFrom().getY()] = new PiezaVacia();
                tablero[7][1] = new Rey(new Posicion(7, 1), rey.getColor(), Tipo.REY);

                tablero[movimiento.getTo().getX()][movimiento.getTo().getY()] = new PiezaVacia();
                tablero[7][2] = new Torre(new Posicion(7,2),rey.getColor(), Tipo.TORRE);
                return true;
            }
            else if (movimiento.getTo().getY()==7)
            {
                tablero[movimiento.getFrom().getX()][movimiento.getFrom().getY()] = new PiezaVacia();
                tablero[7][5] = new Rey(new Posicion(7, 5), rey.getColor(), Tipo.REY);
                tablero[movimiento.getTo().getX()][movimiento.getTo().getY()] = new PiezaVacia();
                tablero[7][4] = new Torre(new Posicion(7,4),rey.getColor(), Tipo.TORRE);
                return true;
            }
        }
        setEstadoJuego(rey);
        return false;
    }
    /**
     * Verifica si un peon llegó al otro extremo del tablero
     * @param pieza Objeto pieza a verificar si es de tipo Peon
     * @param movimiento Movimiento realizado por la pieza
     * @return bool
     *
     */
    public boolean verificarPromocion(Pieza pieza, Movimiento movimiento){
       if( pieza.getTipo()==Tipo.PEON){
           if(pieza.getColor()==Color.BLANCAS && movimiento.getTo().getX()==7) return true;
           else return pieza.getColor() == Color.NEGRAS && movimiento.getTo().getX() == 0;
       }
       return false;
    }

    /**
     * Añade movimientos a la lista de movimientos del tablero
     * @param movimiento Movimiento realizado
     * @param piezaMovida Objeto pieza que fue movido
     * @param piezaComida Objeto pieza que fue comida (si es que hubo)
     * @param huboPromocion booleano que permite añadir un mensaje extra indicando la promoción de un Peón
     *
     */

    public void addMovimientoHistorial(Movimiento movimiento, Pieza piezaMovida, Pieza piezaComida, boolean huboPromocion){
        Conversor conversor = new Conversor();
        String resultado = piezaMovida.toString()+", "+conversor.convertirPosicionAString(movimiento.getFrom())+" -> ";
        resultado+=conversor.convertirPosicionAString(movimiento.getTo());

        if(piezaComida.getTipo()!=null) resultado+=" come: "+piezaComida;
        if(huboPromocion) resultado+="<br>¡Peón "+ piezaMovida.getColor()+" promocionó y ahora es una REINA!";
        switch (estadoJuego){
            case JAQUE -> resultado+=" le hace JAQUE al rey de "+piezaMovida.getColor().opposite();
            case TERMINADO -> resultado+="<br>JAQUE MATE, Ganan las: "+getColorGanador()+". Fin del juego";
            case EMPATE -> resultado+="<br>EMPATE por tablas. Fin del juego";
        }
        historialMovimientos.add(resultado);

    }

    public void setEstadoJuego(Pieza pieza){
        if( esJaque(pieza.getColor().opposite()) ) {
            if (esJaqueMate(pieza.getColor().opposite())) {
                //JAQUE MATE
                estadoJuego = EstadoJuego.TERMINADO;
                colorGanador = pieza.getColor();
            }
            else{
                //JAQUE: "¡Es Jaque, tu Rey está en peligro. ¡Muévelo!"
                estadoJuego = EstadoJuego.JAQUE;
                colorEnJaque = pieza.getColor().opposite();
            }
            return;
        }
        else if(esTablas()){
            estadoJuego = EstadoJuego.EMPATE;
            return;
        }
        if(colorEnJaque==pieza.getColor() && esJaque(pieza.getColor()) ){
            // Si el juego ya estaba en jaque, y sigue en jaque, fin del juego
            estadoJuego=EstadoJuego.TERMINADO;
            colorGanador = pieza.getColor().opposite();
            return;
        }
        estadoJuego=EstadoJuego.EN_CURSO;
    }

    private boolean esTablas() {
        List<Pieza> piezasBlancas = new ArrayList<>();
        List<Pieza> piezasNegras = new ArrayList<>();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if(tablero[x][y].getTipo()!=null){
                    if(tablero[x][y].getColor()==Color.BLANCAS) piezasBlancas.add(tablero[x][y]);
                    else piezasNegras.add(tablero[x][y]);
                }

            }
        }
        return evaluarTablas(piezasBlancas,piezasNegras);


    }
    public boolean evaluarTablas(List<Pieza> piezasBlancas,List<Pieza> piezasNegras){

        // Verificar si solo quedan los reyes:
        if(piezasBlancas.size()==1 && piezasNegras.size()==1){
            return true;
        }
        // Verificar si hay combinaciones específicas de piezas que conducen a tablas
        if (piezasBlancas.size() == 2 && piezasNegras.size() == 1) {
            Pieza piezaBlanca1 = piezasBlancas.get(0);
            Pieza piezaBlanca2 = piezasBlancas.get(1);
            Pieza piezaNegra = piezasNegras.get(0);

            if ((piezaBlanca1 instanceof Alfil || piezaBlanca1 instanceof Caballo || piezaBlanca1 instanceof Rey)
                    && (piezaBlanca2 instanceof Alfil || piezaBlanca2 instanceof Caballo || piezaBlanca2 instanceof Rey)
                    && piezaNegra instanceof Rey) {
                return true;
            }
        }

        if (piezasBlancas.size() == 1 && piezasNegras.size() == 2) {
            Pieza piezaBlanca = piezasBlancas.get(0);
            Pieza piezaNegra1 = piezasNegras.get(0);
            Pieza piezaNegra2 = piezasNegras.get(1);

            return (piezaNegra1 instanceof Alfil || piezaNegra1 instanceof Caballo || piezaNegra1 instanceof Rey)
                    && (piezaNegra2 instanceof Alfil || piezaNegra2 instanceof Caballo || piezaNegra2 instanceof Rey)
                    && piezaBlanca instanceof Rey;
        }

        return false;
    }

    public EstadoJuego getEstadoJuego(){
        return estadoJuego;
    }
    public Color getColorGanador(){
        return colorGanador;
    }

    public boolean esJaque(Color color) {
        // Buscar la posición del rey del color dado
        Posicion posicionRey = buscarPosicionRey(color);

        // Verificar si alguna pieza del oponente puede capturar al rey en el siguiente movimiento
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                TPI.AjedrezApi.models.Piezas.Pieza pieza = tablero[i][j];
                if (pieza.getColor() != color && pieza.getTipo() != null) {
                    Movimiento movimiento = new Movimiento(new Posicion(i, j), posicionRey);
                    if (pieza.movimientoValido(movimiento,this))
                    {

                        return true; // El rey está en jaque
                    }
                }
            }
        }
        return false; // El rey no está en jaque
    }

    public boolean esJaqueMate(Color color) {
        List<Posicion> listaAdyacente = new ArrayList<>();
        Posicion rey = buscarPosicionRey(color);
        Pieza reyPieza = tablero[rey.getX()][rey.getY()];

        if (esJaque(color)) {
            // Generar las posiciones adyacentes al rey
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    Posicion adyacente = new Posicion(rey.getX() + i, rey.getY() + j);
                    if (esPosicionValidaJaqueMate(adyacente)) {
                        listaAdyacente.add(adyacente);
                    }
                }
            }

            Iterator<Posicion> iterator = listaAdyacente.iterator();
            while (iterator.hasNext()) {
                Posicion adyacente = iterator.next();
                Movimiento movimiento = new Movimiento(rey, adyacente);
                if (!reyPieza.movimientoValido(movimiento, this)) {
                    iterator.remove();
                }
            }

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    Pieza pieza = tablero[i][j];
                    if (pieza.getColor() != color && pieza.getTipo() != null) {
                        for (int x = 0; x < listaAdyacente.size(); x++) {
                            Movimiento movimiento = new Movimiento(new Posicion(i, j), listaAdyacente.get(x));
                            if (pieza.movimientoValido(movimiento, this)) {
                                listaAdyacente.remove(x);
                            }
                        }
                    }
                }
            }


        }
        return listaAdyacente.isEmpty(); // No hay jaque, el rey no está en jaque mate
    }

    private boolean esPosicionValidaJaqueMate(Posicion posicion) {
        int x = posicion.getX();
        int y = posicion.getY();
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    // Buscar la posición del rey del color especificado en el tablero
    private Posicion buscarPosicionRey(Color color) {

        Posicion pos = null;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                TPI.AjedrezApi.models.Piezas.Pieza pieza = tablero[i][j];
                if (pieza.getTipo()==Tipo.REY && pieza.getColor() == color) {
                    pos = pieza.getPosicion();
                    break;
                }
            }
        }
        return pos;
    }

    /**
     * Enroque, verifica que la pieza que se esta moviendo sea de tipo rey, luego verifica que el rey este en su posicion inicial,
     * despues verifica que se quiera mover a la pocision inicial de la torre y que la torre no se haya movido de ahi,
     * por ultimo verifica si los lugares entre el rey y la torre estan vacios, si esto se cumple retorna true, si no false.
     * @param pieza
     * @param movimiento
     * @return true es es un enroque válido
     *
     */
    public boolean verificarEnroque(Pieza pieza, Movimiento movimiento) {
        if (pieza.getTipo() != Tipo.REY) return false;

        if (pieza.getColor() == Color.BLANCAS) {
            if (movimiento.getFrom().getX() == 0 && movimiento.getFrom().getY() == 4)
            {
                if (movimiento.getTo().getX() == 0 && movimiento.getTo().getY() == 0 && tablero[0][0].getTipo() == Tipo.TORRE)
                    return (tablero[0][1].getTipo() == null && tablero[0][2].getTipo() == null && tablero[0][3].getTipo() == null);
                else if (movimiento.getTo().getX() == 0 && movimiento.getTo().getY() == 7 && tablero[0][7].getTipo() == Tipo.TORRE)
                    return  (tablero[0][5].getTipo() == null && tablero[0][6].getTipo() == null);
            }
        }
        if (pieza.getColor() == Color.NEGRAS) {
            if (movimiento.getFrom().getX() == 7 && movimiento.getFrom().getY() == 3)
                if (movimiento.getTo().getX() == 7 && movimiento.getTo().getY() == 0 && tablero[7][0].getTipo() == Tipo.TORRE)
                    return tablero[7][1].getTipo() == null && tablero[7][2].getTipo() == null;
                else if (movimiento.getTo().getX() == 7 && movimiento.getTo().getY() == 7 && tablero[7][7].getTipo() == Tipo.TORRE)
                    return tablero[7][4].getTipo() == null && tablero[7][5].getTipo() == null && tablero[7][6].getTipo() == null;
        }

        return false;
    }

}
