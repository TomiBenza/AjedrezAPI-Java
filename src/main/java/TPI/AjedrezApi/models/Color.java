package TPI.AjedrezApi.models;

public enum Color {
    BLANCAS,NEGRAS;

    public Color opposite() {
        if (this == BLANCAS) {
            return NEGRAS;
        } else {
            return BLANCAS;
        }
    }
}
