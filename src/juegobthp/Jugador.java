package juegobthp;

public class Jugador {
    protected Tablero tablero;

    public Jugador(Tablero tablero) {
        this.tablero = tablero;
    }

    public void disparar(int x, int y, Tablero tableroEnemigo) {
        // Método genérico
    }

    public Tablero getTablero() {
        return tablero;
    }
}
