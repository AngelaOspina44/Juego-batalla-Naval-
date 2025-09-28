package juegobthp;

public class Jugador {
    protected Tablero tablero;

    public Jugador(Tablero tablero) {
        this.tablero = tablero;
    }

    public boolean disparar(Posicion posicion, Tablero tableroEnemigo) {
        return tableroEnemigo.recibirDisparo(posicion);
    }

    public Tablero getTablero() {
        return tablero;
    }
}
