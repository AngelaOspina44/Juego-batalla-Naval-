package juegobthp;

public class JugadorHumano extends Jugador {
    public JugadorHumano(Tablero tablero) {
        super(tablero);
    }

    @Override
    public void disparar(int x, int y, Tablero tableroEnemigo) {
        tableroEnemigo.recibirDisparo(x, y);
    }
}
