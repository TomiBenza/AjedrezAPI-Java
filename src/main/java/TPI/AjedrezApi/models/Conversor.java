package TPI.AjedrezApi.models;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class Conversor {

    public Posicion convertirStringAPosicion(String posicion){
        if(posicion.length()!=2) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"El formato de la posición debe ser letra-número. Ej: 'A1'");

        posicion=posicion.toUpperCase();
        int letraConvertida;
        int numeroConvertido;

        char letra = posicion.charAt(0);
        char numero = posicion.charAt(1);

        if(!Character.isLetter(letra) || !Character.isDigit(numero)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"El formato de la posición debe ser letra-número. Ej: 'A1'");
        }
        numeroConvertido = Integer.parseInt(String.valueOf(numero)) -1;

        switch (letra) {
            case 'A' -> letraConvertida = 0;
            case 'B' -> letraConvertida = 1;
            case 'C' -> letraConvertida = 2;
            case 'D' -> letraConvertida = 3;
            case 'E' -> letraConvertida = 4;
            case 'F' -> letraConvertida = 5;
            case 'G' -> letraConvertida = 6;
            case 'H' -> letraConvertida = 7;
            default -> letraConvertida = -1;
        }

        if(letraConvertida==-1) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,letra+" no es una letra válida");
        if(numeroConvertido<0 || numeroConvertido>7 ) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,numeroConvertido + 1 + " no es un número válido");
        return new Posicion(letraConvertida,numeroConvertido);
    }

    public String convertirPosicionAString(Posicion posicion){
        String letra = "";
        switch (posicion.getX()) {
            case 0 -> letra = "A";
            case 1 -> letra = "B";
            case 2 -> letra = "C";
            case 3 -> letra = "D";
            case 4 -> letra = "E";
            case 5 -> letra = "F";
            case 6 -> letra = "G";
            case 7 -> letra = "H";
        }
        return letra + (posicion.getY()+1);
    }
}
